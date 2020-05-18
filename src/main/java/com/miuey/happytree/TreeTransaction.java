package com.miuey.happytree;

import java.util.Collection;
import java.util.List;
import com.miuey.happytree.exception.TreeException;

/**
 * Dedicated interface to handle all stored tree sessions. It is responsible for
 * transactions that handle sessions in the API.
 * 
 * <p>With this interface, the user API can create, deactivate, activate,
 * retrieve or destroy any sessions.</p>
 * 
 * <p>Structurally, this interface acts as a bridge between the 
 * {@link TreeManager} and the {@link TreeSession}, making the inherent part of
 * session, handling the total responsibility of this class, leaving the
 * {@link TreeManager} interface free to just manipulate the elements.</p>
 * 
 * <p>An {@link Element} represents a node in a tree and a tree can only exist
 * within a previously created session ({@link TreeSession}). But to create a
 * session, it is needed to use an object that represents the session
 * transaction, and this object is an instance of this interface. However, a
 * transaction only can be recovered from within the {@link TreeManager} by
 * invoking {@link TreeManager#getTransaction()}.</p>
 * 
 * <p>There are two ways of creating tree sessions:
 * 	<ol>
 * 		<li>Create a new empty tree session;</li>
 * 		<li>Create a tree session from the <b>API Transformation Process</b>.
 * 		</li>
 * 	</ol>
 * </p>
 * 
 * <p>The first one happens when the user API just wants create a default empty
 * tree session by invoking {@link #initializeSession(String)}}. Here an empty
 * tree is created.</p>
 * 
 * <p>The last one happens when an user API desire to transform a 
 * <code>Collection</code> of an annotated object that represents a linear tree
 * structure. This transformation (which is called by API Transformation
 * Process) convert this linear structure into a real hierarchical tree
 * structure of objects (represented by {@link Element}} objects. This API
 * Transformation Process implements an internal life cycle.</p>
 * 
 * <p><b>The <code>TreeTransaction</code> can only work with one
 * {@link TreeSession} per time, meanwhile the other sessions stay in
 * background waiting to be selected again in another time.</b></p>
 * 
 * @author Diego Nóbrega
 * @author Miuey
 * 
 * @see {@link TreeSession}
 * 
 * @version %I%, %G%
 *
 */
public interface TreeTransaction {
	
	/**
	 * Initialize a default new empty tree session with the specified
	 * identifier. Automatically, after of creating the session, this new
	 * session gets checked out to be able to work over it.
	 * 
	 * <p>In this method, the user API must create the elements one-by-one until
	 * he assembles his desired tree. It is easily done by {@link TreeManager}
	 * methods.</p>
	 * 
	 * <p>The identifier can not be empty or null.</p>
	 * 
	 * @param identifier the new session identifier
	 * 
	 * @throws TreeException when there is another session with the same
	 * identifier
	 * 
	 * @throws IllegalArgumentException when the <code>identifier</code> is
	 * <code>null</code> or empty
	 */
	public void initializeSession(String identifier) throws TreeException;
	
