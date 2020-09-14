package com.miuey.happytree.transaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;

import org.junit.Test;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeSession;
import com.miuey.happytree.TreeTransaction;
import com.miuey.happytree.core.HappyTree;
import com.miuey.happytree.example.Directory;
import com.miuey.happytree.example.TreeAssembler;
import com.miuey.happytree.exception.TreeException;

/**
 * Test class for {@link TreeTransaction} operations.
 * 
 * <p>This test class represents the <i>happy scenario</i> for all operations of
 * {@link TreeTransaction}.</p>
 * 
 * @author Diego Nóbrega
 * @author Miuey
 * 
 * @see {@link TreeAssembler}
 * @see {@link Directory}
 */
public class TreeTransactionTest {

	/**
	 * Test for the {@link TreeTransaction#initializeSession(String, Object)}.
	 * 
	 * <p>Happy scenario for this operation</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to initialize a new session.
	 * <p><b>Expected:</b></p>
	 * Receive the same session which it was initialized.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create an identifier for the new session;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session with the identifier;</li>
	 * 	<li>Execute the {@link TreeTransaction#sessionCheckout(String)} using
	 * 	the same identifier;</li>
	 * 	<li>Verify that session is not <code>null</code>.</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void initializeSession() throws TreeException {
		final String nameTree = "initializeSession";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(nameTree, Directory.class);
		TreeSession session = transaction.sessionCheckout(nameTree);
		
		assertNotNull(session);
	}
	
	/**
	 * Test for the {@link TreeTransaction#initializeSession(String, Collection)}.
	 * 
	 * <p>Happy scenario for this operation</p>
	 * 
	 * <p>This test is solely and exclusively for the API Transformation
	 * Process. This makes use of the {@link TreeAssembler} and
	 * {@link Directory} classes to assemble a collection of linear objects that
	 * have tree behavior and that are going to be transformed.</p>
	 * 
	 * <p><b>For this demonstration, please see these sample classes in
	 * question.</b></p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to initialize a new session using the Transformation Process.
	 * <p><b>Expected:</b></p>
	 * Confirm that the element called <i>happytree</i> is inside of
	 * <i>projects</i> conform by
	 * {@link TreeAssembler#getDirectoryTree()}.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Get the collection of source objects to be transformed;</li>
	 * 	<li>Initialize a new session;</li>
	 * 	<li>Get the <i>projects</i> element through
	 * 	{@link TreeManager#getElementById(Object)};</li>
	 * 	<li>Verify if the <i>projects</i> element is not <code>null</code>;</li>
	 * 	<li>Get the <i>happytree</i> element through
	 * 	{@link TreeManager#getElementById(Object)};</li>
	 * 	<li>Verify if the <i>happytree</i> element is not <code>null</code>;
	 * 	</li>
	 * 	<li>Verify if the <i>projects</i> has the same id than the
	 * 	<i>happytree</i> parent id (true).</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void initializeSession_apiTransformationProcess()
			throws TreeException {
		final String sessionId = "initializeSession_apiTransformationProcess";
		final long projectId = 93209;
		final long happytreeId = 859452;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.
				getDirectoryTree();
		
		transaction.initializeSession(sessionId, directories);
		
		Element<Directory> projects = manager.getElementById(projectId);
		assertNotNull(projects);
		Element<Directory> happytree = manager.getElementById(happytreeId);
		assertNotNull(happytree);
		
		assertEquals(projects.getId(), happytree.getParent());
		//assertTrue(manager.containsElement(projects, happytree));
	}
	
	/**
	 * Test for the {@link TreeTransaction#destroySession()}.
	 * 
	 * <p>Happy scenario for this operation</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to destroy the current session.
	 * <p><b>Expected:</b></p>
	 * Receive a <code>null</code> value when trying to checkout a destroyed
	 * current session.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create two identifiers for the new sessions;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize the both sessions with their respective identifiers;</li>
	 * 	<li>Check out session 2;</li>
	 * 	<li>Check out session 1;</li>
	 * 	<li>Destroy the last checked out session (session 1);</li>
	 * 	<li>Verify that the session 1 is <code>null</code> and the session 2 is
	 * 	not <code>null</code>.</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void destroySession() throws TreeException {
		final String sessionIdentifier1 = "destroySession1";
		final String sessionIdentifier2 = "destroySession2";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionIdentifier1, Directory.class);
		transaction.initializeSession(sessionIdentifier2, Directory.class);
		
		TreeSession session2 = transaction.sessionCheckout(sessionIdentifier2);
		TreeSession session1 = transaction.sessionCheckout(sessionIdentifier1);
		
		/*
		 * Destroy the last checked out session (session1).
		 */
		transaction.destroySession();
		
