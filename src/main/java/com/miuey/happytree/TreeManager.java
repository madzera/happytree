package com.miuey.happytree;

import com.miuey.happytree.exception.TreeException;

/**
 * Provides ways of handling elements within the tree session. With this, it is
 * possible to create, persist, update, cut, copy, remove, retrieve elements and
 * any other operations within the tree.
 * 
 * <p>This interface works directly handling objects represented by the 
 * {@link Element} interface, where each one behaves similarly to a node within 
 * the tree. So, In practical terms, this interface allows the client API to 
 * handle business objects with tree-like behavior similar to 
 * <i>JavaScript HTML DOM</i>.</p>
 * 
 * <p>The operations here are done for elements within others or for "root" 
 * elements. The root elements are considered to be those that are not inside of
 * other elements, but those that are at the "top" of the tree hierarchy in 
 * question. Each tree can contain 0 or more root elements, each of which must 
 * have different identifiers.</p>
 * 
 * <p>Conceptually, this interface works by handling trees through a 
 * transaction. This transaction is represented by the {@link TreeTransaction} 
 * interface and the relation between both interfaces is 1:1. Therefore, this 
 * interface will always be linked to a single transaction, which can contain no 
 * one or many sessions, but it is only possible to handle one session at the 
 * time.</p>
 * 
 * <p>For operations to work, the following validations are made:</p>
 * <ul>
 * 		<li>The transaction must have a session binded <b>always</b>.</li>
 * 		<li>It is <b>mandatory</b> that the session is <b>activated.</b></li>
 * </ul>
 * <p>If one of these validations fails, an exception will be thrown.</p>
 * 
 * @author Diego Nóbrega
 * @author Miuey
 * 
 * @see {@link TreeSession}
 * 
 * @version %I%, %G%
 *
 */
public interface TreeManager {
	
	/**
	 * Cut the <code>from</code> element for inside the <code>to</code> element,
	 * whether for the same session or not. With this, the element to be cut can
	 * be cut into the same tree session or to another tree in another session.
	 * All children of <code>from</code> element will be cut too.
	 * 
	 * <p>If the <code>to</code> parameter element is <code>null</code> then the
	 * <code>from</code> element with all children will be moved to the root
	 * level of the same tree. If the {@literal @Id}} attribute of the
	 * <code>to</code> element is <code>null</code> or not found in the tree,
	 * then the element will be cut to the root level of the tree which the
	 * element belongs.</p>
	 * 
	 * <p>When cut for the target element, the <code>from</code> parameter
	 * element cannot have the same identifier in the tree where the
	 * <code>to</code> parameter element is in another session. It include also
	 * the children identifiers of the <code>from</code> element.</p>
	 * 
	 * <p>It is imperative that the tree of the <code>to</code> element must be 
	 * activated.</p>
	 * 
	 * @param <T> the class type of the source wrapped object that will be
	 * encapsulated into the {@link Element} object
	 * 
	 * @param from the source element
	 * 
	 * @param to the target element
	 * 
	 * @return the own <code>from</code> element to be cut
	 * 
	 * @throws TreeException 
	 * <ul>
	 * 	<li>The transaction has no session selected to work it;</li>
	 * 	<li>The current session or the session which the <code>to</code> element
	 * 		belongs is not active;
	 * 	</li>
	 * 	<li>The <code>from</code> element has an already existing identifier in
	 * 		the target tree.
	 * 	</li>
	 * 	<li>the <code>from</code> element neither the <code>to</code> element is
	 * 		not attached in any activated tree.
	 * 	</li>
	 * </ul>
	 * 
	 * @throws IllegalArgumentException when the <code>from</code> parameter or
	 * its <code>id</code> is <code>null</code>
	 */
	public <T> Element<T> cut(Element<T> from, Element<T> to) 
			throws TreeException;
	
