package org.vaadin.activiti.simpletravel.service.impl;

import org.springframework.stereotype.Service;

@Service
public class MyEmployeeService {

	public String getEmailAddress(String userId) {
		return userId + "@vaadin-activiti.org";
	}
}
