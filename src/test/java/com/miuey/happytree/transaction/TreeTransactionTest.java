package com.miuey.happytree.transaction;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
		final String identifierSession1 = "destroySession1";
		final String identifierSession2 = "destroySession2";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		/*
		 * Clear all the sessions so as not to interfere with the accounting of
		 * sessions.
		 */
		transaction.destroyAllSessions();
		
		transaction.initializeSession(identifierSession1, Directory.class);
		transaction.initializeSession(identifierSession2, Directory.class);
		
		TreeSession session2 = transaction.sessionCheckout(identifierSession2);
		TreeSession session1 = transaction.sessionCheckout(identifierSession1);
		
		/*
		 * Destroy the last checked out session (session1).
		 */
		transaction.destroySession();
		
		session1 = transaction.sessionCheckout(identifierSession1);
		assertNull(session1);
		session2 = transaction.sessionCheckout(identifierSession2);
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
		final String identifierSession1 = "destroySession1";
		final String identifierSession2 = "destroySession2";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		/*
		 * Clear all the sessions so as not to interfere with the accounting of
		 * sessions.
		 */
		transaction.destroyAllSessions();
		
		transaction.initializeSession(identifierSession1, Directory.class);
		transaction.initializeSession(identifierSession2, Directory.class);
		transaction.destroySession(identifierSession1);
		
		TreeSession session1 = transaction.sessionCheckout(identifierSession1);
		TreeSession session2 = transaction.sessionCheckout(identifierSession2);
		
		assertNull(session1);
		assertNotNull(session2);
	}
}