	/**
	 * Initialize a session with the specified identifier by a previous list of
	 * source objects to be transformed into a real tree structure. 
	 * Automatically, after of creating the session, this new session gets
	 * checked out to be able to work over it.
	 * 
	 * <p>It corresponds to one of the main features of the API. when it is
	 * necessary to convert a linear list of objects to a real hierarchical tree
	 * structure. For this, the object to be transformed needs to follow some
	 * requirements for the API transformation process to occur successfully.
	 * </p>
	 * 
	 * <p>These requirements are validated throughout the life cycle of the API
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
	 * 			<td>Pre-Validation</td><td>Validate the object's requirements.
	 * 			</td><td></td>
	 * 		</tr>
	 * 
	 * 		<tr>
	 * 			<td>Core Engine</td><td>Processes tree assembly and
	 * 			transformation/migration from the source object list.</td>
	 * 			<td>Extraction - Initialization - Binding</td>
	 * 		</tr>
	 * 
	 * 		<tr>
	 * 			<td>Post-Validation</td><td>compares the assembled tree with the
	 * 			source objects.</td><td></td>
	 * 		</tr>
	 * 	</table>
	 * </p>
	 * 
	 * <p><b>Pre-Validation:</b></p>
	 * <p>This phase represents the begin of the API Transformation Process. In
	 * this phase, the core API needs that all aspects of the input to be
	 * transformed need to conform. So it will do a validation of:
	 * 
	 * 	<ul>
	 * 		<li>If the <code>identifier</code> or <code>objects</code> are
	 * 		<code>null</code> or empty;</li>
	 * 		<li>If there is a existing session with the same identifier passed
	 * 		through;</li>
	 * 		<li>If the source class object to be transformed has the
	 * 		{@literal @Tree} annotation;</li>
	 * 		<li>If the source identifier attribute class object to be
	 * 		transformed has the {@literal @Id} annotation;</li>
	 * 		<li>If the source parent attribute class object to be transformed
	 * 		has no the {@literal @Parent} annotation;</li>
	 * 		<li>Iterate over each object and verify if the {@literal @Id}
	 * 		annotated attribute value is null;</li>
	 * 		<li>Validate if all source objects have all of them the
	 * 		{@literal @Id} and {@literal @Parent} attributes with the same type.
	 * 		</li>
	 * 	</ul>
	 * </p>
	 * 
	 * <p><b>Core Engine:</b></p>
	 * <p>If all requirements of the objects are right, then in this phase will
	 * begin the process of assembly of the tree.</p>
	 * <p><i>Extraction:</i></p>
	 * <p>
	 * 	<ol>
	 * 		<li>Iterate over each source object to be extracted;</li>
	 * 		<li>Create a map to store these objects and for each the objects:
	 * 		</li>
	 * 			<ol>
	 * 				<li>The map key will be the {@literal @Id} attribute value
	 * 				from the source object;</li>
	 * 				<li>The map value will be the own respective source
	 * 				object.</li>
	 * 			</ol>
	 * 	</ol>
	 * </p>
	 * 
	 * <p><i>Initialization:</i></p>
	 * <p>After having the fill up map of source objects stored, then
	 * the API will iterate over it and for each source object:</p>
	 * <p>
	 * 	<ol>
	 * 		<li>It will instantiate an <code>Element</code> object;</li>
	 * 		<li>The <code>id Element</code> attribute will be the corresponding
	 * 		identifier attribute of the source object to be transformed;</li>
	 * 		<li>The <code>parent Element</code> attribute will be the
	 * 		corresponding parent attribute of the source object to be
	 * 		transformed.</li>
	 * 	</ol>
	 * </p>
	 * 
	 * <p><i>Binding:</i></p>
	 * <p>After having the resulting collection of elements initialized, the
	 * core API will bind each element with its respective source object,
	 * through the <code>id</code> attribute. So, for this work, the core API
	 * will seek for each element and:</p>
	 * <p>
	 * 	<ol>
	 * 		<li>Wrap the source transformed object into this own element;</li>
	 * 		<li>Put this own element into another previously created element,
	 * 		as its child, compounding the <code>parent</code> attribute of the
	 * 		child with the <code>id</code> attribute of the parent element
	 * 		(<b>if the parent element is not found, then the element will be in
	 * 		the tree root level</b>);</li>
	 * 		<li>In the end, each element will contains other elements inside of
	 * 		it, as a tree real structure of objects, ready to be manipulated as
	 * 		if it were the <i>DOM Javascript</i>.</li>
	 * 	</ol>
	 * </p>
	 * <p>So, the resulting <code>Element</code> object will have:</p>
	 * <p>
	 * 	<ul>
	 * 		<li>The <code>id</code> attribute value;</li>
	 * 		<li>The <code>parent</code> attribute value;</li>
	 * 		<li>The <code>wrappedObject</code> corresponding the source object
	 * 		transformed in this process;</li>
	 * 		<li>The collection of children, corresponding to other elements in
	 * 		which they are children of it;</li>
	 * 		<li>The tree session identifier which it belongs.</li>
	 * 	</ul>
	 * </p>
	 * 
	 * <p><b>Post-Validation:</b></p>
	 * <p>In this phase, the core API will compare the resulting created
	 * elements with the source objects. If there is a single difference, then a
	 * <codeTreeException</code> must be threw</p>
	 * 
	 * <p>this life cycle is only triggered in the API Transformation Process,
	 * by invoking this method, passing a linear structure to be transformed.
	 * </p>
	 * 
	 * @param identifier the tree session identifier
	 * 
	 * @param objects the <code>Collection</code> of source objects which will
	 * be transformed
	 * 
	 * @throws TreeException
	 * <ul>
	 * 	<li>There is another session with the same identifier;</li>
	 * 	<li>When the class of the objects to be converted has not been annotated
	 * 	by {@literal @Tree};</li>
	 * 	<li>the attribute class of the objects to be converted has not been
	 * 	annotated by {@literal @Id};</li>
	 * 	<li>the attribute class of the objects to be converted has not been
	 * 	annotated by {@literal @Parent};</li>
	 * 	<li>When the annotated {@literal @Id} and {@literal @Parent} attribute
	 * 	have incompatible types;</li>
	 * 	<li>When the API Transformation Process is not well processed at
	 * 	Post Validation phase.</li>
	 * </ul>
	 * 
	 * @throws IllegalArgumentException when the <code>identifier</code> or
	 * <code>objects</code> are <code>null</code> or empty
	 */
	public void initializeSession(String identifier, Collection<?> objects) 
			throws TreeException;
	
