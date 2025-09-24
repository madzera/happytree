package com.madzera.happytree.core;

import java.util.Collection;
import java.util.Iterator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.madzera.happytree.Element;
import com.madzera.happytree.TreeSession;

@JsonPropertyOrder({ "element", "children" })
@JacksonXmlRootElement(localName = "element")
class TreeElementCore<T> implements Element<T> {

	/*
	 * To be exposed to the interface.
	 */
	@JsonIgnore
	private Object id;
	private Object parentId;
	@JsonProperty("element")
	@JsonUnwrapped
	private T wrappedNode;
	@JacksonXmlElementWrapper(useWrapping = false)
	private Collection<Element<T>> children;
	private TreeSession session;
	
	/*
	 * To be used internally.
	 */
	private ElementState state;
	private boolean isRoot;
	private Class<?> type;
	private Object newId;
	private Object oldParentId;
	
	
	TreeElementCore(Object id, Object parentId, T wrappedNode,
			TreeSession session) {
		this.id = id;
		this.parentId = parentId;
		this.oldParentId = parentId;
		this.children = TreeFactory.collectionFactory().createHashSet();
		
		this.wrappedNode = wrappedNode;
		if (wrappedNode != null) {
			this.setType(wrappedNode.getClass());
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
		/*
		 * Invoking this method should not update the id automatically, instead
		 * that, store the id value in a newId attribute to be submitted when
		 * the element is going to be updated.
		 */
		if (id != null) {
			this.setNewId(id);
			transitionState(ElementState.DETACHED);
		}
	}

	@Override
	@JsonIgnore
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
		return Recursion.searchElementById(getChildren(), id);
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
		Iterator<Element<T>> iterator = children.iterator();
		while (iterator.hasNext()) {
			Element<T> element = iterator.next();
			this.removeChild(element);
		}
	}

