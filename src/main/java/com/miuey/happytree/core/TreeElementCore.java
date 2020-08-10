package com.miuey.happytree.core;

import java.util.Collection;
import java.util.Iterator;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeSession;
import com.miuey.happytree.exception.TreeException;

class TreeElementCore<T> implements Element<T> {

	/*
	 * To be exposed to the interface.
	 */
	private Object id;
	private Object parentId;
	private Collection<Element<T>> children;
	private T wrappedObject;
	private TreeSession session;
	
	/*
	 * To be used internally.
	 */
	private boolean isRoot;
	private ElementState state;
	private Class<?> type;
	
	
	TreeElementCore(Object id, Object parentId, T wrappedObject,
			TreeSession session) {
		this.id = id;
		this.parentId = parentId;
		this.children = TreeFactory.collectionFactory().createHashSet();
		
		this.wrappedObject = wrappedObject;
		if (wrappedObject != null) {
			this.type = wrappedObject.getClass();
		}
		this.session = session;
		
		transitionState(ElementState.NOT_EXISTED);
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

	public Element<T> getElementById(Object id) {
		return Recursivity.searchElementById(getChildren(), id);
	}
	
	@Override
	public void addChild(Element<T> child) {
		if (child != null) {
			this.children.add(child);
		}
	}

	@Override
	public void addChildren(Collection<Element<T>> children) {
		if (children != null && !children.isEmpty()) {
			this.children.addAll(children);
		}
	}

	@Override
	public void removeChildren(Collection<Element<T>> children) {
		this.children.removeAll(children);
	}

	@Override
	public void removeChild(Element<T> child) {
		this.children.remove(child);
	}

	@Override
	public void removeChild(Object id) {
		Iterator<Element<T>> iterator = this.children.iterator();
		
		while (iterator.hasNext()) {
			Element<T> element = iterator.next();
			if (element.getId().equals(id)) {
				iterator.remove();
				break;
			}
		}
	}

	@Override
	public void wrap(T object) throws TreeException {
		this.wrappedObject = object;
	}

	@Override
	public T unwrap() {
		return this.wrappedObject;
	}

	@Override
	public TreeSession attachedTo() {
		return this.session;
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
		
		TreeSession otherSession = other.attachedTo();
		
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
		
		if ((this.session == null && otherSession != null) ||
				(this.session != null &&
				!this.session.equals(otherSession))) {
			return Boolean.FALSE;
		}
		return isEqual;
	}
	
	ElementState getState() {
		return state;
	}

	void setState(ElementState state) {
		this.state = state;
	}

	void changeSession(TreeSession session) {
		this.session = session;
	}
	
	boolean isRoot() {
		return isRoot;
	}
	
	Class<?> getType() {
		return type;
	}

	void transitionState(ElementState nextState) {
		this.state = nextState;
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
