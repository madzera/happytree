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
 * <p>This test class represents the <i>alternative scenarios</i> for the
 * operations of {@link TreeManager}.</p>
 * 
 * <p>Some operations in this class will use the sample model class
 * <code>Directory</code> and <code>TreeAssembler. Please consider see
 * these sample classes to understand the tests scenarios.</p>
 * 
 * @author Diego Nóbrega
 * @author Miuey
 *
 */
public class TreeManagerAlternativeTest {

	/**
	 * Test for the {@link TreeManager#createElement(Object, Object)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to create an
	 * element with <code>null</code> value id.</p>
	 * 
	 * <p>For remind, it is allowed trying to create an element with the
	 * <code>null</code> value because the created element here is free up. It
	 * does not belongs to none tree yet.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * try to create an element with <code>null</code> value id.
	 * <p><b>Expected:</b></p>
	 * A not <code>null</code> element but <code>null</code> id and parent id
	 * of the same element.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create the identifier for the new session;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session;</li>
	 * 	<li>Create an element with <code>null</code> id;</li>
	 * 	<li>Verify that the element is not <code>null</code>;</li>
	 * 	<li>Verify that the element id and its parent id is <code>null</code>.
	 * 	</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void createElement_nullElementId() throws TreeException {
		final String sessionId = "createElement_nullElementId";
		final String nullablElementId = null;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionId, Directory.class);
		Element<Directory> element = manager.createElement(nullablElementId,
				null);
		
		assertNotNull(element);
		assertNull(element.getId());
		assertNull(element.getParent());
	}
	
	/**
	 * Test for the {@link TreeManager#getElementById(Object)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to get an
	 * element with a <code>null</code> argument id and with a not existing
	 * id in the tree assembled through API Transformation Process.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to get an element with a <code>null</code> argument id and with a not
	 * existing id.
	 * <p><b>Expected:</b></p>
	 * A <code>null</code> element passing through a <code>null</code> id and
	 * other <code>null</code> element passing through a not existing id in the
	 * previous assembled tree.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session by API Transformation Process using the
	 * 	previous assembled tree;</li>
	 * 	<li>Try to get an element with the <code>null</code> argument id;</li>
	 * 	<li>Verify that this element is <code>null</code>;</li>
	 * 	<li>Try to get an element with an ID that does not exist in the tree;
	 * 	</li>
	 * 	<li>Now, verify that this element is <code>null</code> too.</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void getElementById_nullArg_notExists() throws TreeException {
		final String sessionId = "createElement";
		final Object nullableElementId = null;
		final long notExistingId = Long.MAX_VALUE;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.
				getDirectoryTree();
		transaction.initializeSession(sessionId, directories);
		
		Element<Directory> nullElement = manager.getElementById(
				nullableElementId);
		assertNull(nullElement);
		
		Element<Directory> noExistingElement = manager.getElementById(
				notExistingId);
		assertNull(noExistingElement);
	}
	
	/**
	 * Test for the {@link TreeManager#cut(Element, Element)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to cut an element
	 * inside of the root level of the tree.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to cut an element inside of the root element.
	 * <p><b>Expected:</b></p>
	 * It is expected that the element to be cut be moved to the root level of
	 * the tree.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session by API Transformation Process using a
	 * 	previous assembled tree;</li>
	 * 	<li>Get the source (&quot;photoshop&quot;) existed element, its parent
	 * 	(&quot;adobe&quot;) and the root element;</li>
	 * 	<li>Verify that the &quot;adobe&quot; element really contains the
	 * 	&quot;photoshop&quot; element;</li>
	 * 	<li>Try to cut the &quot;photoshop&quot; inside of the root;</li>
	 * 	<li>Verify that the element now has as parent id value, the same id than
	 * 	session id (representing the root level);</li>
	 * 	<li>Verify now that the &quot;adobe&quot; element does not contains the
	 * 	&quot;photoshop&quot; element anymore (because it is in the root).</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void cut_elementToRoot() throws TreeException {
		final String sessionId = "cut_elementToRoot";
		final long adobeId = 24935;
		final long photoshopId = 909443;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.
				getDirectoryTree();
		transaction.initializeSession(sessionId, directories);
		
		Element<Directory> adobe = manager.getElementById(adobeId);
		Element<Directory> photoshop = manager.getElementById(photoshopId);
		Element<Directory> root = transaction.currentSession().tree();
		
		assertTrue(manager.containsElement(adobe, photoshop));
		
		Element<Directory> photoshopCut = manager.cut(photoshop, root);
		assertFalse(manager.containsElement(adobe, photoshop));
		assertEquals(sessionId, photoshopCut.getParent());
	}
	
	/**
	 * Test for the {@link TreeManager#cut(Element, Element)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to cut an element
	 * for inside of a not existing target element.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to cut an element for inside of a not existing target element.
	 * <p><b>Expected:</b></p>
	 * It is expected that the element to be cut be moved to the root level of
	 * the tree.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session by API Transformation Process using a
	 * 	previous assembled tree;</li>
	 * 	<li>Get the source (<code>from</code>) existed element;</li>
	 * 	<li>Verify that the source parent element is named by &quot;Devel&quot;;
	 * 	</li>
	 * 	<li>Try to cut this element for inside of a not existed element;</li>
	 * 	<li>Verify that the source parent element id is now with the same id
	 * 	value of the session id, representing the root id;</li>
	 * 	<li>Verify if the source child element was moved too by invoking
	 * 	{@link TreeManager#containsElement(Object, Object)}.</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void cut_notExistingToElement() throws TreeException {
		final String sessionId = "cut_notExistingToElement";
		final long notExistingToId = Long.MAX_VALUE;
		
		final long sdkDevId = 84709;
		final long sdkDevChildId = 983533;
		final String sdkDevParentName = "Devel";
		final String sdkDevName = "sdk_dev";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.
				getDirectoryTree();
		transaction.initializeSession(sessionId, directories);
		
		Element<Directory> sdkDevElement = manager.getElementById(sdkDevId);
		Element<Directory> sdkDevParentElement = manager.getElementById(
				sdkDevElement.getParent());
		Element<Directory> nullableElement = manager.getElementById(
				notExistingToId);
		
		Directory devel = sdkDevParentElement.unwrap();
		assertEquals(sdkDevParentName, devel.getName());
		
		Element<Directory> cutElement = manager.cut(sdkDevElement,
				nullableElement);
		assertEquals(sessionId, cutElement.getParent());
		assertEquals(sdkDevName, cutElement.unwrap().getName());
		assertTrue(manager.containsElement(sdkDevId, sdkDevChildId));
	}
	
	/**
	 * Test for the {@link TreeManager#cut(Object, Object)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to cut an element
	 * by only its id for inside of a not existing target element.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to cut an element only by its id for inside of a not existing target
	 * element.
	 * <p><b>Expected:</b></p>
	 * It is expected that the element to be cut be moved to the root level of
	 * the tree.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session by API Transformation Process using a
	 * 	previous assembled tree;</li>
	 * 	<li>Get the source (<code>from</code>) existed element;</li>
	 * 	<li>Verify that the source parent element is named by &quot;Devel&quot;;
	 * 	</li>
	 * 	<li>Try to cut this element for inside of a not existed element;</li>
	 * 	<li>Verify that the source parent element id is now with the same id
	 * 	value of the session id, representing the root id;</li>
	 * 	<li>Verify if the source child element was moved too by invoking
	 * 	{@link TreeManager#containsElement(Object, Object)}.</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void cut_notExistingToObjectId() throws TreeException {
		final String sessionId = "cut_notExistingToObjectId";
		final long notExistingToId = Long.MAX_VALUE;
		
		final long sdkDevId = 84709;
		final long sdkDevChildId = 983533;
		final String sdkDevParentName = "Devel";
		final String sdkDevName = "sdk_dev";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.
				getDirectoryTree();
		transaction.initializeSession(sessionId, directories);
		
		Element<Directory> sdkDevElement = manager.getElementById(sdkDevId);
		Element<Directory> sdkDevParentElement = manager.getElementById(
				sdkDevElement.getParent());
		
		Directory devel = sdkDevParentElement.unwrap();
		assertEquals(sdkDevParentName, devel.getName());
		
		/*
		 * Verify that the element was cut to the root level.
		 */
		Element<Directory> cutElement = manager.cut(sdkDevId, notExistingToId);
		assertEquals(sessionId, cutElement.getParent());
		assertEquals(sdkDevName, cutElement.unwrap().getName());
		assertTrue(manager.containsElement(sdkDevId, sdkDevChildId));
	}
	
	/**
	 * Test for the {@link TreeManager#cut(Element, Element)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to cut an element
	 * for inside of other one of another tree.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to cut an element for inside of other one of another tree.
	 * <p><b>Expected:</b></p>
	 * It is expected that the element be removed from the source parent element
	 * of the source tree and placed inside of other element which belongs to
	 * another tree.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize two sessions, the respective source and target. Both
	 * 	previously loaded from <code>TreeAssembler</code>;</li>
	 * 	<li>Get the source element (Winamp) and the target (System) element
	 * 	from other tree;
	 * 	</li>
	 * 	<li>Verify that the both elements are not <code>null</code>;</li>
	 * 	<li>Change the session for the source tree by invoking
	 * 	{@link TreeTransaction#sessionCheckout(String)};</li>
	 * 	<li>Try to cut the &quot;Winamp&quot; element for inside of the
	 * 	&quot;System&quot; element;</li>
	 * 	<li>Verify that the &quot;Winamp&quot; does not exists in the source
	 * 	tree anymore;</li>
	 * 	<li>Change the session for the target tree;</li>
	 * 	<li>Verify that the &quot;System&quot; element contains the
	 * 	&quot;Winamp&quot; element by invoking
	 * 	{@link TreeManager#containsElement(Element, Element)}.</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void cut_toAnotherTree() throws TreeException {
		final String sourceSessionId = "source";
		final String targetSessionId = "target";
		
		final Long winampId = 32099l;
		final String winampName = "Winamp";
		final Long systemId = 100l;
		final String systemName = "System";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> targetDir = TreeAssembler.
				getSimpleDirectoryTree();
		Collection<Directory> sourceDir = TreeAssembler.
				getDirectoryTree();
		
		transaction.initializeSession(sourceSessionId, sourceDir);
		Element<Directory> winamp = manager.getElementById(winampId);
		assertNotNull(winamp);
		
		transaction.initializeSession(targetSessionId, targetDir);
		Element<Directory> system = manager.getElementById(systemId);
		assertNotNull(system);
		
		transaction.sessionCheckout(sourceSessionId);
		manager.cut(winamp, system);
		
		winamp = manager.getElementById(winampId);
		assertNull(winamp);
		
		transaction.sessionCheckout(targetSessionId);
		winamp = manager.getElementById(winampId);
		assertNotNull(winamp);
		assertEquals(winampName, winamp.unwrap().getName());
		assertEquals(systemName, system.unwrap().getName());
		assertTrue(manager.containsElement(system, winamp));
	}
	
	/**
	 * Test for the {@link TreeManager#cut(Element, Element)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to cut an element
	 * for the root level of another tree. All children of the element will be
	 * cut too.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to cut an element for the root level of another tree.
	 * <p><b>Expected:</b></p>
	 * It is expected that the element be removed from the source tree and
	 * placed at the target with its children together.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize two sessions, the respective source and target. Both
	 * 	previously loaded from <code>TreeAssembler</code>;</li>
	 * 	<li>Get the source element and target (target session id - root);
	 * 	</li>
	 * 	<li>Change the session for the source session;</li>
	 * 	<li>Cut the element for the target root tree;</li>
	 * 	<li>Verify that the source element is not inside of the source tree
	 * 	anymore;</li>
	 * 	<li>Change the session for the target session;</li>
	 * 	<li>Verify if the target root tree really has the cut source element;
	 * 	</li>
	 * 	<li>Also verify if the children of the source element were cut too.</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	public void cut_toRootOfAnotherTree() throws TreeException {
		final String sourceSessionId = "source";
		final String targetSessionId = "target";
		
		final Long develId = 93832l;
		final String develName = "Devel";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> targetDir = TreeAssembler.
				getSimpleDirectoryTree();
		Collection<Directory> sourceDir = TreeAssembler.
				getDirectoryTree();
		
		transaction.initializeSession(sourceSessionId, sourceDir);
		Element<Directory> devel = manager.getElementById(develId);
		
		transaction.initializeSession(targetSessionId, targetDir);
		Element<Directory> targetRootTree = transaction.currentSession().tree();
		
		transaction.sessionCheckout(sourceSessionId);
		
		manager.cut(devel, targetRootTree);
		
		/*
		 * Devel does not exists in the source tree anymore.
		 */
		devel = manager.getElementById(develId);
		assertNull(devel);
		
		/*
		 * Devel now is in target tree.
		 */
		transaction.sessionCheckout(targetSessionId);
		devel = manager.getElementById(develId);
		assertNotNull(devel);
		assertEquals(develName, devel.unwrap().getName());
		assertEquals(targetSessionId, devel.getParent());
		assertTrue(manager.containsElement(targetRootTree, devel));
		assertTrue(devel.getChildren().size() > 0);
	}
	
	/**
	 * Test for the {@link TreeManager#copy(Element, Element)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to copy an element
	 * for the root level of another tree. All children of the element will be
	 * copied too.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to copy an element for the root level of another tree.
	 * <p><b>Expected:</b></p>
	 * It is expected that the element be copied from the source tree and
	 * replicated at the target tree with its children together.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize two sessions, the respective source and target. Both
	 * 	previously loaded from <code>TreeAssembler</code>;</li>
	 * 	<li>Get the source element and target (target session id - root);
	 * 	</li>
	 * 	<li>Copy the source element in the source tree for the root level of
	 * 	the target tree;</li>
	 * 	<li>Change the session for the target session;</li>
	 * 	<li>Verify if the target root really has the copied source element;</li>
	 * 	<li>Verify if the children of source element were copied too;</li>
	 * 	<li>Change the session for the source session;</li>
	 * 	<li>Verify if the source element still is inside of the source tree.
	 * 	</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void copy_toRootOfAnotherTree() throws TreeException {
		final String sourceSessionId = "source";
		final String targetSessionId = "target";
		
		final Long develId = 93832l;
		final String develName = "Devel";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> targetDir = TreeAssembler.
				getSimpleDirectoryTree();
		Collection<Directory> sourceDir = TreeAssembler.
				getDirectoryTree();
		
		transaction.initializeSession(sourceSessionId, sourceDir);
		Element<Directory> devel = manager.getElementById(develId);
		
		transaction.initializeSession(targetSessionId, targetDir);
		Element<Directory> targetRootTree = transaction.currentSession().tree();
		
		transaction.sessionCheckout(sourceSessionId);
		manager.copy(devel, targetRootTree);
		
		transaction.sessionCheckout(targetSessionId);
		Element<Directory> copiedDevel = manager.getElementById(develId);
		assertEquals(targetSessionId, copiedDevel.getParent());
		assertEquals(develName, copiedDevel.unwrap().getName());
		assertTrue(manager.containsElement(targetRootTree, copiedDevel));
		assertTrue(copiedDevel.getChildren().size() > 0);
		
		transaction.sessionCheckout(sourceSessionId);
		devel = manager.getElementById(develId);
		assertNotNull(devel);
	}
	
	/**
	 * Test for the {@link TreeManager#removeElement(Element)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to remove an
	 * element which it has the <code>null</code> value.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to remove an element with a <code>null</code> argument element.
	 * <p><b>Expected:</b></p>
	 * Receive the <code>false</code> value when invoking
	 * {@link TreeManager#removeElement(Element)}.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Try to remove an element with a <code>null</code> value;</li>
	 * 	<li>Verify if the return of the operation is <code>false</code>.</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void removeElement_nullElement() throws TreeException {
		final String sessionId = "removeElement_nullElement";
		final Element<Directory> nullableElement = null;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.getDirectoryTree();
		
		transaction.initializeSession(sessionId, directories);
		assertNull(manager.removeElement(nullableElement));
	}
	
	/**
	 * Test for the {@link TreeManager#removeElement(Object)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to remove an
	 * element only by its id which it has the <code>null</code> value.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to remove an element with a <code>null</code> argument.
	 * <p><b>Expected:</b></p>
	 * Receive the <code>false</code> value when invoking
	 * {@link TreeManager#removeElement(Object)}.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Try to remove an element only by its id with a <code>null</code> id
	 * 	value;</li>
	 * 	<li>Verify if the return of the operation is <code>false</code>.</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void removeElement_nullObjectId() throws TreeException {
		final String sessionId = "removeElement_nullElement";
		final Object nullableId = null;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.getDirectoryTree();
		
		transaction.initializeSession(sessionId, directories);
		assertNull(manager.removeElement(nullableId));
	}
	
	/**
	 * Test for the {@link TreeManager#removeElement(Element)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to remove a not
	 * existing element.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to remove a not existing element.
	 * <p><b>Expected:</b></p>
	 * Receive the <code>false</code> value when invoking
	 * {@link TreeManager#removeElement(Element)}.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Try to get an element with a not existing element id in the tree;
	 * 	</li>
	 * 	<li>Try to remove the previous return element;</li>
	 * 	<li>Verify if the return of the operation is <code>false</code>.</li>
	 * </ol>
	 * @throws TreeException
	 */
	@Test
	public void remove_notExistingElement() throws TreeException {
		final String sessionId = "remove_notExistingElement";
		final long notExistingId = Long.MAX_VALUE;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.getDirectoryTree();
		
		transaction.initializeSession(sessionId, directories);
		Element<Directory> nullableElement = manager.getElementById(
				notExistingId);
		assertNull(manager.removeElement(nullableElement));
	}
	
	/**
	 * Test for the {@link TreeManager#removeElement(Object)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to remove an
	 * element through a not existing id.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to remove an element through a not existing id.
	 * <p><b>Expected:</b></p>
	 * Receive the <code>false</code> value when invoking
	 * {@link TreeManager#removeElement(Object)}.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Try to remove an element through a not existing id in the tree;</li>
	 * 	<li>Verify if the return of the operation is <code>false</code>.</li>
	 * </ol>
	 * @throws TreeException
	 */
	@Test
	public void remove_notExistingObjectId() throws TreeException {
		final String sessionId = "remove_notExistingObjectId";
		final long notExistingId = Long.MAX_VALUE;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.getDirectoryTree();
		
		transaction.initializeSession(sessionId, directories);
		assertNull(manager.removeElement(notExistingId));
	}
}
