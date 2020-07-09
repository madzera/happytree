package com.miuey.happytree.manager;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.miuey.happytree.Directory;
import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeTransaction;
import com.miuey.happytree.core.HappyTree;
import com.miuey.happytree.exception.TreeException;

/**
 * Test class for {@link TreeManager} operations.
 * 
 * <p>This test class represents the <i>happy scenario</i> for all operations of
 * {@link TreeManager}.</p>
 * 
 * @author Diego Nóbrega
 * @author Miuey
 *
 */
public class TreeManagerTest {

	/**
	 * Test for the {@link TreeManager#getTransaction()}.
	 * 
	 * <p>Happy scenario for this operation.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Get the associated transaction.
	 * <p><b>Expected:</b></p>
	 * A not <code>null</code> instance of <code>TreeTransaction</code>.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Verify if the transaction is not <code>null</code>.</li>
	 * </ol>
	 */
	@Test
	public void getTransaction() {
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		assertNotNull(transaction);
	}
	
	/**
	 * Test for the {@link TreeManager#createElement(Object, Object)}.
	 * 
	 * <p>Happy scenario for this operation.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to create an element.
	 * <p><b>Expected:</b></p>
	 * Create an element with the specified <i>Id</i>.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Define a session identifier;</li>
	 * 	<li>Define an element <i>Id</i>;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize the session with the previous defined session identifier;
	 * 	</li>
	 * 	<li>Invoke {@link TreeManager#createElement(Object, Object)} to create
	 * 	a new detached element;</li>
	 * 	<li>Verify if the new element is not <code>null</code>.</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void createElement() throws TreeException {
		final String sessionId = "createElement";
		final String elementId = "Documents";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionId, Directory.class);
		Element<Directory> element = manager.createElement(elementId, null);
		
		assertNotNull(element);
	}
}
