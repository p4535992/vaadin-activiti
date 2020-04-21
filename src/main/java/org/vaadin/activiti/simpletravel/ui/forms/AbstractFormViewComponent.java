package org.vaadin.activiti.simpletravel.ui.forms;

import com.github.peholmst.mvp4vaadin.AbstractViewComponent;
import com.github.peholmst.mvp4vaadin.Presenter;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

import org.vaadin.activiti.simpletravel.domain.validation.ValidationException;

public abstract class AbstractFormViewComponent<V extends FormView, P extends Presenter<V>> extends AbstractViewComponent<V, P> implements FormView {

    @Override
    public void showOptimisticLockingError() {
        //MOD 4535992
    	//getWindow().showNotification("The information has been altered by another user.", "Please reload the information and try again");
    	Notification.show("The information has been altered by another user.", "Please reload the information and try again",Type.HUMANIZED_MESSAGE);
    }

    @Override
    public void setValidationError(ValidationException error) {
        // No operation, subclasses may override.
    }

    @Override
    public void clearValidationError() {
        // No operation, subclasses may override.
    }       
    
}
