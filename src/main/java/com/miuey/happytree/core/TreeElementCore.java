package com.miuey.happytree.core;

import java.util.Collection;
import java.util.Iterator;

import com.miuey.happytree.Element;
import com.miuey.happytree.exception.TreeException;

class TreeElementCore<T> implements Element<T> {

	private Object id;
	private Object parentId;
	private Collection<Element<T>> children;
	private T wrappedObject;
	private String sessionId;
	private boolean isAttached;
	private boolean isRoot;
	
	
	TreeElementCore(Object id, Object parentId) {
		this.id = id;
		this.parentId = parentId;
		this.children = TreeFactory.collectionFactory().createHashSet();
	}
	
	
	@Override
	public Object getId() {
		return this.id;
	}

	@Override
	public void setId(Object id) {
		this.id = id;
		detach();
	}

	@Override
	public Object getParent() {
		return this.parentId;
	}

	@Override
	public void setParent(Object parent) {
		this.parentId = parent;
		detach();
	}

	@Override
	public Collection<Element<T>> getChildren() {
		return this.children;
	}

	@Override
	public void addChild(Element<T> child) {
		if (child != null) {
			this.children.add(child);
			detach();
		}
	}

	@Override
	public void addChildren(Collection<Element<T>> children) {
		if (children != null && !children.isEmpty()) {
			this.children.addAll(children);
			detach();
		}
	}

	@Override
	public void removeChildren(Collection<Element<T>> children) {
		if (this.children.removeAll(children)) {
			detach();
		}
	}

	@Override
	public void removeChild(Element<T> child) {
		if (this.children.remove(child)) {
			detach();
		}
	}

	@Override
	public void removeChild(Object id) {
		Iterator<Element<T>> iterator = this.children.iterator();
		
		while (iterator.hasNext()) {
			Element<T> element = iterator.next();
			if (element.getId().equals(id)) {
				iterator.remove();
				detach();
				break;
			}
		}
	}

	@Override
	public void wrap(T object) throws TreeException {
		this.wrappedObject = object;
		detach();
	}

	@Override
	public T unwrap() {
		return this.wrappedObject;
	}

	@Override
	public String attachedTo() {
		return this.sessionId;
	}

	boolean isAttached() {
		return isAttached;
	}

	void changeSession(String sessionId) {
		this.sessionId = sessionId;
	}
	
	boolean isRoot() {
		return isRoot;
	}
	
	synchronized void attach(String sessionId) {
		this.isAttached = Boolean.TRUE;
		changeSession(sessionId);
	}

	synchronized void detach() {
		this.isAttached = Boolean.FALSE;
	}
	
	/*
	 * Only in the root assembly. When there is a session being initialized,
	 * then this root element cannot be detached.
	 */
	void initRoot(Collection<Element<T>> children) {
		if (children != null && !children.isEmpty()) {
			this.children.addAll(children);
			this.isRoot = Boolean.TRUE;
		}
	}
}
