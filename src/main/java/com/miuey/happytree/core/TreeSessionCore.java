package com.miuey.happytree.core;

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

	void setRoot(Element<?> root) {
		this.root = root;
	}
}
