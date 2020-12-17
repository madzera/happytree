package com.madzera.happytree;

import java.util.Collection;

import com.madzera.happytree.exception.TreeException;

/**
 * An <code>Element</code> represents a node within a tree. It consists of the
 * elementary unit of the HappyTree API, which is directly handled through
 * {@link TreeManager} interface. An element can move from one tree to another,
 * be removed, copied and even encapsulate any object (node) within it, making
 * this encapsulated object hierarchically available within a tree structure.
 * 
 * <p>This interface is only responsible for handling the element itself and its
 * children only. Here, there is no direct relationship with the other available
 * interfaces, except its respective session ({@link TreeSession}) which this
 * element belongs in. Considering that, as an element corresponds to an
 * elementary unit, it is only possible to relate to itself and its children.
 * </p>
 * 
 * <p>An element <b>always</b> is associated with a session. Even after right
 * its creation, the element already belongs to a session, but thats not means
 * that this element is already in the tree. It depends on its life cycle state.
 * </p>
 * 
 * <p>To be handled, an element must be in an appropriate state in its life
 * cycle, depending on the operation that is desired to perform. Its life cycle
 * consists of 3 states:</p>
 * 
 * <table summary="Life cycle">
 * <tr><th>State</th><th>Description</th></tr>
 * 
 * <tr>
 * 		<td>NOT_EXISTED</td>
 * 		<td>When the element is created. At this time, the element was not
 * 			persisted inside the any tree session yet.
 * 		</td>
 * </tr>
 * 
 * <tr>
 * 		<td>ATTACHED</td>
 * 		<td>When the element is created and after that, the same is persisted
 * 			inside the tree session by invoking.
 * 		</td>
 * </tr>
 * 
 * <tr>
 * 		<td>DETACHED</td>
 * 		<td>When the element is already inside the tree as <i>ATTACHED</i> but
 * 			it got some change from the moment the API client has access to it.
 * 		</td>
 * </tr>
 * </table>
 * 
 * <p><b>Considering that, an element previously attached to some session, Each
 * subsequent manipulation from the perspective of the element itself will turn
 * its state to <code>DETACHED</code>, thus requiring an update by invoking
 * {@link TreeManager#updateElement(Element)}.</b></p>
 * 
 * <p>The API client has access to the following characteristics:</p>
 * 
 * <table summary="Characteristics">
 * 	<tr><th>Characteristics</th><th>Description</th></tr>
 * 	<tr>
 * 		<td><code>ID</code></td><td>The element identifier within the tree
 * 		(<b>must be unique within the tree</b>).</td>
 * 	</tr>
 * 	<tr>
 * 		<td><code>Parent</code></td><td>The parent element within the tree (if
 * 		the parent element is not found or <code>null</code> then this element
 * 		will be moved to the tree root level).</td>
 * 	</tr>
 * 	<tr>
 * 		<td><code>Children</code></td><td>The children list of the element.</td>
 * 	</tr>
 * 	<tr>
 * 		<td><code>Wrapped Node</code></td><td>The encapsulated node.</td>
 * 	</tr>
 * 	<tr>
 * 		<td><code>Session</code></td><td>The session which this element	belongs
 * 		in.</td>
 * 	</tr>
 * 	<tr>
 * 		<td><code>Life Cycle</code></td><td>The current state in life cycle.
 * 		</td>
 * 	</tr>
 * </table>
 * 
 * <p>The relationship between {@literal Id} and {@literal Parent} in the
 * tree composition represents the heart of the HappyTree API. The tree is
 * assembled strictly by this relation, of which a parent of an element
 * references the id of the parent element. Therefore, an id of a element can
 * never be <code>null</code> and if a parent of an element is <code>null</code>
 * or not found, that same element is moved to the root level of the tree.</p>
 * 
 * @author Diego Madson de Andrade Nóbrega
 * 
 * @see TreeTransaction
 * 
 * @param <T> the class type of the wrapped node that will be encapsulated into
 * this <code>Element</code>
 */
public interface Element<T> {
	
