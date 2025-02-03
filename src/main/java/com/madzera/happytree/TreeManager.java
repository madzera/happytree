package com.madzera.happytree;

import com.madzera.happytree.exception.TreeException;

/**
 * Provides ways of handling elements within the tree session. With this, turn
 * on possible to create, persist, update, cut, copy, remove, retrieve elements
 * and any other operations within the tree.
 * 
 * <p>This interface works directly handling objects represented by the 
 * {@link Element} interface, where each one behaves similarly to a node within 
 * the tree. So, In practical terms, this interface allows the API client to 
 * handle business objects with tree-like behavior similar to
 * <i>JavaScript DOM</i>.</p>
 * 
 * <p>The operations here are done for elements within others or for "roots" 
 * elements. The roots elements are considered to be those that are not inside
 * of other elements, but those that are at the "top" of the tree hierarchy in 
 * question. When the tree is created, automatically its root also are created,
 * with this root being empty or having various children.</p>
 * 
 * <p>The operations also are done for cases where this is desirable reallocate
 * elements for inside of other trees sessions. In this case, the oneness of
 * each element must be respected, related to the tree which elements will be
 * inside.</p>
 * 
 * <p>This is important to note that there are two distinct and well-defined
 * contexts in the HappyTree API. The inside and outside contexts. When the
 * API client obtains an element and its children through the manager, the
 * API client is actually working with identical copies of each node (element).
 * When the API client makes any changes to any of the elements, a change in the
 * element's life cycle is made, and this change is not immediately reflected in
 * the tree in question. This context is called outside of the tree. An inside
 * context represents the client's action to perform the persist/update of the
 * element, so the change is actually reflected in the tree, but made through
 * this manager itself. The persist/update operations work by invoking the
 * {@link TreeManager#persistElement(Element)} and
 * {@link #updateElement(Element)} respectively.</p>
 * 
 * <p>Conceptually, this interface works by handling trees through a transaction.
 * This transaction is represented by the {@link TreeTransaction} interface and
 * the relation between both interfaces is 1:1. Therefore, this interface will
 * always be linked to a single transaction, which can contain no one or many
 * sessions, but this is only possible to handle one session at the time.</p>
 * 
 * <p>The API client must be sure to handle elements through their respective
 * transactions. A simple swap of a tree, by a transaction, makes this manager
 * ready to deal with a totally different tree.</p>
 * 
 * <p>For operations to work, the following validations are made:</p>
 * <ul>
 * 		<li>The transaction must have a session binded, <b>always</b>.</li>
 * 		<li>This is <b>mandatory</b> that the session be <b>activated.</b></li>
 * 		<li>
 * 			According with the method, the elements must have in the proper
 * 			state.
 * 		</li>
 * 		<li>for each tree session, each element inside must have unique id.</li>
 * 		<li>
 * 			An element cannot be handle within trees which have different
 * 			types of wrapped nodes.
 * 		</li>
 * 		<li>This is not possible to handle roots elements.</li>
 * </ul>
 * 
 * <p>If one of these validations fail, an exception will be thrown.</p>
 * 
 * <table summary="Operations by element state.">
 * 	<tr>
 * 		<th>Element State/operation</th><th>Attached</th><th>Detached</th>
 * 		<th>Not Existed</th>
 * 	</tr>
 * 	<tr>
 * 		<td>Cut</td><td>&#10004;</td><td>X</td><td>X</td>
 * 	</tr>
 * 	<tr>
 * 		<td>Copy</td><td>&#10004;</td><td>X</td><td>X</td>
 * 	</tr>
 * 	<tr>
 * 		<td>Remove</td><td>&#10004;</td><td>X</td><td>X</td>
 * 	</tr>
 * 	<tr>
 * 		<td>Update</td><td>&#10004;</td><td>&#10004;</td><td>X</td>
 * 	</tr>
 * 	<tr>
 * 		<td>Persist</td><td>X</td><td>X</td><td>&#10004;</td>
 * 	</tr>
 * </table>
 * 
 * @author Diego Madson de Andrade Nóbrega
 * 
 * @see TreeSession
 *
 */
public interface TreeManager {
	
