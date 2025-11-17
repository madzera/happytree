package com.madzera.happytree;

import java.util.Collection;
import java.util.List;

import com.madzera.happytree.exception.TreeException;

/**
 * Dedicated interface to handle all stored tree sessions. It is responsible for
 * handling sessions in the API.
 * 
 * <p>With the transaction, the API client can initialize, deactivate, activate,
 * retrieve or destroy any sessions.</p>
 * 
 * <p>Structurally, this interface acts as a bridge between the 
 * {@link TreeManager} and the {@link TreeSession}, being an inherent part of
 * the session, handling the total responsibility of this class, leaving the
 * <code>TreeManager</code> interface free to just manipulate the elements
 * inside the session.</p>
 * 
 * <p>An {@link Element} represents a node in a tree, and a tree can only exist
 * within a previously created session. But to create a session, an object that
 * represents the session transaction is needed, and this object is an instance
 * of this interface. However, a transaction can only be recovered from within
 * the <code>TreeManager</code> by invoking {@link TreeManager#getTransaction()}.
 * </p>
 * 
 * <p>There are two ways of creating tree sessions:</p>
 * 	<ol>
 * 		<li>Create a new empty tree session;</li>
 * 		<li>Create a tree session from the <b>API Transformation Process</b>.
 * 		</li>
 * 	</ol>
 * 
 * <p>The first one happens when the API client just wants to create a default
 * empty tree session by invoking {@link #initializeSession(String, Class)}.
 * Here, an empty tree is created, specifying which type of class this session
 * will have as a node.</p>
 * 
 * <p>The last one happens when an API client desires to transform a 
 * <code>Collection</code> of objects that represents a linear tree structure.
 * This transformation (which is called the API Transformation Process) converts
 * this linear structure into an actual tree structure, where each node is
 * represented by <code>Element</code> objects.
 * 
 * <p><b>The <code>TreeTransaction</code> can only work with only one
 * <code>TreeSession</code> at a time, while the other sessions remain in the
 * background waiting to be checked in again later.</b></p>
 * 
 * @author Diego Madson de Andrade Nóbrega
 */
public interface TreeTransaction {
	
	/**
	 * Initializes a new empty tree session with the specified identifier.
	 * Automatically, after creating the session, it gets checked in to be able
	 * to work as the current session.
	 * 
	 * <p>In this method, the API client must create elements one-by-one until
	 * it assembles the desired tree. This is easily done through the
	 * {@link TreeManager} methods.</p>
	 * 
	 * <p>When starting a new standard session, it is necessary to specify the
	 * parameterized class type that corresponds to the node class type that the
	 * <code>Element</code> object will wrap. Then, each element will contain
	 * its respective node of this parameterized type.</p>
	 * 
	 * <p>The <code>identifier</code> and <code>type</code> parameters cannot
	 * be <code>null</code>.</p>
	 * 
	 * @param <T> the class type of the session which will store elements with
	 * this type of nodes
	 * 
	 * @param identifier the session identifier (it must be unique)
	 * 
	 * @param type class type of the nodes that will be wrapped within their
	 * respective elements in this tree session
	 * 
	 * @throws TreeException when there is another session with the same
	 * identifier
	 * 
	 * @throws IllegalArgumentException when the <code>identifier</code> or
	 * <code>type</code> parameters are <code>null</code>
	 */
	public <T> void initializeSession(String identifier, Class<T> type)
			throws TreeException;
	
