package com.madzera.happytree.core;

import java.util.Collection;

import com.madzera.happytree.Element;
import com.madzera.happytree.TreeSession;

class TreeSessionCore implements TreeSession {

	/*
	 * To be exposed by interface.
	 */
	private String identifier;
	private boolean isActive;
	private TreeElementCore<?> root;
	
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
		return prime * result + identifier.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		else if ((obj == null)
			|| (getClass() != obj.getClass())
			|| !((TreeSessionCore) obj).identifier.equals(this.identifier)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "TreeSessionCore [identifier=" + identifier + "]";
	}

	void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/*
	 * This method represents the only one place to initialize a tree. Put the
	 * root element attached to this session and vice-versa.
	 */
	<T> void setRoot(Element<?> root, Collection<TreeElementCore<T>> tree) {
		@SuppressWarnings("unchecked")
		TreeElementCore<T> rootCast = (TreeElementCore<T>) root;
		
		rootCast.initRoot(tree);
		this.root = rootCast;
		setActive(Boolean.TRUE);
	}

	<T> void save(Element<T> element) {
		this.applyRecursionCacheOperation(element, SessionHandler.SAVE);
	}
	
	void delete(Object id) {
		Element<?> element = this.cache.read(id);
		this.applyRecursionCacheOperation(element, SessionHandler.DELETE);
	}
	
	<T> TreeElementCore<T> get(Object id) {
		return this.cache.read(id);
	}
	
	Class<?> getTypeTree() {
		return typeTree;
	}
	
	TreeSessionCore cloneSession(String newSessionId) {
		TreeSessionCore clone = TreeFactory.serviceFactory().
				createTreeSession(newSessionId, this.getTypeTree());
		
		clone.setActive(this.isActive);
		
		TreeElementCore<?> clonedTree = (TreeElementCore<?>) this.tree();
		TreeElementCore<?> clonedRoot = clonedTree.cloneElement();
		
		clonedRoot.mergeUpdatedId(newSessionId);
		clone.root = clonedRoot;
		
		updateSessionClonedElements(clone);
		return clone;
	}
	
	private <T> void applyRecursionCacheOperation(Element<T> element,
			SessionHandler handler) {
		Collection<?> descendants = Recursion.toPlainList(element);
		
		for (Object object : descendants) {
			TreeElementCore<?> iterator = (TreeElementCore<?>) object;
			
			if (handler.equals(SessionHandler.SAVE)) {
				iterator.transitionState(ElementState.ATTACHED);
				this.cache.write(iterator.getId(), iterator);
			} else {
				iterator.transitionState(ElementState.NOT_EXISTED);
				this.cache.delete(iterator.getId());
			}
		}
	}
	
	private void updateSessionClonedElements(TreeSessionCore clone) {
		TreeElementCore<?> clonedRoot = (TreeElementCore<?>) clone.tree();
		Collection<?> descendants = Recursion.toPlainList(clonedRoot);
		
		for (Object object : descendants) {
			TreeElementCore<?> iterator = (TreeElementCore<?>) object;
			
			iterator.changeSession(clone);
			clone.cache.write(iterator.getId(), iterator);
		}
	}
	
	private enum SessionHandler {
		SAVE,
		DELETE;
	}
}
