package com.madzera.happytree.transaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;

import org.junit.Test;

import com.madzera.happytree.TreeManager;
import com.madzera.happytree.TreeSession;
import com.madzera.happytree.TreeTransaction;
import com.madzera.happytree.core.HappyTree;
import com.madzera.happytree.demo.model.Directory;
import com.madzera.happytree.demo.util.TreeAssembler;
import com.madzera.happytree.exception.TreeException;

/**
 * Test class for {@link TreeTransaction} operations.
 * 
 * <p>This test class represents the <i>alternative scenarios</i> for the
 * operations of {@link TreeTransaction}.</p>
 * 
 * @author Diego Nóbrega
 *
 */
public class TreeTransactionAlternativeTest {

	/**
	 * Test for the {@link TreeTransaction#destroySession(String)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to destroy a
	 * session that corresponds with the current session.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to destroy an existent session which corresponds to the current
	 * session.
	 * <p><b>Expected:</b></p>
	 * Receive a <code>null</code> value when trying to get the current session.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create the identifier for the new session;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize the new session with the specified identifier;</li>
	 * 	<li>Get the current session and verify if it is not <code>null</code>;
	 * 	</li>
	 * 	<li>Verify if the current session identifier is equals to the initial
	 * 	created session identifier;</li>
	 * 	<li>Destroy the previous created session;</li>
	 * 	<li>Get the current session again and verify if it is <code>null</code>.
	 * 	</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void destroySession_thatIsCurrentSession() throws TreeException {
		final String sessionId = "destroySession_thatIsCurrentSession";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionId, Directory.class);
		
		TreeSession session = transaction.currentSession();
		assertNotNull(session);
		assertEquals(sessionId, session.getSessionId());
		
		transaction.destroySession(sessionId);
		
		session = transaction.currentSession();
		assertNull(session);
		
		transaction.destroyAllSessions();
	}
	
	/**
	 * Test for the {@link TreeTransaction#destroySession(String)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to destroy a
	 * session that is not the current session.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to destroy an existent session that is not marked as current session.
	 * <p><b>Expected:</b></p>
	 * Expect the same counting before and after of trying to destroy a session
	 * (by invoking {@link TreeTransaction#destroySession()}) that is not the
	 * current session.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create the identifier for the new session;</li>
	 * 	<li>Create another inexistent identifier ;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize the new session with the specified identifier;</li>
	 * 	<li>Try to do a check out of an inexistent session by the inexistent
	 * 	identifier created before and check if the session has the
	 * 	<code>null</code> value;</li>
	 * 	<li>Verify if the transaction has only one created session;</li>
	 * 	<li>Invokes the {@link TreeTransaction#destroySession()};</li>
	 * 	<li>Count again and verify if the session size list is 1;</li>
	 * 	<li>Verify if the previous created session identifier corresponds with
	 * 	the session identifier of the transaction.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void destroySession_noCurrentSession() throws TreeException {
		final int sessionsListLength = 1;
		final String sessionId = "destroySession_noCurrentSession";
		final String inexistentSessionId = "bar";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		transaction.initializeSession(sessionId, Directory.class);
		TreeSession inexistentSession = transaction.sessionCheckout(
				inexistentSessionId);
		
		assertNull(inexistentSession);
		
		assertEquals(sessionsListLength, transaction.sessions().size());
		transaction.destroySession();
		assertEquals(sessionsListLength, transaction.sessions().size());
		
		assertEquals(sessionId, transaction.sessionCheckout(sessionId).
				getSessionId());
	}
	
	/**
	 * Test for the {@link TreeTransaction#destroySession(String)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to destroy a
	 * session with a <code>null</code> identifier argument.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to destroy a session with a <code>null</code> identifier parameter.
	 * <p><b>Expected:</b></p>
	 * Expect the same counting before and after of trying to destroy a session
	 * with a <code>null</code> parameter by invoking
	 * {@link TreeTransaction#destroySession(String)}.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create the identifier for the new session;</li>
	 * 	<li>Create another identifier with the <code>null</code> value;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize the new session with the specified identifier;</li>
	 * 	<li>Verify if the transaction has only one created session;</li>
	 * 	<li>Invokes the {@link TreeTransaction#destroySession(String)} with the
	 * 	<code>null</code> session identifier parameter;</li>
	 * 	<li>Count again and verify if the session size list is 1;</li>
	 * 	<li>Verify if the previous created session identifier corresponds with
	 * 	the session identifier of the transaction.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void destroySession_arg_nullIdentifier() throws TreeException {
		final int sessionsListLength = 1;
		final String sessionId = "destroySession_arg_nullIdentifier";
		final String nullableSessionId = null;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		transaction.initializeSession(sessionId, Directory.class);
		assertEquals(sessionsListLength, transaction.sessions().size());
		transaction.destroySession(nullableSessionId);
		assertEquals(sessionsListLength, transaction.sessions().size());
		
		assertEquals(sessionId, transaction.sessionCheckout(sessionId).
				getSessionId());
	}
	
	/**
	 * Test for the {@link TreeTransaction#destroyAllSessions()}.
	 * 
	 * <p>Happy scenario for this operation</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to destroy all sessions with no one session been initialized before.
	 * <p><b>Expected:</b></p>
	 * Expect that the transaction has no session to be handle.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Invoke {@link TreeTransaction#destroyAllSessions()};</li>
	 * 	<li>Verify that the transaction has no sessions anymore.</li>
	 * </ol>
	 * @throws TreeException in case of an error
	 */
	@Test
	public void destroyAllSessions_noInitializedSession() throws TreeException {
		final int noSession = 0;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		List<TreeSession> sessions = transaction.sessions();
		assertEquals(noSession, sessions.size());
	}
	
