package org.vaadin.activiti.simpletravel.alexdp.ui;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.activiti.simpletravel.alexdp.model.Applicant;
import org.vaadin.activiti.simpletravel.alexdp.util.spring.SpringDependencyInjector;
import org.vaadin.activiti.simpletravel.alexdp.util.vaadin.WorkflowFormLayout;

import com.vaadin.ui.Panel;

@Configurable(autowire = Autowire.BY_TYPE)
@Transactional
public class ValidationFormPanel extends Panel {
	
	@Autowired
	private TaskService taskService;

	public ValidationFormPanel(Applicant applicant, String taskId) {
		super("Registration Form");
		SpringDependencyInjector.getInjector().inject(this);
		WorkflowFormLayout<Applicant> form = new WorkflowFormLayout<Applicant>(applicant);
		form.addButton("accept", false, new WorkflowFormLayout.Command<Applicant>() {
			@Override
			public void execute(Applicant bean) {
		        Map<String, Object> variables = new HashMap<String, Object>();
				variables.put("approved", true);
		        taskService.complete(taskId, variables);
		        getUI().setContent(new LoginPanel());
			}
		});
		form.addButton("reject", false, new WorkflowFormLayout.Command<Applicant>() {
			@Override
			public void execute(Applicant bean) {
		        Map<String, Object> variables = new HashMap<String, Object>();
				variables.put("approved", false);
		        taskService.complete(taskId, variables);
		        getUI().setContent(new LoginPanel());
			}
		});
		form.setReadOnly(true);
		setContent(form);
	}

	

}
