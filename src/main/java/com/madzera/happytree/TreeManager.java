package com.madzera.happytree;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.madzera.happytree.exception.TreeException;

/**
 * Provides ways of handling elements within the tree session. With this, it
 * becomes possible to create, persist, update, cut, copy, remove, retrieve
 * elements, and perform any other operations within the tree.
 * 
 * <p>This interface works by directly handling objects represented by the 
 * {@link Element} interface, where each one behaves similarly to a node within 
 * the tree. So, in practical terms, this interface allows the API client to 
 * handle business objects with tree-like behavior similar to the
 * <i>JavaScript DOM</i>.</p>
 * 
 * <p>The operations here are done for elements within others or for "root" 
 * elements. The root elements are considered to be those that are not inside
 * other elements, but those that are at the "top" of the tree hierarchy in 
 * question. When the tree is created, its root is also automatically created,
 * with this root being empty or having various children.</p>
 * 
 * <p>The operations are also done for cases where it is desirable to reallocate
 * elements to other tree sessions. In this case, the uniqueness of each element
 * must be respected, related to the tree in which elements will be placed.</p>
 * 
 * <p>It is important to note that there are two distinct and well-defined
 * contexts in the HappyTree API: the inside and outside contexts. When the API
 * client obtains an element and its children through the manager, the API
 * client is actually working with identical copies of each node (element). When
 * the API client makes any changes to any of the elements, a change in the
 * element's life cycle is made, and this change is not immediately reflected in
 * the tree in question. This context is called outside the tree. An inside
 * context represents the client's action to perform the persist/update of the
 * element, so the change is actually reflected in the tree, but made through
 * this manager itself. The persist/update operations work by invoking
 * {@link TreeManager#persistElement(Element)} and
 * {@link #updateElement(Element)} respectively.</p>
 * 
 * <p>Conceptually, this interface works by handling trees through a transaction.
 * This transaction is represented by the {@link TreeTransaction} interface, and
 * the relation between both interfaces is 1:1. Therefore, this interface will
 * always be linked to a single transaction, which can contain none or many
 * sessions, but it is only possible to handle one session at a time.</p>
 * 
 * <p>The API client must be sure to handle elements through their respective
 * transactions. A simple swap of a tree, by a transaction, makes this manager
 * ready to deal with a totally different tree.</p>
 * 
 * <p>For operations to work, the following validations are made:</p>
 * <ul>
 * 		<li>The transaction must have a session bound, <b>always</b>.</li>
 * 		<li>It is <b>mandatory</b> that the session be <b>activated</b>.</li>
 * 		<li>
 * 			According to the method, the elements must be in the proper
 * 			state.
 * 		</li>
 * 		<li>For each tree session, each element inside must have a unique id.
 * 		</li>
 * 		<li>
 * 			An element cannot be handled within trees that have different
 * 			types of wrapped object nodes.
 * 		</li>
 * 		<li>It is not possible to handle root elements for the
 * 			{@link #copy(Element, Element)}, {@link #cut(Element, Element)},
 * 			{@link #persistElement(Element)} and {@link #remove(Element)}
 * 			methods.
 * 		</li>
 * </ul>
 * 
 * <p>If one of these validations fails, an exception will be thrown.</p>
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
	 * Cuts the <code>from</code> element to inside of the <code>to</code>
	 * element, whether for the same session or not. With this, the element to
	 * be cut can be cut into the same tree session or to another tree in
	 * another session. All children of the <code>from</code> element will be
	 * cut as well.
	 * 
	 * <p>If the <code>to</code> parameter element is <code>null</code>, then
	 * the <code>from</code> element with all children will be moved to the root
	 * level of the same tree.</p>
	 * 
	 * <p>When cutting to the target element, the <code>from</code> parameter
	 * element cannot have a duplicate <code>@Id</code> in the tree where the
	 * <code>to</code> parameter element is located. This also includes the
	 * children IDs of the <code>from</code> element.</p>
	 * 
	 * <p>It is imperative that both trees of the <code>from</code> and
	 * <code>to</code> elements be activated.</p>
	 * 
	 * @param <T> the class type of the wrapped object node that will be
	 * encapsulated into the {@link Element} object
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
	 * 		types of wrapped nodes;
	 * 	</li>
	 * 	<li>
	 * 		The <code>from</code> element is represented by the root of the tree
	 * 		(it is not possible to handle root elements);
	 * 	</li>
	 * 	<li>
	 * 		The <code>from</code> or <code>to</code> element or at least one of
	 * 		their children have a DETACHED or NOT_EXISTED state in the lifecycle;
	 * 	</li>
	 * 	<li>
	 * 		The <code>from</code> element has an already existing
	 * 		<code>@Id</code> in the target tree (if the <code>to</code> element
	 * 		is in another tree).
	 * 	</li>
	 * </ul>
	 * 
	 * @throws IllegalArgumentException when the <code>from</code> parameter is
	 * <code>null</code>
	 */
	public <T> Element<T> cut(Element<T> from, Element<T> to) 
			throws TreeException;
	
	/**
	 * Cuts the <code>from</code> element to inside of the <code>to</code>
	 * element, <b>inside of the same session</b>. Both parameters represent the
	 * <code>@Id</code> of elements.
	 * 
	 * <p>If the <code>to</code> parameter is <code>null</code> or if its
	 * respective element is not found, then the <code>from</code> element with
	 * all children will be moved to the root level of the tree.</p>
	 * 
	 * <p>If it is not possible to find the <code>from</code> element id passed
	 * through the parameter, then <code>null</code> is returned.</p>
	 * 
	 * <p>Using this <code>cut(Object, Object)</code> operation, an element can
	 * only be cut into the same tree. To cut elements to other trees, consider
	 * using {@link #cut(Element, Element)} where the target element is linked
	 * to another tree.</p>
	 * 
	 * @param <T> the class type of the wrapped object node that will be
	 * encapsulated into the {@link Element} object
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
	 * <p><b>Ensure that the current session is referencing the session to which
	 * the <code>from</code> element belongs, otherwise an exception with the
	 * description &quot;<i>Element not defined in this session</i>&quot; will
	 * be thrown.</b></p>
	 * 
	 * <p><b>Also, this method should only be used when the client desires to
	 * copy the element between different trees. It is not possible to copy
	 * elements inside the same tree, because it will throw a duplicate
	 * <code>@Id</code> exception.</b></p>
	 * 
	 * <p>The following steps are done internally inside the core API to copy
	 * an element:</p>
	 * 
	 * 		<ol>
	 * 			<li>Validates the current session and the input;</li>
	 * 			<li>Copies the whole element structure with all the children;
	 * 			</li>
	 * 			<li>Invokes {@link TreeTransaction#sessionCheckout(String)} so
	 * 				that the target tree can be worked on;
	 * 			</li>
	 * 			<li>Pastes the elements;</li>
	 * 			<li>Invokes {@link TreeTransaction#sessionCheckout(String)} back
	 * 				to the source tree, as before.
	 * 			</li>
	 * 		</ol>
	 * 
	 * <p>It is mandatory that both <code>from</code> and <code>to</code>
	 * elements be attached in different trees, and both trees must be activated.
	 * </p>
	 * 
	 * @param <T> the class type of the wrapped object node that will be
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
	 * 		The current session (to which the <code>from</code> element belongs)
	 * 		or the session which the <code>to</code> element belongs is not
	 * 		active;
	 * 	</li>
	 * 	<li>
	 * 		The <code>from</code> element does not belong in the correct current
	 * 		session (it occurs when the current session is not the same to which
	 * 		<code>from</code> element belongs);
	 * 	</li>
	 * 	<li>
	 * 		The <code>from</code> and <code>to</code> elements have different
	 * 		types of wrapped nodes;
	 * 	</li>
	 * 	<li>
	 * 		The <code>from</code> element is represented by the root of the tree
	 * 		(it is not possible to handle root elements);
	 * 	</li>
	 * 	<li>
	 * 		The <code>from</code> or <code>to</code> element or at least one of
	 * 		their children have a DETACHED or NOT_EXISTED state in the lifecycle;
	 * 	</li>
	 * 	<li>
	 * 		The <code>from</code> element has an already existing
	 * 		<code>@Id</code> in	the target tree.
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
	 * have the <i>NOT_EXISTED</i> state in the lifecycle. In a case of reinsert
	 * this removed element, then the same should be persisted again.
	 * 
	 * <p>The element to be removed must be attached (<i>ATTACHED</i> state) in
	 * the tree and cannot have changes. All children will be removed as well,
	 * if they are in the <i>ATTACHED</i> state in their lifecycle. If there is
	 * at least a single child element that is not <i>ATTACHED</i>, then none
	 * will be removed and this method will return <code>null</code>.</p>
	 * 
	 * <p>In the case where the <code>element</code> or its children are not
	 * attached, then it is necessary to attach them in the current tree session
	 * by invoking {@link #persistElement(Element)} if it is a new element or 
	 * invoking {@link #updateElement(Element)} if it is a changed element. 
	 * After that, the <code>element</code> in question becomes attached again
	 * and can then be removed.</p>
	 * 
	 * <p>If the <code>element</code> parameter is <code>null</code> then this
	 * method also will return <code>null</code>.</p>
	 * 
	 * @param <T> the class type of the wrapped object node that will be
	 * encapsulated into the {@link Element} object
	 * 
	 * @param element the element to be removed with all its children
	 * 
	 * @return the own removed element itself, but now with the
	 * <i>NOT_EXISTED</i> state in the lifecycle
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
	 * 	<li>
	 * 		The <code>element</code> has a different type of wrapped node
	 * 		related	to the current session;
	 * 	</li>
	 * 	<li>
	 * 		The <code>element</code> is represented by the root of the tree (it
	 * 		is not possible to handle root elements);
	 * 	</li>
	 * 	<li>
	 * 		The <code>element</code> or at least one of its children have a
	 * 		DETACHED or NOT_EXISTED state in the lifecycle;
	 * 	</li>
	 * </ul>
	 */
	public <T> Element<T> removeElement(Element<T> element) throws TreeException;
	
	/**
	 * <p>Removes the element by its <code>@Id</code>. All children of the found
	 * element are removed as well and returns the removed element itself. Note
	 * that removing an element means that this element will be permanently
	 * eliminated from inside the tree, having its state as <i>NOT_EXISTED</i>
	 * in its lifecycle.</p>
	 * 
	 * <p>If the <code>@Id</code> cannot be found or is <code>null</code>, then
	 * this method will return <code>null</code>.</p>
	 * 
	 * <p>Be sure to be in the correct tree session, so as not to remove an
	 * element with the same <code>@Id</code> but in another tree.</p>
	 * 
	 * @param <T> the class type of the wrapped object node that will be
	 * encapsulated into the {@link Element} object
	 * 
	 * @param id the identifier of the element to be removed
	 * 
	 * @return the own removed element itself, but with the <i>NOT_EXISTED</i>
	 * state in the lifecycle
	 * 
	 * @throws TreeException when:
	 * <ul>
	 * 	<li>
	 * 		The transaction has no selected session to work;
	 * 	</li>
	 * 	<li>
	 * 		The current session is not active;
	 * 	</li>
	 * </ul>
	 */
	public <T> Element<T> removeElement(Object id) throws TreeException;
	
	/**
	 * Obtains an element by <code>@Id</code> in the current tree session.
	 * 
	 * <p>If the <code>@Id</code> is <code>null</code> or it cannot be
	 * found in the tree, then this method will return <code>null</code>.</p>
	 * 
	 * <p>The id corresponds to the <code>@Id</code> annotated attribute of the
	 * wrapped object node in the <b>API Transformation Process</b> or just an
	 * ID which the API client choose. When a tree is being built by a previous
	 * collection of objects (<b>API Transformation Process</b>), the core API
	 * will bind the elements by this <code>@Id</code> annotated attribute.</p>
	 * 
	 * <p>Therefore:</p>
	 * <ul>
	 * 		<li><b>The <code>@Id</code> can never be <code>null</code>;</b></li>
	 * 		<li><b>The <code>@Id</code> is always <i>unique</i> in the tree.</b>
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
	 * @param <T> the class type of the wrapped object node that will be
	 * encapsulated into the {@link Element} object
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
	 * <i>ATTACHED</i> to this tree session, then <code>false</code> is returned.
	 * </p>
	 * 
	 * @param <T> the class type of the wrapped object node that will be
	 * encapsulated into the {@link Element} object
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
	 * caught from a tree and they are automatically with the <i>ATTACHED</i>
	 * state, so this method returns <code>true</code> when the
	 * <code>descendant</code> element is inside of the <code>parent</code>
	 * element.</p>
	 * 
	 * @param parent the parent identifier which will contain the
	 * <code>descendant</code> element
	 * 
	 * @param descendant the descendant identifier which will be inside of the
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
	 * <p>If the <code>element</code> is <code>null</code>, not found or if the
	 * <code>element</code> or at least one of its children is not in the
	 * <i>ATTACHED</i> state in the lifecycle then <code>false</code> is
	 * returned.</p>
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
	 * Verifies that the current tree session has the specified element by the
	 * given <code>@Id</code>.
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
	 * wrapped object node. Only the <code>id</code> is mandatory. When the
	 * <code>parent</code> is null or not found, then this element will be moved
	 * to inside of the root level (first level) of the tree, when persisted.
	 * 
	 * <p>Creating a new element does not means that it will be automatically in
	 * the tree of the current session. When creating a new element, it is
	 * &quot;outside&quot; of the tree yet, having the <i>NOT_EXISTED</i> state
	 * in the lifecycle. The element needs to be attached in the tree right
	 * after the creation moment time, by invoking
	 * {@link #persistElement(Element)}. So, after that, the element becomes
	 * attached and finally can be handled by {@link #cut(Element, Element)} or
	 * {@link #copy(Element, Element)} operations for example.</p>
	 * 
	 * <p>Ensure that the parameterized type when creating an element is the
	 * same type of the object related to the current session.</p>
	 * 
	 * @param <T> the class type of wrapped object node that will be
	 * encapsulated into the {@link Element} object
	 * 
	 * @param id the identifier of the new element
	 * 
	 * @param parent the parent identifier of this new element
	 * 
	 * @param wrappedNode the object to be encapsulated in this element
	 * 
	 * @return a new element with the <i>NOT_EXISTED</i> state in lifecycle
	 * 
	 * @throws TreeException when the transaction has no selected session to
	 * work or the current session is not active
	 * 
	 * @throws IllegalArgumentException when the <code>id</code> parameter is
	 * <code>null</code>
	 */
	public <T> Element<T> createElement(Object id, Object parent, T wrappedNode)
			throws TreeException;
	
	/**
	 * Persists a new element into the current tree session. The new element to
	 * be persisted must have a unique identifier in the tree session. If the
	 * <code>@Parent</code> of this new element is defined with
	 * <code>null</code> or if it is simply not found, then this new element
	 * will be persisted in root level of the tree (first level).
	 * 
	 * <p>Also, the new element must be essentially new, created using the
	 * {@link #createElement(Object, Object, Object)} method to guarantee a
	 * coherent detached state from the current tree. After creating the element,
	 * it will have the <i>NOT_EXISTED</i> state in the lifecycle, even its
	 * children (in case of creating children inside this element).</p>
	 * 
	 * <p>This method also allows new chained children elements to be persisted
	 * at once. If the element has duplicate identifier or there is a descendant
	 * in this element to be created that has duplicate identifier in relation
	 * to the tree, then an exception will be thrown.</p>
	 * 
	 * <p>The <i>NOT_EXISTED</i> state represents a
	 * &quot;outside tree session&quot; element, that means then this element
	 * has not been inside any tree. When the element is persisted by this
	 * operation, its state changes to <i>ATTACHED</i> in the lifecycle. This
	 * also happens with its children when they are persisted at once.</p>
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
	 * @param <T> the class type of the wrapped object node that will be
	 * encapsulated into the {@link Element} object
	 * 
	 * @param newElement the element to be persisted
	 * 
	 * @return a copy of the new persisted element with the <i>ATTACHED</i>
	 * state in lifecycle
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
	 * 	<li>
	 * 		The <code>newElement</code> has a different type of wrapped node
	 * 		related to the current session;
	 * 	</li>
	 * 	<li>
	 * 		The <code>newElement</code> or at least one of its descendants have
	 * 		a <i>DETACHED</i> or <i>ATTACHED</i> state in the lifecycle;
	 * 	</li>
	 * 	<li>
	 * 		The <code>newElement</code> has an already existing identifier in
	 * 		this session.
	 * 	</li>
	 * </ul>
	 * 
	 * @throws IllegalArgumentException when the <code>newElement</code> or its
	 * <code>@Id</code> is <code>null</code>
	 */
	public <T> Element<T> persistElement(Element<T> newElement) 
			throws TreeException;
	
	/**
	 * Updates the state of the element to the tree. Synchronizes a previous
	 * changed element.
	 * 
	 * <p>To be updated, an element should be previously captured by invoking
	 * {@link #getElementById(Object)} for example, and its state should be as
	 * <i>DETACHED</i>.</p>
	 * 
	 * <p>A <i>DETACHED</i> element represents an element, or one of its
	 * descendants, in which it has undergone some change, whether it be the
	 * <code>@Id</code> the <code>@Parent</code> or the wrapped node. <b>Ensure
	 * yourself to avoid duplicate <code>@Id</code> in current session by an
	 * <code>@Id</code> change</b>. To move up the element for the root level
	 * (first level), just set the parent ID as <code>null</code> or reference
	 * an inexistent parent element.</p>
	 * 
	 * <p>This operation is for <i>DETACHED</i> elements. Trying to update a
	 * <i>NOT_EXISTED</i> element in the tree, an exception is threw. For
	 * <i>ATTACHED</i> element nothing happens, because the element is already
	 * attached, just the same is returned.</p>
	 * 
	 * <p>Therefore, this is only possible to update an element is through the
	 * {@link #persistElement(Element)} invocation or by the API Transformation
	 * Process, passing a previous list of elements to be transformed into a
	 * tree by the
	 * {@link TreeTransaction#initializeSession(String, java.util.Collection)}
	 * method.</p>
	 * 
	 * <p>This operation also works for the root element, allowing the update
	 * when the root element adds or removes children elements directly.</p>
	 * 
	 * <p>When updating an element, its children list will also be automatically
	 * updated recursively. After updating, all elements have their states as
	 * <i>ATTACHED</i> in the lifecycle.</p>
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
	 * @param <T> the class type of the wrapped object node that will be
	 * encapsulated into the {@link Element} object
	 * 
	 * @param element the element to be updated
	 * 
	 * @return a copy of the updated element with the <i>ATTACHED</i> state in
	 * the lifecycle
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
	 * 	<li>
	 * 		The <code>element</code> has a different type of wrapped node
	 * 		related to the current session;
	 * 	</li>
	 * 	<li>
	 * 		The <code>element</code> or at least one of its descendants have a
	 * 		<i>NOT_EXISTED</i> state in the lifecycle;
	 * 	</li>
	 * 	<li>
	 * 		The <code>element</code> has an already existing <code>@Id</code>
	 * 		in this	session.
	 * 	</li>
	 * </ul>
	 * 
	 * @throws IllegalArgumentException when the <code>element</code> or its
	 * <code>@Id</code> is <code>null</code>
	 */
	public <T> Element<T> updateElement(Element<T> element) 
			throws TreeException;
	
	/**
	 * Obtains the {@link TreeTransaction} instance associated to this manager.
	 * 
	 * <p>The manager is closely related to the transaction. Absolutely every
	 * operations defined in this interface need to check the transaction and
	 * verify whether there is a session to be managed.</p>
	 * 
	 * <p>If there is no session to be handled or the session is not active
	 * inside of the transaction, an error occurs. The API client of this method
	 * should know what session (tree) is preferred to work with. This
	 * transaction object has this objective to provide and handle the sessions.
	 * </p>
	 * 
	 * <b>
	 * TREEMANAGER (invokes) -&gt; TREETRANSACTION (to store) -&gt;
	 * TREESESSION (that contains) -&gt; ELEMENT
	 * </b>
	 * 
	 * @return the transaction associated to this manager
	 */
	public TreeTransaction getTransaction();
	
	/**
	 * Returns the root of the tree in this current session.
	 * 
	 * <p>The root structure is also an object of {@link Element} type which
	 * represents the top of the tree and encompasses all other elements.</p>
	 * 
	 * <p>It contains a collection of children, which in turn contains a
	 * collection of children, and so on recursively. All this structure is
	 * returned in this method as {@link Element} type.</p>
	 * 
	 * <p>The root element is like a &quot;special&quot; element created
	 * exclusively by the core API. All elements that the API client handles are
	 * under the root element. <b>Thus it is not possible to create a root
	 * element</b>. Because of that, every object node with a <code>null</code>
	 * <code>@Parent</code> or an unknown (not found) <code>@Parent</code> will
	 * be attached directly as an immediate root child (first level).</p>
	 * 
	 * <p>Different from regular elements, the root element has no metadata
	 * associated with it, such as <code>@Id</code>, <code>@Parent</code> and
	 * wrapped object node. Therefore, calling the {@link Element#getId()},
	 * {@link Element#getParent()} or {@link Element#unwrap()} methods on the
	 * root element will always return <code>null</code>.</p>
	 * 
	 * <p>For being a special element, write operations like copy, cut or remove
	 * cannot be applied directly to the root element, otherwise an exception
	 * will be thrown and the execution aborted.</p>
	 * 
	 * <p>Below, an example of a tree structure with its root element and
	 * children:</p>
	 * 	<pre>
	 * 
	 * 							ELEMENT(ROOT)
	 *                               /\
	 *                     ELEMENT(A)  ELEMENT(B)
	 *                         /\         /\
	 *                    E(A1) E(A2) E(B1) E(B2)
	 * 
	 * 	</pre>
	 * 
	 * The creation of the root element is responsibility of the core API. It
	 * occurs at the moment of initialization of a new session when the
	 * {@link TreeTransaction#initializeSession(String, java.util.Collection)}
	 * or {@link TreeTransaction#initializeSession(String, Class)} is invoked.
	 * 
	 * @param <T> the class type of the wrapped object node that will be
	 * encapsulated into the {@link Element} object
	 * 
	 * @return the root element
	 * 
	 * @throws TreeException when the transaction has no selected session to
	 * work or if the current session is not active
	 */
	public <T> Element<T> root() throws TreeException;

	/**
	 * Searches for elements that satisfy a specific condition within the entire
	 * tree structure. The method returns a list of elements that match the
	 * provided condition.
	 * 
	 * <p>This method traverses the complete tree starting from the root element
	 * and evaluates each element against the specified condition. If an element
	 * satisfies the condition, it is included in the resulting list.</p>
	 * 
	 * <p>The resulting list consists of all elements that match the specified
	 * condition, <b>including their children</b>. Therefore, the list will have
	 * the elements that satisfy the condition but within of each element there
	 * will have its hierarchical structure preserved.</p>
	 * 
	 * <p><b>Example usage:</b></p>
	 * <pre>
	 * //Find all elements which the object node has its name starting with "A"
	 * List&lt;Element&lt;MyNodeType&gt;&gt; results = manager.search(
	 *     e -&gt; e.unwrap().getName().startsWith("A")
	 * );
	 * </pre>
	 * 
	 * @param <T> the class type of the wrapped object node that will be
	 * encapsulated into the {@link Element} object
	 * 
	 * @param condition the predicate function defining the search criteria
	 * 
	 * @return a list of elements that satisfy the specified condition
	 * 
	 * @throws TreeException when the transaction has no selected session to
	 * work or if the current session is not active
	 */
	public <T> List<Element<T>> search(Predicate<Element<T>> condition) 
			throws TreeException;

	/**
	 * Applies a function to be performed on all elements within the entire tree
	 * structure. The action applied to every element in the tree is
	 * automatically reflected on the tree session (if there are any changes),
	 * not being necessary to invoke the {@link #persistElement(Element)}
	 * neither {@link #updateElement(Element)} to save the changes.
	 * 
	 * <p>This method traverses the complete tree starting from the root element
	 * and applies the given action to all elements in the tree (<b>except for
	 * the root element itself</b>). Unlike {@link #apply(Consumer, Predicate)},
	 * this method operates on the entire tree structure rather than just a
	 * subset of elements that match the condition.</p>
	 * 
	 * <p>The action function receives each {@link Element} as a parameter and
	 * can perform any operation on it, such as modifying the wrapped object,
	 * changing element properties, or performing calculations/verifications.
	 * </p>
	 * 
	 * <p>The action is applied to all elements in the tree, <b>except for the
	 * root element itself</b>, as it is a special element created by the
	 * HappyTree API itself, not having the <code>@Id</code>,
	 * <code>@Parent</code> neither a wrapped object node.</p>
	 * 
	 * <p><b>Example usage:</b></p>
	 * <pre>
	 * //Transform all directory names to uppercase in the entire tree
	 * manager.apply(e -&gt; e.unwrap().transformNameToUpperCase());
	 * 
	 * //Log all element IDs in the tree
	 * manager.apply(e -&gt; System.out.println("Element ID: " + e.getId()));
	 * </pre>
	 * 
	 * <p><b>Note:</b> The function is applied to <b>all</b> elements in the
	 * entire tree (<b>except for the root element itself</b>). If you need
	 * conditional execution based on specific criteria, consider using
	 * {@link #apply(Consumer, Predicate)} instead.</p>
	 * 
	 * @param <T> the class type of the wrapped object node that will be
	 * encapsulated into the {@link Element} object
	 * 
	 * @param action the function to apply to each element in the tree
	 * 
	 * @throws TreeException when the transaction has no selected session to
	 * work or if the current session is not active
	 * 
	 * @see #apply(Consumer, Predicate)
	 */
	public <T> void apply(Consumer<Element<T>> action) throws TreeException;

	/**
	 * Applies a function to be performed on elements that satisfy a specific
	 * condition within the entire tree structure. The action applied to every
	 * matching element in the tree is automatically reflected on the tree
	 * session (if there are any changes), not being necessary to invoke the
	 * {@link #persistElement(Element)} neither
	 * {@link #updateElement(Element)} to save the changes.
	 * 
	 * <p>This method traverses the complete tree starting from the root element
	 * and applies the given action only to elements that satisfy the specified
	 * condition (<b>not including the root element itself</b>). Unlike
	 * {@link #apply(Consumer)}, this method allows for selective application of
	 * the action based on custom criteria defined by the condition.</p>
	 * 
	 * <p>The action function receives each {@link Element} that meets the
	 * condition as a parameter and can perform any operation on it, such as
	 * modifying the wrapped object, changing element properties, or performing
	 * calculations/verifications.</p>
	 * 
	 * <p>The condition is a predicate function that takes an {@link Element}
	 * as input and returns a boolean value indicating whether the action should
	 * be applied to that element. Only elements for which the condition
	 * evaluates to <code>true</code> will have the action applied.</p>
	 * 
	 * <p><b>Example usage:</b></p>
	 * <pre>
	 * //Transform names to uppercase for directories only
	 * manager.apply(
	 *     e -&gt; e.unwrap().transformNameToUpperCase(),
	 *     e -&gt; e.unwrap().isDirectory()
	 * );
	 * 
	 * //Log IDs of elements with names starting with "A"
	 * manager.apply(
	 *     e -&gt; System.out.println("Element ID: " + e.getId()),
	 *     e -&gt; e.unwrap().getName().startsWith("A")
	 * );
	 * </pre>
	 * 
	 * <p><b>Note:</b> The function is applied only to elements that satisfy the
	 * specified condition (<b>not including the root element</b>). If you want
	 * to apply an action to all elements in the tree, consider using
	 * {@link #apply(Consumer)} instead.</p>
	 * 
	 * @param <T> the class type of the wrapped object node that will be
	 * encapsulated into the {@link Element} object
	 * 
	 * @param action the function to apply to each element that meets the
	 * condition
	 * 
	 * @param condition the predicate function to determine which elements
	 * should have the action applied
	 * 
	 * @throws TreeException when the transaction has no selected session to
	 * work or if the current session is not active
	 * 
	 * @see #apply(Consumer)
	 */
	public <T> void apply(Consumer<Element<T>> action,
			Predicate<Element<T>> condition) throws TreeException;
}