	/**
	 * Test for the {@link TreeTransaction#sessionCheckout(String)}.
	 * 
	 * <p>Alternative scenario for this operation when the session is not
	 * initialized/existed.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to checkout an inexistent session.
	 * <p><b>Expected:</b></p>
	 * Receive a <code>null</code> session value when checking out an inexistent
	 * session.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create two variables with the different identifiers values;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session with the first identifier;</li>
	 * 	<li>Execute the {@link TreeTransaction#sessionCheckout(String)} using
	 * 	the second identifier (inexistent);</li>
	 * 	<li>Assert the <code>null</code> value for the session returned.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void sessionCheckout_noInitializedSession() throws TreeException {
		final String nameTree = "sessionCheckout_noInitializedSession";
		final String inexistentSession = "foo";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(nameTree, Directory.class);
		TreeSession session = transaction.sessionCheckout(inexistentSession);
		
		assertNull(session);
	}
	
	/**
	 * Test for the {@link TreeTransaction#activateSession()}.
	 * 
	 * <p>Alternative scenario for this operation when there is no current
	 * sessions initialized.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to activate the a session when there is no session initialized.
	 * <p><b>Expected:</b></p>
	 * Receive a <code>null</code> session value when checking out an inexistent
	 * session and 0 session inside of the transaction.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create the inexistent session identifiers value;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Execute the {@link TreeTransaction#activateSession()} using the
	 * 	(inexistent) identifier;</li>
	 * 	<li>Assert the <code>null</code> value for the session returned.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void activateSession_noCurrentSession() throws TreeException {
		final int sessionsListLength = 0;
		final String inexistentSessionId = "foo";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		transaction.activateSession();
		TreeSession inexistentSession = transaction.sessionCheckout(
				inexistentSessionId);
		assertNull(inexistentSession);
		assertEquals(sessionsListLength, transaction.sessions().size());
	}
	
	/**
	 * Test for the {@link TreeTransaction#activateSession(String)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to activate a
	 * session with a <code>null</code> identifier argument.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to activate a session with a <code>null</code> identifier parameter.
	 * <p><b>Expected:</b></p>
	 * Expect that there is no any error passing a <code>null</code> parameter
	 * by invoking {@link TreeTransaction#activateSession(String)} and a
	 * <code>null</code> session when lookup the current session.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create a <code>null</code> session identifier;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Invoke the {@link TreeTransaction#activateSession(String)} using the
	 * 	(inexistent or <code>null</code>) identifier;</li>
	 * 	<li>Verify the <code>null</code> value for the session returned.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void activateSession_arg_nullIdentifier() throws TreeException {
		final String nullableIdentifier = null;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.activateSession(nullableIdentifier);
		
		TreeSession session = transaction.currentSession();
		assertNull(session);
	}
	
	/**
	 * Test for the {@link TreeTransaction#deactivateSession()}.
	 * 
	 * <p>Alternative scenario for this operation when there is no current
	 * sessions initialized.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to deactivate the a session when there is no session initialized.
	 * <p><b>Expected:</b></p>
	 * Receive a <code>null</code> session value when trying to deactivate a
	 * <code>null</code> current session.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Execute directly the {@link TreeTransaction#deactivateSession()}
	 * 	without any session initialized;</li>
	 * 	<li>Try to get the current session;</li>
	 * 	<li>Verify if the current session is <code>null</code>.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void deactivateSession_noCurrentSession() throws TreeException {
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.deactivateSession();
		TreeSession session = transaction.currentSession();
		
		assertNull(session);
	}
	
	/**
	 * Test for the {@link TreeTransaction#deactivateSession(String)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to deactivate a
	 * session with a <code>null</code> identifier argument.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to deactivate a session with a <code>null</code> identifier parameter.
	 * <p><b>Expected:</b></p>
	 * Expect that there is no any error passing a <code>null</code> parameter
	 * by invoking {@link TreeTransaction#deactivateSession(String)} and a
	 * <code>null</code> session when lookup the current session.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create a <code>null</code> session identifier;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Invoke the {@link TreeTransaction#deactivateSession(String)} using
	 * 	the	(inexistent or <code>null</code>) identifier;</li>
	 * 	<li>Verify the <code>null</code> value for the session returned.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void deactivateSession_arg_nullIdentifier() throws TreeException {
		final String nullableIdentifier = null;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.deactivateSession(nullableIdentifier);
		TreeSession session = transaction.currentSession();
		assertNull(session);
	}
	
	/**
	 * Test for the {@link TreeTransaction#sessions()}.
	 * 
	 * <p>Alternative scenario for this operation when trying to get the list
	 * sessions when there is no any session initialized.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Verify the size list when there is no session initialized.
	 * <p><b>Expected:</b></p>
	 * Expect that the list of sessions is empty.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Invoke the {@link TreeTransaction#sessions()};</li>
	 * 	<li>Verify if the list of sessions is empty.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void sessions_empty() throws TreeException {
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		List<TreeSession> sessions = transaction.sessions();
		
		assertTrue(sessions.isEmpty());
	}
	
	/**
	 * Test for the {@link TreeTransaction#cloneSession(String, String)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to clone an
	 * existing session with a <code>null</code> session identifier.</p>
	 * 
	 * <p>This makes use of the {@link TreeAssembler} and {@link Directory}
	 * classes to assemble a collection of linear objects that have tree
	 * behavior and that are going to be transformed.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to clone an existing session with a <code>null</code> session
	 * identifier.
	 * <p><b>Expected:</b></p>
	 * No error should occur and the cloned session returned has
	 * <code>null</code> value.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create a <code>null</code> session identifier;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Invoke the {@link TreeTransaction#cloneSession(String, String)}
	 * 	using the <code>null</code> session identifier;</li>
	 * 	<li>Verify the <code>null</code> value for the session returned.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void cloneSessionById_arg_nullIdentifier() throws TreeException {
		final String sourceSessionId = "directory_session";
		final String nullableSessionId = null;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.
				getDirectoryTree();
		
		/*
		 * To load the tree by ATP.
		 */
		transaction.initializeSession(sourceSessionId, directories);
		