	/**
	 * Initializes a session with a specified identifier and a list of linear
	 * objects (with tree logical structure) to be transformed into an actual
	 * tree structure. Automatically, after creating the session, the session is
	 * already available to work as the current session.
	 * 
	 * <p>This corresponds to one of the main features of the HappyTree API.
	 * When necessary, it converts a linear list of objects that behave as a
	 * tree into an actual tree structure. For this, these objects need to
	 * follow some requirements for the <b>API Transformation Process</b> to
	 * occur successfully.</p>
	 * 
	 * <p>This process implements a lifecycle with 4 general phases:</p>
	 * 
	 * 	<ol>
	 * 		<li><b>Pre-Validation</b></li>
	 * 		<li><b>Core Engine (with 3 sub-phases)</b></li>
	 * 	</ol>
	 * 
	 * <p><b>Note: The concept of sub-phase is merely illustrative and serves
	 * only to distinguish the phase of validation from the phases of the tree
	 * transformation process.</b></p>
	 * 
	 * 	<table summary="ATP Lifecycle">
	 * 		<tr>
	 * 			<th>Phase</th><th>Description</th><th>Sub-Phases</th>
	 * 		</tr>
	 * 		<tr>
	 * 			<td>Pre-Validation</td><td>Validates each input object
	 * 			requirements.</td><td></td>
	 * 		</tr>
	 * 
	 * 		<tr>
	 * 			<td>Core Engine</td>
	 * 			<td>Processes the tree assembly and transformation from the
	 * 			source objects list into an actual tree structure.
	 * 			</td>
	 * 			<td>Extraction - Initialization - Binding</td>
	 * 		</tr>
	 * 	</table>
	 * 
	 * <p><b>Pre-Validation:</b></p>
	 * <p>This phase represents the beginning of the API Transformation Process.
	 * In this phase, the core API requires that all input aspects to be
	 * transformed conform. The following validations are done:</p>
	 * 
	 * 	<ul>
	 * 		<li>Verifies whether the list of objects to be transformed is not
	 * 			<code>null</code> or empty;
	 * 		</li>
	 * 		<li>Verifies whether there is an existing session with the same
	 * 			identifier;
	 * 		</li>
	 * 		<li>Verifies whether the class of the objects to be transformed is
	 * 			annotated with {@code @Tree} annotation;
	 * 		</li>
	 * 		<li>Verifies whether the identifier attribute of the object to be
	 * 			transformed has the	{@code @Id} annotation;
	 * 		</li>
	 * 		<li>Verifies whether the parent attribute of the object to be
	 * 			transformed has the {@code @Parent} annotation (for parent
	 * 			attribute with <code>null</code> value, this node will be placed
	 * 			in the first level of the tree - under the root);
	 * 		</li>
	 * 		<li>Verifies whether the class of the objects to be transformed
	 * 			implements <code>Serializable</code>;
	 * 		</li>
	 * 		<li>Verifies whether the identifier attribute of each object is
	 * 			<code>null</code>;
	 * 		</li>
	 * 		<li>Validates whether all source objects have both the
	 * 			{@code @Id} and {@code @Parent} attributes with the same class
	 * 			type;
	 * 		</li>
	 * 		<li>Checks for duplicate {@code @Id};</li>
	 * 		<li>Verifies whether the class of the objects to be transformed has
	 * 			getters and setters.
	 * 		</li>
	 * 	</ul>
	 * 
	 * <p><b>Core Engine:</b></p>
	 * <p><i>Extraction:</i></p>
	 * <p>If the input represented by the list of objects to be transformed
	 * passes all validations from the previous phase, then the HappyTree API
	 * takes them and extracts them in order to separate them from their
	 * respective parents. Therefore, as a product for the next phase, there
	 * will be the objects and their respective parents separated into two
	 * blocks.</p>
	 * 
	 * <p><i>Initialization:</i></p>
	 * <p>In this phase, the HappyTree API instantiates an object of type
	 * {@link Element} for each source object used as input and passes the
	 * respective {@code @Id} and {@code @Parent} attributes of the source
	 * object to that element. In addition, the source object itself is
	 * automatically wrapped into that element, thus making the source object
	 * liable to be a tree node, since the element naturally represents a node
	 * in the context of the HappyTree API. After the tree is built, to retrieve
	 * the source object just invoke the {@link Element#unwrap()} method.</p>
	 * 
	 * <p><i>Binding:</i></p>
	 * <p>After obtaining the list of resulting elements from the previous phase,
	 * the HappyTree API will now bind each element to its respective parent
	 * through the block of separated parents objects from the Extraction phase.
	 * </p>
	 * 
	 * <p>Therefore, it is at this phase that the tree is actually assembled.
	 * Thus, for each node in the tree we have a represented element object,
	 * where each element has:</p>
	 * 
	 * 	<ul>
	 * 		<li>The {@code @Id} attribute value;</li>
	 * 		<li>The {@code @Parent} attribute value;</li>
	 * 		<li>The <code>wrappedNode</code> corresponding to the source object
	 * 			used in this process;
	 * 		</li>
	 * 		<li>The collection of <code>children</code>, corresponding to other
	 * 			elements that are children of this one;
	 * 		</li>
	 * 		<li>The tree <code>session</code> to which this element belongs.
	 * 		</li>
	 * 	</ul>
	 * 
	 * <p>This lifecycle is only triggered in the <b>API Transformation
	 * Process</b> by invoking this method, passing a collection of a linear
	 * structure (<code>nodes</code>) to be transformed into an actual tree.</p>
	 * 
	 * @param <T> the class type of the session which will store elements with
	 * this type of nodes
	 * 
	 * @param identifier the session identifier
	 * 
	 * @param nodes the <code>Collection</code> of the linear objects to be
	 * transformed into an actual tree
	 * 
	 * @throws TreeException when:
	 * <ul>
	 * 	<li>There is another session with the same identifier;</li>
	 * 	<li>The class of the object to be converted has not been annotated
	 * 		with {@code @Tree};
	 * 	</li>
	 * 	<li>The identifier attribute of the object to be converted has not been
	 * 		annotated with {@code @Id};
	 * 	</li>
	 * 	<li>The parent attribute of the object to be converted has not been
	 * 		annotated with {@code @Parent};
	 * 	</li>
	 * 	<li>The class of the object to be transformed does not implement
	 * 		<code>Serializable</code>;
	 * 	</li>
	 * 	<li>The annotated {@code @Id} and {@code @Parent} attributes
	 * 		have incompatible types;
	 * 	</li>
	 * 	<li>There are duplicate IDs;</li>
	 * 	<li>The class of the object to be transformed has not getters & setters.
	 * 	</li>
	 * </ul>
	 * 
	 * @throws IllegalArgumentException when the <code>identifier</code> or
	 * <code>nodes</code> parameters are <code>null</code> or the
	 * <code>nodes</code> parameter is empty
	 */
	public <T> void initializeSession(String identifier, Collection<T> nodes) 
			throws TreeException;
	
