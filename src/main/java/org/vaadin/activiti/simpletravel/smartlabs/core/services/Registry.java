package org.vaadin.activiti.simpletravel.smartlabs.core.services;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;


public class Registry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -527520591206774191L;
	
	
	private static final Registry singleton = new Registry();

	private Registry() {
	}
	
	
	@Autowired
	private AlfrescoRepoService alfrescoRepoService;
	
	
	public static Registry getInstance() {
		return singleton;
	}
	
	
	public AlfrescoRepoService getAlfrescoRepoService() {
		return getInstance().alfrescoRepoService;
	}
}