	/**
	 * Cuts the <code>from</code> element for inside of the <code>to</code>
	 * element, whether for the same session or not. With this, the element to
	 * be cut can be cut into the same tree session or to another tree in
	 * another session. All children of <code>from</code> element will be cut
	 * too.
	 * 
	 * <p>If the <code>to</code> parameter element is <code>null</code> then the
	 * <code>from</code> element with all children will be moved to the root
	 * level of the same tree.</p>
	 * 
	 * <p>When cutting for the target element, the <code>from</code> parameter
	 * element cannot have the duplicated identifier in the tree where the
	 * <code>to</code> parameter element is in. It includes also the children
	 * identifiers of the <code>from</code> element.</p>
	 * 
	 * <p>This is imperative that both trees of the <code>from</code> and
	 * <code>to</code> elements must be activated.</p>
	 * 
	 * @param <T> the class type of the wrapped node that will be encapsulated
	 * into the {@link Element} object
	 * 
	 * @param from the source element
	 * 
	 * @param to the target element
	 * 
	 * @return a copy of <code>from</code> element after cut
	 * 
	 * @throws TreeException when:
	 * <ul>
	 * 	<li>
	 * 		The transaction has no selected session to work;
	 * 	</li>
	 * 	<li>
	 * 		The current session or the session which the <code>to</code> element
	 * 		belongs is not active;
	 * 	</li>
	 * 	<li>
	 * 		The <code>from</code> element does not belong in the correct current
	 * 		session;
	 * 	</li>
	 * 
	 * 	<li>
	 * 		The <code>from</code> and <code>to</code> elements have different
	 * 		types of wrapped nodes related to the current session;
	 * 	</li>
	 * 	<li>
	 * 		The <code>from</code> element is represented by a root of tree (this
	 * 		is no possible to handle root elements);
	 * 	</li>
	 * 	<li>
	 * 		The <code>from</code> or <code>to</code> element or at least one of
	 * 		their children have a DETACHED or NOT_EXISTED state in life cycle;
	 * 	</li>
	 * 	<li>
	 * 		The <code>from</code> element has an already existing identifier in
	 * 		the target tree (if the <code>to</code> element is in another tree).
	 * 	</li>
	 * </ul>
	 * 
	 * @throws IllegalArgumentException when the <code>from</code> parameter is
	 * <code>null</code>
	 */
	public <T> Element<T> cut(Element<T> from, Element<T> to) 
			throws TreeException;
	
	/**
	 * Cuts the <code>from</code> element for inside of the <code>to</code>
	 * element, <b>just inside of the same session</b>. Both parameters
	 * represent the id of elements.
	 * 
	 * <p>If the <code>to</code> parameter element is <code>null</code> or if
	 * its respective element is not found, then the <code>from</code> element
	 * with all children will be moved to the root level of the tree.</p>
	 * 
	 * <p>If this is no possible to find out the <code>from</code> element id
	 * passed through parameter then <code>null</code> is returned.</p>
	 * 
	 * <p>Using this <code>cut(Object, Object)</code> operation, an element just
	 * can be cut into the same tree. To cut elements for other trees consider
	 * using the {@link #cut(Element, Element)} which the target element is
	 * linked to another tree.</p>
	 * 
	 * @param <T> the class type of the wrapped node that will be encapsulated
	 * into the {@link Element} object
	 * 
	 * @param from the source element
	 * 
	 * @param to the target element
	 * 
	 * @return a copy of the element represented by the <code>from</code>
	 * parameter, after cut
	 * 
	 * @throws TreeException when:
	 * <ul>
	 * 	<li>
	 * 		The transaction has no selected session to work;
	 * 	</li>
	 * 	<li>
	 * 		The current session is not active;
	 * 	</li>
	 * 	<li>
	 * 		The <code>from</code> element does not belong in the correct current
	 * 		session;
	 * 	</li>
	 * 
	 * 	<li>
	 * 		The <code>from</code> element is represented by a root of tree (this
	 * 		is no possible to handle root elements).
	 * 	</li>
	 * </ul>
	 * 
	 * @throws IllegalArgumentException when the <code>from</code> parameter is
	 * <code>null</code>
	 */
	public <T> Element<T> cut(Object from, Object to) throws TreeException;
	