		session1 = transaction.sessionCheckout(sessionIdentifier1);
		assertNull(session1);
		session2 = transaction.sessionCheckout(sessionIdentifier2);
		assertNotNull(session2);
	}
	
	/**
	 * Test for the {@link TreeTransaction#destroySession(String)}.
	 * 
	 * <p>Happy scenario for this operation</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to destroy an existent session.
	 * <p><b>Expected:</b></p>
	 * Receive a <code>null</code> value when trying to checkout a destroyed
	 * session specified by parameter.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create two identifiers for the new sessions;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize the both sessions with their respective identifiers;</li>
	 * 	<li>Destroy the session 1;</li>
	 * 	<li>Verify that the session 1 is <code>null</code> and the session 2 is
	 * 	not	<code>null</code>.</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void destroySession_arg() throws TreeException {
		final String sessionIdentifier1 = "destroySession1";
		final String sessionIdentifier2 = "destroySession2";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionIdentifier1, Directory.class);
		transaction.initializeSession(sessionIdentifier2, Directory.class);
		transaction.destroySession(sessionIdentifier1);
		
		TreeSession session1 = transaction.sessionCheckout(sessionIdentifier1);
		TreeSession session2 = transaction.sessionCheckout(sessionIdentifier2);
		
		assertNull(session1);
		assertNotNull(session2);
	}
	
	/**
	 * Test for the {@link TreeTransaction#destroyAllSessions()}.
	 * 
	 * <p>Happy scenario for this operation</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to destroy all sessions.
	 * <p><b>Expected:</b></p>
	 * Expect that the transaction has no session to be handle.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create 5 new session identifiers;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize all the 5 sessions with its respective identifiers;</li>
	 * 	<li>Verify if the transaction really has the 5 sessions;</li>
	 * 	<li>Invoke {@link TreeTransaction#destroyAllSessions()};</li>
	 * 	<li>Verify that the transaction has no sessions anymore.</li>
	 * </ol>
	 * @throws TreeException
	 */
	@Test
	public void destroyAllSessions() throws TreeException {
		final int noSession = 0;
		final int totalSessions = 5;
		
		final String sessionId1 = "destroyAllSessions1";
		final String sessionId2 = "destroyAllSessions2";
		final String sessionId3 = "destroyAllSessions3";
		final String sessionId4 = "destroyAllSessions4";
		final String sessionId5 = "destroyAllSessions5";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionId1, Directory.class);
		transaction.initializeSession(sessionId2, Directory.class);
		transaction.initializeSession(sessionId3, Directory.class);
		transaction.initializeSession(sessionId4, Directory.class);
		transaction.initializeSession(sessionId5, Directory.class);
		
		List<TreeSession> sessions = transaction.sessions();
		assertEquals(totalSessions, sessions.size());
		
		transaction.destroyAllSessions();
		
		sessions = transaction.sessions();
		assertEquals(noSession, sessions.size());
	}
	
	/**
	 * Test for the {@link TreeTransaction#activateSession()}.
	 * 
	 * <p>Happy scenario for this operation</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to activate a session.
	 * <p><b>Expected:</b></p>
	 * The activated session status.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create the session identifier;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize the session;</li>
	 * 	<li>Deactivate the session status;</li>
	 * 	<li>Verify if the session status is deactivated;</li>
	 * 	<li>Invoke {@link TreeTransaction#activateSession()} to activate
	 * 	the session;</li>
	 * 	<li>Verify if the session status is activated.</li>
	 * </ol>
	 * @throws TreeException
	 */
	@Test
	public void activateSession() throws TreeException {
		final String sessionId = "activateSession";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionId, Directory.class);
		transaction.deactivateSession();
		TreeSession session = transaction.currentSession();
		assertFalse(session.isActive());
		transaction.activateSession();
		session = transaction.currentSession();
		assertTrue(session.isActive());
	}
	
	/**
	 * Test for the {@link TreeTransaction#activateSession(String)}.
	 * 
	 * <p>Happy scenario for this operation</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to activate a session.
	 * <p><b>Expected:</b></p>
	 * The activated session status.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create the session identifier;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize the session;</li>
	 * 	<li>Deactivate the session status;</li>
	 * 	<li>Verify if the session status is deactivated;</li>
	 * 	<li>Invoke {@link TreeTransaction#activateSession(String)} to activate
	 * 	the session;</li>
	 * 	<li>Verify if the session status is activated.</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void activateSession_arg() throws TreeException {
		final String sessionId = "activateSession_arg";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionId, Directory.class);
		transaction.deactivateSession(sessionId);
		TreeSession session = transaction.currentSession();
		assertFalse(session.isActive());
		transaction.activateSession(sessionId);
		session = transaction.currentSession();
		assertTrue(session.isActive());
	}
	
	/**
	 * Test for the {@link TreeTransaction#deactivateSession()}.
	 * 
	 * <p>Happy scenario for this operation</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to deactivate a session.
	 * <p><b>Expected:</b></p>
	 * The deactivated session status.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create the session identifier;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize the session;</li>
	 * 	<li>Verify if the session status is activated;</li>
	 * 	<li>Deactivate the session status;</li>
	 * 	<li>Verify if the session status is deactivated.</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void deactivateSession() throws TreeException {
		final String sessionId = "deactivateSession";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionId, Directory.class);
		assertTrue(transaction.currentSession().isActive());
		transaction.deactivateSession();
		assertFalse(transaction.currentSession().isActive());
	}
	
	/**
	 * Test for the {@link TreeTransaction#deactivateSession(String)}.
	 * 
	 * <p>Happy scenario for this operation</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to deactivate a session.
	 * <p><b>Expected:</b></p>
	 * The deactivated session status.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create the session identifier;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize the session;</li>
	 * 	<li>Verify if the session status is activated;</li>
	 * 	<li>Deactivate the session status;</li>
	 * 	<li>Verify if the session status is deactivated.</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void deactivateSession_arg() throws TreeException {
		final String sessionId = "deactivateSession_arg";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionId, Directory.class);
		assertTrue(transaction.currentSession().isActive());
		transaction.deactivateSession(sessionId);
		assertFalse(transaction.currentSession().isActive());
	}
	
	/**
	 * Test for the {@link TreeTransaction#sessions()}.
	 * 
	 * <p>Happy scenario for this operation</p>
	 * 
	 * <p><b>Test:</b></p>
	 * List all initialized sessions.
	 * <p><b>Expected:</b></p>
	 * Ensure that the number of sessions is the same as the number of
	 * initialized sessions.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create six session identifiers;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize the six session;</li>
	 * 	<li>Invoke {@link TreeTransaction#sessions()} and verify if the number
	 * 	of sessions is the same as the number of initialized sessions;</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void sessions() throws TreeException {
		final int expected = 6;
		final String sessionId1 = "sessions1";
		final String sessionId2 = "sessions2";
		final String sessionId3 = "sessions3";
		final String sessionId4 = "sessions4";
		final String sessionId5 = "sessions5";
		final String sessionId6 = "sessions6";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionId1, Directory.class);
		transaction.initializeSession(sessionId2, Directory.class);
		transaction.initializeSession(sessionId3, Directory.class);
		transaction.initializeSession(sessionId4, Directory.class);
		transaction.initializeSession(sessionId5, Directory.class);
		transaction.initializeSession(sessionId6, Directory.class);
		
		List<TreeSession> sessions = transaction.sessions();
		
		assertEquals(expected, sessions.size());
	}
	
	/**
	 * Test for the {@link TreeTransaction#cloneSession(String, String)}.
	 * 
	 * <p>Happy scenario for this operation.</p>
	 * 
	 * <p>This makes use of the {@link TreeAssembler} and {@link Directory}
	 * classes to assemble a collection of linear objects that have tree
	 * behavior and that are going to be transformed.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Clone the session by session identifier.
	 * <p><b>Expected:</b></p>
	 * Ensure that the cloned session has the same elements.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Clone this session by invoking
	 * 	{@link TreeTransaction#cloneSession(String, String)};</li>
	 * 	<li>Get two elements which one of their is inside of other one</li>
	 * 	<li>Verify that the elements belong into the source session yet;</li>
	 * 	<li>Swap the transaction to work with the cloned session;</li>
	 * 	<li>Get the both elements again, but in cloned session (target session)
	 * 	now;</li>
	 * 	<li>Verify now that the elements belong into the target session;</li>
	 * 	<li>Set the parent id of one element;</li>
	 * 	<li>Swap the transaction to work with the source session;</li>
	 * 	<li>Check that the change over the parent id, when the transaction was
	 * 	pointing to the cloned session, does not cause problems in the same
	 * 	element (VLC) in this session.</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void cloneSessionByIdentifiers() throws TreeException {
		final String sourceSessionId = "directory_session";
		final String targetSessionId = "cloned_directory_session";
		
		final long vlcId = 10239;
		final long rec2Id = 1038299;
		
		final long vlcParentId = 42345;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.
				getDirectoryTree();
		
		transaction.initializeSession(sourceSessionId, directories);
		
		TreeSession clonedSession = transaction.cloneSession(sourceSessionId,
				targetSessionId);
		
		assertNotNull(clonedSession);
		assertEquals(sourceSessionId, transaction.currentSession().
				getSessionId());
		
		Element<Directory> vlc = manager.getElementById(vlcId);
		Element<Directory> rec2 = manager.getElementById(rec2Id);
		
		assertEquals(sourceSessionId, vlc.attachedTo().getSessionId());
		assertEquals(sourceSessionId, rec2.attachedTo().getSessionId());
		
		transaction.sessionCheckout(targetSessionId);
		
		vlc = manager.getElementById(vlcId);
		rec2 = manager.getElementById(rec2Id);
		
		assertNotNull(vlc);
		assertNotNull(rec2);
		assertTrue(manager.containsElement(vlc, rec2));
		
		assertEquals(targetSessionId, vlc.attachedTo().getSessionId());
		assertEquals(targetSessionId, rec2.attachedTo().getSessionId());
		
		vlc.setParent(Long.MAX_VALUE);
		
		transaction.sessionCheckout(sourceSessionId);
		Element<Directory> sourceVlc = manager.getElementById(vlcId);
		
		assertEquals(sourceSessionId, sourceVlc.attachedTo().getSessionId());
		assertEquals(vlcParentId, sourceVlc.getParent());
	}
	
	/**
	 * Test for the {@link TreeTransaction#cloneSession(String, String)}.
	 * 
	 * <p>Happy scenario for this operation.</p>
	 * 
	 * <p>This makes use of the {@link TreeAssembler} and {@link Directory}
	 * classes to assemble a collection of linear objects that have tree
	 * behavior and that are going to be transformed.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Clone the session.
	 * <p><b>Expected:</b></p>
	 * Ensure that the cloned session has the same elements.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Clone this session by invoking
	 * 	{@link TreeTransaction#cloneSession(TreeSession, String)};</li>
	 * 	<li>Get two elements and verify if they are the same structure than
	 * 	source session.</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void cloneSession() throws TreeException {
		final String sourceSessionId = "directory_session";
		final String targetSessionId = "cloned_directory_session";
		
		final long vlcId = 10239;
		final long rec2Id = 1038299;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.getDirectoryTree();
		
		transaction.initializeSession(sourceSessionId, directories);
		
		TreeSession sourceSession = transaction.currentSession();
		TreeSession clonedSession = transaction.cloneSession(sourceSession,
				targetSessionId);
		
		assertNotNull(clonedSession);
		
		transaction.sessionCheckout(targetSessionId);
		
		Element<Directory> vlc = manager.getElementById(vlcId);
		Element<Directory> rec2 = manager.getElementById(rec2Id);
		
		assertNotNull(vlc);
		assertNotNull(rec2);
		assertTrue(manager.containsElement(vlc, rec2));
	}
}
