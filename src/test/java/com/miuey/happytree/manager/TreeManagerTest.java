package com.miuey.happytree.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
		final long elementId = Long.MAX_VALUE;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Directory directory = new Directory(elementId, 0, "Paint");
		
		transaction.initializeSession(sessionId, Directory.class);
		Element<Directory> element = manager.createElement(elementId, null,
				directory);
		
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
		assertEquals(0, sdkDev.getChildren().size());
		
		assertTrue(manager.containsElement(sdkId, jdkId));
		assertEquals(jdkName, jdk.unwrap().getName());
		assertEquals(sdkId, jdk.getParent());
	}
	
	/**
	 * Test for the {@link TreeManager#copy(Element, Element)}.
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
	 * Copy an element from a source tree session to other element from another
	 * tree session.
	 * <p><b>Expected:</b></p>
	 * It is expected that the source element be copied (in the source tree) for
	 * inside of the target element from other tree (in the target tree). All
	 * children are copied too.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize two sessions, the respective source and target. Both
	 * 	previously loaded from <code>TreeAssembler</code>;</li>
	 * 	<li>Get the source element which will be copied;</li>
	 * 	<li>Verify that this element is not <code>null</code>;</li>
	 * 	<li>Get the target element which will contain the source element;</li>
	 * 	<li>Change the session for the source session;</li>
	 * 	<li>Copy the source element for inside of the target element, which this
	 * 	target element is in another tree;</li>
	 * 	<li>In the target tree get an element with the same id than the source
	 * 	element;</li>
	 * 	<li>Verify if the copied element really have the &quot;System32&quot; as
	 * 	parent;</li>
	 * 	<li>Verify if the copied element really have two copied children from
	 * 	the source element;</li>
	 * 	<li>Change the session for the source session again;</li>
	 * 	<li>Verify now if the source element still is inside of the source tree.
	 * 	</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void copy() throws TreeException {
		final String sourceSessionId = "source";
		final String targetSessionId = "target";
		
		final long sourceId = 848305;
		final long targetId = 1000;
		
		final String sourceName = "recorded";
		final String targetName = "System32";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> sourceDir = TreeAssembler.getDirectoryTree();
		Collection<Directory> targetDir = TreeAssembler.getSimpleDirectoryTree();
		
		transaction.initializeSession(sourceSessionId, sourceDir);
		Element<Directory> source = manager.getElementById(sourceId);
		
		transaction.initializeSession(targetSessionId, targetDir);
		Element<Directory> target = manager.getElementById(targetId);

		source = manager.getElementById(sourceId);
		assertNull(source);
		
		transaction.sessionCheckout(sourceSessionId);
		
		source = manager.getElementById(sourceId);
		assertNotNull(source);
		
		manager.copy(source, target);
		
		transaction.sessionCheckout(targetSessionId);
		
		Element<Directory> copiedSource = manager.getElementById(sourceId);
		Element<Directory> newParentCopiedSource = manager.getElementById(
				copiedSource.getParent());
		assertNotNull(copiedSource);
		assertTrue(manager.containsElement(newParentCopiedSource, copiedSource));
		assertEquals(targetName, newParentCopiedSource.unwrap().getName());
		assertEquals(sourceName, copiedSource.unwrap().getName());
		assertEquals(2, copiedSource.getChildren().size());
		
		transaction.sessionCheckout(targetSessionId);
		source = manager.getElementById(sourceId);
		assertNotNull(source);
	}
	
	/**
	 * Test for the {@link TreeManager#removeElement(Element)}.
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
	 * Remove an element from a tree session.
	 * <p><b>Expected:</b></p>
	 * It is expected that the element and its children be removed definitely
	 * from the tree.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get the element (Adobe), its parent element (Program Files) and its
	 * 	children (Reader, Photoshop and Dreamweaver);</li>
	 * 	<li>Confirm that the parent element (Program Files) has the element to
	 * 	be removed (Adobe) and its children (Reader, Photoshop and Dreamweaver);
	 * 	</li>
	 * 	<li>Remove now the element (Adobe);</li>
	 * 	<li>Confirm now that the parent (Program Files) has not anymore the
	 * 	removed element (Adobe) and its children (Reader, Photoshop and
	 * 	Dreamweaver).</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void removeElement() throws TreeException {
		final String sessionId = "removeElement";
		
		final long programFilesId = 42345;
		final long adobeId = 24935;
		final long readerId = 403940;
		final long photoshopId = 909443;
		final long dreamweaverId = 502010;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> sourceDir = TreeAssembler.getDirectoryTree();
		
		transaction.initializeSession(sessionId, sourceDir);
		Element<Directory> programFiles = manager.getElementById(programFilesId);
		Element<Directory> adobe = manager.getElementById(adobeId);
		Element<Directory> reader = manager.getElementById(readerId);
		Element<Directory> photoshop = manager.getElementById(photoshopId);
		Element<Directory> dreamweaver = manager.getElementById(dreamweaverId);
		
		assertTrue(manager.containsElement(programFiles, adobe));
		assertTrue(manager.containsElement(programFiles, reader));
		assertTrue(manager.containsElement(programFiles, photoshop));
		assertTrue(manager.containsElement(programFiles, dreamweaver));
		
		assertNotNull(manager.removeElement(adobe));
		
		assertFalse(manager.containsElement(programFiles, adobe));
		assertFalse(manager.containsElement(programFiles, reader));
		assertFalse(manager.containsElement(programFiles, photoshop));
		assertFalse(manager.containsElement(programFiles, dreamweaver));
	}
	
	/**
	 * Test for the {@link TreeManager#removeElement(Object)}.
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
	 * Remove an element using just the element id from a tree session.
	 * <p><b>Expected:</b></p>
	 * It is expected that the element and its children be removed definitely
	 * from the tree.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get the element (Adobe), its parent element (Program Files) and its
	 * 	children (Reader, Photoshop and Dreamweaver);</li>
	 * 	<li>Confirm that the parent element (Program Files) has the element to
	 * 	be removed (Adobe) and its children (Reader, Photoshop and Dreamweaver);
	 * 	</li>
	 * 	<li>Remove now the element (Adobe) using only the element id;</li>
	 * 	<li>Confirm now that the parent (Program Files) has not anymore the
	 * 	removed element (Adobe) and its children (Reader, Photoshop and
	 * 	Dreamweaver).</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void removeObject() throws TreeException {
		final String sessionId = "removeElement";
		
		final long programFilesId = 42345;
		final long adobeId = 24935;
		final long readerId = 403940;
		final long photoshopId = 909443;
		final long dreamweaverId = 502010;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> sourceDir = TreeAssembler.getDirectoryTree();
		
		transaction.initializeSession(sessionId, sourceDir);
		Element<Directory> programFiles = manager.getElementById(programFilesId);
		Element<Directory> adobe = manager.getElementById(adobeId);
		Element<Directory> reader = manager.getElementById(readerId);
		Element<Directory> photoshop = manager.getElementById(photoshopId);
		Element<Directory> dreamweaver = manager.getElementById(dreamweaverId);
		
		assertTrue(manager.containsElement(programFiles, adobe));
		assertTrue(manager.containsElement(programFiles, reader));
		assertTrue(manager.containsElement(programFiles, photoshop));
		assertTrue(manager.containsElement(programFiles, dreamweaver));
		
		assertNotNull(manager.removeElement(adobeId));
		
		assertFalse(manager.containsElement(programFiles, adobe));
		assertFalse(manager.containsElement(programFiles, reader));
		assertFalse(manager.containsElement(programFiles, photoshop));
		assertFalse(manager.containsElement(programFiles, dreamweaver));
	}
	
	/**
	 * Test for the {@link TreeManager#containsElement(Element, Element)}.
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
	 * Verify if an element contains another one.
	 * <p><b>Expected:</b></p>
	 * Receive the <code>true</code> value when invoking
	 * {@link TreeManager#containsElement(Element, Element)}.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get the element (Adobe);</li>
	 * 	<li>Get the element (reader.exe);</li>
	 * 	<li>Confirm that the (Adobe) element contains the (reader.exe) element.
	 * 	</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void containsElement() throws TreeException {
		final String sessionId = "containsElement";
		
		final long adobeId = 24935;
		final long readerExeId = 8493845;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> sourceDir = TreeAssembler.getDirectoryTree();
		transaction.initializeSession(sessionId, sourceDir);
		
		Element<Directory> adobe = manager.getElementById(adobeId);
		Element<Directory> readerExe = manager.getElementById(readerExeId);
		
		assertTrue(manager.containsElement(adobe, readerExe));
	}
	
	/**
	 * Test for the {@link TreeManager#containsElement(Object, Object)}.
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
	 * Verify if an element contains another one through only the element id.
	 * <p><b>Expected:</b></p>
	 * Receive the <code>true</code> value when invoking
	 * {@link TreeManager#containsElement(Object, Object)}.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Invoke {@link TreeManager#containsElement(Object, Object)};</li>
	 * 	<li>Confirm that the (Adobe) element contains the (reader.exe) element
	 * 	through only the element id.</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void containsObject() throws TreeException {
		final String sessionId = "containsElement";
		
		final long adobeId = 24935;
		final long readerExeId = 8493845;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> sourceDir = TreeAssembler.getDirectoryTree();
		transaction.initializeSession(sessionId, sourceDir);
		
		assertTrue(manager.containsElement(adobeId, readerExeId));
	}
	
	/**
	 * Test for the {@link TreeManager#containsElement(Element)}.
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
	 * Verify if a tree has inside of it an element.
	 * <p><b>Expected:</b></p>
	 * Receive the <code>true</code> value when invoking
	 * {@link TreeManager#containsElement(Element)}.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get the element (bin);</li>
	 * 	<li>Confirm that the tree contains the (bin) element by invoking
	 * 	{@link TreeManager#containsElement(Element)}.</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void treeContainsElement() throws TreeException {
		final String sessionId = "treeContainsElement";
		
		final long binId = 7753032;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> sourceDir = TreeAssembler.getDirectoryTree();
		transaction.initializeSession(sessionId, sourceDir);
		Element<Directory> bin = manager.getElementById(binId);
		
		assertTrue(manager.containsElement(bin));
	}
	
	/**
	 * Test for the {@link TreeManager#containsElement(Object)}.
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
	 * Verify if a tree has inside of it an element id.
	 * <p><b>Expected:</b></p>
	 * Receive the <code>true</code> value when invoking
	 * {@link TreeManager#containsElement(Object)}.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Confirm that the tree contains the (bin) element id by invoking
	 * 	{@link TreeManager#containsElement(Object)}.</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void treeContainsObjectId() throws TreeException {
		final String sessionId = "treeContainsObjectId";
		
		final long binId = 7753032;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> sourceDir = TreeAssembler.getDirectoryTree();
		transaction.initializeSession(sessionId, sourceDir);
		
		assertTrue(manager.containsElement(binId));
	}
	
	/**
	 * Test for the {@link TreeManager#persistElement(Element)}.
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
	 * Persist an element and its child into a tree.
	 * <p><b>Expected:</b></p>
	 * It is expected that the new element and its child be part of the tree
	 * when the {@link TreeManager#persistElement(Element)} is invoked.
	 * {@link TreeManager#containsElement(Object)}.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>create two new elements;</li>
	 * 	<li>Verify that the element (Program Files) which the new element will
	 * 	be inserted does not contain the new element yet;</li>
	 * 	<li>Of the new elements, make one as the child of the other;</li>
	 * 	<li>Verify now that the parent element (Program Files) contains the new
	 * 	inserted element as well as its child.</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void persistElement() throws TreeException {
		final String sessionId = "persistElement";
		
		final long programFilesId = 42345;
		final long gamesId = Long.MAX_VALUE;
		final long ageOfEmpiresId = 48593500;
	
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.getDirectoryTree();
		transaction.initializeSession(sessionId, directories);
		
		Directory gamesDir = new Directory(gamesId, programFilesId, "Games");
		Directory ageGameDir = new Directory(ageOfEmpiresId, gamesId,
				"Age of Empires II");
		Element<Directory> games = manager.createElement(gamesId,
				programFilesId, gamesDir);
		Element<Directory> ageGame = manager.createElement(ageOfEmpiresId,
				gamesId, ageGameDir);
		
		games.addChild(ageGame);
		
		assertFalse(manager.containsElement(programFilesId, gamesId));
		assertNotNull(manager.persistElement(games));
		assertTrue(manager.containsElement(programFilesId, gamesId));;
		assertTrue(manager.containsElement(programFilesId, ageOfEmpiresId));
	}
	
	/**
	 * Test for the {@link TreeManager#updateElement(Element)}.
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
	 * Update an element after changing its parent.
	 * <p><b>Expected:</b></p>
	 * It is expected that the element to have its parent updated after changing
	 * its parent and invoking {@link TreeManager#persistElement(Element)}.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get three elements, the source and its current parent (recorded and
	 * 	VLC), and the element which it will be the new parent (Winamp);
	 * 	</li>
	 * 	<li>Verify that the (recorded) element has the (VLC) as its parent and
	 * 	the (Winamp) is not the parent of (recorded) yet;</li>
	 * 	<li>Change the parent of (recorded) element pointing to (Winamp);</li>
	 * 	<li>Update the element;</li>
	 * 	<li>Verify now that the (recorded) element has the new parent (Winamp)
	 * 	instead of (VLC).</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void updateElement() throws TreeException {
		final String sessionId = "updateElement";
		
		final long recordedId = 848305;
		final long parentRecordedIdVlc = 10239;
		final long winampId = 32099;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.getDirectoryTree();
		transaction.initializeSession(sessionId, directories);
		
		Element<Directory> winamp = manager.getElementById(winampId);
		Element<Directory> recorded = manager.getElementById(recordedId);
		Element<Directory> vlc = manager.getElementById(parentRecordedIdVlc);
		
		assertTrue(manager.containsElement(vlc, recorded));
		assertFalse(manager.containsElement(winamp, recorded));
		
		/*
		 * Setting the parent of recorded pointing to Winamp.
		 * Here, the recorded element is detached, it needs to be updated.
		 */
		recorded.setParent(winamp.getId());
		
		manager.updateElement(recorded);
		
		assertFalse(manager.containsElement(parentRecordedIdVlc, recordedId));
		assertTrue(manager.containsElement(winampId, recordedId));
	}
}
