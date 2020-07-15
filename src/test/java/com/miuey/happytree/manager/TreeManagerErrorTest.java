package com.miuey.happytree.manager;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeTransaction;
import com.miuey.happytree.core.HappyTree;
import com.miuey.happytree.example.Directory;
import com.miuey.happytree.exception.TreeException;

/**
 * Test class for {@link TreeManager} operations.
 * 
 * <p>This test class represents the <i>error scenarios</i> for the operations
 * of {@link TreeManager}.</p>
 * 
 * @author Diego Nóbrega
 * @author Miuey
 *
 */
public class TreeManagerErrorTest {

	/**
	 * Test for almost all operations for {@link TreeManager} interface.
	 * 
	 * <p>Error scenario for the operations when the transaction has no defined
	 * session to run the operations.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to create an element without check out a session.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code> with the
	 * message: <i>&quot;No defined session.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the manager;</li>
	 * 	<li>Try to create an element;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 */
	@Test
	public void noDefinedSession() {
		final String messageError = "No defined session.";
		
		final String elementId = "foo";
		final String parentElementId = "bar";
		
		String error = null;

		try {
			TreeManager manager = HappyTree.createTreeManager();
			
			/*
			 * All TreeManager operations must work under a defined session.
			 */
			manager.createElement(elementId, parentElementId);
		} catch (TreeException e) {
			error = e.getMessage();
		} finally {
			assertEquals(messageError, error);
		}
	}
	
	/**
	 * Test for almost all operations for {@link TreeManager} interface.
	 * 
	 * <p>Error scenario for the operations when the transaction has no active
	 * selected session to run the operations.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to create an element with a deactivated session.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code> with the
	 * message: <i>&quot;No active session.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session;</li>
	 * 	<li>Deactivate the same session;</li>
	 * 	<li>Try to create an element;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 */
	@Test
	public void noActiveSession() {
		final String sessionId = "noActiveSession";
		final String messageError = "No active session.";
		
		final String elementId = "foo";
		final String parentElementId = "bar";
		
		String error = null;

		try {
			TreeManager manager = HappyTree.createTreeManager();
			TreeTransaction transaction = manager.getTransaction();
			
			transaction.initializeSession(sessionId, Directory.class);
			transaction.deactivateSession();
			
			/*
			 * All TreeManager operations must work under a defined session.
			 */
			manager.createElement(elementId, parentElementId);
		} catch (TreeException e) {
			error = e.getMessage();
		} finally {
			assertEquals(messageError, error);
		}
	}
}
