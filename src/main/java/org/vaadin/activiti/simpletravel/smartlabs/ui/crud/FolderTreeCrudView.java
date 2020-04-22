package org.vaadin.activiti.simpletravel.smartlabs.ui.crud;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.event.DataBoundTransferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.event.dd.acceptcriteria.And;
import com.vaadin.event.dd.acceptcriteria.ClientSideCriterion;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.AbstractSelect.AbstractSelectTargetDetails;
import com.vaadin.ui.AbstractSelect.AcceptItem;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Tree.TargetItemAllowsChildren;
import com.vaadin.ui.Tree.TreeDragMode;
import com.vaadin.ui.VerticalLayout;

public class FolderTreeCrudView extends CssLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = -355259828752846992L;

	public static final String VIEW_NAME = "Repository";

	private Tree folderTree;


	private FolderTreeCrudLogic crudLogic = new FolderTreeCrudLogic(this);

	public FolderTreeCrudView() {
		setSizeFull();
		addStyleName("crud-view");


		folderTree = new Tree("Repository Tree", ExampleUtil.getHardwareContainer());
		folderTree.setItemCaptionPropertyId(ExampleUtil.hw_PROPERTY_NAME);
		folderTree.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		folderTree.addActionHandler(crudLogic);
		folderTree.addValueChangeListener(crudLogic);

		folderTree.setDragMode(TreeDragMode.NODE);
		folderTree.setDropHandler(new DropHandler() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -4072192670746973902L;

			@Override
			public AcceptCriterion getAcceptCriterion() {

				return new And(TargetItemAllowsChildren.get(), AcceptItem.ALL);
			}

			@Override
			public void drop(DragAndDropEvent dropEvent) {
				// criteria verify that this is safe
				DataBoundTransferable t = (DataBoundTransferable) dropEvent.getTransferable();
				Container sourceContainer = t.getSourceContainer();
				Object sourceItemId = t.getItemId();
				Item sourceItem = sourceContainer.getItem(sourceItemId);
				String name = sourceItem.getItemProperty("name").toString();
				String category = sourceItem.getItemProperty("category").toString();

				AbstractSelectTargetDetails dropData = ((AbstractSelectTargetDetails) dropEvent.getTargetDetails());
				Object targetItemId = dropData.getItemIdOver();

				// find category in target: the target node itself or its parent
				if (targetItemId != null && name != null && category != null) {
					String treeCategory = getTreeNodeName(folderTree, targetItemId);
					if (category.equals(treeCategory)) {
						// move item from table to category'
						Object newItemId = folderTree.addItem();
						folderTree.getItem(newItemId).getItemProperty(ExampleUtil.hw_PROPERTY_NAME).setValue(name);
						folderTree.setParent(newItemId, targetItemId);
						folderTree.setChildrenAllowed(newItemId, false);

						sourceContainer.removeItem(sourceItemId);
					} else {
						String message = name + " is not a " + treeCategory.toLowerCase().replaceAll("s$", "");
						new Notification(message, Notification.Type.WARNING_MESSAGE).show(Page.getCurrent());
					}
				}

			}
		});

		VerticalLayout barAndGridLayout = new VerticalLayout();
		barAndGridLayout.addComponent(folderTree);
		barAndGridLayout.setMargin(true);
		barAndGridLayout.setSpacing(true);
		barAndGridLayout.setSizeFull();

		barAndGridLayout.setStyleName("crud-main-layout");

		addComponent(barAndGridLayout);
		crudLogic.init();
	}

	private static String getTreeNodeName(Container.Hierarchical source, Object sourceId) {
		return (String) source.getItem(sourceId).getItemProperty(ExampleUtil.hw_PROPERTY_NAME).getValue();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		crudLogic.enter(event.getParameters());
	}

}