	/**
	 * Obtains the element identifier.
	 * 
	 * <p><b>This identifier is unique within the tree session when this is
	 * attached to the tree.</b></p>
	 * 
	 * @return the element identifier
	 */
	public Object getId();
	
	/**
	 * Set the element identifier.
	 * 
	 * <p>By invoking this method, the element will not change its id
	 * immediately. It is necessary to update the element for the change to take
	 * effect.</p>
	 * 
	 * <p><b>The <code>id</code> must be unique and whether it is
	 * <code>null</code> nothing happens because this is used to be updated, so
	 * the element keeps the old one.</b></p>
	 * 
	 * @param id the element identifier to be updated
	 */
	public void setId(Object id);
	
	/**
	 * Obtains the parent identifier reference of this element.
	 * 
	 * @return the parent element identifier
	 * 
	 * @see #setParent(Object)
	 */
	public Object getParent();
	
	/**
	 * Set the parent identifier reference of this element.
	 * 
	 * <p>If the parent reference is <code>null</code> or references a
	 * non-existing element, then it is certain that this element will be in the
	 * root level of the tree when it is going to be persisted or updated. If it
	 * is the case, then after persisting or updating this element, its parent
	 * will be the own session identifier.</p>
	 * 
	 * @param parent the parent element identifier reference to be updated
	 */
	public void setParent(Object parent);
	
	/**
	 * Obtains all child elements of the current element. This includes all
	 * elements within the child elements recursively.
	 * 
	 * @return all children of the current element
	 */
	public Collection<Element<T>> getChildren();
	
	/**
	 * Add a new child element into the current element.
	 * 
	 * <p>If the <code>child</code> element contains children, they will also be
	 * added.</p>
	 * 
	 * @param child the element to be added as child
	 */
	public void addChild(Element<T> child);
	
	/**
	 * Add a list of child elements to be concatenated to the current children
	 * list.
	 * 
	 * <p>If each element within the <code>children</code> list contains more
	 * children within it recursively, they will also be added.</p>
	 * 
	 * @param children the list of child elements to be concatenated to the
	 * current children list
	 */
	public void addChildren(Collection<Element<T>> children);

	/**
	 * Searches within the current element for an element according to the
	 * <code>id</code> parameter passed through.
	 * 
	 * <p>If the element is not found, then <code>null</code> is returned.</p>
	 * 
	 * @param id the element identifier to be searched for
	 * 
	 * @return the found element inside this one
	 */
	public Element<T> getElementById(Object id);
	
	/**
	 * Removes a collection of elements inside this one.
	 * 
	 * <p>The element references are removed whether they are found in children
	 * list. When an element is removed, all of its children as well as the
	 * elements below the hierarchy are also removed, recursively.</p>
	 * 
	 * @param children the list of child elements to be removed
	 */
	public void removeChildren(Collection<Element<T>> children);
	
	/**
	 * Removes the specified <code>child</code> element from the children list
	 * of the current element.
	 * 
	 * <p>The <code>child</code> element reference is removed if he is found in
	 * children list. When an element is removed, all of its children as well as
	 * the elements below the hierarchy are also removed, recursively.</p>
	 * 
	 * @param child the element to be removed
	 */
	public void removeChild(Element<T> child);
	
	/**
	 * Removes the element from the children list of the current element by the
	 * <code>id</code> passed through.
	 * 
	 * <p>If it exists, then the element and all of its children are removed
	 * too.</p>
	 * 
	 * @param id the element identifier to be search for and removed
	 */
	public void removeChild(Object id);
	
