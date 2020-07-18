package com.miuey.happytree.core;

import java.util.Map;

/**
 * Pipeline represents an object that will be taken from the beginning to the
 * end of the processing chain. In this context, this object will be taken to
 * the validations chain and API Transformation Process, in its life cycle.
 * 
 * @author Diego Nóbrega
 *
 */
public class TreePipeline {

	/*
	 * Map of attributes
	 */
	private Map<String, Object> attributes = TreeFactory.mapFactory().
			createHashMap();
	
	/*
	 * Protect this construct from the outside world. If it is exposed, then the
	 * client API will have access to use the addAtributes() and getAttribute()
	 * methods. This class needs be public and also its methods because it needs
	 * to be accessible to others packages. Therefore, it is crucial that this
	 * constructor be not public.
	 */
	TreePipeline() {}

	/*
	 * This method needs be public because it needs to be accessible to others
	 * packages.
	 */
	public void addAttribute(String attributeName, Object attribute) {
		this.attributes.put(attributeName, attribute);
	}
	
	/*
	 * This method needs be public because it needs to be accessible to others
	 * packages.
	 */
	public Object getAttribute(String attributeName) {
		return this.attributes.get(attributeName);
	}
}
