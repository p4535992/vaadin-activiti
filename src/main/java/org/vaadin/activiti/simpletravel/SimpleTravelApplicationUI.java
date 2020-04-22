package org.vaadin.activiti.simpletravel;

//import com.vaadin.Application;
//import com.vaadin.service.ApplicationContext.TransactionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.activiti.simpletravel.alexdp.ui.LoginPanel;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Viewport;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.UI;
/**
 * @href https://examples.javacodegeeks.com/enterprise-java/vaadin/vaadin-spring-example/
 * @href https://github.com/dominic-simplan/vaadin-widgetset/blob/master/test-ui/src/main/java/com/example/test/MyUI.java
 */
@SpringUI
@SpringViewDisplay
@Theme("reindeer")
@Viewport("user-scalable=no,initial-scale=1.0")
//@Theme("simpletheme")
@Widgetset("org.smartlabs.AlfrescoRepoWidgetset")
//@Configurable
public class SimpleTravelApplicationUI extends UI{ //MOD 4535992 extends Application implements TransactionListener {

//    @Autowired
//    protected transient CurrentUserFactoryBean currentUserFactoryBean;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		Responsive.makeResponsive(this);
		setLocale(vaadinRequest.getLocale());
		getPage().setTitle("Alfresco Repo Login");
//		setSizeFull();
//		setContent(new LoginPanel());
		//TODO understand why launch exception
		//setTheme("simpletravel");
		//addWindow(new MainWindow());
	}

	//MOD 4535992
//    @Override
//    public void init() {
//        setTheme("simpletravel");
//        getContext().addTransactionListener(this);
//        setMainWindow(new MainWindow());
//    }
//
//    @Override
//    public void close() {
//        setUser(null);
//        getContext().removeTransactionListener(this);
//        ((MainWindow) getMainWindow()).destroy();
//        super.close();
//    }
//
//    @Override
//    public void transactionStart(Application application, Object transactionData) {
//        String username = (String) getUser();
//        currentUserFactoryBean.setCurrentUsername(username);
//    }
//
//    @Override
//    public void transactionEnd(Application application, Object transactionData) {
//        currentUserFactoryBean.setCurrentUsername(null);
//    }
	//END MOD 4535992
}
