package com.miuey.happytree.transaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
 * <p>This test class represents the <i>happy scenario</i> for all operations of
 * {@link TreeTransaction}.</p>
 * 
 * @author Diego Nóbrega
 * @author Miuey
 *
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
	 * 	<li>Clear all the sessions so as not to interfere with the another test
	 * 	</li>
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
		
		/*
		 * Clear all the sessions so as not to interfere with the accounting of
		 * sessions.
		 */
		transaction.destroyAllSessions();
		
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
	 * 	<li>Clear all the sessions so as not to interfere with the another test;
	 * 	</li>
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
		
		/*
		 * Clear all the sessions so as not to interfere with the accounting of
		 * sessions.
		 */
		transaction.destroyAllSessions();
		
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
	 * 	<li>Clear all the sessions so as not to interfere with the another test;
	 * 	</li>
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
		
		/*
		 * Clear all the sessions so as not to interfere with the accounting of
		 * sessions.
		 */
		transaction.destroyAllSessions();
		
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
	 * 	<li>Clear all the sessions so as not to interfere with the another test;
	 * 	</li>
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
		
		/*
		 * Clear all the sessions so as not to interfere with the accounting of
		 * sessions.
		 */
		transaction.destroyAllSessions();
		
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
	 * 	<li>Clear all the sessions so as not to interfere with the another test;
	 * 	</li>
	 * 	<li>Initialize the session;</li>
	 * 	<li>Deactivate the session status;</li>
	 * 	<li>Verify if the session status is deactivated;</li>
	 * 	<li>Invoke {@link TreeTransaction#activateSession(String)} to activate
	 * 	the session;</li>
	 * 	<li>Verify if the session status is activated.</li>
	 * </ol>
	 * @throws TreeException
	 */
	@Test
	public void activateSession_arg() throws TreeException {
		final String sessionId = "activateSession_arg";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		/*
		 * Clear all the sessions so as not to interfere with the accounting of
		 * sessions.
		 */
		transaction.destroyAllSessions();
		
		transaction.initializeSession(sessionId, Directory.class);
		transaction.deactivateSession(sessionId);
		TreeSession session = transaction.currentSession();
		assertFalse(session.isActive());
		transaction.activateSession(sessionId);
		session = transaction.currentSession();
		assertTrue(session.isActive());
	}
}
