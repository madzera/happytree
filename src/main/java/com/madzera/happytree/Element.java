package com.madzera.happytree;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.madzera.happytree.exception.TreeException;

/**
 * An <code>Element</code> represents a node within a tree. It consists of the
 * elementary unit of the HappyTree API, which is directly handled through the
 * {@link TreeManager} interface. An element can move from one tree to another,
 * be removed, copied, and even encapsulate any object (node) within it, making
 * this encapsulated object hierarchically available within a tree structure.
 * 
 * <p>This interface is only responsible for handling the element itself and its
 * children only. Here, there is no direct relationship with the other available
 * interfaces, except its respective session ({@link TreeSession}) which this
 * element belongs in. Considering that, as an element corresponds to an
 * elementary unit, it is only possible to relate to itself and its children.
 * </p>
 * 
 * <p>An element <b>always</b> is associated with a session. Even after its
 * creation, the element already belongs to a session, but that does not mean
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
 * 		<td>When the element is created. At this time, the element has not been
 * 			persisted inside any tree session yet.
 * 		</td>
 * </tr>
 * 
 * <tr>
 * 		<td>ATTACHED</td>
 * 		<td>When the element is created and after that, it is persisted
 * 			inside the tree session.
 * 		</td>
 * </tr>
 * 
 * <tr>
 * 		<td>DETACHED</td>
 * 		<td>When the element is already inside the tree as <i>ATTACHED</i> but
 * 			it has been changed since the API client gained access to it.
 * 		</td>
 * </tr>
 * </table>
 * 
 * <p><b>Considering that an element is previously attached to some session, each
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
 * 		<td><code>Session</code></td><td>The session to which this element belongs.</td>
 * 	</tr>
 * 	<tr>
 * 		<td><code>Life Cycle</code></td><td>The current state in life cycle.
 * 		</td>
 * 	</tr>
 * </table>
 * 
 * <p>The relationship between {@literal Id} and {@literal Parent} in the
 * tree composition represents the heart of the HappyTree API. The tree is
 * assembled strictly by this relation, in which the parent of an element
 * references the id of the parent element. Therefore, the id of an element can
 * never be <code>null</code>, and if the parent of an element is <code>null</code>
 * or not found, that element is moved to the root level of the tree.</p>
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
	 * <p><b>The <code>id</code> must be unique. If it is
	 * <code>null</code>, nothing happens because this method is used for updates, so
	 * the element keeps the old id.</b></p>
	 * 
	 * <p>When the element is a root element, the id is not set.</p>
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
	 * root level of the tree when it is going to be persisted or updated.</p>
	 * 
	 * <p>When the element is a root element, the <code>parent</code> is not
	 * set.</p>
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
	 * <p>The search is performed recursively within the children of this 
	 * element starting by the current element itself. If the element is not
	 * found, then <code>null</code> is returned.</p>
	 * 
	 * @param id the element identifier to be searched for
	 * 
	 * @return the found element within this one
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
	 * <p>The <code>child</code> element reference is removed if it is found in the
	 * children list. When an element is removed, all of its children as well as
	 * the elements below in the hierarchy are also removed, recursively.</p>
	 * 
	 * @param child the element to be removed
	 */
	public void removeChild(Element<T> child);
	
	/**
	 * Removes the element from the children list of the current element by the
	 * <code>id</code> passed.
	 * 
	 * <p>If it exists, then the element and all of its children are removed
	 * as well.</p>
	 * 
	 * @param id the element identifier to be searched for and removed
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
	 * <p>To be effectively wrapped within the tree session, the invocation of
	 * {@link TreeManager#persistElement(Element)} (for new elements) or
	 * {@link TreeManager#updateElement(Element)} (for changing objects nodes)
	 * is required, depending on the context.</p>
	 * 
 * <p>The first one would be to create an empty tree. In this way, the
 * wrapped node is determined after the tree is ready by invoking this
 * method. This includes the choice of not determining it, leaving it with a
 * <code>null</code> value.</p>
	 * 
 * <p>The last one happens in the <b>API Transformation Process</b>, that
 * is, when at the moment of initialization, before the tree is created, a call
 * to {@link TreeTransaction#initializeSession(String, Collection)}
 * transforms a previous linear structure into a real hierarchical
 * tree structure. Then, each <code>Element</code> object will wrap its
 * respective node automatically.</p>
	 * 
 * <p>Therefore, each object node of this linear structure would be
 * represented precisely by this encapsulated object within its respective
 * <code>Element</code> object in its respective hierarchy inside the
 * tree. In this case, during the <b>API Transformation Process</b> life
 * cycle, the object node will be transformed, managed, and wrapped within
 * its own element automatically.</p>
	 * 
	 * <p>There are some requirements for this object node to be wrapped when
	 * initializing a session by the API Transformation Process:</p>
	 * 	<ul>
	 * 		<li>The class of this node must implement <code>Serializable</code>;
	 * 		</li>
	 * 		<li>The class of this node must be annotated by <code>@Tree</code>;
	 * 		</li>
	 * 		<li>The class of this node must be annotated by <code>@Parent</code>;
	 * 		</li>
	 * 		<li>The class of this node must be annotated by <code>@Id</code>;
	 * 		</li>
	 * 		<li>The <code>@Id</code> value cannot be <code>null</code>;
	 * 		</li>
	 * 		<li>The <code>@Id</code> and <code>@Parent</code> must be of the
	 * 		same type.</li>	
	 * 	</ul>
	 * 
	 * <p>Those requirements are only applied when the session is going to be
	 * initialized by the <b>API Transformation Process</b>.</p>
	 * 
 * <p>When the element is a root element, it is not possible to wrap it. So,
 * the wrapped object node is always <code>null</code>.</p>
	 * 
	 * @param object the node to be wrapped within the element
	 * 
	 */
	public void wrap(T object);
	
	/**
	 * Obtains a copy of the object node wrapped in this element.
	 * 
	 * <p>The object node itself cannot be changed. This method just
	 * provides a way to access the object node wrapped within this element. To
	 * modify its state, consider using the {@link #wrap(Object)} method and
	 * save the changes by invoking the
	 * {@link TreeManager#updateElement(Element)} method.</p>
	 * 
	 * @return a copy of the wrapped object node inside this element
	 * 
	 * @see #wrap(Object)
	 */
	public T unwrap();
	
	/**
	 * Returns the {@link TreeSession} instance to which this element belongs.
	 * 
	 * <p>When an element is created, either through the <b>API Transformation
	 * Process</b> or normally through invoking
	 * {@link TreeManager#createElement(Object, Object, Object)}, the current
	 * session of the transaction is associated with the created element.</p>
	 * 
	 * <p>Therefore, an element is <b>always</b> associated with a session.</p>
	 * 
	 * @return the session to which this element belongs
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
 * <b>For all operations where the life cycle does not correspond, a
 * {@link TreeException} will be thrown, except for
 * {@link TreeManager#containsElement(Element, Element)} which just returns
 * <code>false</code> when the elements are not <i>ATTACHED</i>.</b>
	 * 
 * <p>All operations returned by <code>TreeManager</code> change the
 * element's state to <i>ATTACHED</i>, except
 * {@link TreeManager#createElement(Object, Object, Object)} (NOT_EXISTED).
 * </p>
	 * 
	 * @return <code>String</code> corresponding the life cycle state name
	 */
	public String lifecycle();

	/**
	 * Converts the whole element structure into a JSON format. This includes 
	 * all children recursively.
	 * 
 * <p>It is mandatory that the element as well as all its children have non-
 * <code>null</code> wrapped object nodes. If there is at least one wrapped
 * object node that is <code>null</code>, then an empty JSON object is
 * returned &quot;{}&quot;.</p>
	 * 
 * <p><b>The above restriction is not applied to the root element</b>, as it
 * is a special element created by the HappyTree API itself with no wrapped
 * object node. Therefore, calling {@link TreeManager#root()} or
 * {@link TreeSession#tree()} is allowed to print the tree structure in a
 * JSON format, but this structure will not contain the <code>@Id</code>,
 * <code>@Parent</code>, or the wrapped object node.</p>
	 * 
	 * <p>The JSON representation consists of the attributes of the wrapped
	 * object node plus an attribute called <b>children</b>, which holds all the
	 * children of the node and so on recursively:</p>
	 * 
	 * <ul>
	 * 	<li><b>Object Node Attributes</b>: all getters attributes from the class
	 * 	annotated with <code>@Tree</code> annotation;</li>
	 * 	<li><b>Children Attribute</b>: the same structure but placed as child in
	 * 	a recursive way.</li>
	 * </ul>
	 * 
	 * <p>For example, considering an element that wraps an object of type
	 * <code>Directory</code>, the JSON representation would look like 
	 * <b>(pretty print)</b>:</p>
	 * 
	 * <pre>
	 * {
	 *	"identifier": 1,
	 *	"parentIdentifier": null,
	 *	"name": "Music"
	 *	"children": [
	 *		{
	 *			"identifier": 2,
	 *			"parentIdentifier": 1,
	 *			"name": "Country"
	 *			"children": [
	 *				{
	 *					"identifier": 3,
	 *					"parentIdentifier": 2,
	 *					"name": "Bruce Springsteen",
	 *					"children": []
	 *				},
	 *				{
	 *					"identifier": 4,
	 *					"parentIdentifier": 2,
	 *					"name": "George Strait",
	 *					"children": []
	 *				}
	 *			]
	 *		}
	 *	]
	 * }
	 * </pre>
	 * 
	 * <p>To convert the wrapped object into a JSON format, it is not necessary
	 * that the element be attached to any session. However, the element as well
	 * as all its children need to have their original objects nodes not
	 * <code>null</code> (except for the root element).</p>
	 * 
	 * <p>For conversion, the Jackson library is used internally, so the wrapped
	 * object can be annotated with Jackson annotations to customize the
	 * conversion if necessary.</p>
	 * 
	 * @return the JSON representation of this element (minified)
	 */
	public String toJSON();

	/**
	 * Converts the whole element structure into a well formatted JSON
	 * <code>String</code>. This includes all children recursively.
	 * 
 * <p>It is mandatory that the element as well as all its children have non-
 * <code>null</code> wrapped object nodes. If there is at least one wrapped
 * object node that is <code>null</code>, then an empty JSON object is
 * returned &quot;{}&quot;.</p>
 * 
 * <p><b>The above restriction is not applied to the root element</b>, as it
 * is a special element created by the HappyTree API itself with no wrapped
 * object node. Therefore, calling {@link TreeManager#root()} or
 * {@link TreeSession#tree()} is allowed to print the tree structure in a
 * JSON format, but this structure will not contain the <code>@Id</code>,
 * <code>@Parent</code>, or the wrapped object node.</p>
	 * 
	 * @return a well formatted JSON of this element (pretty print)
	 * 
	 * @see {@link Element#toJSON()}
	 */
	public String toPrettyJSON();

	/**
	 * Converts the whole element structure into a XML <code>String</code>. This
	 * includes all children recursively.
	 * 
 * <p>It is mandatory that the element as well as all its children have non-
 * <code>null</code> wrapped object nodes. If there is at least one
 * wrapped object node that is <code>null</code>, then an empty XML is
 * returned.</p>
	 * 
 * <p><b>The above restriction is not applied to the root element</b>, as it
 * is a special element created by the HappyTree API itself with no wrapped
 * object node. Therefore, calling {@link TreeManager#root()} or
 * {@link TreeSession#tree()} is allowed to print the tree structure as
 * an XML <code>String</code>, but this structure will not contain the
 * <code>@Id</code>, <code>@Parent</code>, or the wrapped object node.
 * </p>
	 * 
 * <p>The XML content consists of the attributes of the wrapped object node
 * plus a tag called <b>children</b>, which holds all other elements that
 * contain all other children of the node and so on recursively. The root
 * tag of this XML is always <b>element</b>.</p>
	 * 
	 * <p>For example, considering an element that wraps an object of type
	 * <code>Directory</code>, the XML content would look like 
	 * <b>(pretty print)</b>:</p>
	 * 
	 * <pre>
	 * 	&lt;element&gt;
	 * 		&lt;identifier&gt;1&lt;/identifier&gt;
	 * 		&lt;parentIdentifier&gt;null&lt;/parentIdentifier&gt;
	 * 		&lt;name&gt;Music&lt;/name&gt;
	 * 		&lt;children&gt;
	 * 			&lt;element&gt;
	 * 				&lt;identifier&gt;2&lt;/identifier&gt;
	 * 				&lt;parentIdentifier&gt;1&lt;/parentIdentifier&gt;
	 * 				&lt;name&gt;Country&lt;/name&gt;
	 * 				&lt;children&gt;
	 * 					&lt;element&gt;
	 * 						&lt;identifier&gt;3&lt;/identifier&gt;
	 * 						&lt;parentIdentifier&gt;2&lt;/parentIdentifier&gt;
	 * 						&lt;name&gt;Bruce Springsteen&lt;/name&gt;
	 * 						&lt;children/&gt;
	 * 					&lt;/element&gt;
	 * 					&lt;element&gt;
	 * 						&lt;identifier&gt;4&lt;/identifier&gt;
	 * 						&lt;parentIdentifier&gt;2&lt;/parentIdentifier&gt;
	 * 						&lt;name&gt;George Strait&lt;/name&gt;
	 * 						&lt;children/&gt;
	 * 					&lt;/element&gt;
	 * 				&lt;/children&gt;
	 * 			&lt;/element&gt;
	 * 		&lt;/children&gt;
	 * 	&lt;/element&gt;
	 * </pre>
	 * 
	 * <p>To convert the wrapped object into a XML format, it is not necessary
	 * that the element be attached to any session. However, the element as well
	 * as all its children need to have their original objects nodes not
	 * <code>null</code> (except for the root element).</p>
	 * 
	 * <p>For conversion, the Jackson library is used internally, so the wrapped
	 * object can be annotated with Jackson annotations to customize the
	 * conversion if necessary.</p>
	 * 
	 * @return the XML content of this element (minified)
	 */
	public String toXML();

	/**
	 * Converts the whole element structure into a well formatted XML
	 * <code>String</code>. This includes all children recursively.
	 * 
 * <p>It is mandatory that the element as well as all its children have non-
 * <code>null</code> wrapped object nodes. If there is at least one wrapped
 * object node that is <code>null</code>, then an empty XML is returned.</p>
 * 
 * <p><b>The above restriction is not applied to the root element</b>, as it
 * is a special element created by the HappyTree API itself with no wrapped
 * object node. Therefore, calling {@link TreeManager#root()} or
 * {@link TreeSession#tree()} is allowed to print the tree structure as
 * an XML <code>String</code>, but this structure will not contain the
 * <code>@Id</code>, <code>@Parent</code>, or the wrapped object node.
 * </p>
	 * 
	 * @return a well formatted XML of this element (pretty print)
	 * 
	 * @see {@link Element#toXML()}
	 */
	public String toPrettyXML();

	/**
	 * Searches for elements that satisfy a specific condition within this
	 * element and its children recursively. The method returns a list of 
	 * elements that match the provided condition.
	 * 
	 * <p>This method traverses the subtree starting from this element
	 * (including the element itself) and evaluates each element against the
	 * specified condition. If an element satisfies the condition, it is
	 * included in the resulting list.</p>
	 * 
	 * <p>The resulting list consists of all elements that match the specified
	 * condition, <b>including their children</b>. Therefore, the list will have
	 * the elements that satisfy the condition but within of each element there
	 * will have its hierarchical structure preserved.</p>
	 * 
	 * <p>When this method is invoked by the root element, the search is
	 * performed on all elements in the tree, <b>except for the root element
	 * itself</b>, as it is a special element created by the HappyTree API
	 * itself, not having the <code>@Id</code>, <code>@Parent</code> neither
	 * a wrapped object node.</p>
	 * 
	 * <p><b>Example usage:</b></p>
	 * <pre>
	 * //Find all elements within this subtree which the object node has its
	 * //name starting with "A"
	 * List&lt;Element&lt;MyNodeType&gt;&gt; results = element.search(
	 *     e -&gt; e.unwrap().getName().startsWith("A")
	 * );
	 * </pre>
	 * 
	 * @param condition the predicate function defining the search criteria
	 * 
	 * @return a list of elements that satisfy the specified condition within
	 * this element's subtree
	 */
	public List<Element<T>> search(Predicate<Element<T>> condition);

	/**
	 * Applies a function to be performed on this element and all its children
	 * recursively within the tree structure. The action applied to the elements
	 * is not automatically reflected on the tree session, thus requiring to
	 * invoke the {@link TreeManager#persistElement(Element)} for new elements
	 * or {@link TreeManager#updateElement(Element)} to save the changes for
	 * already existing elements.
	 * 
	 * <p>This method traverses the entire subtree starting from this element
	 * (including the element itself) and applies the given action to all
	 * elements in the tree.</p>
	 * 
	 * <p>The action function receives each {@link Element} as a parameter and
	 * can perform any operation on it, such as modifying the wrapped object,
	 * changing element properties, or performing calculations/verifications.
	 * </p>
	 * 
	 * <p>When this method is invoked by the root element, the action is applied
	 * to all elements in the tree, <b>except for the root element itself</b>,
	 * as it is a special element created by the HappyTree API itself, not
	 * having the <code>@Id</code>, <code>@Parent</code> neither a wrapped
	 * object node.</p>
	 * 
	 * <p><b>Example usage:</b></p>
	 * <pre>
	 * //Transform all directory names to uppercase
	 * element.apply(e -&gt; e.unwrap().transformNameToUpperCase());
	 * 
	 * //Log all element IDs in the subtree
	 * element.apply(e -&gt; System.out.println("Element ID: " + e.getId()));
	 * </pre>
	 * 
	 * <p><b>Note:</b> The function is applied to <b>all</b> elements in the
	 * subtree (<b>except for the root element itself</b>). If you need
	 * conditional execution based on specific criteria, consider using
	 * {@link #apply(Consumer, Predicate)} instead.</p>
	 * 
	 * @param action the function to apply to each element in the
	 * subtree
	 * 
	 * @see #apply(Consumer, Predicate)
	 */
	public void apply(Consumer<Element<T>> action);

	/**
	 * Applies a function to be performed within this element that satisfy the
	 * specified condition. The action applied to the elements is not
	 * automatically reflected on the tree session, thus requiring to invoke the
	 * {@link TreeManager#persistElement(Element)} for new elements
	 * or {@link TreeManager#updateElement(Element)} to save the changes for
	 * already existing elements.
	 * 
	 * <p>This method traverses the entire subtree starting from this element
	 * (including the element itself) and applies the given action only to those
	 * elements that match the specified predicate condition.</p>
	 * 
	 * <p>The condition predicate is evaluated for each element in the subtree,
	 * and the action is only performed on elements where the predicate returns
	 * {@code true}. This allows for fine-grained control over which elements
	 * are affected by the function.</p>
	 * 
	 * <p>When this method is invoked by the root element, the action is applied
	 * to all elements that match the condition in the tree, <b>except for the
	 * root element itself</b>, as it is a special element created by the
	 * HappyTree API itself, not having the <code>@Id</code>,
	 * <code>@Parent</code> neither a wrapped object node.</p>
	 * 
	 * <p><b>Example usage:</b></p>
	 * <pre>
	 * //Transform only elements containing "photo" in their name
	 * element.apply(
	 *     e -&gt; e.unwrap().transformNameToUpperCase(),
	 *     e -&gt; e.unwrap().getName().toLowerCase().contains("photo")
	 * );
	 * 
	 * //Apply action only to leaf elements (elements with no children)
	 * element.apply(
	 *     e -&gt; processLeafElement(e),
	 *     e -&gt; e.getChildren().isEmpty()
	 * );
	 * </pre>
	 * 
	 * <p><b>Note:</b> Unlike {@link #apply(Consumer)}, the function is applied
	 * only for elements that satisfy the condition passed as parameter.
	 * Elements that don't match the condition remain unchanged, providing
	 * selective capabilities.</p>
	 * 
	 * @param action the function to apply to matching elements
	 * @param condition the predicate that determines which elements should
	 * receive the action
	 * 
	 * @see #apply(Consumer)
	 */
	public void apply(
		Consumer<Element<T>> action, Predicate<Element<T>> condition);
}