	/**
	 * Deletes the session with the specified <code>identifier</code>.
	 * 
	 * <p>When a session is deleted, it is permanently removed, as it is not
	 * possible to retrieve it anymore. Consequently, the tree and its elements
	 * within this session are also removed.</p>
	 * 
	 * @param identifier the name of the session identifier to be deleted
	 */
	public void destroySession(String identifier);
	
	/**
	 * Deletes the current session previously checked out.
	 * 
	 * <p>When the current session is deleted, it is permanently removed,
	 * as it is not possible to retrieve it anymore. Consequently, the tree and its
	 * elements within this session are also removed.</p>
	 * 
	 * <p>In the case of removing the current session, the API client needs to
	 * specify a new session to be checked out right after the removal.</p>
	 */
	public void destroySession();
	
	/**
	 * Deletes all the registered sessions.
	 * 
	 * <p>The removal occurs for both activated and deactivated sessions.</p>
	 */
	public void destroyAllSessions();
	
	/**
	 * Pick a tree session to work over it. If there is a session with the
	 * specified <code>identifier</code>, then it is returned.
	 * 
	 * <p>When the session is selected to work over it, the current session
	 * stays in background, waiting to be selected in another time, while the
	 * checked out session, now, becomes the current session. It occurs because
	 * the transaction is able only to work over one session per time.</p>
	 * 
 * <p>Passing a <code>null</code> or non-existent identifier causes the
 * current session of the transaction to be &quot;canceled&quot;. A
 * &quot;canceled&quot; session means that the transaction has no session
 * available to work on, and therefore, it is not possible to execute any
 * operation from {@link TreeManager}.</p>
	 * 
	 * @param identifier the name of the session identifier to be picked up
	 * 
	 * @return an instance of <code>TreeSession</code> representing the
	 * current session
	 */
	public TreeSession sessionCheckout(String identifier);
	
	/**
	 * Activates a session by the specified <code>identifier</code>.
	 * 
	 * <p>With an active session, its elements can be handle freely within the
	 * tree.</p>
	 * 
 * <p><b>This method just activates a session and does not make it ready to be
 * worked on. For this, invoke {@link #sessionCheckout(String)} before or
 * after activating a session.</b></p>
	 * 
	 * @param identifier the name of the session identifier to be activated
	 */
	public void activateSession(String identifier);
	
	/**
	 * Activates the current session.
	 * 
	 * <p>With an active session, its elements can be handled freely within the
	 * tree.</p>
	 * 
	 * <p>The current session, if not <code>null</code>, will always be active
	 * by invoking this method, regardless of its current state.</p>
	 */
	public void activateSession();
	
