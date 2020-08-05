package com.miuey.happytree.core;

import java.util.Collection;
import java.util.Map;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeSession;

class TreeSessionCore implements TreeSession {

	private String identifier;
	private boolean isActive;
	private Element<?> root;
	private Class<?> typeTree;
	
	/*
	 * Main cache of all elements from this session. All searches will use this
	 * cache to increase perfomance.
	 */
	private Map<Object, Element<?>> cache = TreeFactory.mapFactory().
			createHashMap();
	
	
	TreeSessionCore(String identifier) {
		this.identifier = identifier;
	}
	
	
	@Override
	public String getSessionId() {
		return this.identifier;
	}

	@Override
	public boolean isActive() {
		return this.isActive;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Element<T> tree() {
		return (Element<T>) root;
	}

	void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/*
	 * This method represents the only one place to initialize a tree. Put the
	 * root element attached to this session and vice-versa.
	 */
	<T> void setRoot(Element<?> root, Collection<Element<T>> tree,
			Class<?> typeTree) {
		@SuppressWarnings("unchecked")
		TreeElementCore<T> rootCast = (TreeElementCore<T>) root;
		
		rootCast.initRoot(tree);
		rootCast.attach(this.getSessionId());
		this.root = rootCast;
		this.typeTree = typeTree;
		setActive(Boolean.TRUE);
	}

	<T> void add(Object id, Element<T> element) {
		this.cache.put(id, element);
	}
	
	void remove(Object id) {
		this.cache.remove(id);
	}
	
	@SuppressWarnings("unchecked")
	<T> Element<T> get(Object id) {
		return (Element<T>) this.cache.get(id);
	}
	
	Class<?> getTypeTree() {
		return typeTree;
	}
}