	/**
	 * Get the respective <code>from</code> and <code>to</code> element passed
	 * through and cut the <code>from</code> element into the <code>to</code>
	 * element in the tree session. All children of <code>from</code> element
	 * will be cut too.
	 * 
	 * <p>If the <code>to</code> parameter element is <code>null</code> then the
	 * <code>from</code> element with all children will be moved to the root
	 * level of the tree.</p>
	 * 
	 * <p>If it is not possible to find out the respective element from passed
	 * through parameter (<code>from</code> parameter) then <code>null</code> is
	 * returned.</p>
	 * 
	 * <p>Using this <code>cut(Object, Object)</code>, an element just can be
	 * cut into the same tree. To cut elements for other tree consider using the 
	 * {@link #cut(Element, Element)} which the target element is linked to
	 * another tree.</p>
	 * 
	 * @param <T> the class type of the source wrapped object that will be
	 * encapsulated into the {@link Element} object
	 * 
	 * @param from the source element
	 * 
	 * @param to the target element
	 * 
	 * @return the own <code>from</code> element to be cut
	 * 
	 * @throws TreeException when the transaction has no session selected to
	 * work it. And also when the current session is not active
	 * 
	 * @throws IllegalArgumentException when the <code>from</code> parameter is
	 * <code>null</code>
	 */
	public <T> Element<T> cut(Object from, Object to) throws TreeException;
	
	/**
	 * Copy the respective <code>from</code> element into <code>to</code>
	 * element in another tree session. All structure of copied element will be
	 * paste inside of <code>to</code> element. The element to be copied and its
	 * all children can not have the same identifier than any element in the
	 * <code>to</code> element tree.
	 * 
	 * <p><i><b>This method only should be used when the client desire copy the
	 * element between different tree. It is no possible to copy elements
	 * inside of the same own tree, because it will throw duplicated <i>Id</i> 
	 * exception.</b></i></p>
	 * 
	 * <p>The following steps are done internally inside the core API to copy
	 * an element:</p>
	 * 
	 * 		<ol>
	 * 			<li>Validate the input and the current session;</li>
	 * 			<li>Copy the whole element structure with all the children;</li>
	 * 			<li>Invoke {@link TreeTransaction#sessionCheckOut(String)} to 
	 * 			the destination tree be able to be worked;</li>
	 * 			<li>Paste the elements;</li>
	 * 			<li>Invoke {@link TreeTransaction#sessionCheckOut(String)} to 
	 * 			the tree source tree, like before.</li>
	 * 		</ol>
	 * 
	 * <p>It is mandatory that the both <code>from</code> and <code>to</code>
	 * elements be attached in different trees, and the respective trees must be
	 * activated.</p>
	 * 
	 * @param <T> the class type of the wrapped object that will be
	 * encapsulated into the {@link Element} object
	 * 
	 * @param from the source element
	 * 
	 * @param to the target element
	 * 
	 * @return the own <code>from</code> element copied (current source tree).
	 * 
	 * @throws TreeException when the transaction has no session selected to
	 * work it. When the current session or the session which the
	 * <code>to</code> element belongs is not active. When the
	 * <code>from</code> element neither the <code>to</code> element is not
	 * attached in different trees. Also when if the copied element identifier
	 * is already existing in the target tree, throwing a duplicated
	 * <code>id</code> exception
	 * 
	 * @throws IllegalArgumentException when the <code>from</code> or
	 * <code>to</code> parameters or their <code>id</code> are <code>null</code>
	 */
	public <T> Element<T> copy(Element<T> from, Element<T> to) 
			throws TreeException;
	