	/**
	 * Copies the respective <code>from</code> element into the <code>to</code>
	 * element <b>in another tree session</b>. All structure of copied element
	 * will be paste inside of <code>to</code> element. The element to be copied
	 * and its all children cannot have the same identifier than any element in
	 * the <code>to</code> element tree.
	 * 
	 * <p><b>This method only should be used when the client desires copy the
	 * element between different trees. This is no possible to copy elements
	 * inside of the same own tree, because it will throw duplicated <i>Id</i> 
	 * exception.</b></p>
	 * 
	 * <p>The following steps are done internally inside of the core API to copy
	 * an element:</p>
	 * 
	 * 		<ol>
	 * 			<li>Validates the current session and the input;</li>
	 * 			<li>Copies the whole element structure with all the children;
	 * 			</li>
	 * 			<li>Invokes {@link TreeTransaction#sessionCheckout(String)} to 
	 * 			the target tree be able to be worked;</li>
	 * 			<li>Pastes the elements;</li>
	 * 			<li>Invokes {@link TreeTransaction#sessionCheckout(String)} to 
	 * 			the source tree, like before.</li>
	 * 		</ol>
	 * 
	 * <p>This is mandatory that the both <code>from</code> and <code>to</code>
	 * elements be attached in different trees, and both of trees must be
	 * activated.</p>
	 * 
	 * @param <T> the class type of the wrapped node that will be
	 * encapsulated into the {@link Element} object
	 * 
	 * @param from the source element
	 * 
	 * @param to the target element
	 * 
	 * @return a copy of the copied element in the target tree session
	 * 
	 * @throws TreeException when:
	 * <ul>
	 * 	<li>
	 * 		The transaction has no selected session to work;
	 * 	</li>
	 * 	<li>
	 * 		The current session or the session which the <code>to</code> element
	 * 		belongs is not active;
	 * 	</li>
	 * 	<li>
	 * 		The <code>from</code> element does not belong in the correct current
	 * 		session;
	 * 	</li>
	 * 
	 * 	<li>
	 * 		The <code>from</code> and <code>to</code> elements have different
	 * 		types of wrapped nodes related to the current session;
	 * 	</li>
	 * 	<li>
	 * 		The <code>from</code> element is represented by a root of tree (this
	 * 		is no possible to handle root elements);
	 * 	</li>
	 * 	<li>
	 * 		The <code>from</code> or <code>to</code> element or at least one of
	 * 		their children have a DETACHED or NOT_EXISTED state in life cycle;
	 * 	</li>
	 * 	<li>
	 * 		The <code>from</code> element has an already existing identifier in
	 * 		the target tree.
	 * 	</li>
	 * </ul>
	 * 
	 * @throws IllegalArgumentException when the <code>from</code> or
	 * <code>to</code> parameters are <code>null</code>
	 */
	public <T> Element<T> copy(Element<T> from, Element<T> to) 
			throws TreeException;
	
	/**
	 * Removes the corresponding element from the tree session and returns the
	 * own removed element. After removed, the element and all its children will
	 * have the <i>NOT_EXISTED</i> state in life cycle. In a case of reinsert
	 * this removed element, then the same should be persisted again.
	 * 
	 * <p>The element to be removed must be attached (<i>ATTACHED</i> state) in
	 * the tree and cannot get changes. All children will be removed too, if
	 * they are with the <i>ATTACHED</i> state in life cycle. If there is at
	 * least a single child element that is not <i>ATTACHED</i>, then no one
	 * will be removed and this method will return <code>null</code>.</p>
	 * 
	 * <p>In case, if the <code>element</code> or its children is not attached,
	 * then it is necessary to attach them in the current tree session by 
	 * invoking {@link #persistElement(Element)} if it is a new element or 
	 * invoking {@link #updateElement(Element)} if it is a changed element. 
	 * After that, the <code>element</code> in question turns on attached again
	 * and it can be removed after that.</p>
	 * 
	 * <p>If the <code>element</code> parameter is <code>null</code> then this
	 * method also will return <code>null</code>.</p>
	 * 
	 * @param <T> the class type of the wrapped node that will be encapsulated
	 * into the {@link Element} object
	 * 
	 * @param element the element to be removed with all its children
	 * 
	 * @return the own removed element itself, but now with the
	 * <i>NOT_EXISTED</i> state in life cycle
	 * 
	 * @throws TreeException when:
	 * <ul>
	 * 	<li>
	 * 		The transaction has no selected session to work;
	 * 	</li>
	 * 	<li>
	 * 		The current session is not active;
	 * 	</li>
	 * 	<li>
	 * 		The <code>from</code> element does not belong in the correct current
	 * 		session;
	 * 	</li>
	 * 
	 * 	<li>
	 * 		The <code>element</code> has different type of wrapped node	related
	 * 		to the current session;
	 * 	</li>
	 * 	<li>
	 * 		The <code>element</code> is represented by a root of tree (this is
	 * 		no possible to handle root elements);
	 * 	</li>
	 * 	<li>
	 * 		The <code>element</code> or at least one of	its children have a
	 * 		DETACHED or NOT_EXISTED state in life cycle;
	 * 	</li>
	 * </ul>
	 */
	public <T> Element<T> removeElement(Element<T> element) throws TreeException;
	
