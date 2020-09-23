package com.miuey.happytree;

import java.util.Collection;
import java.util.List;
import com.miuey.happytree.exception.TreeException;

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
 * <p>There are two ways of creating tree sessions:
 * 	<ol>
 * 		<li>Create a new empty tree session;</li>
 * 		<li>Create a tree session from the <b>API Transformation Process</b>.
 * 		</li>
 * 	</ol>
 * </p>
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
	 * <p>It corresponds to one of the main features. When this is necessary to
	 * convert a linear list of objects (nodes) that behave as a tree into
	 * a real tree structure. For this, the transformed nodes needs to follow
	 * some requirements for the API transformation process occurs
	 * successfully.</p>
	 * 
	 * <p>These requirements are validated throughout the API life cycle of the
	 * transformation process. This process implements a life cycle with 3
	 * general phases:
	 * 
	 * 	<ol>
	 * 		<li><b>Pre-Validation</b></li>
	 * 		<li><b>Core Engine (with 3 sub-phases)</b></li>
	 * 		<li><b>Post-Validation</b></li>
	 * 	</ol>
	 * 
	 * 	<table>
	 * 		<th>Phase</th><th>Description</th><th>Sub-Phases</th>
	 * 
	 * 		<tr>
	 * 			<td>Pre-Validation</td><td>Validates each node's requirements.
	 * 			</td><td></td>
	 * 		</tr>
	 * 
	 * 		<tr>
	 * 			<td>Core Engine</td><td>Processes the tree assembly and
	 * 			transformation/migration from the nodes list into a real tree
	 * 			structure.</td><td>Extraction - Initialization - Binding</td>
	 * 		</tr>
	 * 
	 * 		<tr>
	 * 			<td>Post-Validation</td><td>Compares the assembled tree with the
	 * 			source nodes used as input.</td><td></td>
	 * 		</tr>
	 * 	</table>
	 * </p>
	 * 
	 * <p><b>Pre-Validation:</b></p>
	 * <p>This phase represents the begin of the API Transformation Process. In
	 * this phase, the core API needs that all inputs aspects to be transformed
	 * need to conform. The following validations will be done:
	 * 
	 * 	<ul>
	 * 		<li>The <code>identifier</code> or <code>nodes</code> are
	 * 		<code>null</code> or <code>nodes</code> collection is empty;</li>
	 * 		<li>There is a existing session with the same identifier passed
	 * 		through;</li>
	 * 		<li>The node class to be transformed has the {@literal @Tree}
	 * 		annotation;</li>
	 * 		<li>The identifier attribute of the node to be transformed has
	 * 		the	{@literal @Id} annotation;</li>
	 * 		<li>The parent attribute of the node to be transformed has the
	 * 		{@literal @Parent} annotation;</li>
	 * 		<li>Verify whether the identifier attribute of each node is
	 * 		<code>null</code>;</li>
	 * 		<li>Validates whether all node objects have all of them the
	 * 		{@literal @Id} and {@literal @Parent} attributes with the same class
	 * 		type.
	 * 		</li>
	 * 	</ul>
	 * </p>
	 * 
	 * <p><b>Core Engine:</b></p>
	 * <p><i>Extraction:</i></p>
	 * <p>If all node requirements are right, then the tree assembly process
	 * will be carried out at this stage.</p>
	 * 	<ol>
	 * 		<li>Iterates over each node to be extracted;</li>
	 * 		<li>Creates a map to store these nodes and for each node:
	 * 		</li>
	 * 			<ol>
	 * 				<li>The key map will be the {@literal @Id} attribute value
	 * 				from the node object to be transformed;</li>
	 * 				<li>The value map will be the own node object to be
	 * 				transformed.</li>
	 * 			</ol>
	 * 	</ol>
	 * 
	 * <p><i>Initialization:</i></p>
	 * <p>After having filling out the map with stored nodes, then the API will
	 * iterate over it and for each node object:</p>
	 * <p>
	 * 	<ol>
	 * 		<li>Will instantiate an <code>Element</code> object;</li>
	 * 		<li>The element <code>id</code> attribute will be the corresponding
	 * 		identifier attribute of the source node to be transformed;</li>
	 * 		<li>The <code>parent</code> attribute of the element will be the
	 * 		corresponding parent attribute of the source node to be	transformed.
	 * 		</li>
	 * 	</ol>
	 * </p>
	 * 
	 * <p><i>Binding:</i></p>
	 * <p>Once having the resulting collection of initialized elements, the
	 * core API will bind each element and transform it into a parent-child
	 * relationship, where each element can contain multiple children within it
	 * and each child can have other children, and so on. That is done through
	 * the <code>id</code> attribute. So, for this work, the core API will seek
	 * for each element and:</p>
	 * <p>
	 * 	<ol>
	 * 		<li>Wraps the transformed node (the source object which it has the
	 * 		{@literal Tree}, {@literal Id} and {@literal Parent} annotations)
	 * 		into this own element;</li>
	 * 		<li>Puts this own element into another one, as child, compounding
	 * 		the <code>parent</code> attribute of the child with the
	 * 		<code>id</code> attribute of the parent element (<b>if the parent
	 * 		element is not found, then the element will be moved to the tree
	 * 		root level</b>);</li>
	 * 		<li>In the end, each element will contains other elements inside of
	 * 		it (as children), as a real tree structure of elements, ready to be
	 * 		handled.</li>
	 * 	</ol>
	 * </p>
	 * <p>So, the resulting <code>Element</code> object will have:</p>
	 * <p>
	 * 	<ul>
	 * 		<li>The <code>id</code> attribute value;</li>
	 * 		<li>The <code>parent</code> attribute value;</li>
	 * 		<li>The <code>wrappedNode</code> corresponding the source node
	 * 		object used in this transformation process;</li>
	 * 		<li>The collection of children elements;</li>
	 * 		<li>The tree session which this element belongs in.</li>
	 * 	</ul>
	 * </p>
	 * 
	 * <p><b>Post-Validation:</b></p>
	 * <p>In this phase, the core API will compare the resulting elements with
	 * the source node objects used in this transformation process. If there is
	 * a single difference, then a <codeTreeException</code> must be threw.</p>
	 * 
	 * <p>this life cycle is only triggered in the API Transformation Process,
	 * by invoking this method, passing a collection of a linear structure to be
	 * transformed.</p>
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
	 * 	<li>When the class of node object to be converted has not been annotated
	 * 	by {@literal @Tree};</li>
	 * 	<li>The identifier attribute of node object to be converted has not been
	 * 	annotated by {@literal @Id};</li>
	 * 	<li>The parent attribute of node object to be converted has not been
	 * 	annotated by {@literal @Parent};</li>
	 * 	<li>When the annotated {@literal @Id} and {@literal @Parent} attributes
	 * 	have incompatible types;</li>
	 * 	<li>When the API Transformation Process was not well processed at Post
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