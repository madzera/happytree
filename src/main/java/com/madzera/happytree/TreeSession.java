package com.madzera.happytree;

import java.util.Collection;

/**
 * Interface responsible for storing the element trees. An element tree
 * corresponds to a hierarchical data structure in a tree format in which the
 * elements are arranged as nodes of that tree. This tree can be created freely
 * or from a data structure whose objects, in that structure, behave like a
 * tree but are arranged linearly. So by invoking
 * {@link TreeTransaction#initializeSession(String, Collection)}, this linear
 * structure is transformed into an actual tree structure.
 * 
 * <p>The sessions are initialized by the {@link TreeTransaction} interface,
 * which can manage one or several sessions, where each session represents an
 * instance of a tree in the HappyTree API context. Although the
 * <code>TreeTransaction</code> interface is able to manage instances of
 * <code>TreeSession</code>, it can only do this one at a time. Thus,
 * when handling a given session, it must be captured previously by invoking
 * {@link TreeTransaction#sessionCheckout(String)}. At each session change, it
 * is necessary to choose the session for the working copy of the
 * <code>TreeTransaction</code> interface to be able to manipulate it.</p> 
 * 
 * <p>It is important to note that when invoking
 * {@link TreeTransaction#initializeSession(String, Class)} or
 * {@link TreeTransaction#initializeSession(String, Collection)}, the session is
 * automatically placed in the working copy of <code>TreeTransaction</code>.</p>
 *
 * <p>A session contains 3 fundamental states:</p>
 * 
 * <table summary="Session States">
 * 	<tr>
 * 		<th>State</th><th>Exists?</th><th>Can be handled?</th>
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
 * <p>An active session, as already known, exists and can be freely handled.
 * A deactivated session, on the other hand, although it cannot be
 * handled, still remains in memory, waiting only to be activated in order to
 * be handled, that is, in this case, the tree and its elements are 'alive' in
 * memory but disabled.</p>
 * 
 * <p>A destroyed session is one that previously existed but has been
 * permanently removed along with the entire tree and its elements. A destroyed
 * session never returns to its natural state. It represents the end of its lifecycle.
 * </p>
 * 
 * @author Diego Madson de Andrade Nóbrega
 * 
 * @see TreeManager
 * @see Element
 */
public interface TreeSession {
	
	/**
	 * Obtains the session identifier name.
	 * 
	 * <p>A session identifier is defined when the session is initialized by
	 * invoking the {@link TreeTransaction#initializeSession(String, Class)} or
	 * {@link TreeTransaction#initializeSession(String, Collection)}. This
	 * identifier <b>must</b> be unique.</p>
	 * 
	 * <p>It is represented by a <code>String</code> name that cannot be
	 * <code>null</code>.</p>
	 * 
	 * @return the session identifier name
	 */
	public String getSessionId();
	
	/**
	 * Verifies if the session is active.
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
	 * Returns the whole tree session structure, represented by the <b>root</b>
	 * element.
	 * 
	 * <p>This method works similarly to the {@link TreeManager#root()} method,
	 * returning the root element of the tree.</p>
	 * 
 * <p>The root element is a special element that cannot be handled and has
 * no <code>@Id</code>, <code>@Parent</code>, or wrapped object node.
 * It is created by the core API when the session is initialized, that is,
 * when invoking {@link TreeTransaction#initializeSession(String, Class)}
 * or {@link TreeTransaction#initializeSession(String, Collection)} methods.
 * </p>
	 * 
	 * @param <T> the class type of the wrapped object node that will be
	 * encapsulated into the {@link Element} object
	 * 
	 * @return the root of the tree
	 * 
	 * @see TreeManager#root()
	 */
	public <T> Element<T> tree();
}