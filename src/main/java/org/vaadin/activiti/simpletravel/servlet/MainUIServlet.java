package org.vaadin.activiti.simpletravel.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.vaadin.activiti.simpletravel.SimpleTravelApplicationUI;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

@WebServlet(urlPatterns = "/*", name = "MainUIServlet", asyncSupported = true)
@VaadinServletConfiguration(ui = SimpleTravelApplicationUI.class, productionMode = false)
public class MainUIServlet extends org.vaadin.artur.icepush.ICEPushServlet{//extends VaadinServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3272497779217338111L;
	
	private static final transient Logger logger = LoggerFactory.getLogger(MainUIServlet.class);
	
	
	/**
	 * @href https://stackoverflow.com/questions/51874785/gwt-spring-boot-autowired-is-not-working
	 */
    @Override
    public void init() throws ServletException {
         super.init();
         //getServletContext().setInitParameter("application", "org.vaadin.activiti.simpletravel.ui.SimpleTravelApplication");
         //getServletContext().setInitParameter("widgetset", "org.vaadin.activiti.simpletravel.ui.SimpleTravelWidgetset");
         SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, getServletContext());
    }
}