	@Override
	public void removeChild(Element<T> child) {
		if (child != null) {
			boolean isRemoved = this.children.remove(child);
			
			if (isRemoved) {
				child.setParent(null);
				transitionState(ElementState.DETACHED);
			}
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
	public void wrap(T object) {
		this.wrappedNode = object;
		
		if (this.wrappedNode != null) {
			setType(object.getClass());
		}
		transitionState(ElementState.DETACHED);
	}

	@Override
	@JsonProperty("element")
	@JsonUnwrapped
	public T unwrap() {
		return this.wrappedNode;
	}

	@Override
	public TreeSession attachedTo() {
		return this.session;
	}
	
	@Override
	public String lifecycle() {
		return getState().name();
	}

	@Override
	public String toJSON() {
		return this.toJSON(Boolean.FALSE);
	}
	
	@Override
	public String toPrettyJSON() {
		return this.toJSON(Boolean.TRUE);
	}

	@Override
	public String toXML() {
		return this.toXML(Boolean.FALSE);
	}


	@Override
	public String toPrettyXML() {
		return this.toXML(Boolean.TRUE);
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
		boolean isEqual = Boolean.TRUE;
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

		isEqual = (this.id.equals(otherId))
			&& ((this.parentId != null && this.parentId.equals(otherParentId))
				|| (this.parentId == null && otherParentId == null))
			&& (this.session.equals(otherSession)); 
		
		return isEqual;
	}
	
	/**
	 * Writes the <code>toString()</code> from the own wrapped node inside of
	 * this element, represented by the parameterized type.
	 * 
	 * @return the <code>toString()</code> of the wrapped node
	 */
	@Override
	public String toString() {
		T obj = unwrap();
		if (obj != null) {
			return obj.toString();
		}
		return "[null]";
	}

	ElementState getState() {
		return state;
	}

	void changeSession(TreeSession session) {
		this.session = session;
	}
	
	/*
	 * This method only can be invoked by core API, when this is updating the
	 * object which a change in the id attribute is necessary. This set the
	 * current id with the desirable id and all children will reference this
	 * new id.
	 */
	void mergeUpdatedId(Object id) {
		this.id = id;
		this.setNewId(null);
		
		for (Element<T> iterator : getChildren()) {
			TreeElementCore<T> child = (TreeElementCore<T>) iterator; 
			child.setParent(id);
			child.syncParentId();
		}
	}

	void syncParentId() {
		this.oldParentId = this.parentId;  
	}
	
	/*
	 * Return if this instance is a root of a tree.
	 */
	boolean isRoot() {
		return isRoot;
	}
	
	Class<?> getType() {
		return type;
	}

	/*
	 * Before persist element, the element state cannot transit from
	 * NOT_EXISTED to DETACHED directly.
	 */
	void transitionState(ElementState nextState) {
		if (!this.state.equals(ElementState.NOT_EXISTED)
				|| !nextState.equals(ElementState.DETACHED)) {
			this.state = nextState;
		}
	}

	/*
	 * This attribute represents the new Id that this element will have. When
	 * the API client invokes setId() the id is not changed automatically, it
	 * just is submitted when the updateElement() from TreeManager is invoked.
	 */
	Object getUpdatedId() {
		return this.newId;
	}

	/*
	 * Only in the root assembly. When there is a session being initialized,
	 * then this root element cannot be detached.
	 */
	void initRoot(Collection<TreeElementCore<T>> children) {
		this.children.addAll(children);
		this.setRoot(Boolean.TRUE);
	}

	/*
	 * This is not recommended to use implicit super clone Object because
	 * few bugs.
	 */
	TreeElementCore<T> cloneElement() {
		TreeElementCore<T> clone = TreeFactory.serviceFactory().
				createElement(
						this.getId(),
						this.getParent(),
						this.unwrap(),
						this.attachedTo());
		
		/*
		 * Clone the descendants.
		 */
		for (Element<T> child : this.getChildren()) {
			TreeElementCore<T> clonedChild = (TreeElementCore<T>) child;
			
			clonedChild = clonedChild.cloneElement();
			clone.getChildren().add(clonedChild);
		}
		
		clone.transitionState(this.getState());
		clone.setRoot(this.isRoot());
		clone.setType(this.getType());
		clone.setNewId(this.getUpdatedId());
		
		return clone;
	}

	void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}
	
	Object getOldParentId() {
		return oldParentId;
	}

	private int calculateHashForId(Object id) {
		final int perfectNumber = 32;
		if (id instanceof Byte) {
			return (byte) id;
		} else if (id instanceof Short) {
			return (short) id;
		} else if (id instanceof Integer) {
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

	private void setType(Class<?> type) {
		this.type = type;
	}

	private void setNewId(Object newId) {
		this.newId = newId;
	}
	
	private String toJSON(final boolean isPrettyJson) {
		final String defaultOutput = "{}";

		try {
			if (this.wrappedNode == null
					|| Recursion.iterateForNullWrappedNode(this.getChildren())) {
				throw TreeFactory.exceptionFactory().createException();
			}
			ObjectMapper objectMapper = TreeFactory.jsonFactory().
					createObjectMapper();

			if (isPrettyJson) {
				ObjectWriter writer = objectMapper.
						writerWithDefaultPrettyPrinter();
				return writer.writeValueAsString(this);
			} else {
				return objectMapper.writeValueAsString(this);
			}
		} catch (Exception e) {
			return defaultOutput;
		}
	}

	private String toXML(final boolean isPrettyXml) {
		final String defaultOutput = "{}";

		try {
			if (this.wrappedNode == null
					|| Recursion.iterateForNullWrappedNode(this.getChildren())) {
				throw TreeFactory.exceptionFactory().createException();
			}
			XmlMapper xmlMapper = TreeFactory.xmlFactory().createXmlMapper();

			if (isPrettyXml) {
				ObjectWriter writer = xmlMapper.writerWithDefaultPrettyPrinter();
				return writer.writeValueAsString(this);
			} else {
				return xmlMapper.writeValueAsString(this);
			}
		} catch (Exception e) {
			return defaultOutput;
		}
	}
	
}
