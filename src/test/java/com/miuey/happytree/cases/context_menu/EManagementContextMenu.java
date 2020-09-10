package com.miuey.happytree.cases.context_menu;

import com.miuey.happytree.annotation.Id;
import com.miuey.happytree.annotation.Parent;
import com.miuey.happytree.annotation.Tree;

@Tree
public enum EManagementContextMenu {

	MENU_1("label_key_1", "management", "update"), MENU_2("label_key_2", "transfer", "update"),
	MENU_3("label_key_3", "authorization", "update"),

	SUBMENU_1("label_key_4", "embarkation", "update", MENU_1.name()),
	SUBMENU_2("label_key_5", "disembarkation", "update", MENU_1.name()),
	SUBMENU_3("label_key_6", "departure_guide", "update", MENU_1.name()),
	SUBMENU_4("label_key_7", "load", "update", MENU_2.name()),
	SUBMENU_5("label_key_8", "unload", "update", MENU_2.name());

	@Id
	private String id;
	private String labelKey;
	private String operationResource;
	private String operationType;
	@Parent
	private String parentId;

	private EManagementContextMenu(String labelKey, String operationResource,
			String operationType, String parentId) {
		this.id = this.name();
		this.labelKey = labelKey;
		this.operationResource = operationResource;
		this.operationType = operationType;
		this.parentId = parentId;
	}

	private EManagementContextMenu(String labelKey, String operationResource,
			String operationType) {
		this.id = this.name();
		this.labelKey = labelKey;
		this.operationResource = operationResource;
		this.operationType = operationType;
	}

	public String getLabelKey() {
		return labelKey;
	}

	public String getOperationResource() {
		return operationResource;
	}

	public String getParentId() {
		return parentId;
	}

	public String getOperationType() {
		return operationType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setOperationResource(String operationResource) {
		this.operationResource = operationResource;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
}