	/**
	 * Removes the element by its <code>id</code>. All children of the found
	 * element are removed too and returns the own removed element. Realize that
	 * removing an element means that this element will be permanently
	 * eliminated from inside of the tree, having its state as
	 * <i>NOT_EXISTED</i> in life cycle.
	 * 
	 * <p>If the id is not to be able to be found or <code>null</code>, then
	 * this method will return <code>null</code>.</p>
	 * 
	 * <p>Be sure of being in the correct tree session, for not remove an
	 * element with the same id but in another tree.</p>
	 * 
	 * @param <T> the class type of the wrapped node that will be encapsulated
	 * into the {@link Element} object
	 * 
	 * @param id the identifier of the element to be removed
	 * 
	 * @return the own removed element itself, but with the <i>NOT_EXISTED</i>
	 * state in life cycle
	 * 
	 * @throws TreeException when:
	 * <ul>
	 * 	<li>
	 * 		The transaction has no selected session to work;
	 * 	</li>
	 * 	<li>
	 * 		The current session is not active;
	 * 	</li>
	 * 
	 * 	<li>
	 * 		The element id is represented by a root of tree (this is no
	 * 		possible to handle root elements);
	 * 	</li>
	 * </ul>
	 */
	public <T> Element<T> removeElement(Object id) throws TreeException;
	
	/**
	 * Obtains an element by <code>id</code> in the current tree session.
	 * 
	 * <p>If the <code>id</code> is <code>null</code> or it is not be able to be
	 * found in the tree or then this method will return <code>null</code>.</p>
	 * 
	 * <p>The id corresponds to the {@literal @Id} annotated attribute of the
	 * wrapped node in the <b>Transformation Process</b> or just an id which
	 * the API client choose. When a tree is being built by a previous
	 * collection of objects (<b>Transformation Process</b>), the core API will
	 * bind the elements by this {@literal @Id} annotated attribute.</p>
	 * 
	 * <p>Therefore:</p>
	 * <ul>
	 * 		<li><b>The {@literal @Id} never can be <code>null</code>;</b></li>
	 * 		<li><b>The {@literal @Id} always is <i>unique</i> in the tree.</b>
	 * 		</li>
	 * </ul>
	 * 
	 * <p>The element and its children returned by this method represents
	 * elements with <i>ATTACHED</i> state in relation with the current tree
	 * session. An attached element means that the element and its children are
	 * &quot;mirrors&quot; pieces of the tree.</p>
	 * 
	 * <p>Changing the element or the children states, then this is necessary to
	 * attach them again in the current tree session by invoking 
	 * {@link #updateElement(Element)} to be able of handling those, for
	 * operation like {@link #cut(Element, Element)} or 
	 * {@link #copy(Element, Element)} for example.</p>
	 * 
	 * @param <T> the class type of the wrapped node that will be encapsulated
	 * into the {@link Element} object
	 * 
	 * @param id the element identifier
	 * 
	 * @return an identical copy of the found element
	 * 
	 * @throws TreeException when the transaction has no selected session to
	 * work or the current session is not active
	 */
	public <T> Element<T> getElementById(Object id) throws TreeException;
	
	/**
	 * Verifies whether the <code>parent</code> element contains inside of it
	 * the <code>descendant</code> element in this current session.
	 * 
	 * <p>If both <code>parent</code> and <code>descendant</code> element is
	 * <code>null</code> or their (including the children) state are not
	 * <i>ATTACHED</i> to this tree session, then <code>false</code> is
	 * returned.</p>
	 * 
	 * @param <T> the class type of the wrapped node that will be encapsulated
	 * into the {@link Element} object
	 * 
	 * @param parent the element which will contain the <code>descendant</code>
	 * element
	 * 
	 * @param descendant the element which is inside of <code>parent</code>
	 * element
	 * 
	 * @return <code>true</code> value if the <code>parent</code> element
	 * contains the <code>descendant</code> element, <code>false</code>
	 * otherwise
	 * 
	 * @throws TreeException when the transaction has no selected session to
	 * work or the current session is not active
	 */
	public <T> boolean containsElement(Element<T> parent, Element<T> descendant)
			throws TreeException;
	
