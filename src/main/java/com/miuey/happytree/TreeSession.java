package com.miuey.happytree;

import java.util.Collection;

/**
 * Interface responsible for storing the initialized element trees. A tree of
 * element corresponds to a hierarchical data structure in a tree format which
 * the elements are arranged as nodes of that tree. This tree can be created
 * freely or from of a data structure whose objects, in that structure, behave
 * like a tree but they are arranged linearly. So by invoking
 * {@link TreeTransaction#initializeSession(String, Collection)}, this linear
 * structure is transformed into a real tree structure.
 * 
 * <p>The sessions are initialized by the {@link TreeTransaction} interface,
 * which it can manage one or several sessions, which each session represents an
 * instance of a tree in the HappyTree API context. Although the
 * <code>TreeTransaction</code> interface is able to manage instances of
 * <code>TreeSession</code>, it is only able to do this at once. Thus, when
 * manipulating a given session, it must be captured previously, by invoking
 * {@link TreeTransaction#sessionCheckout(String)}. At each session change, it
 * is necessary to choose the session for the working copy of the
 * <code>TreeTransaction</code> interface to be able to manipulate it. It is
 * important to note that when invoking
 * {@linkplain TreeTransaction#initializeSession(String, Collection)} the
 * session is automatically placed in the working copy of
 * <code>TreeTransaction</code>.</p>
 *
 * <p>A session contains 3 fundamentals state:</p>
 * 
 * <p>
 * <table>
 * 	<th>State</th><th>Exists?</th><th>Can be handled?</th>
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
 * </p>
 * 
 * <p>An activated session, as is already known, exists and can be freely
 * manipulated. A deactivated session, on the other hand, although it is not
 * possible to be manipulated, it still remains in memory, waiting only to be
 * activated in order to be manipulated, that is, in this case, the tree and its
 * elements are 'alive' in memory but disabled.</p>
 * 
 * <p>A destroyed session is one that previously existed but has been
 * permanently removed with the entire tree and its elements. A destroyed
 * session never returns to its natural state. Represents its end of life cycle.
 * </p>
 * 
 * @author Diego Nóbrega
 * @author Miuey
 * 
 * @see {@link TreeManager}
 * 
 * @version %I%, %G%
 *
 */
public interface TreeSession {
	
	/**
	 * Get the session identifier.
	 * 
	 * <p>A session identifier is defined when the session is initialized by
	 * invoking the {@link TreeTransaction#initializeSession(String)} or
	 * {@link TreeTransaction#initializeSession(String, Collection)}. This
	 * identifier <b>must</b> be unique.</p>
	 * 
	 * <p>It is represented by a <code>String</code> name that cannot be empty
	 * or <code>null</code></p>
	 * 
	 * @return <code>String</code> the session identifier
	 */
	public String getSessionId();
	
	/**
	 * Verify if the session is activated.
	 * 
	 * <p>Here, the method consider just two of session state:</p>
	 * <ul>
	 * 	<li><b>Activated</b></li>
	 * 	<li><b>Deactivated</b></li>
	 * </ul>
	 * <p>The <i>Destroyed</i> state is not considered because it is a
	 * <code>null</code> state.</p>
	 * 
	 * @return <code>true</code> if the session is activated, <code>false</code>
	 * either.
	 */
	public boolean isActive();
	
	/**
	 * Return the full tree session.
	 * 
	 * <p>Whole the tree is returned since from the most high level on the
	 * hierarchy, <i>a.k.a</i> the root level. The root element is created
	 * automatically by the core API, at the session initialization moment.</p>
	 * 
	 * <p>All new elements created will remain below the root element. So, this
	 * method returns this root element, of which there will be children and
	 * more children who will be able to contain other children within and in a
	 * hierarchical way.</p>
	 * 
	 * @param <T> the class type of the source wrapped object that will be
	 * encapsulated into the {@link Element} object
	 * 
	 * @return <code>Element</code> the root element
	 * 
	 * @see {@link TreeManager#root()}
	 */
	public <T> Element<T> tree();
}