	/**
	 * Deactivates a session by the specified <code>identifier</code>.
	 * 
	 * <p>Deactivating a session does not remove it from the list of registered
	 * sessions, instead, the session is just disabled.</p>
	 * 
 * <p>With a deactivated session, its elements <b>cannot</b> be handled
 * freely within the tree.</p>
	 * 
	 * @param identifier the name of the session identifier to be deactivated
	 */
	public void deactivateSession(String identifier);
	
	/**
	 * Deactivates the current session.
	 * 
	 * <p>Deactivating the current session does not remove it from the list of
	 * registered sessions, instead, the current session is just disabled.</p>
	 * 
	 * <p>With an deactivated session, its elements <b>can not</b> be handled
	 * freely within the tree.</p>
	 */
	public void deactivateSession();
	
	/**
	 * Returns the list of all registered sessions.
	 * 
	 * <p>The list of all sessions includes for both activated and deactivated
	 * sessions.</p>
	 * 
	 * @return the list of all registered sessions
	 */
	public List<TreeSession> sessions();
	
	/**
	 * Replicates the tree session defined by <code>from</code> identifier for
	 * the session defined by <code>to</code> identifier.
	 * 
	 * <p>Replicating an existing tree session consists of faithfully
	 * reproducing all the elements defined in the source tree for a target
	 * tree, whether this is a new or already existed target tree.</p>
	 * 
	 * <p>Replicating a session to an already existed session implies replacing
	 * the entire tree in the target session defined by <code>to</code>
	 * identifier, which causes the total loss of the previous states of the
	 * elements that were defined in the target tree.</p>
	 * 
	 * <p><b>Warning: the programmer is responsible for ensuring that the
	 * session defined by the <code>to</code> identifier is already existed or
	 * not.</b></p>
	 * 
	 * <p>In contrast, whether the session defined by the <code>to</code>
	 * identifier does not exist, then a new session is created with the tree
	 * and its elements replicated from the source tree session.</p>
	 * 
 * <p><b>This method only clones a session and does not make it ready to be
 * worked on. For this, invoke {@link #sessionCheckout(String)} before or
 * after cloning a session.</b></p>
	 * 
	 * @param from the identifier of the source tree session to be replicated
	 * 
	 * @param to the identifier of the target tree session
	 * 
	 * @return the cloned session
	 */
	public TreeSession cloneSession(String from, String to);
	
	/**
	 * Replicates the tree session defined by <code>from</code> identifier for
	 * the session defined by <code>to</code> identifier.
	 * 
	 * <p>Replicating an existing tree session consists of faithfully
	 * reproducing all the elements defined in the source tree for a target
	 * tree, whether this is a new or already existed target tree.</p>
	 * 
	 * <p>Replicating a session to an already existed session implies replacing
	 * the entire tree in the target session defined by <code>to</code>
	 * identifier, which causes the total loss of the previous states of the
	 * elements that were defined in the target tree.</p>
	 * 
	 * <p><b>Warning: the programmer is responsible for ensuring that the
	 * session defined by the <code>to</code> identifier is already existed or
	 * not.</b></p>
	 * 
	 * <p>In contrast, whether the session defined by the <code>to</code>
	 * identifier does not exist, then a new session is created with the tree
	 * and its elements replicated from the source tree session.</p>
	 * 
 * <p><b>This method only clones a session and does not make it ready to be
 * worked on. For this, invoke {@link #sessionCheckout(String)} before or
 * after cloning a session.</b></p>
	 * 
	 * @param from the instance of the tree session to be replicated
	 * 
	 * @param to the identifier of the target tree session
	 * 
	 * @return the cloned session
	 */
	public TreeSession cloneSession(TreeSession from, String to);
	
	/**
	 * Obtains the current session of the transaction. The current session
	 * implies the session that the transaction is referring to at this very
	 * moment.
	 * 
	 * <p>Since the transaction can only work with one session at a time, the
	 * API client needs to define which session will have the elements handled.
	 * To choose the session to be worked on, just invoke the method
	 * {@link #sessionCheckout(String)}, and to obtain the session to which it
	 * was chosen, just execute this method.</p>
	 * 
	 * <p><b>The programmer is responsible for ensuring that the current session
	 * is not <code>null</code>, as it causes errors when trying to execute
	 * operations of {@link TreeManager}.</b></p>
	 * 
	 * @return the current session of this transaction
	 */
	public TreeSession currentSession();
}