	/**
	 * Verifies whether the <code>parent</code> element contains inside of it
	 * the <code>descendant</code> element in this current session.
	 * 
	 * <p>The <code>parent</code> and <code>descendant</code> identifiers are
	 * used to bring the respective elements. If both <code>parent</code> and
	 * <code>descendant</code> elements are <code>null</code> or not found, then
	 * <code>false</code> is returned.</p>
	 * 
	 * <p>When the elements are found, then it means that they were already
	 * caught from a tree and they are automatically attached, so this method
	 * returns <code>true</code> when the <code>descendant</code> element is
	 * inside of the <code>parent</code> element.</p>
	 * 
	 * @param parent the parent identifier which will contain the
	 * <code>descendant</code> element
	 * 
	 * @param descendant the child identifier which will be inside of the
	 * <code>parent</code> element
	 * 
	 * @return <code>true</code> value if the <code>parent</code> element
	 * contains the <code>descendant</code> element, <code>false</code>
	 * otherwise
	 * 
	 * @throws TreeException when the transaction has no selected session to
	 * work or the current session is not active
	 */
	public boolean containsElement(Object parent, Object descendant) 
			throws TreeException;
	
	/**
	 * Verifies that the current tree session has the specified
	 * <code>element</code>.
	 * 
	 * <p>If the <code>element</code> is <code>null</code> or if the
	 * <code>element</code> or at least one of its children has not been in
	 * <i>ATTACHED</i> state in life cycle then <code>false</code> is returned.
	 * </p>
	 * 
	 * @param element the specified element to be searched
	 * 
	 * @return <code>true</code> value if the tree contains the
	 * <code>element</code>, <code>false</code> otherwise
	 * 
	 * @throws TreeException when the transaction has no selected session to
	 * work or the current session is not active
	 */
	public boolean containsElement(Element<?> element) throws TreeException;
	
	/**
	 * Verifies that the current tree session has the specified element
	 * <code>id</code>.
	 * 
	 * <p>If the element is not found in the tree or the <code>id</code> is
	 * <code>null</code> then <code>false</code> is returned.</p>
	 * 
	 * @param id the identifier of the element to be searched
	 * 
	 * @return <code>true</code> value if the tree contains the respective
	 * element, <code>false</code> otherwise
	 * 
	 * @throws TreeException when the transaction has no selected session to
	 * work or the current session is not active
	 */
	public boolean containsElement(Object id) throws TreeException;
	
	/**
	 * Creates an element with the <code>id</code>, <code>parent</code> and the
	 * wrapped node object. Only the <code>id</code> is mandatory. When the
	 * <code>parent</code> is null, then this element will be moved to the root
	 * level of the tree, when persisted.
	 * 
	 * <p>Creating a new element does not means that it will be automatically in
	 * the tree of the current session. When creating a new element, it is
	 * &quot;outside&quot; of the tree yet, having the <i>NOT_EXISTED</i> state
	 * in life cycle. The element needs to be attached in the tree right after
	 * the creation moment time, by invoking {@link #persistElement(Element)}.
	 * So, after that, the element becomes attached and finally can be handled
	 * by {@link #cut(Element, Element)} or {@link #copy(Element, Element)}
	 * operations for example.</p>
	 * 
	 * <p>Ensure that the parameterized type when creating an element is the
	 * same type related to the current session.</p>
	 * 
	 * @param <T> the class type of wrapped node that will be encapsulated
	 * into the {@link Element} object
	 * 
	 * @param id the identifier of the new element
	 * 
	 * @param parent the parent identifier of this new element
	 * 
	 * @param wrappedNode the object to be encapsulated in this element node
	 * 
	 * @return a new element containing the <i>NOT_EXISTED</i> state in life
	 * cycle
	 * 
	 * @throws TreeException when the transaction has no selected session to
	 * work or the current session is not active
	 * 
	 * @throws IllegalArgumentException when the <code>id</code> parameter is
	 * <code>null</code>
	 */
	public <T> Element<T> createElement(Object id, Object parent,
			T wrappedNode) throws TreeException;
	
