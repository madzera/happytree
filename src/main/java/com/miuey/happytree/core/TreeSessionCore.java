package com.miuey.happytree.core;

import java.util.Collection;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeSession;

class TreeSessionCore implements TreeSession {

	/*
	 * To be exposed by interface.
	 */
	private String identifier;
	private boolean isActive;
	private Element<?> root;
	
	/*
	 * To be used internally.
	 */
	private Class<?> typeTree;
	
	/*
	 * Main cache of all elements from this session. All searches will use this
	 * cache to increase performance.
	 */
	private Cache cache = TreeFactory.utilFactory().createCacheSession();
	
	
	TreeSessionCore(String identifier, Class<?> typeTree) {
		this.identifier = identifier;
		this.typeTree = typeTree;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((identifier == null) ?
				0 : identifier.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TreeSessionCore other = (TreeSessionCore) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier)) {
			return false;
		}
		return true;
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
		this.root = rootCast;
		setActive(Boolean.TRUE);
	}

	<T> void add(Element<T> element) {
		this.applyRecursivityCacheOperation(element, Boolean.FALSE);
	}
	
	void remove(Object id) {
		Element<?> element = this.cache.read(id);
		this.applyRecursivityCacheOperation(element, Boolean.TRUE);
	}
	
	@SuppressWarnings("unchecked")
	<T> Element<T> get(Object id) {
		return (Element<T>) this.cache.read(id);
	}
	
	Class<?> getTypeTree() {
		return typeTree;
	}
	
	/*
	 * Add or remove from the cache recursively.
	 */
	private <T> void applyRecursivityCacheOperation(Element<T> element,
			boolean toRemove) {
		Collection<?> descendants = Recursivity.toPlainList(element);
		for (Object object : descendants) {
			Element<?> iterator = (Element<?>) object;

			if (toRemove) {
				this.cache.delete(iterator.getId());
			} else {
				this.cache.write(iterator.getId(), iterator);
			}
		}
	}
}
