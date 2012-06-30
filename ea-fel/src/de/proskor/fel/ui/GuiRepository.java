package de.proskor.fel.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.proskor.fel.Type;
import de.proskor.fel.container.EventTypeContainer;
import de.proskor.fel.event.EventType;
import de.proskor.fel.ui.MappingUtils.ObjectMapping;

public class GuiRepository {
	private static abstract class Handler {
		protected final Tree elementsTree;
		
//		protected final ObjectMapping<Type, TreeItem> mapping; 
		protected final ObjectMapping mapping; // extra keine Typ-Sicherheit, da ich sonst beim Exportieren der Listen diese Abschreiben m�sste - weil Java keinen Cast erlaubt. -.-
		
		public Handler(Tree elementsTree) {
			this.elementsTree = elementsTree;
			mapping = new ObjectMapping<Type, TreeItem>();
		}		
		
		public List<TreeItem> getTreeItems() {
			return mapping.getMappedObjectBs();
		}
		
		public TreeItem getTreeItemByType(Type type) {
			return (TreeItem) mapping.getObjectAMapping(type);
		}		
		
		public Type getTypeByTreeItem(TreeItem treeItem) {
			return (Type) mapping.getObjectBMapping(treeItem);
		}
		
		public boolean hasType(Type type) {
			return getTreeItemByType(type) != null;
		}
		
		public List<Type> getTypesByTreeItems(List<TreeItem> items) {
			ArrayList<Type> types = new ArrayList<Type>();
			
			for(TreeItem item : items)
				types.add(getTypeByTreeItem(item));
			
			return types;
		}		
		
		public List<TreeItem> getTreeItemsByTypes(List<Type> types) {
			ArrayList<TreeItem> items = new ArrayList<TreeItem>();
			
			for(Type type : types)
				items.add(getTreeItemByType(type));
			
			return items;
		}
		
		protected TreeItem addType(Type type) {
			TreeItem treeItem = createTreeItem(type);
			mapping.put(type, treeItem);
			
			return treeItem;
		}		
		
		protected TreeItem addSubType(TreeItem parent, Type subType) {
			TreeItem treeItem = createSubTreeItem(parent, subType);
			mapping.put(subType, treeItem);			
			return treeItem;
		}		
		
		protected String[] getTypeTreeItemText(Type type) {
			return new String[] {
					type.getName(),
					type.getDescription(),
					type.getAuthor(),
					type.getId()+" / "+type.getGuid()
				};
		}
		
		/**
		 * Calls {@link #getTypeTreeItemText(Type)} on default. Overwrite to change behavior.
		 * @param parent
		 * @param type
		 * @return
		 */
		protected String[] getTypeSubTreeItemText(TreeItem parent, Type subType) {
			return getTypeTreeItemText(subType);
		}
		
		private TreeItem createSubTreeItem(TreeItem parent, Type subType) {
			TreeItem item = new TreeItem(parent, 0);
			item.setText(getTypeSubTreeItemText(parent, subType));
			return item;
		}
		
		private TreeItem createTreeItem(Type type) {
			TreeItem item = new TreeItem(elementsTree, 0);
			item.setText(getTypeTreeItemText(type));
			return item;
		}
		
		public void clear() {
			elementsTree.clearAll(true);
			mapping.clear();
		}
	}
	
	public static class EventTypeContainerHandler extends Handler {
		public List<EventTypeContainer> getContainers() {
			return mapping.getMappedObjectAs();
		}
		
		public TreeItem addContainer(EventTypeContainer container) {
			TreeItem item = addType(container);
			return item;
		}
		
		public TreeItem addContainerAndSubContainers(EventTypeContainer parentContainer) {
			TreeItem parentItem = addContainer(parentContainer);
			addAllSubContainer(parentItem, parentContainer);
			
			return parentItem;
		}
		
		private void addAllSubContainer(TreeItem parentContainerItem, EventTypeContainer parentContainer) {
			for(EventTypeContainer subContainer : parentContainer.getChildren()) {
				TreeItem subContainerItem = addSubType(parentContainerItem, subContainer);
				addAllSubContainer(subContainerItem, subContainer);
			}
		}
		
		public EventTypeContainerHandler(Tree containerTree) {
			super(containerTree);
			clear();
		}
	}
	
	public static class EventTypesHandler extends Handler {
		public List<EventType> getEvents() {
			return mapping.getMappedObjectAs();
		}
		
		public EventTypesHandler(Tree eventsTree) {
			super(eventsTree);
		}
		
		public TreeItem addEvent(EventType event) {
			TreeItem item = addType(event);
			return item;
		}
	}
}
