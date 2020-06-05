package com.miuey.happytree.transaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;

import com.miuey.happytree.Directory;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeSession;
import com.miuey.happytree.TreeTransaction;
import com.miuey.happytree.core.HappyTree;
import com.miuey.happytree.exception.TreeException;

/**
 * Test class for {@link TreeTransaction} operations.
 * 
 * <p>This test class represents the <i>alternative scenarios</i> for the
 * operations of {@link TreeTransaction}.</p>
 * 
 * @author Diego Nóbrega
 * @author Miuey
 *
 */
public class TreeTransactionAlternativeTest {

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
	 * @throws TreeException
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
	 * @throws TreeException
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
	 * 	<li>Clear all the sessions so as not to interfere with the accounting of
	 * 	total sessions
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
	 * @throws TreeException
	 */
	@Test
	public void destroySession_noCurrentSession() throws TreeException {
		final int sessionsListLength = 1;
		final String sessionId = "destroySession_noCurrentSession";
		final String inexistentSessionId = "bar";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		/*
		 * Clear all the sessions so as not to interfere with the accounting of
		 * sessions.
		 */
		transaction.destroyAllSessions();
		
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
	 * 	<li>Clear all the sessions so as not to interfere with the accounting of
	 * 	total sessions
	 * 	<li>Initialize the new session with the specified identifier;</li>
	 * 	<li>Verify if the transaction has only one created session;</li>
	 * 	<li>Invokes the {@link TreeTransaction#destroySession(String)} with the
	 * 	<code>null</code> session identifier parameter;</li>
	 * 	<li>Count again and verify if the session size list is 1;</li>
	 * 	<li>Verify if the previous created session identifier corresponds with
	 * 	the session identifier of the transaction.</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void destroySession_arg_nullIdentifier() throws TreeException {
		final int sessionsListLength = 1;
		final String sessionId = "destroySession_arg_nullIdentifier";
		final String nullableSessionId = null;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		/*
		 * Clear all the sessions so as not to interfere with the accounting of
		 * sessions.
		 */
		transaction.destroyAllSessions();
		
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
	 * @throws TreeException
	 */
	@Test
	public void destroyAllSessions_noInitializedSession() throws TreeException {
		final int noSession = 0;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		/*
		 * Clear all the sessions so as not to interfere with the accounting of
		 * sessions.
		 */
		transaction.destroyAllSessions();
		
		List<TreeSession> sessions = transaction.sessions();
		assertEquals(noSession, sessions.size());
	}
}
