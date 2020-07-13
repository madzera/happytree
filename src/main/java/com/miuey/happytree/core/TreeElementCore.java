package com.miuey.happytree.core;

import java.util.Collection;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeSession;
import com.miuey.happytree.exception.TreeException;

class TreeElementCore<T> implements Element<T> {

	private Object id;
	private Object parentId;
	private Collection<Element<T>> children;
	private T wrappedObject;
	private TreeSession session;
	private boolean isAttached;
	
	
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
	}

	@Override
	public Object getParent() {
		return this.parentId;
	}

	@Override
	public void setParent(Object parent) {
		this.parentId = parent;
	}

	@Override
	public Collection<Element<T>> getChildren() {
		return this.children;
	}

	@Override
	public void addChild(Element<T> child) throws TreeException {
		this.children.add(child);
	}

	@Override
	public void addChildren(Collection<Element<T>> children)
			throws TreeException {
		this.children.addAll(children);
	}

	@Override
	public void removeChildren(Collection<Element<T>> children) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeChild(Element<T> child) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeChild(Object id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void wrap(T object) throws TreeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T unwrap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TreeSession attachedTo() {
		// TODO Auto-generated method stub
		return null;
	}

	boolean isAttached() {
		return isAttached;
	}

	synchronized void attach(TreeSession session) {
		this.isAttached = Boolean.TRUE;
		this.session = session;
	}

	synchronized void detach() {
		this.isAttached = Boolean.TRUE;
		this.session = null;
	}
}
