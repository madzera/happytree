package com.miuey.happytree.session;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeSession;
import com.miuey.happytree.TreeTransaction;
import com.miuey.happytree.core.HappyTree;
import com.miuey.happytree.example.Directory;
import com.miuey.happytree.exception.TreeException;

/**
 * 
 * Test class for {@link TreeSession} operations.
 * 
 * @author Diego Nóbrega
 * @author Miuey
 *
 */
public class TreeSessionTest {

	/**
	 * Test for the {@link TreeSession#getSessionId()}.
	 * 
	 * <p><b>Test:</b></p>
	 * Get the session identifier.
	 * <p><b>Expected:</b></p>
	 * Match the session identifier at the creation with the return of
	 * {@link TreeSession#getSessionId()}.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session with a specific identifier;</li>
	 * 	<li>Get the current session;</li>
	 * 	<li>Compare the result of {@link TreeSession#getSessionId()} with the
	 * 	previous identifier.</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void getSessionId() throws TreeException {
		final String sessionId = "FIRST_SESSION";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionId, Directory.class);
		TreeSession session = transaction.currentSession();
		
		assertEquals(sessionId, session.getSessionId());
	}
	
	/**
	 * Test for the {@link TreeSession#isActive()}.
	 * 
	 * <p><b>Test:</b></p>
	 * Verify the status session.
	 * <p><b>Expected:</b></p>
	 * Initialize a session and verify if it is active. After that deactivate
	 * the session and verify if it changed to not active.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session;</li>
	 * 	<li>Get the current session;</li>
	 * 	<li>Verify if the session is active;</li>
	 * 	<li>Deactivate the same session by invoking
	 * 	{@link TreeTransaction#deactivateSession()};</li>
	 * 	<li>Verify if the same session is not active anymore.</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void isActive() throws TreeException {
		final String sessionId = "SESSION_STATUS";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionId, Directory.class);
		TreeSession session = transaction.currentSession();
		assertTrue(session.isActive());
		
		transaction.deactivateSession();
		session = transaction.currentSession();
		assertFalse(session.isActive());
	}
	
	/**
	 * Test for the {@link TreeSession#tree()}.
	 * 
	 * <p><b>Test:</b></p>
	 * Get the root element.
	 * <p><b>Expected:</b></p>
	 * A not <code>null</code> root element with the default <i>id</i> called
	 * <i>HAPPYTREE_ROOT</i>.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session;</li>
	 * 	<li>Get the current session;</li>
	 * 	<li>Get the root element by invoking {@link TreeSession#tree()};</li>
	 * 	<li>Verify if the root element is not <code>null</code>;</li>
	 * 	<li>Verify if the root element has id called &quot;HAPPYTREE_ROOT&quot;.
	 * 	</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void tree() throws TreeException {
		final String sessionId = "TREE";
		final String rootId = sessionId;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionId, Directory.class);
		TreeSession session = transaction.currentSession();
		Element<Directory> root = session.tree();
		
		assertNotNull(root);
		assertEquals(rootId, root.getId());
	}
}
