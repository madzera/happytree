package com.miuey.happytree.transaction;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeTransaction;
import com.miuey.happytree.core.HappyTree;
import com.miuey.happytree.example.Directory;
import com.miuey.happytree.exception.TreeException;

/**
 * Test class for {@link TreeTransaction} operations.
 * 
 * <p>This test class represents the <i>error scenarios</i> for the operations
 * of {@link TreeTransaction}.</p>
 * 
 * @author Diego Nóbrega
 * @author Miuey
 *
 */
public class TreeTransactionErrorTest {

	/**
	 * Test for the {@link TreeTransaction#initializeSession(String, Object)}.
	 * 
	 * <p>Error scenario for this operation when the session identifier is
	 * <code>null</code>.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to initialize a session with a <code>null</code> identifier.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>IllegalArgumentException</code>
	 * with the message:
	 * <i>&quot;Invalid null/empty argument(s).&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create a identifier with <code>null</code> value;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session with the <code>null</code> argument;</li>
	 * 	<li>Catch the <code>IllegalArgumentException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 * @throws TreeException 
	 */
	@Test
	public void nullIdentifier() throws TreeException {
		final String nameTree = null;
		final String messageError = "Invalid null/empty argument(s).";
		String error = null;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		try {
			transaction.initializeSession(nameTree, Directory.class);
			transaction.sessionCheckout(nameTree);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		} finally {
			assertEquals(messageError, error);
		}
	}
	
	/**
	 * Test for the {@link TreeTransaction#initializeSession(String, Object)}.
	 * 
	 * <p>Error scenario for this operation when the session identifier is
	 * already initialized.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to initialize sessions with the same identifiers.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code> with the
	 * message:
	 * <i>&quot;Already existing initialized session.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create two variables with the same identifiers values;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session with the first identifier;</li>
	 * 	<li>Execute the {@link TreeTransaction#sessionCheckout(String)} to
	 * 	refresh the current session;</li>
	 * 	<li>Initialize a new session with the second identifier;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 */
	@Test
	public void duplicateIdentifier() {
		final String nameTree = "duplicateIdentifier";
		final String nameTreeDuplicated = nameTree;
		final String messageError = "Already existing initialized session.";
		String error = null;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		try {
			transaction.initializeSession(nameTree, Directory.class);
			transaction.sessionCheckout(nameTree);
			
			/*
			 * Try to init an existing identifier session.
			 */
			transaction.initializeSession(nameTreeDuplicated, Directory.class);
		} catch (TreeException e) {
			error = e.getMessage();
		} finally {
			assertEquals(messageError, error);
		}
	}
}