	/**
	 * Persists a new element into the current tree session. The new element to
	 * be persisted must have a unique identifier in the tree session. If the
	 * {@literal @Parent} of this new element is defined with <code>null</code>
	 * or if it is simply not found, then this new element will be persisted in
	 * root level of the tree.
	 * 
	 * <p>Also, the new element must be essentially new, created using the
	 * {@link #createElement(Object, Object, Object)} method to guarantee a
	 * coherent detached state from the current tree. After creating the
	 * element, the same will have the <i>NOT_EXISTED</i> state in life cycle,
	 * even its children (in case of creating children inside this element).</p>
	 * 
	 * <p>This method also can allows new chained children elements to be
	 * persisted at once. If the element has duplicated identifier or there is a
	 * child in this new created element that has duplicated identifier in
	 * relation to the tree, then an exception will threw.</p>
	 * 
	 * <p>The <i>NOT_EXISTED</i> state represents a &quot;free up&quot; element,
	 * that means then this element is not been inside of none tree. When the
	 * element is persisted by this operation, its state turns on to
	 * <i>ATTACHED</i> in life cycle. The same happens with its children when
	 * they are persisted at once.</p>
	 * 
	 * <table summary="The element states which can be operated.">
	 * 	<tr>
	 * 	<th>Element Status</th><th>Ways</th><th>persistElement()</th>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>NOT_EXISTED</td>
	 * 		<td>{@link #createElement(Object, Object, Object)}</td>
	 * 		<td>Yes</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>ATTACHED</td>
	 * 		<td>{@link #getElementById(Object)}</td>
	 * 		<td>No</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>DETACHED</td>
	 * 		<td>{@link #getElementById(Object)} - 
	 * 		{@link Element#setId(Object)}</td><td>No</td>
	 * 	</tr>
	 * </table>
	 * 
	 * @param <T> the class type of the wrapped node that will be encapsulated
	 * into the {@link Element} object
	 * 
	 * @param newElement the element to be persisted
	 * 
	 * @return a copy of the new persisted element
	 * 
	 * @throws TreeException when:
	 * <ul>
	 * 	<li>
	 * 		The transaction has no selected session to work;
	 * 	</li>
	 * 	<li>
	 * 		The current session is not active;
	 * 	</li>
	 * 	<li>
	 * 		The <code>newElement</code> does not belong in the correct current
	 * 		session;
	 * 	</li>
	 * 
	 * 	<li>
	 * 		The <code>newElement</code> has different type of wrapped node
	 * 		related to the current session;
	 * 	</li>
	 * 	<li>
	 * 		The <code>newElement</code> or at least one of its children have a
	 * 		DETACHED or ATTACHED state in life cycle;
	 * 	</li>
	 * 	<li>
	 * 		The <code>newElement</code> has an already existing identifier in
	 * 		this session.
	 * 	</li>
	 * </ul>
	 * 
	 * @throws IllegalArgumentException when the <code>newElement</code> is
	 * <code>null</code>
	 */
	public <T> Element<T> persistElement(Element<T> newElement) 
			throws TreeException;
	