		TreeSession clonedSession = transaction.cloneSession(sourceSessionId,
				nullableSessionId);
		
		assertNull(clonedSession);
	}
	
	/**
	 * Test for the {@link TreeTransaction#cloneSession(TreeSession, String)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to clone an
	 * existing session with a <code>null</code> session identifier.</p>
	 * 
	 * <p>This makes use of the {@link TreeAssembler} and {@link Directory}
	 * classes to assemble a collection of linear objects that have tree
	 * behavior and that are going to be transformed.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to clone an existing session with a <code>null</code> session
	 * identifier.
	 * <p><b>Expected:</b></p>
	 * No error should occur and the cloned session returned has
	 * <code>null</code> value.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create a <code>null</code> session identifier;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Invoke the {@link TreeTransaction#cloneSession(TreeSession, String)}
	 * 	using the <code>null</code> session identifier;</li>
	 * 	<li>Verify the <code>null</code> value for the session returned.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void cloneSession_arg_nullIdentifier() throws TreeException {
		final String sourceSessionId = "directory_session";
		final String nullableSessionId = null;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.
				getDirectoryTree();
		
		/*
		 * To load the tree by ATP.
		 */
		transaction.initializeSession(sourceSessionId, directories);
		
		TreeSession sourceSession = transaction.currentSession();
		TreeSession clonedSession = transaction.cloneSession(sourceSession,
				nullableSessionId);
		
		assertNull(clonedSession);
	}
	
	/**
	 * Test for the {@link TreeTransaction#cloneSession(TreeSession, String)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to clone a not
	 * existing session.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to clone a not existing session.
	 * <p><b>Expected:</b></p>
	 * No error should occur and the cloned session returned has
	 * <code>null</code> value.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Get a not existing session;</li>
	 * 	<li>Invoke the {@link TreeTransaction#cloneSession(TreeSession, String)}
	 * 	using the session which does not exists;</li>
	 * 	<li>Verify the <code>null</code> value for the session returned.</li>
	 * </ol>
	 * @throws TreeException in case of an error
	 */
	@Test
	public void cloneSession_nullSession() throws TreeException {
		final String targetSessionId = "bar";
		final String notExistingSessionId = "foo";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		TreeSession sourceSession = transaction.sessionCheckout(
				notExistingSessionId);
		TreeSession clonedSession = transaction.cloneSession(sourceSession,
				targetSessionId);
		
		assertNull(clonedSession);
	}
}
