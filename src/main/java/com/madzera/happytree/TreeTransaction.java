package com.madzera.happytree;

import java.util.Collection;
import java.util.List;

import com.madzera.happytree.exception.TreeException;

/**
 * Dedicated interface to handle all stored tree sessions. It is responsible to
 * handle sessions in the API.
 * 
 * <p>With the transaction, the API client can initialize, deactivate, activate,
 * retrieve or destroy any sessions.</p>
 * 
 * <p>Structurally, this interface acts as a bridge between the 
 * {@link TreeManager} and the {@link TreeSession}, making inherent part of
 * session, handling the total responsibility of this class, leaving the
 * <code>TreeManager</code> interface free to just manipulate the elements
 * inside of the session.</p>
 * 
 * <p>An {@link Element} represents a node in a tree and a tree can only exist
 * within a previously created session. But to create a session, it is needed to
 * use an object that represents the session transaction, and this object is an
 * instance of this interface. However, a transaction only can be recovered from
 * within the <code>TreeManager</code> by invoking
 * {@link TreeManager#getTransaction()}.</p>
 * 
 * <p>There are two ways of creating tree sessions:</p>
 * 	<ol>
 * 		<li>Create a new empty tree session;</li>
 * 		<li>Create a tree session from the <b>API Transformation Process</b>.
 * 		</li>
 * 	</ol>
 * 
 * <p>The first one happens when the API client just wants create a default
 * empty tree session by invoking {@link #initializeSession(String, Class)}.
 * Here an empty tree is created, specifying which type of class this session
 * will have as node.</p>
 * 
 * <p>The last one happens when an API client desire to transform a 
 * <code>Collection</code> of objects that represents a linear tree structure.
 * This transformation (which is called by API Transformation Process) converts
 * this linear structure into a real tree structure, which each node is
 * represented by an <code>Element</code> objects. This API Transformation
 * Process implements an internal life cycle.</p>
 * 
 * <p><b>The <code>TreeTransaction</code> can only work with one
 * <code>TreeSession</code> per time, meanwhile the other sessions stay in
 * background waiting to be selected again in another time.</b></p>
 * 
 * @author Diego Madson de Andrade Nóbrega
 * 
 */
public interface TreeTransaction {
	
