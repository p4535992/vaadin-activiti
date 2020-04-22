package org.vaadin.activiti.simpletravel.smartlabs.core.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.FileableCmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.Tree;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.data.RepositoryCapabilities;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.CapabilityContentStreamUpdates;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.vaadin.activiti.simpletravel.smartlabs.core.exception.RepoServiceException;
import org.vaadin.activiti.simpletravel.smartlabs.core.services.AlfrescoRepoService;

@Service
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class AlfrescoRepoServiceImpl implements AlfrescoRepoService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8988579508779851000L;

	private static final String READ_TIMEOUT = "60000";

	private static final String CONNECTION_TIMEOUT = "10000";

	@Value("${alfrescoRepoHost}")
	private String alfrescoRepoHost;

	@Override
	public Session login(String user, String password) {
		Map<String, String> parameters = new HashMap<String, String>();

		parameters.put(SessionParameter.USER, user);
		parameters.put(SessionParameter.PASSWORD, password);
		parameters.put(SessionParameter.CONNECT_TIMEOUT, CONNECTION_TIMEOUT);
		parameters.put(SessionParameter.READ_TIMEOUT, READ_TIMEOUT);
		parameters.put(SessionParameter.ATOMPUB_URL, alfrescoRepoHost);
		parameters.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());

		SessionFactoryImpl factoryImpl = SessionFactoryImpl.newInstance();

		List<Repository> repositories = factoryImpl.getRepositories(parameters);

		Repository repository = repositories.get(0);

		Session createSession = repository.createSession();

		return createSession;
	}

	@Override
	public List<Tree<FileableCmisObject>> getObjectTreeStructure(Session session, int depth) {
		List<Tree<FileableCmisObject>> descendants = null;
		RepositoryCapabilities capabilities = session.getRepositoryInfo().getCapabilities();
		if (capabilities != null) {
			if (capabilities.isGetDescendantsSupported()) {
				return session.getRootFolder().getDescendants(depth);
			} else if (capabilities.isGetFolderTreeSupported()) {
				return session.getRootFolder().getFolderTree(depth);
			}
		}
		return descendants;
	}

	@Override
	public Folder createNewFolder(Session session, Folder parent, String folderName) {
		Map<String, String> newFolderProps = new HashMap<String, String>();
		newFolderProps.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
		newFolderProps.put(PropertyIds.NAME, folderName);
		Folder newFolder = parent.createFolder(newFolderProps);

		return newFolder;
	}

	@Override
	public Document createNewDocument(Session session, Folder parent, String filename, String mimetype, InputStream content) throws IOException {

		ContentStream contentStream = session.getObjectFactory().createContentStream(filename, content.available(), mimetype, content);

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
		properties.put(PropertyIds.NAME, filename);

		Document doc = parent.createDocument(properties, contentStream, VersioningState.MAJOR);

		return doc;
	}

	@Override
	public ContentStream readDocument(Session session, String documnetID) {
		Document doc = (Document) session.getObject(documnetID);
		if (doc != null) {
			ContentStream contentStream = doc.getContentStream();
			return contentStream;
		} else
			return null;
	}

	@Override
	public String readDocumentAsString(ContentStream contentStream) throws IOException {
		return getContentAsString(contentStream);
	}

	@Override
	public Document updateDocument(Session session, String documentId, String mimetype, String fileName, InputStream content) throws IOException, RepoServiceException {
		Document storedDoc = (Document) session.getObject(documentId);

		Map<String, String> properties = new HashMap<String, String>();
		properties.put(PropertyIds.NAME, fileName);

		storedDoc.updateProperties(properties, true);

		if (!session.getRepositoryInfo().getCapabilities().getContentStreamUpdatesCapability().equals(CapabilityContentStreamUpdates.ANYTIME)) {
			throw new RepoServiceException("update without checkout not supported in this repository");
		}

		ContentStream contentStream = session.getObjectFactory().createContentStream("test3.txt", content.available(), mimetype, content);

		Document updatedDoc = storedDoc.setContentStream(contentStream, true);
		return updatedDoc;

	}

	@Override
	public void deleteFolder(Session session, Folder folder) {
		folder.deleteTree(true, UnfileObject.DELETE, true);
		//session.delete(folder, true);
	}

	private String getContentAsString(ContentStream stream) throws IOException {
		StringBuilder sb = new StringBuilder();
		Reader reader = new InputStreamReader(stream.getStream(), "UTF-8");

		try {
			final char[] buffer = new char[4 * 1024];
			int b;
			while (true) {
				b = reader.read(buffer, 0, buffer.length);
				if (b > 0) {
					sb.append(buffer, 0, b);
				} else if (b == -1) {
					break;
				}
			}
		} finally {
			reader.close();
		}

		return sb.toString();
	}

	@Override
	public void deleteDocument(Session session, String documnetId) {
		CmisObject object = session.getObject(documnetId);
		session.delete(object, true);
	}
}
