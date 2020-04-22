package org.vaadin.activiti.simpletravel.smartlabs.ui.about;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.Version;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class AboutView extends VerticalLayout implements View {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3634947779299827960L;
	public static final String VIEW_NAME = "About";

    public AboutView() {
        CustomLayout aboutContent = new CustomLayout("aboutview");
        aboutContent.setStyleName("about-content");


        aboutContent.addComponent(
                new Label(FontAwesome.INFO_CIRCLE.getHtml()
                        + " This application is using Vaadin "
                        + Version.getFullVersion(), ContentMode.HTML), "info");

        setSizeFull();
        setStyleName("about-view");
        addComponent(aboutContent);
        setComponentAlignment(aboutContent, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }

}
