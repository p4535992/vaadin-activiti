package org.vaadin.activiti.simpletravel.smartlabs.ui.crud;

import org.vaadin.activiti.simpletravel.SimpleTravelApplicationUI;

//import org.apache.log4j.Logger;
//import org.smartlabs.MainUI;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.server.Page;



public class FolderTreeCrudLogic implements Handler, ValueChangeListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7169465854052992327L;
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(FolderTreeCrudLogic.class);
	
	private FolderTreeCrudView view;
	
    private static final Action ADD = new Action("Add item");
    private static final Action DELETE = new Action("Delete item");
    private static final Action[] actions = new Action[] { ADD, DELETE };

    public FolderTreeCrudLogic(FolderTreeCrudView folderTreeCrudView) {
        view = folderTreeCrudView;
    }

    public void init() {
/*        editProduct(null);
        // Hide and disable if not admin
        if (!MyUI.get().getAccessControl().isUserInRole("admin")) {
            view.setNewProductEnabled(false);
        }

        view.showProducts(DataService.get().getAllProducts());*/
    }

    public void cancelProduct() {
/*        setFragmentParameter("");
        view.clearSelection();
        view.editProduct(null);*/
    }

    /**
     * Update the fragment without causing navigator to change view
     */
    private void setFragmentParameter(String productId) {
        String fragmentParameter;
        if (productId == null || productId.isEmpty()) {
            fragmentParameter = "";
        } else {
            fragmentParameter = productId;
        }

        Page page = SimpleTravelApplicationUI.get().getPage();
        page.setUriFragment("!" + FolderTreeCrudView.VIEW_NAME + "/"
                + fragmentParameter, false);
    }

    public void enter(String productId) {
/*        if (productId != null && !productId.isEmpty()) {
            if (productId.equals("new")) {
                newProduct();
            } else {
                // Ensure this is selected even if coming directly here from
                // login
                try {
                    int pid = Integer.parseInt(productId);
                    Product product = findProduct(pid);
                    view.selectRow(product);
                } catch (NumberFormatException e) {
                }
            }
        }*/
    }

	@Override
	public Action[] getActions(Object target, Object sender) {
		return actions;
	}

	@Override
	public void handleAction(Action action, Object sender, Object target) {
	
		if(action == DELETE){
			logger.info("DELETE item");
		}else 
			if(action == ADD){
				logger.info("ADD item");
			}

	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		Property property = event.getProperty();
		
	}

   /* private Product findProduct(int productId) {
        return DataService.get().getProductById(productId);
    }

    public void saveProduct(Product product) {
        view.showSaveNotification(product.getProductName() + " ("
                + product.getId() + ") updated");
        view.clearSelection();
        view.editProduct(null);
        view.refreshProduct(product);
        setFragmentParameter("");
    }

    public void deleteProduct(Product product) {
        DataService.get().deleteProduct(product.getId());
        view.showSaveNotification(product.getProductName() + " ("
                + product.getId() + ") removed");

        view.clearSelection();
        view.editProduct(null);
        view.removeProduct(product);
        setFragmentParameter("");
    }

    public void editProduct(Product product) {
        if (product == null) {
            setFragmentParameter("");
        } else {
            setFragmentParameter(product.getId() + "");
        }
        view.editProduct(product);
    }

    public void newProduct() {
        view.clearSelection();
        setFragmentParameter("new");
        view.editProduct(new Product());
    }

    public void rowSelected(Product product) {
        if (MyUI.get().getAccessControl().isUserInRole("admin")) {
            view.editProduct(product);
        }
    }*/
}