	/**
	 * Encapsulates any object node within the element, as long as this object
	 * has the same type as other objects that were encapsulated within the same
	 * tree session.
	 * 
	 * <p>As an element conceptually represents a <i>node</i> within a tree, the
	 * wrapped object node would be simulating precisely its position within the
	 * tree in question.</p>
	 * 
	 * <p>The object node itself can be wrapped at two different times:</p>
	 * 	<ol>
	 * 		<li>Initializing a new empty tree session;</li>
	 * 		<li>Initializing a tree session from the <b>API Transformation
	 * 		Process</b>.</li>
	 * 	</ol>
	 * 
	 * <p>The first one would be to create an empty tree. In this way, the
	 * wrapped node is determined after the tree is ready, invoking this
	 * method. This includes the choice of not determining, making it with the
	 * <code>null</code> value.</p>
	 * 
	 * <p>The last one happens in the <b>API Transformation Process</b>, that
	 * is, when the moment of initialization, before the tree is created, a call
	 * for {@link TreeTransaction#initializeSession(String, Collection)}
	 * transforms a previous linear structure into a real hierarchical
	 * structure of a tree. Then, each <code>Element</code> object will wrap its
	 * respective node automatically.</p>
	 * 
	 * <p>Therefore, each object node of this linear structure would be
	 * represented precisely by this encapsulated object within its respective
	 * <code>Element</code> object in its respective hierarchy inside of the
	 * tree. In this case, during the <b>API Transformation Process</b> life
	 * cycle, the object node will be transformed, managed and wrapped within
	 * the own element automatically.</p>
	 * 
	 * <p>There are some requirements for this object node to be wrapped when
	 * initializing a session by the API Transformation Process:</p>
	 * 	<ul>
	 * 		<li>The class of this node must be annotated by {@literal @Tree};
	 * 		</li>
	 * 		<li>The class of this node must be annotated by {@literal @Parent};
	 * 		</li>
	 * 		<li>The class of this node must be annotated by {@literal @Id};
	 * 		</li>
	 * 		<li>The {@literal @Id} value cannot be <code>null</code>;
	 * 		</li>
	 * 		<li>The {@literal @Id} and {@literal @Parent} must be of the same
	 * 		type.</li>
	 * 	</ul>
	 * 
	 * <p>Those requirements are only applied when the session is going to be
	 * initialized by the <b>API Transformation Process</b>.</p>
	 * 
	 * @param object the node to be wrapped within the element
	 * 
	 */
	public void wrap(T object);
	
	/**
	 * Obtains the object node wrapped into this element.
	 * 
	 * @return the wrapped object node inside of this element
	 * 
	 * @see #wrap(Object)
	 */
	public T unwrap();
	
	/**
	 * Returns the {@link TreeSession} instance which this element belongs in.
	 * 
	 * <p>When an element is created, either through the <b>API Transformation
	 * Process</b> or normally through invoking the
	 * {@link TreeManager#createElement(Object, Object, Object)}, the current
	 * session of the transaction is associated to the created element.</p>
	 * 
	 * <p>Therefore, an element <b>always</b> is associated with a session.</p>
	 * 
	 * @return the session which this element belongs in
	 */
	public TreeSession attachedTo();
	
	/**
	 * Returns the current life cycle state of this element.
	 * 
	 * <p>The life cycle is important to indicate what are the allowed
	 * operations that this element can perform through the {@link TreeManager}
	 * interface. It works like a <i>State Machine</i> concept.</p>
	 * 
	 * <table summary="Possible Operations">
	 * <tr><th>TreeManager Operation</th><th>Allowed Life Cycle</th></tr>
	 * 
	 * <tr><td>cut()</td><td>ATTACHED</td></tr>
	 * <tr><td>copy()</td><td>ATTACHED</td></tr>
	 * <tr><td>removeElement()</td><td>ATTACHED</td></tr>
	 * <tr><td>containsElement()</td><td>ATTACHED</td></tr>
	 * <tr><td>persistElement()</td><td>NOT_EXISTED</td></tr>
	 * <tr><td>updateElement()</td><td>ATTACHED; DETACHED</td></tr>
	 * 
	 * </table><br>
	 * 
	 * <b>For all operations that the life cycle does not correspond, a
	 * {@link TreeException} will be threw, except for the
	 * {@link TreeManager#containsElement(Element, Element)} that just returns
	 * <code>false</code> when the elements are not <i>ATTACHED</i>.</b>
	 * 
	 * <p>All operations returned by <code>TreeManager</code>, change the
	 * element's state to <i>ATTACHED</i>, except
	 * {@link TreeManager#createElement(Object, Object, Object)} (NOT_EXISTED).
	 * </p>
	 * 
	 * @return <code>String</code> corresponding the life cycle state name
	 */
	public String lifecycle();
}