	/**
	 * Remove the corresponding element from the tree session and return
	 * <code>true</code>. The element must be attached in the tree and can not
	 * get changes. All children will be removed too, in case respecting the
	 * above rules. If there is at least a single element that the rule is not
	 * contemplating, then no one will be removed and this method will return
	 * <code>false</code>. Realize that removing an element means detach it from
	 * the tree session.
	 * 
	 * <p>In case, if the <code>element</code> or its children is not attached,
	 * then it is necessary to attach them in the current tree session by 
	 * invoking {@link #persistElement(Element)} if it is a new element or 
	 * invoking {@link #updateElement(Element)} if it is a changed element. 
	 * After that, the <code>element</code> in question turn on attached and it
	 * can be removed after that.</p>
	 * 
	 * <p>Particularly, this method is less reliable to use than
	 * {@link #removeElement(Object)} because if the client of this method
	 * pass through a detached element, then this method will not remove the
	 * element and it will return <code>false</code>. Invoking the 
	 * {@link #removeElement(Object)} will remove certainly the element if the 
	 * <i>Id</i> is found.</p>
	 * 
	 * <p>So, the client of this method must guarantee the following rules, if
	 * he wishes getting <code>true</<ode>;
	 * 		<ul>
	 * 			<li><b>The element must be attached in the current session.</b>
	 * 			</li>
	 * 			<li><b>The element must no be changed after attached.</b></li>
	 * 		</ul>
	 * </p>
	 * 
	 * <p>If the <code>element</code> is <code>null</code> then this method will
	 * return <code>false</code>.</p>
	 * 
	 * @param <T> the class type of the wrapped object that will be
	 * encapsulated into the {@link Element} object
	 * 
	 * @param element the element to be removed with all children
	 * 
	 * @return the own removed element itself, but now detached
	 * 
	 * @throws TreeException when the transaction has no session selected to
	 * work it. When the current session or the session which the
	 * <code>to</code> element belongs is not active
	 */
	public <T> Element<T> removeElement(Element<T> element) throws TreeException;
	
	/**
	 * Remove the element by <code>id</code>. All children of the found element
	 * is removed too and return <code>true</code>. Realize that removing an
	 * element means detach it from the tree session.
	 * 
	 * <p>If the <code>id</code> is not be able to be found or <code>null</code>,
	 * then this method will return <code>false</code>.</p>
	 * 
	 * <p>This method is a safe way to guarantee that if an element is found,
	 * passing the <code>id</code> through, then it is certain that the element
	 * and its children will be removed too. It happens because it finds out the
	 * element directly in the tree session by <code>id</code> and bring it to
	 * the client of this method. So, even changing the element after that, and
	 * invoking this method then, the result will be the same element before
	 * changing.</p>
	 * 
	 * @param id the identifier of the element to be removed
	 * 
	 * @return the own removed element itself, but now detached
	 * 
	 * @throws TreeException when the transaction has no session selected to
	 * work it. When if the current session is not active
	 */
	public <T> Element<T> removeElement(Object id) throws TreeException;
	
	/**
	 * Get an element by its <code>id</code> in the current tree session.
	 * 
	 * <p>If the <code>id</code> is not be able to be found in the tree or
	 * <code>null</code> then this method will return <code>null</code>.</p>
	 * 
	 * <p>The <code>id</code> corresponds the {@literal @Id} annotated
	 * attribute of the source wrapped object in the <b>transformation
	 * process</b>. When a tree is being built by a previous collection of
	 * objects, the core API will bind the elements by this {@literal @Id}
	 * annotated attribute.</p>
	 * 
	 * <p>Therefore:</p>
	 * <ul>
	 * 		<li><b>The {@literal @Id} never can be <code>null</code>;</b></li>
	 * 		<li><b>The {@literal @Id} always is <i>unique</i> in the tree.</b>
	 * 		</li>
	 * </ul>
	 * 
	 * <p>All elements returned by this method represents elements with attached
	 * state in relation with the current tree session. An attached element
	 * means that the element returned and its children are a &quot;mirror&quot;
	 * piece of the tree.</p>
	 * 
	 * <p>Changing the element or the children states, then it is necessary to
	 * attach them again in the current tree session by invoking 
	 * {@link #updateElement(Element)} to be able of handling those, for
	 * operation like {@link #cut(Element, Element)} or 
	 * {@link #copy(Element, Element)} for example.</p>
	 * 
	 * @param <T> the class type of the source wrapped object that will be
	 * encapsulated into the {@link Element} object returned
	 * 
	 * @param id the element identifier
	 * 
	 * @return the element and its children caught from the tree
	 * 
	 * @throws TreeException when the transaction has no session selected to
	 * work it. When if the current session is not active
	 */
	public <T> Element<T> getElementById(Object id) throws TreeException;
	