	/**
	 * Delete the session with the <code>identifier</code> specified.
	 * 
	 * <p>When a session is deleted, this is permanently removed, as it not
	 * possible to retrieve it anymore. consequently the tree and its elements
	 * within this section are also removed.</p>
	 * 
	 * @param identifier the identifier name of the session to be deleted
	 */
	public void destroySession(String identifier);
	
	/**
	 * Delete the current session previously checked out.
	 * 
	 * <p>When the current session is deleted, this is permanently removed, as
	 * it not possible to retrieve it anymore. consequently the tree and its
	 * elements within this section are also removed.</p>
	 */
	public void destroySession();
	
	/**
	 * Delete all the registered sessions.
	 * 
	 * <p>The removal occurs for both activated and deactivated sessions.</p>
	 */
	public void destroyAllSessions();
	
	/**
	 * Pick a tree session to work over it by the <code>identifier</code>. If
	 * there is a session with the specified <code>identifier</code>, then it is
	 * returned.
	 * 
	 * <p>When the session is selected to be work, the current session stays in
	 * background waiting to be selected in another time. It occurs because a
	 * transaction is able only to work over one session per time.</p>
	 * 
	 * <p>If the session is not found or the <code>identifier</code> is empty or
	 * <code>null</code> the current session will not be replaced by being
	 * worked on and <code>null</code> is returned.</p>
	 * 
	 * @param identifier the identifier of the session to be pick it up
	 * 
	 * @return an instance of <code>TreeSession</code>
	 */
	public TreeSession sessionCheckout(String identifier);
	
	/**
	 * Activate a session by the <code>identifier</code> specified.
	 * 
	 * <p>With an active session, its elements can be manipulated freely within
	 * the tree.</p>
	 * 
	 * <p><b>This method only active a session, and not turn it ready to be
	 * worked. For this, invoke the {@link #sessionCheckout(String)} before or
	 * after of activate a session.</p>
	 * 
	 * @param identifier the identifier of the session to be activated
	 */
	public void activateSession(String identifier);
	
	/**
	 * Activate the current session.
	 * 
	 * <p>With an active session, its elements can be manipulated freely within
	 * the tree.</p>
	 */
	public void activateSession();
	
	/**
	 * Deactivate a session by the <code>identifier</code> specified.
	 * 
	 * <p>Deactivating a session does not remove it from the list of registered
	 * sessions, instead the session is just disabled.</p>
	 * 
	 * <p>With an deactivated session, its elements <b>can not</b> be
	 * manipulated freely within the tree.</p>
	 * 
	 * @param identifier the identifier of the session to be deactivated
	 */
	public void deactivateSession(String identifier);
	
	/**
	 * Deactivate the current session.
	 * 
	 * <p>Deactivating the session does not remove it from the list of
	 * registered sessions, instead the current session is just disabled.</p>
	 * 
	 * <p>With an deactivated session, its elements <b>can not</b> be
	 * manipulated freely within the tree.</p>
	 */
	public void deactivateSession();
	
	/**
	 * Return the list of all registered sessions.
	 * 
	 * <p>The list of all sessions includes for both activated and deactivated
	 * sessions.</p>
	 * 
	 * @return sessions the list of all registered sessions
	 */
	public List<TreeSession> sessions();
	
	/**
	 * Replicate the tree session defined by <code>from</code> identifier for
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
	 * <p>In contrast, if the session defined by the <code>to</code> identifier
	 * does not exist, then a new session is created with the tree and its
	 * elements replicated from the source tree session.</p>
	 * 
	 * <p><b>This method only clone a session, and not turn it ready to be
	 * worked. For this, invoke the {@link #sessionCheckout(String)} before or
	 * after of cloning a session.</p>
	 * 
	 * @param from the source tree session to be replicated
	 * 
	 * @param to the target tree session
	 */
	public void cloneSession(String from, String to);
}