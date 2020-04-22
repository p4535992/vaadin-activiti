package org.vaadin.activiti.simpletravel.smartlabs.ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;

/**
 * View shown when trying to navigate to a view that does not exist using
 * {@link com.vaadin.navigator.Navigator}.
 * 
 * 
 */
public class ErrorView extends VerticalLayout implements View {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2878672774879966895L;
	private Label explanation;

    public ErrorView() {
        setMargin(true);
        setSpacing(true);

        Label header = new Label("The view could not be found");
        header.addStyleName(Reindeer.LABEL_H1);
        addComponent(header);
        addComponent(explanation = new Label());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        explanation.setValue(String.format(
                "You tried to navigate to a view ('%s') that does not exist.",
                event.getViewName()));
       
        CssLayout menuPart = new CssLayout();
        menuPart.addStyleName(ValoTheme.MENU_PART);
        MenuBar logoutMenu = new MenuBar();
        logoutMenu.addItem("Logout", FontAwesome.SIGN_OUT, new Command() {

            /**
			 * 
			 */
			private static final long serialVersionUID = 4516476951398791590L;

			@Override
            public void menuSelected(MenuItem selectedItem) {
                VaadinSession.getCurrent().getSession().invalidate();
                Page.getCurrent().reload();
            }
        });

        logoutMenu.addStyleName("user-menu");
        menuPart.addComponent(logoutMenu);     
        addComponent(menuPart);
    }


}
