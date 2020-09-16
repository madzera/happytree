package com.miuey.happytree.core;

class TreePipelineAttributes {
	
	private TreePipelineAttributes() {}
	
	
	/*
	 * Keys validations for Session validations.
	 */
	static final String SESSION_ID = "sessionId";
	static final String SESSION_TYPE = "sessionType";
	
	/*
	 * Keys validations for Element validations.
	 */
	static final String SOURCE_ELEMENT = "sourceElement";
	static final String TARGET_ELEMENT = "targetElement";
	static final String OPERATION = "operation";
	static final String CURRENT_SESSION = "session";
	
	/*
	 * Keys validations for Session initializations.
	 */
	static final String NODES = "nodes";
	static final String MANAGER = "manager";
}
