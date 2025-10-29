package com.madzera.happytree.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.junit.Test;

import com.madzera.happytree.Element;
import com.madzera.happytree.TreeManager;
import com.madzera.happytree.TreeTransaction;
import com.madzera.happytree.common.TreeCommonTestHelper;
import com.madzera.happytree.core.HappyTree;
import com.madzera.happytree.demo.model.Directory;
import com.madzera.happytree.demo.model.Metadata;
import com.madzera.happytree.demo.util.TreeAssembler;
import com.madzera.happytree.exception.TreeException;

/**
 * Test class for {@link TreeManager} operations.
 * 
 * <p>This test class represents the <i>alternative scenarios</i> for the
 * operations of {@link TreeManager}.</p>
 * 
 * <p>Some operations in this class will use the sample in
 * <code>Directory</code> and <code>TreeAssembler</code> model classes. Please
 * consider see these sample classes to understand the tests scenarios.</p>
 * 
 * @author Diego Madson de Andrade Nóbrega
 *
 */
public class TreeManagerAlternativeTest extends TreeCommonTestHelper {

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
	 * A <code>null</code> element for both situations, with a not existing id
	 * and with a <code>null</code> id.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session by API Transformation Process using the
	 * 	previous assembled tree;</li>
	 * 	<li>Try to get an element with the <code>null</code> argument id;</li>
	 * 	<li>Verify that this element is <code>null</code>;</li>
	 * 	<li>Try to get an element with an id that does not exist in the tree;
	 * 	</li>
	 * 	<li>Now, verify that this element is <code>null</code> too.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void getElementById_nullArg_notExists() throws TreeException {
		final String sessionId = "createElement";
		final Object nullableElementId = null;
		final long notExistingId = Long.MAX_VALUE;

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Collection<Directory> directories = TreeAssembler.getDirectoryTree();
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
	 * for inside of the root level of the tree.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to cut an element for inside of the root element.
	 * <p><b>Expected:</b></p>
	 * It is expected that the element to be cut be moved to the root level of
	 * the own tree.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session by the API Transformation Process using a
	 * 	previous assembled tree;</li>
	 * 	<li>Get the source (&quot;photoshop&quot;) existed element, its parent
	 * 	(&quot;adobe&quot;) and the root element;</li>
	 * 	<li>Verify that the &quot;adobe&quot; element really contains the
	 * 	&quot;photoshop&quot; element;</li>
	 * 	<li>Try to cut the &quot;photoshop&quot; for inside of the root level;
	 * 	</li>
	 * 	<li>Verify if the element now is in the root level;</li>
	 * 	<li>Verify now that the &quot;adobe&quot; element does not contain the
	 * 	&quot;photoshop&quot; element anymore (because it is in the root now).
	 * 	</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void cut_elementToRoot() throws TreeException {
		final String sessionId = "cut_elementToRoot";
		final long adobeId = 24935;
		final long photoshopId = 909443;

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Collection<Directory> directories = TreeAssembler.getDirectoryTree();
		transaction.initializeSession(sessionId, directories);

		Element<Directory> adobe = manager.getElementById(adobeId);
		Element<Directory> photoshop = manager.getElementById(photoshopId);
		Element<Directory> root = manager.root();

		assertTrue(manager.containsElement(adobe, photoshop));

		manager.cut(photoshop, root);

		root = manager.root();
		boolean wasMoved = root.getChildren()
				.stream()
				.filter(e -> photoshopId == (long) e.getId())
				.findFirst()
				.isPresent();
				
		assertTrue(wasMoved);
		assertFalse(manager.containsElement(adobe, photoshop));
		assertTrue(manager.containsElement(root, photoshop));
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
	 * the own tree.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session by the API Transformation Process using a
	 * 	previous assembled tree;</li>
	 * 	<li>Get the source (sdk_dev) element;</li>
	 * 	<li>Verify that the source parent element is named &quot;Devel&quot;;
	 * 	</li>
	 * 	<li>Try to cut this element for inside of a not existed element;</li>
	 * 	<li>Verify if the element now is in the root level;</li>
	 * 	<li>Verify if the source child element was moved too by invoking
	 * 	{@link TreeManager#containsElement(Object, Object)}.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
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

		Collection<Directory> directories = TreeAssembler.getDirectoryTree();
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

		Element<Directory> root = manager.root();

		/*
		 * Verify that the element was cut to the root level.
		 */
		boolean wasMoved = root.getChildren()
				.stream()
				.filter(e -> sdkDevId == (long) e.getId())
				.findFirst()
				.isPresent();
		
		assertTrue(wasMoved);
		assertTrue(manager.containsElement(root, cutElement));
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
	 * the own tree.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session by API Transformation Process using a
	 * 	previous assembled tree;</li>
	 * 	<li>Get the source (sdk_dev) existed element;</li>
	 * 	<li>Verify that the source parent element is named &quot;Devel&quot;;
	 * 	</li>
	 * 	<li>Try to cut this element for inside of a not existed element;</li>
	 * 	<li>Verify if the element now is in the root level;</li>
	 * 	<li>Verify if the source child element was moved too by invoking
	 * 	{@link TreeManager#containsElement(Object, Object)}.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
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

		Collection<Directory> directories = TreeAssembler.getDirectoryTree();
		transaction.initializeSession(sessionId, directories);

		Element<Directory> sdkDevElement = manager.getElementById(sdkDevId);
		Element<Directory> sdkDevParentElement = manager.getElementById(
				sdkDevElement.getParent());

		Directory devel = sdkDevParentElement.unwrap();
		assertEquals(sdkDevParentName, devel.getName());

		Element<Directory> cutElement = manager.cut(sdkDevId, notExistingToId);

		Element<Directory> root = manager.root();

		/*
		 * Verify that the element was cut to the root level.
		 */
		boolean wasMoved = root.getChildren()
				.stream()
				.filter(e -> sdkDevId == (long) e.getId())
				.findFirst()
				.isPresent();
		
		assertTrue(wasMoved);
		assertTrue(manager.containsElement(root, cutElement));
		assertEquals(sdkDevName, cutElement.unwrap().getName());
		assertTrue(manager.containsElement(sdkDevId, sdkDevChildId));
	}

	/**
	 * Test for the {@link TreeManager#cut(Object, Object)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to cut an element
	 * by only its id for inside of a <code>null</code> target element.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to cut an element only by its id for inside of a <code>null</code>
	 * target element.
	 * <p><b>Expected:</b></p>
	 * It is expected that the element to be cut be moved to the root level of
	 * the own tree.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session by API Transformation Process using a
	 * 	previous assembled tree;</li>
	 * 	<li>Get the source (<code>from</code>) existed element;</li>
	 * 	<li>Verify that the source parent element is named &quot;Devel&quot;;
	 * 	</li>
	 * 	<li>Try to cut this element for inside of a <code>null</code> element;
	 * 	</li>
	 * 	<li>Verify if the element now is in the root level;</li>
	 * 	<li>Verify if the source child element was moved too by invoking
	 * 	{@link TreeManager#containsElement(Object, Object)}.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void cut_nullToObjectId() throws TreeException {
		final String sessionId = "cut_nullToObjectId";
		final Long cut_nullToObjectId = null;

		final long sdkDevId = 84709;
		final long sdkDevChildId = 983533;
		final String sdkDevParentName = "Devel";
		final String sdkDevName = "sdk_dev";

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Collection<Directory> directories = TreeAssembler.getDirectoryTree();
		transaction.initializeSession(sessionId, directories);

		Element<Directory> sdkDevElement = manager.getElementById(sdkDevId);
		Element<Directory> sdkDevParentElement = manager.getElementById(
				sdkDevElement.getParent());

		Directory devel = sdkDevParentElement.unwrap();
		assertEquals(sdkDevParentName, devel.getName());

		Element<Directory> cutElement = manager.cut(sdkDevId,
				cut_nullToObjectId);

		Element<Directory> root = manager.root();
		
		/*
		 * Verify that the element was cut to the root level.
		 */
		boolean wasMoved = root.getChildren()
				.stream()
				.filter(e -> sdkDevId == (long) e.getId())
				.findFirst()
				.isPresent();
		
		assertTrue(wasMoved);
		assertTrue(manager.containsElement(root, cutElement));
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
	 * @throws TreeException in case of an error
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

		Collection<Directory> targetDir = TreeAssembler.getSimpleDirectoryTree();
		Collection<Directory> sourceDir = TreeAssembler.getDirectoryTree();

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
	 * @throws TreeException in case of an error
	 */
	@Test
	public void cut_toRootOfAnotherTree() throws TreeException {
		final String sourceSessionId = "source";
		final String targetSessionId = "target";

		final Long develId = 93832l;
		final String develName = "Devel";

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Collection<Directory> targetDir = TreeAssembler.getSimpleDirectoryTree();
		Collection<Directory> sourceDir = TreeAssembler.getDirectoryTree();

		transaction.initializeSession(sourceSessionId, sourceDir);
		Element<Directory> devel = manager.getElementById(develId);

		transaction.initializeSession(targetSessionId, targetDir);
		Element<Directory> targetRootTree = manager.root();

		transaction.sessionCheckout(sourceSessionId);

		manager.cut(devel, targetRootTree);

		/*
		 * Devel does not exist in the source tree anymore.
		 */
		devel = manager.getElementById(develId);
		assertNull(devel);

		/*
		 * Devel now is in the target tree.
		 */
		transaction.sessionCheckout(targetSessionId);
		devel = manager.getElementById(develId);
		assertNotNull(devel);
		assertEquals(develName, devel.unwrap().getName());
		assertEquals(targetSessionId, devel.attachedTo().getSessionId());
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
	 * @throws TreeException in case of an error
	 */
	@Test
	public void copy_toRootOfAnotherTree() throws TreeException {
		final String sourceSessionId = "source";
		final String targetSessionId = "target";

		final Long develId = 93832l;
		final String develName = "Devel";

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Collection<Directory> targetDir = TreeAssembler.getSimpleDirectoryTree();
		Collection<Directory> sourceDir = TreeAssembler.getDirectoryTree();

		transaction.initializeSession(sourceSessionId, sourceDir);
		Element<Directory> devel = manager.getElementById(develId);

		transaction.initializeSession(targetSessionId, targetDir);
		Element<Directory> targetRootTree = manager.root();

		transaction.sessionCheckout(sourceSessionId);
		manager.copy(devel, targetRootTree);

		transaction.sessionCheckout(targetSessionId);
		Element<Directory> copiedDevel = manager.getElementById(develId);
		assertEquals(targetSessionId, copiedDevel.attachedTo().getSessionId());
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
	 * @throws TreeException in case of an error
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
	 * @throws TreeException in case of an error
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
	 * 
	 * @throws TreeException in case of an error
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
	 * 
	 * @throws TreeException in case of an error
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

	/**
	 * Test for the {@link TreeManager#containsElement(Element, Element)} and
	 * {@link TreeManager#containsElement(Element)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to verify if an
	 * element contains another one which that one has <code>null</code> value.
	 * </p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to verify if an element contains another one which that one is
	 * <code>null</code>.
	 * <p><b>Expected:</b></p>
	 * Receive the <code>false</code> value.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get an element from this tree session;</li>
	 * 	<li>Verify if this element contains an element with <code>null</code>
	 * 	value;</li>
	 * 	<li>Receive the <code>false</code> value.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void containsElement_nullElement() throws TreeException {
		final String sessionId = "containsElement_nullElement";

		final long officeId = 53024;
		final Element<Directory> nullableElement = null;

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Collection<Directory> directories = TreeAssembler.getDirectoryTree();

		transaction.initializeSession(sessionId, directories);

		Element<Directory> office = manager.getElementById(officeId);

		assertFalse(manager.containsElement(office, nullableElement));
		assertFalse(manager.containsElement(nullableElement, office));
		assertFalse(manager.containsElement(nullableElement));
	}

	/**
	 * Test for the {@link TreeManager#containsElement(Element, Element)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to verify if an
	 * element contains another one which one of them have a <i>DETACHED</i>
	 * child.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to verify if an element contains another one which one of them have a
	 * detached child.
	 * <p><b>Expected:</b></p>
	 * Receive the <code>false</code> value.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get two elements which one is actually child of the other;</li>
	 * 	<li>Confirm the <code>true</code> result;</li>
	 * 	<li>Remove a different child from one of them turning it as
	 * 	<i>DETACHED</i>;</li>
	 * 	<li>Confirm the <code>false</code> value.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void containsElement_detachedChildElement() throws TreeException {
		final String sessionId = "containsElement_detachedChildElement";

		final long realtekId = 94034;
		final long binId = 7753032;
		final long sdkId = 113009;

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Collection<Directory> directories = TreeAssembler.getDirectoryTree();

		transaction.initializeSession(sessionId, directories);

		Element<Directory> realtek = manager.getElementById(realtekId);
		Element<Directory> bin = manager.getElementById(binId);

		assertTrue(manager.containsElement(realtek, bin));

		Element<Directory> sdk = realtek.getChildren()
				.stream()
				.filter(child -> child.getId().equals(sdkId))
				.findFirst()
				.get();

		realtek.removeChild(sdk);

		assertFalse(manager.containsElement(realtek, bin));
	}

	/**
	 * Test for the {@link TreeManager#containsElement(Object, Object)} and
	 * {@link TreeManager#containsElement(Element)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to verify if an
	 * element contains another one which they have different types of wrapped
	 * objects.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>,
	 * <code>Metadata</code> and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to verify if an element contains another one which they have
	 * different types of wrapped nodes.
	 * <p><b>Expected:</b></p>
	 * Receive the <code>false</code> value.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session previously loaded from
	 * 	<code>TreeAssembler</code> with the <code>Directory</code> type;</li>
	 * 	<li>Get an element from this tree session;</li>
	 * 	<li>Initialize another session previously loaded from
	 * 	<code>TreeAssembler</code> with the <code>Metadata</code> type;</li>
	 * 	<li>Get an element from this tree session;</li>
	 * 	<li>Try to verify if an element contains the other one;</li>
	 * 	<li>Receive the <code>false</code> value.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void containsElement_mismatchElement() throws TreeException {
		final String sourceSessionId = "source";
		final String targetSessionId = "target";

		final long happytreeId = 859452;
		final String ownerId = "owner";

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Collection<Directory> directories = TreeAssembler.getDirectoryTree();
		Collection<Metadata> metadatas = TreeAssembler.getMetadataTree();

		transaction.initializeSession(sourceSessionId, directories);
		Element<Directory> happytree = manager.getElementById(happytreeId);

		transaction.initializeSession(targetSessionId, metadatas);
		Element<Metadata> owner = manager.getElementById(ownerId);

		assertFalse(manager.containsElement(happytree, owner));
		assertFalse(manager.containsElement(happytree));
	}

	/**
	 * Test for the {@link TreeManager#containsElement(Element, Element)} and
	 * {@link TreeManager#containsElement(Element)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to verify if an
	 * element contains another one which one of them has the <i>DETACHED</i>
	 * state in life cycle.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to verify if an element contains another one which one of them has
	 * the <i>DETACHED</i> state in life cycle.
	 * <p><b>Expected:</b></p>
	 * Receive the <code>false</code> value.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get two elements from this tree session;</li>
	 * 	<li>Confirm if an element is descendant of the other one;</li>
	 * 	<li>Modify the wrapped node to turn on one of elements with
	 * 	<i>DETACHED</i> state;</li>
	 * 	<li>Try to verify if this modified element contains the other one;</li>
	 * 	<li>Receive the <code>false</code> value.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void containsElement_detachedElement() throws TreeException {
		final String sessionId = "containsElement_detachedElement";

		final long vlcId = 10239;
		final long rec2Id = 1038299;

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Collection<Directory> directories = TreeAssembler.getDirectoryTree();

		transaction.initializeSession(sessionId, directories);
		Element<Directory> vlc = manager.getElementById(vlcId);
		Element<Directory> rec2 = manager.getElementById(rec2Id);

		assertTrue(manager.containsElement(vlc, rec2));
		assertTrue(manager.containsElement(vlc));

		Directory proj = new Directory((long) vlc.getId(),
				(long) vlc.getParent(), "Media Player");

		/*
		 * Becomes detached here.
		 */
		vlc.wrap(proj);

		assertFalse(manager.containsElement(vlc, rec2));
		assertFalse(manager.containsElement(vlc));
	}

	/**
	 * Test for the {@link TreeManager#containsElement(Object, Object)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to verify if an
	 * element contains another one which using the <code>null</code> arguments.
	 * </p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to verify whether an element contains another one when they are
	 * <code>null</code>.
	 * <p><b>Expected:</b></p>
	 * Receive the <code>false</code> value.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Declare two already existing <code>Object</code> ids corresponding
	 * 	to their respective elements;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Confirms that one of these elements actually is child of the other;
	 * 	</li>
	 * 	<li>Now set one of them as <code>null</code> id;</li>
	 * 	<li>Confirm the <code>false</code> result;</li>
	 * 	<li>Set the other as <code>null</code> id;</li>
	 * 	<li>Confirm the <code>false</code> result.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void containsElement_nullObjectIdArguments() throws TreeException {
		final String sessionId = "containsElement_nullObjectIdArguments";

		Long rec2Id = 1038299L;
		Long recordedId = 848305L;

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Collection<Directory> directories = TreeAssembler.getDirectoryTree();

		transaction.initializeSession(sessionId, directories);

		assertTrue(manager.containsElement(recordedId, rec2Id));

		rec2Id = null;

		assertFalse(manager.containsElement(recordedId, rec2Id));

		rec2Id = 0L;
		recordedId = null;

		assertFalse(manager.containsElement(recordedId, rec2Id));
	}

	/**
	 * Test for the {@link TreeManager#containsElement(Object)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to check if the
	 * tree session has two elements declared by id when they are non-existent
	 * and <code>null</code> respectively.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to verify whether the tree session has two declared elements by id
	 * when they are non-existent and <code>null</code> respectively.
	 * <p><b>Expected:</b></p>
	 * Receive the <code>false</code> value.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Declare two object IDs, one non-existent in the defined tree and the
	 * 	other <code>null</code>;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Confirm the <code>false</code> result for both.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void containsElement_nullObjectId() throws TreeException {
		final String sessionId = "containsElement_nullObjectId";

		Long nullObjectId = null;
		Long notFoundObjectId = 123456789L;

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Collection<Directory> directories = TreeAssembler.getDirectoryTree();

		transaction.initializeSession(sessionId, directories);

		assertFalse(manager.containsElement(nullObjectId));
		assertFalse(manager.containsElement(notFoundObjectId));
	}

	/**
	 * Test for the {@link TreeManager#updateElement(Element)} operation.
	 * 
	 * <p>Alternative scenario for this operation when trying to update an id of
	 * element with a <code>null</code> value.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to update an element id with a <code>null</code> id.
	 * <p><b>Expected:</b></p>
	 * Even trying to set a <code>null</code> id, the real value of the id keeps
	 * the previous value.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get an existing element from the current tree session;</li>
	 * 	<li>Set the id of this element with a <code>null</code> value;</li>
	 * 	<li>Try to update the element;</li>
	 * 	<li>Verify if the value of id corresponds with the previous id value;
	 * 	</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void updateElement_nullIdElement() throws TreeException {
		final String sessionId = "updateElement_nullIdElement";

		final Object wordId = 674098L;
		Object nullableId = null;

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Collection<Directory> directories = TreeAssembler.getDirectoryTree();
		transaction.initializeSession(sessionId, directories);

		Element<Directory> word = manager.getElementById(wordId);
		word.setId(nullableId);

		manager.updateElement(word);

		word = manager.getElementById(wordId);

		assertNotNull(word);
		assertEquals(wordId, word.getId());
	}

	/**
	 * Test for the {@link TreeManager#updateElement(Element)} operation.
	 * 
	 * <p>Alternative scenario for this operation when trying to update an
	 * element id.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>,
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to update an element id.
	 * <p><b>Expected:</b></p>
	 * After updating a changed element id, the new id already becomes
	 * available.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get an existing element from the tree session;</li>
	 * 	<li>Set the id of this element;</li>
	 * 	<li>Verify that the id still not changed, even invoking the
	 * 	{@link Element#setId(Object)};</li>
	 * 	<li>Try to update the element;</li>
	 * 	<li>Verify now that the element has the new id;</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void updateElement_setId() throws TreeException {
		final String sessionId = "updateElement_setId";

		final long fooId = 48224;

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Collection<Directory> directories = TreeAssembler.getDirectoryTree();
		transaction.initializeSession(sessionId, directories);

		Element<Directory> foo = manager.getElementById(fooId);
		foo.setId(Long.MAX_VALUE);

		assertEquals(fooId, foo.getId());

		foo = manager.updateElement(foo);

		assertNotEquals(fooId, foo.getId());
		assertEquals(Long.MAX_VALUE, foo.getId());

		Element<Directory> tmp = null;

		for (Element<Directory> child : foo.getChildren()) {
			tmp = child;
		}

		assertEquals(Long.MAX_VALUE, tmp.getParent());
	}

	@Test
	public void updateElement_setChildParentId() throws TreeException {
		final String sessionId = "updateElement_setChildParentId";

		final Long recordedId = 848305L;
		final Long rec1Id = 3840200L;
		final Long winampId = 32099L;

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Collection<Directory> directories = TreeAssembler.getDirectoryTree();
		transaction.initializeSession(sessionId, directories);

		Element<Directory> recorded = manager.getElementById(recordedId);
		assertNotNull(recorded);

		for (Element<Directory> child : recorded.getChildren()) {
			if (rec1Id.equals(child.getId())) {
				Element<Directory> rec1 = child;
				rec1.setParent(winampId);
			}
		}

		manager.updateElement(recorded);

		Element<Directory> rec1 = manager.getElementById(rec1Id);

		assertEquals(winampId, rec1.getParent());
	}
	
	/**
	 * Test for the {@link TreeManager#updateElement(Element)} operation.
	 * 
	 * <p>Alternative scenario for this operation when trying to update the
	 * element by adding a new element as child of an existing one.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>,
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to add a new element as child of an existing one and then update the
	 * element.
	 * <p><b>Expected:</b></p>
	 * After updating the parent element, the new child already becomes
	 * available.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Create a new element which will be added as child of an existing
	 * 	element;</li>
	 * 	<li>Persist this new element;</li>
	 * 	<li>Add this new element as child of an existing one;</li>
	 * 	<li>Try to update the parent element;</li>
	 * 	<li>Verify now that the parent element has the new child.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void updateElement_addNewChild() throws TreeException {
		final String sessionId = "updateElement_addNewChild";

		final Long wordId = 4611329L;

		Directory officeDirectory = new Directory(9999L, null, "Office");

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Collection<Directory> directories = TreeAssembler.getDirectoryTree();

		transaction.initializeSession(sessionId, directories);

		Element<Directory> office = manager.createElement(officeDirectory
				.getIdentifier(), officeDirectory.getParentIdentifier(),
				officeDirectory);

		assertEquals("NOT_EXISTED", office.lifecycle());

		office = manager.persistElement(office);

		assertEquals("ATTACHED", office.lifecycle());

		Element<Directory> word = manager.getElementById(wordId);

		word.addChild(office);
		word = manager.updateElement(word);

		assertEquals(1, word.getChildren().size());
		assertEquals(wordId, office.getParent());
	}
	
	/**
	 * Test for the {@link TreeManager#persistElement(Element)} operation.
	 * 
	 * <p>Alternative scenario for this operation when trying to persist an
	 * element and checking whether it changes the own object reference.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>,
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to persist an element which its reference was changed outside of the
	 * tree.
	 * <p><b>Expected:</b></p>
	 * After persisting the element, the reference is refreshed only when the
	 * element is retrieved from the manager by invoking
	 * {@link TreeManager#getElementById(Long)}.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Create a new element which its reference is not part of the current
	 * 	tree session;</li>
	 * 	<li>Persist this new element;</li>
	 * 	<li>Verify that the lifecycle state of this element is still as
	 * 	<code>NOT_EXISTED</code>;</li>
	 * 	<li>Try to get this element by its id;</li>
	 * 	<li>Verify now that the lifecycle state of this element is
	 * 	<code>ATTACHED</code>, meaning that it refreshed its reference and
	 * 	now is part of the current tree session.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void updateElement_testingElementReference() throws TreeException {
		final String sessionId = "updateElement_testingParamReference";

		final Long programFilesId = 42345L;
		final Long intelliJId = 4887582L;

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Directory intelliJDirectory = new Directory(4887582L, programFilesId,
				sessionId);

		Collection<Directory> directories = TreeAssembler.getDirectoryTree();

		transaction.initializeSession(sessionId, directories);

		Element<Directory> intelliJ = manager.createElement(intelliJId,
				programFilesId, intelliJDirectory);

		assertEquals("NOT_EXISTED", intelliJ.lifecycle());

		/*
		 * Persist the element and verify its lifecycle state. The lifecycle
		 * state should keep as NOT_EXISTED, as the element does not refresh its
		 * reference.
		 */
		manager.persistElement(intelliJ);
		assertEquals("NOT_EXISTED", intelliJ.lifecycle());

		/*
		 * Now the element has updated its reference.
		 */
		intelliJ = manager.getElementById(intelliJId);
		assertEquals("ATTACHED", intelliJ.lifecycle());
	}

	/**
	 * Test for the {@link TreeManager#search(Predicate)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to search with a
	 * <code>null</code> predicate parameter. This test verifies the behavior
	 * when a <code>null</code> condition is passed to the search method.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to search for elements with a <code>null</code> predicate condition.
	 * <p><b>Expected:</b></p>
	 * Return an empty list when a <code>null</code> predicate is provided,
	 * since no valid search condition can be evaluated.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session by API Transformation Process using the
	 * 	previous assembled tree;</li>
	 * 	<li>Create a <code>null</code> predicate condition;</li>
	 * 	<li>Invoke {@link TreeManager#search(Predicate)} with the
	 * 	<code>null</code> predicate;</li>
	 * 	<li>Verify that the result list is empty.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void search_nullPredicate() throws TreeException {
		final String sessionId = "search_nullPredicate";

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Collection<Directory> directoryTree = TreeAssembler.getDirectoryTree();

		transaction.initializeSession(sessionId, directoryTree);

		Predicate<Element<Directory>> nullPredicate = null;
		List<Element<Directory>> result = manager.search(nullPredicate);

		assertEquals(0, result.size());
	}

	/**
	 * Test for the {@link TreeManager#search(Predicate)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to search for
	 * elements with a condition that matches no elements in the tree. This test
	 * verifies the behavior when a valid predicate is provided but no elements
	 * satisfy the search criteria.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to search for elements using a condition that matches no existing
	 * elements in the tree.
	 * <p><b>Expected:</b></p>
	 * Return an empty list when no elements satisfy the search condition,
	 * demonstrating that the search method handles cases where no matches are
	 * found.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session by API Transformation Process using the
	 * 	previous assembled tree;</li>
	 * 	<li>Invoke {@link TreeManager#search(Predicate)} with a condition that
	 * 	checks if directory names start with "Z" (which should not exist in the
	 * 	test data);</li>
	 * 	<li>Verify that the result list is empty since no directories in the
	 * 	test tree start with "Z".</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void search_noFoundElements() throws TreeException {
		final String sessionId = "search_noFoundElements";

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Collection<Directory> directoryTree = TreeAssembler.getDirectoryTree();

		transaction.initializeSession(sessionId, directoryTree);

		List<Element<Directory>> result = manager.search(
			element -> directoryNameStartsWithZ(element)
		);

		assertEquals(0, result.size());
	}

	/**
	 * Test for the {@link TreeManager#apply(Consumer)} operation.
	 * 
	 * <p>Alternative scenario for this operation when trying to apply an action
	 * with a <code>null</code> consumer parameter. This test verifies that the
	 * tree remains unchanged when a <code>null</code> action is applied.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to apply a <code>null</code> action to all elements in the tree.
	 * <p><b>Expected:</b></p>
	 * No changes should be made to any tree elements. The original element
	 * names and structure should remain completely unchanged.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session by API Transformation Process using the
	 * 	previous assembled tree;</li>
	 * 	<li>Try to apply a <code>null</code> consumer action to all elements;
	 * 	</li>
	 * 	<li>Get a specific element (Photoshop) from the tree;</li>
	 * 	<li>Verify that the element name remains unchanged since the action is
	 * 	<code>null</code>;</li>
	 * 	<li>Verify that the child elements also remain unchanged.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void apply_nullAction() throws TreeException {
		final String sessionId = "apply_nullAction";
		final long photoshopId = 909443L;

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Collection<Directory> directoryTree = TreeAssembler.getDirectoryTree();

		transaction.initializeSession(sessionId, directoryTree);

		Consumer<Element<Directory>> nullConsumer = null;
		manager.apply(nullConsumer);

		Element<Directory> photoshop = manager.getElementById(photoshopId);

		assertEquals("Photoshop", photoshop.unwrap().getName());
		assertEquals("photoshop.exe", photoshop.getChildren().iterator()
				.next().unwrap().getName());
	}

	/**
	 * Test for the {@link TreeManager#apply(Consumer, Predicate)} operation.
	 * 
	 * <p>Alternative scenario for this operation when trying to apply an action
	 * with null consumer parameter and null predicate condition. This test
	 * verifies the behavior when null values are passed to the apply method.
	 * </p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to apply actions and conditions with null values in different
	 * combinations.
	 * <p><b>Expected:</b></p>
	 * No changes should be made to the tree elements when null consumer or null
	 * predicate is passed. The original element names should remain unchanged.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session by API Transformation Process using the
	 * 	previous assembled tree;</li>
	 * 	<li>Try to apply with a null consumer and a valid condition that checks
	 * 	if directory name starts with "Photo";</li>
	 * 	<li>Verify that the (Photoshop) element name remains unchanged since the
	 * 	action is null;</li>
	 * 	<li>Try to apply with a valid action and a condition that returns null;
	 * 	</li>
	 * 	<li>Verify that the (Photoshop) element name remains unchanged since the
	 * 	condition returns null;</li>
	 * 	<li>Try to apply with a valid action and a null predicate;</li>
	 * 	<li>Verify that the (Photoshop) element name remains unchanged since the
	 * 	predicate is null.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void apply_nullActionAndNullCondition() throws TreeException {
		final String sessionId = "apply_nullActionAndNullCondition";
		final long photoshopId = 909443L;

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Collection<Directory> directoryTree = TreeAssembler.getDirectoryTree();

		transaction.initializeSession(sessionId, directoryTree);

		Consumer<Element<Directory>> nullConsumer = null;
		manager.apply(
			nullConsumer,
			element -> directoryNameStartsWithPhoto(element)
		);

		/*
		 * The name keeps the same, as the action is null so it does nothing.
		 */
		Element<Directory> photoshop = manager.getElementById(photoshopId);
		assertEquals("Photoshop", photoshop.unwrap().getName());
		assertEquals("photoshop.exe", photoshop.getChildren().iterator()
				.next().unwrap().getName());

		manager.apply(
			element -> applyUpperCaseDirectoryName(element),
			element -> nullCondition()
		);

		photoshop = manager.getElementById(photoshopId);

		/*
		 * The name keeps the same, as the condition has a predicate inside that
		 * returns null and does nothing.
		 */
		assertEquals("Photoshop", photoshop.unwrap().getName());
		assertEquals("photoshop.exe", photoshop.getChildren().iterator()
				.next().unwrap().getName());

		Predicate<Element<Directory>> nullPredicate = null;
		manager.apply(
			element -> applyUpperCaseDirectoryName(element),
			nullPredicate
		);

		photoshop = manager.getElementById(photoshopId);

		/*
		 * The name keeps the same, as the condition is null and does nothing.
		 */
		assertEquals("Photoshop", photoshop.unwrap().getName());
		assertEquals("photoshop.exe", photoshop.getChildren().iterator()
				.next().unwrap().getName());
	}
}