	/**
	 * Verify if the <code>parent</code> element contains inside it the
	 * <code>descendant</code> specified element in this current session.
	 * 
	 * <p>If both <code>parent</code> and <code>descendant</code> element is
	 * <code>null</code> or detached, then <code>false</code> is returned.</p>
	 * 
	 * <p>hierarchically, all children of <code>descendant</code> element
	 * <b>must be</b> identical to parent's children. The method compares
	 * one by one and if at least one element is not equals to another one then 
	 * <code>false</code> is immediately returned.</p>
	 * 
	 * <p>For this, even if the identifier of both elements are the same 
	 * (comparing time), the core API internally executes
	 * {@link Element#equals(Object)} to ensure the identity</p>
	 * 
	 * @param <T> the class type of the wrapped object that will be
	 * encapsulated into the {@link Element} object
	 * 
	 * @param parent the element which will contains the <code>descendant</code>
	 * element
	 * 
	 * @param descendant the element which is inside of <code>parent</code>
	 * element
	 * 
	 * @return <code>Boolean</code> the <code>true</code> value if the
	 * <code>parent</code> element contains the <code>descendant</code>
	 * element, <code>false</code> either
	 * 
	 * @throws TreeException when the transaction has no session selected to
	 * work it. When if the current session is not active
	 */
	public <T> boolean containsElement(Element<T> parent, Element<T> descendant)
			throws TreeException;
	
	/**
	 * Verify if the <code>parent</code> element contains inside it the
	 * <code>descendant</code> specified element in this current session.
	 * 
	 * <p>The <code>parent</code> and <code>descendant</code> identifiers are
	 * used to bring the respective elements. If both <code>parent</code> and
	 * <code>descendant</code> element is <code>null</code> or not found, then
	 * <code>false</code> is returned.</p>
	 * 
	 * <p>If the elements is found, then it means that they were already
	 * caught from a tree and they are automatically attached, so this method
	 * returns <code>true</code>.</p>
	 * 
	 * @param parent the parent identifier which will contains the
	 * <code>descendant</code>
	 * 
	 * @param descendant the child identifier
	 * 
	 * @return <code>Boolean</code> the <code>true</code> value if the
	 * <code>parent</code> element contains the <code>descendant</code>
	 * element, <code>false</code> either
	 * 
	 * @throws TreeException when the transaction has no session selected to
	 * work it. When if the current session is not active
	 */
	public boolean containsElement(Object parent, Object descendant) 
			throws TreeException;
	
	/**
	 * Verify if the current tree session has the specified
	 * <code>element</code>.
	 * 
	 * <p>If the <code>element</code> is detached from the tree or
	 * <code>null</code> then <code>false</code> is returned.</p>
	 * 
	 * <p>Internally, whole the hierarchy is iterated to seek the specified
	 * <code>element</code>. The verification is done when the 
	 * {@link Element#equals(Object)} is <code>true</code>.</p>
	 * 
	 * @param element the specified element to be verified
	 * 
	 * @return <code>Boolean</code> the <code>true</code> value if the tree
	 * contains the <code>element</code>, <code>false</code> either
	 * 
	 * @throws TreeException when the transaction has no session selected to
	 * work it. When if the current session is not active
	 */
	public boolean containsElement(Element<?> element) throws TreeException;
	
	/**
	 * Verify if the current tree session has the element specified by its
	 * <code>id</code>.
	 * 
	 * <p>If the element is not found in the tree or <code>null</code> then
	 * <code>false</code> is returned.</p>
	 * 
	 * <p>Internally, whole the hierarchy is iterated to seek the specified
	 * <code>id</code>.</p>
	 * 
	 * @param id the identifier of the element to be verified
	 * 
	 * @return <code>Boolean</code> the <code>true</code> value if the tree
	 * contains the element, <code>false</code> either
	 * 
	 * @throws TreeException when the transaction has no session selected to
	 * work it. When if the current session is not active
	 */
	public boolean containsElement(Object id) throws TreeException;
	
