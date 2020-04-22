package org.vaadin.activiti.simpletravel;

//import com.vaadin.Application;
//import com.vaadin.service.ApplicationContext.TransactionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.activiti.simpletravel.alexdp.ui.LoginPanel;
import org.vaadin.activiti.simpletravel.smartlabs.core.services.AlfrescoRepoService;
import org.vaadin.activiti.simpletravel.smartlabs.core.services.Registry;
import org.vaadin.activiti.simpletravel.smartlabs.ui.MainScreen;
import org.vaadin.activiti.simpletravel.smartlabs.ui.authentication.AccessControl;
import org.vaadin.activiti.simpletravel.smartlabs.ui.authentication.BasicAccessControl;
import org.vaadin.activiti.simpletravel.smartlabs.ui.authentication.LoginScreen;
import org.vaadin.activiti.simpletravel.smartlabs.ui.authentication.LoginScreen.LoginListener;
import org.vaadin.activiti.simpletravel.ui.MainWindow;
import org.vaadin.activiti.simpletravel.ui.dashboard.components.DashboardViewComponent;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Viewport;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
/**
 * @href https://examples.javacodegeeks.com/enterprise-java/vaadin/vaadin-spring-example/
 * @href https://github.com/dominic-simplan/vaadin-widgetset/blob/master/test-ui/src/main/java/com/example/test/MyUI.java
 */
@SpringUI
@SpringViewDisplay
@Theme("reindeer")
@Viewport("user-scalable=no,initial-scale=1.0")
//@Theme("simpletheme")
//@Widgetset("org.smartlabs.AlfrescoRepoWidgetset")
@Widgetset("org.vaadin.activiti.simpletravel.ui.SimpleTravelWidgetset")
//@Configurable
public class SimpleTravelApplicationUI extends UI { //MOD 4535992 extends Application implements TransactionListener {

//    @Autowired
//    protected transient CurrentUserFactoryBean currentUserFactoryBean;

//	@Override
//	protected void init(VaadinRequest vaadinRequest) {
////		Responsive.makeResponsive(this);
//		setLocale(vaadinRequest.getLocale());
//		getPage().setTitle("Alfresco Repo Login");
//		setSizeFull();
//		//setContent(new LoginPanel());
//		//TODO understand why launch exception
//		//setTheme("simpletravel");
//		addWindow(new MainWindow());
//	}
	
	
	private AccessControl accessControl = new BasicAccessControl();

	@Autowired
	AlfrescoRepoService repoService;
	
	@Override
	protected void init(VaadinRequest vaadinRequest) {
		Responsive.makeResponsive(this);
		setLocale(vaadinRequest.getLocale());
		getPage().setTitle("Alfresco Repo Login");
		
		Registry.getInstance().getAlfrescoRepoService();
		
		if (!accessControl.isUserSignedIn()) {
			setContent(new LoginScreen(accessControl, new LoginListener() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 3574029619376034417L;

				@Override
				public void loginSuccessful() {
					showMainView();
				}
			}));
		} else {
			showMainView();
		}

	}

	protected void showMainView() {
		addStyleName(ValoTheme.UI_WITH_MENU);
//		setContent(new MainScreen(SimpleTravelApplicationUI.this));
//		getNavigator().navigateTo(getNavigator().getState());
		addWindow(new MainWindow());
		//showDashboardView();
//		DashboardViewComponent dashboardView = new DashboardViewComponent();
//        //dashboardView.addListener(this);
//        setContent((DashboardViewComponent) dashboardView);
//        dashboardView.startProcessEnginePolling();
	}

	public static SimpleTravelApplicationUI get() {
		return (SimpleTravelApplicationUI) UI.getCurrent();
	}

	public AccessControl getAccessControl() {
		return accessControl;
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
