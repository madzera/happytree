package com.miuey.happytree.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeTransaction;
import com.miuey.happytree.core.HappyTree;
import com.miuey.happytree.example.Directory;
import com.miuey.happytree.example.TreeAssembler;
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
	 * 	a new detached element with the specified element id;</li>
	 * 	<li>Verify if the new element is not <code>null</code>.</li>
	 * 	<li>Verify if the element id corresponds.</li>
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
		assertEquals(elementId, element.getId());
	}

	/**
	 * Test for the {@link TreeManager#getElementById(Object)}.
	 * 
	 * <p>Happy scenario for this operation</p>
	 * 
	 * <p>This makes use of the {@link TreeAssembler} and {@link Directory}
	 * classes to assemble a collection of linear objects that have tree
	 * behavior and that are going to be transformed.</p>
	 * 
	 * <p><b>For this demonstration, please see these sample classes in
	 * question.</b></p>
	 * 
	 * <p><b>Test:</b></p>
	 * Get elements in the tree by element <code>id</code>.
	 * <p><b>Expected:</b></p>
	 * To obtain the element which wrapped the &quot;recorded&quot; object and
	 * &quot;VLC&quot; (the parent element)
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize the session;</li>
	 * 	<li>Try to get the &quot;recorded&quot; element by its id;</li>
	 * 	<li>Verify if the &quot;recorded&quot; is not <code>null</code>;</li>
	 * 	<li>Verify the name of the wrapped element from &quot;recorded&quot;;
	 * 	</li>
	 * 	<li>Get the parent id through the returned element;</li>
	 * 	<li>Invoke {@link TreeManager#getElementById(Object)} to get the parent
	 * 	of &quot;recorded&quot; element (&quot;VLC&quot;);</li>
	 * 	<li>Verify if the &quot;VLC&quot; is not <code>null</code>;</li>
	 * 	<li>Verify the name of the wrapped element from &quot;VLC&quot;;
	 * 	</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void getElementById() throws TreeException {
		final String sessionId = "createElement";
		final long recordedId = 848305;
		
		final String recordedName = "recorded";
		final String parentRecordedName = "VLC";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.
				getDirectoryTree();
		transaction.initializeSession(sessionId, directories);
		
		Element<Directory> recorded = manager.getElementById(recordedId);
		assertNotNull(recorded);
		
		Directory rec = recorded.unwrap();
		assertEquals(recordedName, rec.getName());
		
		Object parentId = recorded.getParent();
		Element<Directory> recordedParent = manager.getElementById(parentId);
		
		assertNotNull(recordedParent);
		
		Directory parentRec = recordedParent.unwrap();
		assertEquals(parentRecordedName, parentRec.getName());
	}
	
	/**
	 * Test for the {@link TreeManager#cut(Element, Element)}.
	 * 
	 * <p>Happy scenario for this operation</p>
	 * 
	 * <p>This makes use of the {@link TreeAssembler} and {@link Directory}
	 * classes to assemble a collection of linear objects that have tree
	 * behavior and that are going to be transformed.</p>
	 * 
	 * <p><b>For this demonstration, please see these sample classes in
	 * question.</b></p>
	 * 
	 * <p><b>Test:</b></p>
	 * Cut an element for inside of other one.
	 * <p><b>Expected:</b></p>
	 * It is expected that the &quot;jdk&quot; element be removed from the
	 * source element and placed inside of the &quot;sdk&quot; element.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session;</li>
	 * 	<li>Get the &quot;jdk&quot; element;</li>
	 * 	<li>Get the &quot;sdk&quot; element;</li>
	 * 	<li>Verify that the parent element of &quot;jdk&quot; has one child (jdk
	 * 	itself);</li>
	 * 	<li>Try to cut &quot;jdk&quot; for inside of &quot;sdk&quot; element;
	 * 	</li>
	 * 	<li>Verify now that the previous &quot;jdk&quot; parent element has no
	 * 	child anymore;</li>
	 * 	<li>Verify that the &quot;sdk&quot; element contains now the
	 * 	&quot;jdk&quot; element	by invoking
	 * 	{@link TreeManager#containsElement(Element, Element)}.
	 * 	</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void cut_element() throws TreeException {
		final String sessionId = "cut_element";
		
		final long jdkId = 983533;
		final long sdkId = 113009;
		final long sdkDevId = 84709;
		
		final String jdkName = "jdk1.6";
		final String sdkName = "sdk";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.
				getDirectoryTree();
		transaction.initializeSession(sessionId, directories);
		
		Element<Directory> sdkDev = manager.getElementById(sdkDevId);
		assertEquals(1, sdkDev.getChildren().size());
		
		Element<Directory> jdk = manager.getElementById(jdkId);
		Element<Directory> sdk = manager.getElementById(sdkId);
		
		jdk = manager.cut(jdk, sdk);
		
		sdkDev = manager.getElementById(sdkDevId);
		assertEquals(0, sdkDev.getChildren().size());
		assertFalse(manager.containsElement(sdkDev, jdk));
		
		assertTrue(manager.containsElement(sdk, jdk));
		assertEquals(sdkName, sdk.unwrap().getName());
		assertEquals(jdkName, jdk.unwrap().getName());
		assertEquals(sdk.getId(), jdk.getParent());
	}
	
	/**
	 * Test for the {@link TreeManager#cut(Object, Object)}.
	 * 
	 * <p>Happy scenario for this operation</p>
	 * 
	 * <p>This makes use of the {@link TreeAssembler} and {@link Directory}
	 * classes to assemble a collection of linear objects that have tree
	 * behavior and that are going to be transformed.</p>
	 * 
	 * <p><b>For this demonstration, please see these sample classes in
	 * question.</b></p>
	 * 
	 * <p><b>Test:</b></p>
	 * Cut an element for inside of other one using only the element id.
	 * <p><b>Expected:</b></p>
	 * It is expected that the &quot;jdk&quot; element be removed from the
	 * source element and placed inside of the &quot;sdk&quot; element.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session;</li>
	 * 	<li>Get the &quot;jdk&quot; parent element;</li>
	 * 	<li>Verify that the parent element of &quot;jdk&quot; has one child (jdk
	 * 	itself);</li>
	 * 	<li>Try to cut &quot;jdk&quot; for inside of &quot;sdk&quot; element
	 * 	using only the id by invoking {@link TreeManager#cut(Object, Object)};
	 * 	</li>
	 * 	<li>Verify now that the previous &quot;jdk&quot; parent element has no
	 * 	child anymore;</li>
	 * 	<li>Verify that the &quot;sdk&quot; element contains now the
	 * 	&quot;jdk&quot; element by invoking
	 * 	{@link TreeManager#containsElement(Object, Object)}.
	 * 	</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void cut_objectId() throws TreeException {
		final String sessionId = "cut_objectId";
		
		final long jdkId = 983533;
		final long sdkId = 113009;
		final long sdkDevId = 84709;
		
		final String jdkName = "jdk1.6";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.
				getDirectoryTree();
		transaction.initializeSession(sessionId, directories);
		
		Element<Directory> sdkDev = manager.getElementById(sdkDevId);
		assertEquals(1, sdkDev.getChildren().size());
		
		Element<Directory> jdk = manager.cut(jdkId, sdkId);
		
		sdkDev = manager.getElementById(sdkDevId);
		assertEquals(1, sdkDev.getChildren().size());
		
		assertTrue(manager.containsElement(sdkId, jdkId));
		assertEquals(jdkName, jdk.unwrap().getName());
		assertEquals(sdkId, jdk.getParent());
	}
}
