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
	
	private int savedHash;
	
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + (calculateHashForId(id));
		result = prime * result + (calculateHashForId(parentId));
		
		return result;
	}

	@Override
	public boolean equals(Object another) {
		Boolean isEqual = Boolean.TRUE;
		if (this == another) {
			return isEqual;
		}
		if (another == null || this.getClass() != another.getClass()) {
			return Boolean.FALSE;
		}
		
		@SuppressWarnings("unchecked")
		TreeElementCore<T> other = (TreeElementCore<T>) another;
		
		Object otherId = other.getId();
		Object otherParentId = other.getParent();
		String otherSessionId = other.attachedTo();
		
		/*
		 * The id can be null in detached elements.
		 */
		if ((this.id == null && otherId != null) ||
				(this.id != null && !this.id.equals(otherId))) {
			return Boolean.FALSE;
		}
		
		if ((this.parentId == null && otherParentId != null) ||
				(this.parentId != null &&
				!this.parentId.equals(otherParentId))) {
			return Boolean.FALSE;
		}
		
		if ((this.sessionId == null && otherSessionId != null) ||
				(this.sessionId != null &&
				!this.sessionId.equals(otherSessionId))) {
			return Boolean.FALSE;
		}
		return isEqual;
	}


	boolean isAttached() {
		TreeElementCore<T> child = null;
		for (Element<T> iterator : this.children) {
			child = (TreeElementCore<T>) iterator;
			if (!child.isAttached) {
				this.isAttached = Boolean.FALSE;
				break;
			}
		}
		return isAttached;
	}

	void changeSession(String sessionId) {
		this.sessionId = sessionId;
	}
	
	boolean isRoot() {
		return isRoot;
	}
	
	public int savedHashContent() {
		return this.savedHash;
	}
	
	synchronized void attach(String sessionId) {
		this.isAttached = Boolean.TRUE;
		changeSession(sessionId);
		this.savedHash = this.hashCode();
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
	
	private int calculateHashForId(Object id) {
		final int perfectNumber = 32;
		if (id instanceof Byte || id instanceof Short || id instanceof Integer) {
			return (int) id;
		} else if (id instanceof Long) {
			long convertedId = (Long) id;
			return (int) (convertedId ^ (convertedId >>> perfectNumber));
		} else if (id instanceof Float) {
			float convertedId = (Float) id;
			return Float.floatToIntBits(convertedId);
		} else if (id instanceof Double) {
			double convertedId = (Double) id;
			long value = Double.doubleToLongBits(convertedId);
			return (int) (value ^ (value >>> perfectNumber));
		} else {
			return ((id == null) ? 0 : id.hashCode());
		}
	}
}