	/**
	 * Initializes a default new empty tree session with the specified
	 * identifier. Automatically, after of creating the session, this one gets
	 * checked out to be able to work as current session.
	 * 
	 * <p>In this method, the API client must create elements one-by-one until
	 * it assembles the desired tree. This is easily done by the
	 * {@link TreeManager} methods.</p>
	 * 
	 * <p>When starting a new standard session, this is necessary to specify the
	 * parameterized type that corresponds to the node class from which the
	 * <code>Element</code> object will wrap it. Then, each element will contain
	 * its respective node from this parameterized type.</p>
	 * 
	 * <p>The <code>identifier</code> and <code>type</code> parameters can not
	 * be <code>null</code>.</p>
	 * 
	 * @param <T> the class type of the session which will store elements with
	 * this type of nodes
	 * 
	 * @param identifier the session identifier
	 * 
	 * @param type class type of the node that will be wrapped by
	 * <code>Element</code> object within tree session
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
	 * Initializes a session with a specified identifier and a list of objects
	 * (which represent nodes) to be transformed into a real tree structure.
	 * Automatically, after of creating the session, this is already available
	 * to work as current session.
	 * 
	 * <p>It corresponds to one of the main features of the HappyTree API. When
	 * this is necessary, it converts a linear list of objects that behave as a
	 * tree into a real tree structure. For this, these objects need to follow
	 * some requirements for the <b>API Transformation Process</b> occurs
	 * successfully.</p>
	 * 
	 * <p>This process implements a life cycle with 5 general phases:</p>
	 * 
	 * 	<ol>
	 * 		<li><b>Pre-Validation</b></li>
	 * 		<li><b>Core Engine (with 3 sub-phases)</b></li>
	 * 		<li><b>Post-Validation</b></li>
	 * 	</ol>
	 * 
	 * <p><b>Note: The concept of sub-phase is merely illustrative and serves
	 * only to distinguish the phases of validations from the phases of the tree
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
	 * 			source objects list into a real tree structure.
	 * 			</td>
	 * 			<td>Extraction - Initialization - Binding</td>
	 * 		</tr>
	 * 
	 * 		<tr>
	 * 			<td>Post-Validation</td>
	 * 			<td>Confirms that the elements of the built tree correspond to
	 * 			their respective input objects.</td><td></td>
	 * 		</tr>
	 * 	</table>
	 * 
	 * <p><b>Pre-Validation:</b></p>
	 * <p>This phase represents the begin of the API Transformation Process. In
	 * this phase, the core API needs that all inputs aspects to be transformed
	 * need to conform. The following validations are done:</p>
	 * 
	 * 	<ul>
	 * 		<li>Verifies whether the list of objects to be transformed is not
	 * 		<code>null</code> or empty;</li>
	 * 		<li>Verifies whether there is an existing session with the same
	 * 		identifier;</li>
	 * 		<li>Verifies whether the class of the objects to be transformed is
	 * 		annotated with {@literal @Tree};</li>
	 * 		<li>Verifies whether the identifier attribute of the object to be
	 * 		transformed has the	{@literal @Id} annotation;</li>
	 * 		<li>Verifies whether the parent attribute of the object to be
	 * 		transformed has the {@literal @Parent} annotation;</li>
	 * 		<li>Verifies whether the identifier attribute of each object is
	 * 		<code>null</code>;</li>
	 * 		<li>Validates whether all source objects have all of them the
	 * 		{@literal @Id} and {@literal @Parent} attributes with the same class
	 * 		type;</li>
	 * 		<li>Checks for duplicate {@literal @Id};</li>
	 * 		<li>Verifies whether the class of the objects to be transformed has
	 * 		getters and setters.</li>
	 * 	</ul>
	 * 
	 * <p><b>Core Engine:</b></p>
	 * <p><i>Extraction:</i></p>
	 * <p>If the input represented by the list of objects to be transformed
	 * passed all validations from the previous phase, then the HappyTree API
	 * takes them and extracts them in order to separate them from their
	 * respective parents. Therefore, as a product for the next phase, there
	 * will be the objects and their respective parents separated into two
	 * blocks.</p>
	 * 
	 * <p><i>Initialization:</i></p>
	 * <p>In this phase, the HappyTree API instantiates an object of type
	 * {@link Element} for each source object used as input and passes the
	 * respective {@literal @Id} and {@literal @Parent} attributes of the source
	 * object to that element. In addition, the source object itself is
	 * automatically wrapped into that element, thus making the source object
	 * liable to be a tree node, since the element naturally represents a node
	 * in the context of the HappyTree API. After the tree is built, to retrieve
	 * the source object just invoke the {@link Element#unwrap()} method.</p>
	 * 
	 * <p><i>Binding:</i></p>
	 * <p>After obtaining the list of resulting elements from the previous phase,
	 * the HappyTree API will now bind each element to its respective parent,
	 * through the block of separated parent objects in the Extraction phase.
	 * </p>
	 * 
	 * <p>Therefore, it is at this phase that the tree is actually assembled.
	 * Thus, for each node in the tree we have a represented element object,
	 * where each element has:</p>
	 * 
	 * 	<ul>
	 * 		<li>The {@literal @Id} attribute value;</li>
	 * 		<li>The {@literal @Parent} attribute value;</li>
	 * 		<li>The <code>wrappedNode</code> corresponding the source object
	 * 		used in this process;</li>
	 * 		<li>The collection of <code>children</code>, corresponding to other
	 * 		elements in which they are children of this;</li>
	 * 		<li>The tree <code>session</code>, which this element belongs.</li>
	 * 	</ul>
	 * 
	 * <p><b>Post-Validation:</b></p>
	 * <p>This phase confirms that the provided input corresponds exactly to the
	 * generated output (the tree itself). If there is any inconsistency, a
	 * <code>TreeException</code> is threw, immediately aborting the process and
	 * rolling back the session.</p>
	 * 
	 * <p>this life cycle is only triggered in the <b>API Transformation
	 * Process</b>, by invoking this method, passing a collection of a linear
	 * structure to be transformed (<code>nodes</code>).</p>
	 * 
	 * @param <T> the class type of the session which will store elements with
	 * this type of nodes
	 * 
	 * @param identifier the session identifier
	 * 
	 * @param nodes the <code>Collection</code> of objects which represent nodes
	 * in the tree and that will be transformed
	 * 
	 * @throws TreeException when:
	 * <ul>
	 * 	<li>There is another session with the same identifier;</li>
	 * 	<li>When the class of the object to be converted has not been annotated
	 * 	with {@literal @Tree};</li>
	 * 	<li>The identifier attribute of the object to be converted has not been
	 * 	annotated with {@literal @Id};</li>
	 * 	<li>The parent attribute of the object to be converted has not been
	 * 	annotated with {@literal @Parent};</li>
	 * 	<li>When the annotated {@literal @Id} and {@literal @Parent} attributes
	 * 	have incompatible types;</li>
	 * 	<li>When there are duplicate IDs;</li>
	 * 	<li>When the class of the object to be transformed has not
	 * 	{@literal getters & setters};</li>
	 * 	<li>When the API Transformation Process was not well processed at Post-
	 * 	Validation phase.</li>
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
	 * <p>When a session is deleted, this one is permanently removed, as it not
	 * possible to retrieve it anymore. consequently the tree and its elements
	 * within this section are also removed.</p>
	 * 
	 * @param identifier the name of the session identifier to be deleted
	 */
	public void destroySession(String identifier);
	
	/**
	 * Deletes the current session previously checked out.
	 * 
	 * <p>When the current session is deleted, this one is permanently removed,
	 * as it not possible to retrieve it anymore. consequently the tree and its
	 * elements within this section are also removed.</p>
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
	 * <p>Passing the <code>null</code> or non-existent identifier causes the
	 * current session of the transaction to be &quot;canceled&quot;. A
	 * &quot;canceled&quot; session means that the transaction has no session
	 * available to work on it, and therefore, it is not possible to run out any
	 * operation from {@link TreeManager}.</p>
	 * 
	 * @param identifier the name of the session identifier to be pick it up
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
	 * <p><b>This method just active a session, and not turn it ready to be
	 * worked. For this, invoke the {@link #sessionCheckout(String)} before or
	 * after of activating a session.</b></p>
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
	 * <p>With an deactivated session, its elements <b>can not</b> be handled
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
	 * <p><b>This method only clone a session, and not turn it ready to be
	 * worked. For this, invoke the {@link #sessionCheckout(String)} before or
	 * after of cloning a session.</b></p>
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
	 * <p><b>This method only clone a session, and not turn it ready to be
	 * worked. For this, invoke the {@link #sessionCheckout(String)} before or
	 * after of cloning a session.</b></p>
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