package org.vaadin.activiti.simpletravel.smartlabs.core.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.FileableCmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.Tree;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.vaadin.activiti.simpletravel.smartlabs.core.exception.RepoServiceException;

public interface AlfrescoRepoService {

	Session login(String user, String password);
	
	List<Tree<FileableCmisObject>> getObjectTreeStructure(Session session,int depth);
	
	Folder createNewFolder(Session session, Folder parent, String folderName);
	
	Document createNewDocument(Session session, Folder parent,String mimetype, String filename, InputStream content) throws IOException;
	
	ContentStream  readDocument(Session session, String documnetId);
	
	String readDocumentAsString(ContentStream contentStream) throws IOException;
	
	Document updateDocument(Session session,String documentId, String mimetype,String fileName,InputStream content) throws IOException, RepoServiceException;
	
	void deleteDocument(Session session,String documnetId);
	
	void deleteFolder(Session session, Folder folder);

}
