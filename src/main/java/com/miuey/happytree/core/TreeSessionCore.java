package com.miuey.happytree.core;

import java.util.Collection;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeSession;

class TreeSessionCore implements TreeSession {

	private String identifier;
	private boolean isActive;
	private Element<?> root;
	
	
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
	<T> void setRoot(Element<?> root, Collection<Element<T>> tree) {
		@SuppressWarnings("unchecked")
		TreeElementCore<T> rootCast = (TreeElementCore<T>) root;
		
		rootCast.initRoot(tree);
		rootCast.attach(this.getSessionId());
		this.root = rootCast;
		setActive(Boolean.TRUE);
	}
}