	/**
	 * Updates the state of the element to the tree. Synchronizes a previous
	 * changed element.
	 * 
	 * <p>To be updated, an element should be previous captured by invoking
	 * {@link #getElementById(Object)} for example, and its state should be as
	 * <i>DETACHED</i>.</p>
	 * 
	 * <p>A <i>DETACHED</i> element represents an element, or one of its
	 * children, in which it has undergone some change, whether it be the id,
	 * the parent id or the wrapped node. <b>Ensure yourself to avoid duplicated
	 * id in current session by an id change</b>. To move up the element for the
	 * root level, just set the parent id as <code>null</code> or reference an
	 * unknown parent id.</p>
	 * 
	 * <p>This operation is for <i>DETACHED</i> elements. Trying to update a
	 * <i>NOT_EXISTED</i> element in the tree, an exception is threw. For
	 * <i>ATTACHED</i> element nothing happens, because the element is already
	 * attached, just the same is returned.</p>
	 * 
	 * <p>Therefore, this is only possible to update a previously persisted
	 * element, then it is safe to say that before invoking this method, a
	 * previous invocation to the {@link #persistElement(Element)} turns on
	 * mandatory at some point.</p>
	 * 
	 * <p>Updating an element, also its children list will be automatically
	 * updated recursively. After updating, all elements have their states
	 * as <i>ATTACHED</i> in life cycle.</p>
	 * 
	 * <table summary="The element states which can be operated.">
	 * 	<tr>
	 * 	<th>Element Status</th><th>Ways</th><th>updateElement()</th>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>NOT_EXISTED</td>
	 * 		<td>{@link #createElement(Object, Object, Object)}</td>
	 * 		<td>No</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>ATTACHED</td>
	 * 		<td>{@link #getElementById(Object)}</td>
	 * 		<td>Yes</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>DETACHED</td>
	 * 		<td>{@link #getElementById(Object)} - 
	 * 		{@link Element#setId(Object)}</td><td>Yes</td>
	 * 	</tr>
	 * </table>
	 * 
	 * @param <T> the class type of the wrapped node that will be encapsulated
	 * into the {@link Element} object
	 * 
	 * @param element the element to be updated
	 * 
	 * @return a copy of the updated element
	 * 
	 * @throws TreeException when:
	 * <ul>
	 * 	<li>
	 * 		The transaction has no selected session to work;
	 * 	</li>
	 * 	<li>
	 * 		The current session is not active;
	 * 	</li>
	 * 	<li>
	 * 		The <code>element</code> does not belong in the correct current
	 * 		session;
	 * 	</li>
	 * 
	 * 	<li>
	 * 		The <code>element</code> has different type of wrapped node	related
	 * 		to the current session;
	 * 	</li>
	 * 	<li>
	 * 		The <code>element</code> is represented by a root of tree (this is
	 * 		no possible to handle root elements);
	 * 	</li>
	 * 	<li>
	 * 		The <code>element</code> or at least one of its children have a
	 * 		<i>NOT_EXISTED</i> state in life cycle;
	 * 	</li>
	 * 	<li>
	 * 		The <code>element</code> has an already existing identifier in this
	 * 		session (in an id change case).
	 * 	</li>
	 * </ul>
	 * 
	 * @throws IllegalArgumentException when the <code>element</code> is
	 * <code>null</code>
	 */
	public <T> Element<T> updateElement(Element<T> element) 
			throws TreeException;
	
	/**
	 * Obtains the {@link TreeTransaction} instance associated to this manager.
	 * 
	 * <p>The manager is closely related to the transaction. Absolutely every
	 * operations defined in this interface needs to check the transaction and
	 * verify if there is a session to be managed.</p>
	 * 
	 * <p>If there is no session to be handled inside the transaction, an error
	 * occurs. The API client of this method should know what session he wishes
	 * to work. This transaction object has this objective, provide and handle
	 * the sessions.</p>
	 * 
	 * <b>
	 * TREEMANAGER (invokes) -&gt; TREETRANSACTION (to store) -&gt;
	 * TREESESSION (that contains) -&gt; ELEMENT
	 * </b>
	 * 
	 * 
	 * @return the transaction associated to this manager
	 */
	public TreeTransaction getTransaction();
	
	/**
	 * Returns the root of the tree in this current session.
	 * 
	 * <p>The root element contains a collection of children, which in turn
	 * contains a collection of children, and so on. All this structure is
	 * returned in this method.</p>
	 * 
	 * <p>The root element represents the top of the tree and its id is always
	 * defined with the respective session identifier name. Because that, every
	 * node object with <code>null {@literal @Parent}</code> or an unknown
	 * {@literal @Parent} will be attached to the tree become immediately child
	 * of root (in the <b>API Transformation Process</b>). After that, its
	 * {@literal Parent} attribute will have the same id of the session
	 * identifier name.</p>
	 * 
	 * <p>Operations to copy, cut, remove or update cannot be applied to roots
	 * elements. An exception will be threw and the execution aborted.</p>
	 * 
	 * 	<pre>
	 *                          ELEMENT(ROOT)
	 *                               /\
	 *                     ELEMENT(A)  ELEMENT(B)
	 *                         /\         /\
	 *                    E(A1) E(A2) E(B1) E(B2)
	 * 	</pre>
	 * 
	 * The creation of the root element is responsibility of the core API. It
	 * occurs at the moment of initialization of a new session when the
	 * {@link TreeTransaction#initializeSession(String, java.util.Collection)}
	 * or {@link TreeTransaction#initializeSession(String, Class)} is invoked.
	 * 
	 * @param <T> the class type of the wrapped node that will be encapsulated
	 * into the {@link Element} object
	 * 
	 * @return the root element
	 * 
	 * @throws TreeException when the transaction has no selected session to
	 * work or if the current session is not active
	 */
	public <T> Element<T> root() throws TreeException; 
}