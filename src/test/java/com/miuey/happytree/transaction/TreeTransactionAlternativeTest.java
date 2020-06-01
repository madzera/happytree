package com.miuey.happytree.transaction;

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
	 * <p>Alternative scenario for this operation when the session identifier
	 * was not initialized.</p>
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
	 * 	the second identifier;</li>
	 * 	<li>Assert the <code>null</code> value for the session.</li>
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
	
}
