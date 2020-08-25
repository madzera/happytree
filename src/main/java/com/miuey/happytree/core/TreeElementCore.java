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
	private ElementState state;
	private boolean isRoot;
	private Class<?> type;
	private Object newId;
	
	
	TreeElementCore(Object id, Object parentId, T wrappedObject,
			TreeSession session) {
		this.id = id;
		this.parentId = parentId;
		this.children = TreeFactory.collectionFactory().createHashSet();
		
		this.wrappedObject = wrappedObject;
		if (wrappedObject != null) {
			this.setType(wrappedObject.getClass());
		}
		this.session = session;
		/*
		 * First initialized state.
		 */
		this.state = ElementState.NOT_EXISTED;
	}
	
	
	@Override
	public Object getId() {
		return this.id;
	}

	@Override
	public void setId(Object id) {
		this.setNewId(id);
		transitionState(ElementState.DETACHED);
	}

	@Override
	public Object getParent() {
		return this.parentId;
	}

	@Override
	public void setParent(Object parent) {
		this.parentId = parent;
		transitionState(ElementState.DETACHED);
	}

	@Override
	public Collection<Element<T>> getChildren() {
		return this.children;
	}

	@Override
	public Element<T> getElementById(Object id) {
		return Recursivity.searchElementById(getChildren(), id);
	}
	
	@Override
	public void addChild(Element<T> child) {
		if (child != null) {
			child.setParent(this.id);
			transitionState(ElementState.DETACHED);
			this.children.add(child);
		}
	}

	@Override
	public void addChildren(Collection<Element<T>> children) {
		if (children != null && !children.isEmpty()) {
			this.children.addAll(children);
			for (Element<T> element : children) {
				element.setParent(this.id);
			}
			transitionState(ElementState.DETACHED);
		}
	}

	@Override
	public void removeChildren(Collection<Element<T>> children) {
		this.children.removeAll(children);
	}

	@Override
	public void removeChild(Element<T> child) {
		if (child != null) {
			this.children.remove(child);
			child.setParent(null);
			transitionState(ElementState.DETACHED);
		}
	}

	@Override
	public void removeChild(Object id) {
		Iterator<Element<T>> iterator = this.children.iterator();
		
		while (iterator.hasNext()) {
			Element<T> element = iterator.next();
			if (element.getId().equals(id)) {
				iterator.remove();
				transitionState(ElementState.DETACHED);
				element.setParent(null);
				break;
			}
		}
	}

	@Override
	public void wrap(T object) throws TreeException {
		this.wrappedObject = object;
		setType(object.getClass());
		transitionState(ElementState.DETACHED);
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
	
	/*
	 * Only for help the tests.
	 */
	@Override
	public String toString() {
		T obj = unwrap();
		if (obj != null) {
			return obj.toString();
		}
		return "null";
	}


	ElementState getState() {
		return state;
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
		/*
		 * Before persist element, the element state cannot transit from
		 * NOT_EXISTED to DETACHED directly.
		 */
		if (!this.state.equals(ElementState.NOT_EXISTED)
				|| !nextState.equals(ElementState.DETACHED)) {
			this.state = nextState;
		}
	}

	Object getUpdatedId() {
		return this.newId;
	}

	/*
	 * Only in the root assembly. When there is a session being initialized,
	 * then this root element cannot be detached.
	 */
	void initRoot(Collection<Element<T>> children) {
		if (children != null && !children.isEmpty()) {
			this.children.addAll(children);
			this.setRoot(Boolean.TRUE);
		}
	}

	/*
	 * This is not recommended to use implicit super clone Object because
	 * few bugs.
	 */
	Element<T> cloneElement() {
		TreeElementCore<T> clone = TreeFactory.serviceFactory().
				createElement(
						this.getId(),
						this.getParent(),
						this.unwrap(),
						this.attachedTo());
		
		/*
		 * User addAll() instead of addChildren for not changing the child
		 * element state.
		 */
		clone.getChildren().addAll(this.getChildren());
		clone.transitionState(this.getState());
		clone.setRoot(this.isRoot());
		clone.setType(this.getType());
		clone.setNewId(this.getUpdatedId());
		
		return clone;
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
	
	private void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	private void setType(Class<?> type) {
		this.type = type;
	}

	private void setNewId(Object newId) {
		this.newId = newId;
	}
}
