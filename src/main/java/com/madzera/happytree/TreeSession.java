package com.madzera.happytree;

import java.util.Collection;

/**
 * Interface responsible for storing element trees. An element tree corresponds
 * to a hierarchical data structure in a tree format in which the elements are
 * arranged as nodes of that tree. This tree can be created freely or from a
 * data structure whose objects, in that structure, behave like a tree but are
 * arranged linearly. By invoking
 * {@link TreeTransaction#initializeSession(String, Collection)}, this linear
 * structure is transformed into an actual tree structure.
 * 
 * <p>The sessions are initialized by the {@link TreeTransaction} interface,
 * which can manage one or more sessions, where each session represents a tree
 * instance in the HappyTree API context. Although the
 * <code>TreeTransaction</code> interface is able to manage instances of
 * <code>TreeSession</code>, it can only do so one at a time. Thus, to handle a
 * given session, it must be captured first by invoking
 * {@link TreeTransaction#sessionCheckout(String)}. On each session change via
 * this method, the chosen session becomes the current session so the
 * <code>TreeTransaction</code> interface can manipulate it.</p>
 * 
 * <p>It is important to note that when invoking
 * {@link TreeTransaction#initializeSession(String, Class)} or
 * {@link TreeTransaction#initializeSession(String, Collection)}, the session is
 * automatically made available to the <code>TreeTransaction</code>. Thus, the
 * session is ready for manipulation immediately after initialization.</p>
 *
 * <p>A session contains three fundamental states:</p>
 * 
 * <table>
 * <caption>Session States</caption>
 * 	<tr>
 * 		<th>State</th><th>Exists?</th><th>Can it be handled?</th>
 * 	</tr>
 * 	<tr>
 * 		<td><b>Activated</b></td><td>&#10004;</td><td>&#10004;</td>
 * 	</tr>
 * 	<tr>
 * 		<td><b>Deactivated</b></td><td>&#10004;</td><td>X</td>
 * 	</tr>
 * 	<tr>
 * 		<td><b>Destroyed</b></td><td>X</td><td>X</td>
 * 	</tr>
 * </table>
 * 
 * <p>An active session, as noted above, exists and can be freely handled. A
 * deactivated session, on the other hand, although it cannot be handled, still
 * remains in memory, waiting only to be activated so it can be handled. In
 * this case, the tree and its elements are 'alive' in memory but disabled.</p>
 * 
 * <p>A destroyed session is one that previously existed but has been
 * permanently removed along with the entire tree and its elements. A destroyed
 * session never returns to its natural state. It represents the end of its
 * lifecycle.</p>
 * 
 * @author Diego Madson de Andrade Nóbrega
 * 
 * @see TreeManager
 * @see Element
 */
public interface TreeSession {
	
	/**
	 * Returns the session identifier name.
	 * 
	 * <p>A session identifier is defined when the session is initialized by
	 * invoking the {@link TreeTransaction#initializeSession(String, Class)} or
	 * {@link TreeTransaction#initializeSession(String, Collection)}. This
	 * identifier <b>must</b> be unique.</p>
	 * 
	 * @return the session identifier name
	 */
	public String getSessionId();
	
	/**
	 * Verifies whether the session is active.
	 * 
	 * <p>Here, the method considers just two session states:</p>
	 * <ul>
	 * 	<li><b>Activated</b></li>
	 * 	<li><b>Deactivated</b></li>
	 * </ul>
	 * 
	 * <p>The <i>Destroyed</i> state is not considered because it is a
	 * <code>null</code> state.</p>
	 * 
	 * @return <code>true</code> if the session is active, <code>false</code>
	 * otherwise
	 */
	public boolean isActive();
	
	/**
	 * Returns the entire tree session structure, represented by the <b>root</b>
	 * element.
	 * 
	 * <p>This method works similarly to the {@link TreeManager#root()} method,
	 * returning the root element of the tree. From the root element, it is
	 * possible to navigate through all its children, each child's children, and
	 * so on recursively, thus accessing the entire tree structure.</p>
	 * 
	 * <p>Below is an example of a tree structure with its root element and
	 * children:</p>
	 * <pre>
	 * 
	 *                         ELEMENT(ROOT)
	 *                               /\
	 *                     ELEMENT(A)  ELEMENT(B)
	 *                         /\         /\
	 *                    E(A1) E(A2) E(B1) E(B2)
	 * 
	 * </pre>
	 * 
	 * <p>The root element is a special element that has no <code>@Id</code>,
	 * <code>@Parent</code>, or wrapped object node. It is created automatically
	 * by the core API when the session is initialized, that is, when invoking
	 * {@link TreeTransaction#initializeSession(String, Class)} or
	 * {@link TreeTransaction#initializeSession(String, Collection)} methods.
	 * </p>
	 * 
	 * <p>Only a few operations cannot be performed on the root element using
	 * the two interfaces: <code>Element</code> and <code>TreeManager</code>.
	 * </p>
	 * 
	 * <b>Element</b>
	 * 
	 * <table>
	 * <caption>Root Element Unsupported Operations</caption>
	 * 	<tr><th>Method</th><th>Description</th></tr>
	 * 	<tr>
	 * 		<td>{@link Element#getId()}</td>
	 * 		<td>Always returns <code>null</code> because the root element has no
	 * 		identifier.</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>{@link Element#setId(Object)}</td>
	 * 		<td>No effect.</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>{@link Element#getParent()}</td>
	 * 		<td>Always returns <code>null</code> because the root element has no
	 * 		<code>@Parent</code>.</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>{@link Element#setParent(Object)}</td>
	 * 		<td>No effect.</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>{@link Element#unwrap()}</td>
	 * 		<td>Always returns <code>null</code> because the root element has no
	 * 		wrapped object node.</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>{@link Element#wrap(Object)}</td>
	 * 		<td>No effect.</td>
	 * 	</tr>
	 * </table>
	 * 
	 * <b>TreeManager</b>
	 * 
	 * <table>
	 * <caption>Root Element Unsupported Operations - First Parameter</caption>
	 * 	<tr><th>Method</th><th>Description</th></tr>
	 * 	<tr>
	 * 		<td>{@link TreeManager#cut(Element, Element)}</td>
	 * 		<td>Throws TreeException.</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>{@link TreeManager#cut(Object, Object)}</td>
	 * 		<td>No effect.</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>{@link TreeManager#copy(Element, Element)}</td>
	 * 		<td>Throws TreeException.</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>{@link TreeManager#removeElement(Element)}</td>
	 * 		<td>Throws TreeException.</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>{@link TreeManager#removeElement(Object)}</td>
	 * 		<td>No effect.</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>{@link TreeManager#persistElement(Element)}</td>
	 * 		<td>Not possible.</td>
	 * 	</tr>
	 * </table>
	 * 
	 * @return the root of the tree
	 * 
	 * @see TreeManager#root()
	 */
	public <T> Element<T> tree();
}