	/**
	 * Create a &quot;detached&quot; element with the <code>id</code> and
	 * <code>parent</code> specified.
	 * 
	 * <p>Creating a new element does not means that it will be automatically in
	 * the tree of the current session. It needs to be attached in the tree
	 * right after the creation by invoking {@link #persistElement(Element)}. So,
	 * after that, the element becomes attached and finally can be handled by
	 * {@link #cut(Element, Element)} or {@link #copy(Element, Element)} for
	 * example.</p>
	 * 
	 * <p>If the {@literal @Parent} is <code>null</code></p>, then the new
	 * element is liable of to be in a root level of the tree when the same is
	 * persisted.</p>
	 * 
	 * @param <T> the class type of wrapped object that will be encapsulated
	 * into the {@link Element} object
	 * 
	 * @param id the identifier of the new element
	 * 
	 * @param parent the parent identifier of this new element
	 * 
	 * @return a new and detached element
	 * 
	 * @throws TreeException when the transaction has no session selected to
	 * work it. Also, when the current session is not active
	 */
	public <T> Element<T> createElement(Object id, Object parent) 
			throws TreeException;
	
	/**
	 * Persist a new element into the current tree session. the new element to
	 * be persisted must have a unique identifier in the tree session. If the
	 * {@literal @Parent} of this new element is defined with <code>null</code>
	 * or if it is simply not found, then this new element will be persisted in
	 * root level of the tree.
	 * 
	 * <p>Also, the new element must be essentially new, created using the
	 * {@link #createElement(Object, Object)} method to guarantee a coherent
	 * detached state from the current tree.</p>
	 * 
	 * <p>Another way to get a 'detached' element to be persisted is simply
	 * get an already existing element from the tree and just change its
	 * <code>id</code> by invoking {@link Element#setId(Object)}. However, this
	 * approach does not provides a real new element because the attribute
	 * values will be maintained according to the previous element captured.</p>
	 * 
	 * <p>This method also can allows new chained children elements to also be
	 * persisted at once. If the element has duplicated identifier or there is a
	 * child in this new created element that has duplicated identifier in
	 * relation to the tree, then an exception will threw.</p>
	 * 
	 * <table>
	 * 
	 * 	<th>Element Status</th><th>Ways</th><th>persistElement()</th>
	 * 	<tr>
	 * 		<td>Detached</td><td>{@link #createElement(Object, Object)}}</td>
	 * 		<td>Yes</td>
	 * 	</tr>
	 * 	<tr>
	 * 	<td>Attached</td><td>{@link #getElementById(Object)}</td><td>No</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>Detached</td><td>{@link #getElementById(Object)} -> 
	 * 		{@link Element#setId(Object)}</td><td>Yes</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>Attached</td><td>{@link #getElementById(Object)} -> 
	 * 		{@link Element#setParent(Object)}</td>
	 * 		<td>No - Did not change the Id - Duplicated Id</td>
	 * 	</tr>
	 * </table>
	 * 
	 * @param <T> the class type of the source wrapped object that will be
	 * encapsulated into the {@link Element} object
	 * 
	 * @param newElement the element to be persisted
	 * 
	 * @return the new persisted element
	 * 
	 * @throws TreeException
	 * <ul>
	 * 	<li>The transaction has no session selected to work it;</li>
	 * 	<li>The current session is not active;</li>
	 * 	<li>The new element has an already existing identifier in the tree.</li>
	 * </ul>
	 * 
	 * @throws IllegalArgumentException when the <code>newElement</code> or its
	 * <code>id</code> is <code>null</code>
	 */
	public <T> Element<T> persistElement(Element<T> newElement) 
			throws TreeException;
	
