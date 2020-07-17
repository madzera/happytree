package com.miuey.happytree.core;

import java.util.Map;

/*
 * Internal class to send attributes for validations chains.
 */
public class TreePipeline {

	private Map<String, Object> attributes = TreeFactory.mapFactory().
			createHashMap();
	
	/*
	 * Protect this construct from the outside world.
	 */
	TreePipeline() {}

	/*
	 * Only the core API package can decide which attributes will be validated.
	 */
	public void addAttribute(String attributeName, Object attribute) {
		this.attributes.put(attributeName, attribute);
	}
	
	/*
	 * Internal usage in validations chains. The reason is because the
	 * validations processes reside in another API package.
	 */
	public Object getAttribute(String attributeName) {
		return this.attributes.get(attributeName);
	}
}
