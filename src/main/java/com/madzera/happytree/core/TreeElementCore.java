package com.madzera.happytree.core;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.madzera.happytree.Element;
import com.madzera.happytree.TreeSession;

@JsonPropertyOrder({"wrappedNode", "element"})
@JacksonXmlRootElement(localName = "element")
class TreeElementCore<T> implements Element<T> {

	/*
	 * To be exposed to the interface.
	 */
	@JsonIgnore
	private Object id;
	private Object parentId;
	@JsonProperty("wrappedNode")
	@JsonUnwrapped
	private T wrappedNode;
	@JsonProperty("children")
	@JacksonXmlElementWrapper(localName = "children")
	@JacksonXmlProperty(localName = "element") 
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
	private T newWrappedNode;
	
	
	TreeElementCore(Object id, Object parentId, T wrappedNode,
			TreeSession session) {
		this.id = id;
		this.parentId = parentId;
		this.oldParentId = parentId;
		this.children = TreeFactory.collectionFactory().createHashSet();
		
		this.wrappedNode = wrappedNode;
		this.newWrappedNode = wrappedNode;
		
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
		boolean isThisElement = !this.isRoot && this.id.equals(id);
		return isThisElement ? 
				this : Recursion.searchElementById(getChildren(), id);
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
		setNewWrappedNode(object);
		
		/*
		 * If the element is in NOT_EXISTED means that it is ready to be
		 * persisted, so there is a chance that the API client creates an
		 * element without wrapped object node and tries to wrap it later.
		 */
		if (ElementState.NOT_EXISTED.equals(this.state)) {
			this.wrappedNode = object;
		} else {
			transitionState(ElementState.DETACHED);
		}

		if (this.newWrappedNode != null) {
			setType(object.getClass());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T unwrap() {
		return (T) TreeUtil.IoUtil.deepCopyObject(this.wrappedNode, null);
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
	public List<Element<T>> search(Predicate<Element<T>> condition) {
		List<Element<T>> result = TreeFactory.collectionFactory()
				.createArrayList();

		if (condition == null) {
			return result;
		}

		/*
		 * The action is not applied to the root element, only to its
		 * descendants.
		 */
		if (!this.isRoot() && condition.test(this)) {
			result.add(this);
		}

		for (Element<T> child : this.getChildren()) {
			result.addAll(child.search(condition));
		}
		return result;
	}

	@Override
	public void apply(Consumer<Element<T>> action) {
		if (action == null) {
			return;
		}

		/*
		 * The action is not applied to the root element, only to its
		 * descendants.
		 */
		if (!this.isRoot()) {
			action.accept(this);
			transitionState(ElementState.DETACHED);
		}

		for (Element<T> child : this.getChildren()) {
			child.apply(action);
		}
	}

	@Override
	public void apply(
			Consumer<Element<T>> action, Predicate<Element<T>> condition) {
		if (action == null || condition == null) {
			return;
		}
		
		try {
			if (!this.isRoot() && condition.test(this)) {
				action.accept(this);
				transitionState(ElementState.DETACHED);
			}
		} catch (NullPointerException exception) {
			/*
			 * The wrapped condition inside Predicate is null, so nothing is
			 * applied.
			 */
			return;
		}

		for (Element<T> child : this.getChildren()) {
			child.apply(action, condition);
		}
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
	 * object which a change in the id attribute is necessary. Update the
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

	/*
	 * This method only can be invoked by core API, when this is updating the
	 * object which a change in the wrapped node attribute is necessary. Update
	 * the current wrapped node with the desirable wrapped node.
	 */
	void mergeUpdatedWrappedNode(T wrappedNode) {
		this.wrappedNode = wrappedNode;
		this.setNewWrappedNode(wrappedNode);
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
	 * This attribute represents the new wrapped node that this element will
	 * have. When the API client invokes wrap() the wrapped node is not changed
	 * automatically, it just is submitted when the updateElement() from
	 * TreeManager is invoked.
	 */
	T getUpdatedWrappedNode() {
		return this.newWrappedNode;
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
		clone.setNewWrappedNode(this.getUpdatedWrappedNode());
		
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

	private void setNewWrappedNode(T newWrappedNode) {
		this.newWrappedNode = newWrappedNode;
	}
	
	private String toJSON(final boolean isPrettyJson) {
		final String defaultOutput = "{}";

		try {
			if (!this.isRoot() && (this.wrappedNode == null
					|| Recursion.iterateForNullWrappedNode(this.getChildren()))) {
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
		final String defaultOutput = "<element/>";

		try {
			if (!this.isRoot() && (this.wrappedNode == null
					|| Recursion.iterateForNullWrappedNode(this.getChildren()))) {
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