	/**
	 * Update the state of the element to the tree. Synchronize a previous
	 * changed element to the tree.
	 * 
	 * <p>To be updated, an element should be previous captured by invoking
	 * {@link #getElementById(Object)} for example, and its state should be as
	 * <i>detached</i>. Trying to update a non-existed element in the tree makes
	 * this method returns <code>null</code>.</p>
	 * 
	 * <p>So, if there is an attempt to update non-existed element or if the
	 * <code>element</code> parameter is <code>null</code> or its
	 * <code>id</code> is <code>null</code> then <code>null</code> is returned.
	 * Therefore, it is only possible to update a previous persisted element,
	 * then it is safe to say that before invoking this method, an invocation to
	 * the {@link #persistElement(Element)} is mandatory.</p>
	 * 
	 * <p>Updating an element, also its children list will be automatically
	 * updated recursively.</p>
	 * 
	 * <table>
	 * 
	 * 	<th>Element Status</th><th>Ways</th><th>updateElement()</th>
	 * 	<tr>
	 * 		<td>Detached</td><td>{@link #createElement(Object, Object)}}</td>
	 * 		<td>No</td>
	 * 	</tr>
	 * 	<tr>
	 * 	<td>Attached</td><td>{@link #getElementById(Object)}</td><td>Yes</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>Detached</td><td>{@link #getElementById(Object)} -> 
	 * 		{@link Element#setId(Object)}</td><td>Yes</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>Attached</td><td>{@link #getElementById(Object)} -> 
	 * 		{@link Element#setParent(Object)}</td>
	 * 		<td>Yes</td>
	 * 	</tr>
	 * </table>
	 * 
	 * <p>The main prerequisite to use this method:
	 * <b><i>The element must be persisted before an update, else
	 * <code>null</code> is returned.</i></b></p>
	 * 
	 * @param <T> the class type of the source wrapped object that will be
	 * encapsulated into the {@link Element} object
	 * 
	 * @param element the element to be updated
	 * 
	 * @return the updated element itself
	 * 
	 * @throws TreeException
	 * <ul>
	 * 	<li>The transaction has no session selected to work it;</li>
	 * 	<li>The current session is not active.</li>
	 * </ul>
	 */
	public <T> Element<T> updateElement(Element<?> element) 
			throws TreeException;
	
	/**
	 * Get the {@link TreeTransaction} instance associated to this manager.
	 * 
	 * <p>The manager is closely related to the transaction. Absolutely every
	 * operations defined in this interface needs to check the transaction and
	 * verify if there is a session to be managed.</p>
	 * 
	 * <p>If there is no session to be handled inside the transaction, an error
	 * occurs. The client of this method should know what session he wishes to
	 * work. This transaction has this objective, provide and handle the
	 * sessions.</p>
	 * 
	 * <p>
	 * 		<pre>
	 * MANAGER (invokes) -> TRANSACTION (to handle) -> SESSIONS
	 * 		</pre>
	 * </p>
	 * 
	 * @return the transaction associated to this manager
	 */
	public TreeTransaction getTransaction();
	
	/**
	 * Return the root of the tree in this current session.
	 * 
	 * <p>the root element contains a collection of children, which in turn
	 * contains a collection of children, and so on. All this structure is
	 * returned in this method.</p>
	 * 
	 * <p>The root element represents the top of the tree and its
	 * <code>id</code> is always defined with the <code>null</code> value.
	 * Because that, every element with <code>null {@literal @Parent}</code>
	 * attached to the tree become immediately child of root.</p>
	 * 
	 * <p>
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
	 * or {@link TreeTransaction#initializeSession(String)} is invoked.</p>
	 * 
	 * 
	 * @param <T> the class type of the source wrapped object that will be
	 * encapsulated into the {@link Element} object
	 * 
	 * @return the root level representing the top of the tree
	 * 
	 * @throws TreeException when the transaction has no session selected to
	 * work it. When if the current session is not active
	 */
	public <T> Element<T> root() throws TreeException; 
}