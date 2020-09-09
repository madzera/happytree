package com.miuey.happytree.core.atp;

class ATPPipelineAttributes {

	private ATPPipelineAttributes() {}
	
	/*
	 * Extraction Phase.
	 */
	static final String NODES = "nodes";
	static final String NODES_MAP = "nodesMap";
	static final String NODES_PARENT_MAP = "nodesParentMap";
	
	/*
	 * Initialization Phase.
	 */
	static final String SESSION_ID = "sessionId";
	static final String MANAGER = "manager";
	static final String NODE_TYPE = "nodeType";
	static final String ELEMENTS = "elements";
	
	/*
	 * Binding Phase.
	 */
	static final String TREE = "tree";
}
