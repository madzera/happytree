package com.madzera.happytree.element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.junit.Test;

import com.madzera.happytree.Element;
import com.madzera.happytree.TreeManager;
import com.madzera.happytree.TreeTransaction;
import com.madzera.happytree.common.TreeCommonTestHelper;
import com.madzera.happytree.core.HappyTree;
import com.madzera.happytree.core.TreeUnitTestHelper;
import com.madzera.happytree.demo.model.Directory;
import com.madzera.happytree.demo.util.TreeAssembler;
import com.madzera.happytree.exception.TreeException;

/**
 * Test class for {@link Element} operations.
 * 
 * <p>This test class represents the <i>alternative scenarios</i> for the
 * operations of {@link Element}.</p>
 * 
 * @author Diego Madson de Andrade Nóbrega
 *
 */
public class ElementAlternativeTest extends TreeCommonTestHelper {

	/**
	 * Test for the {@link Element#setId(Object)} and {@link Element#getId()}.
	 * 
	 * <p>Alternative scenario for this operation when trying to set an element
	 * Id with a <code>null</code> value.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to set an element Id with a <code>null</code> value.
	 * <p><b>Expected:</b></p>
	 * Receive the same id even after invoking {@link Element#setId(Object)}.
	 * The id of element only changes after a call for
	 * {@link TreeManager#updateElement(Element)}
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session;</li>
	 * 	<li>Create a detached element using not <code>null</code> for both
	 * 	identifiers;</li>
	 * 	<li>Verify if the identifiers are not <code>null</code>;</li>
	 * 	<li>Invoke {@link Element#setId(Object)} with <code>null</code> value;
	 * 	</li>
	 * 	<li>Verify if the element Id is <code>null</code> and parent Id is not
	 * 	<code>null</code>.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void setId_nullArg() throws TreeException {
		final String sessionId = "setId";
		final Date nullableId = null;
		
		final Date elementId = new Date();
		final Date randomParentId = new Date();
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionId, Directory.class);
		
		Element<Directory> element = manager.createElement(elementId,
				randomParentId, null);
		
		assertNotNull(element);
		assertNotNull(element.getParent());
		assertNotNull(element.getId());
		
		/*
		 * At the Element detached context, it is allowed accept null Id because
		 * it only changes after an update operation.
		 */
		element.setId(nullableId);
		assertNotNull(element.getId());
		assertNotNull(element.getParent());
	}
	
	/**
	 * Test for the {@link Element#setParent(Object)}
	 * and {@link Element#getParent()}.
	 * 
	 * <p>Alternative scenario for this operation when trying to set a parent Id
	 * with a <code>null</code> value.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to set a parent Id with a <code>null</code> value.
	 * <p><b>Expected:</b></p>
	 * Receive a <code>null</code> value when trying to get the parent Id.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session;</li>
	 * 	<li>Create a detached element using not <code>null</code> for both
	 * 	identifiers;</li>
	 * 	<li>Verify if the identifiers are not <code>null</code>;</li>
	 * 	<li>Invoke {@link Element#setParent(Object)} with <code>null</code>
	 * 	value;</li>
	 * 	<li>Verify if the element Id is not <code>null</code> and parent Id is
	 * 	<code>null</code>.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void setParent_nullArg() throws TreeException {
		final String sessionId = "setParent";
		final Long elementId = 100L;
		final Long parentId = 101L;
		
		final Long nullableParentId = null;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionId, Directory.class);
		
		Element<Directory> element = manager.createElement(elementId,
				parentId, null);
		
		assertNotNull(element);
		assertNotNull(element.getId());
		assertNotNull(element.getParent());
		
		element.setParent(nullableParentId);
		assertNotNull(element.getId());
		assertNull(element.getParent());
	}
	
	/**
	 * Test for the {@link Element#addChildren(Collection)} operation.
	 * 
	 * <p>Alternative scenario for this operation when trying to add children
	 * with a <code>null</code> or empty collection.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to add children with a <code>null</code> or empty collection.
	 * <p><b>Expected:</b></p>
	 * The children list remains empty after trying to add children with a
	 * <code>null</code> or empty collection.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session;</li>
	 * 	<li>Create a new element;</li>
	 * 	<li>Persist the new element created in the previous step;</li>
	 * 	<li>Try to add children with a <code>null</code> collection;</li>
	 * 	<li>Confirm that the children list is empty;</li>
	 * 	<li>Try to add children with an empty collection;</li>
	 * 	<li>Confirm that the children list of the new element is empty again.
	 * 	</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void addChildren_nullEmptyArg() throws TreeException {
		final String sessionId = "addChildren_nullEmptyArg";
		final Byte elementId = 1;
		final Byte parentId = 2;

		final int expectedChildrenSize = 0;
		final Collection<Element<Directory>> nullChild = null;
		final Collection<Element<Directory>> emptyChild = Arrays.asList();

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		transaction.initializeSession(sessionId, Directory.class);
		Element<Directory> element = manager.createElement(elementId, parentId,
				null);
		element = manager.persistElement(element);

		element.addChildren(nullChild);
		assertEquals(expectedChildrenSize, element.getChildren().size());
		element.addChildren(emptyChild);
		assertEquals(expectedChildrenSize, element.getChildren().size());
	}

	/**
	 * Test for the {@link Element#removeChild(Element)} and
	 * {@link Element#addChild(Element)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to remove an
	 * element with a <code>null</code> value.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to remove an element with a <code>null</code> value.
	 * <p><b>Expected:</b></p>
	 * One element inside the children list after adding two elements, one valid
	 * and the other is <code>null</code>, even after removing the
	 * <code>null</code> element.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session;</li>
	 * 	<li>Create three elements, one parent and two children, with one of them
	 * 	with a <code>null</code> value;</li>
	 * 	<li>Verify if the children list has only one element;</li>
	 * 	<li>Try to remove the <code>null</code> element;</li>
	 * 	<li>Verify if the children list has only one element again.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void removeChild_nullArgElement() throws TreeException {
		final String sessionId = "removeChild_nullArgElement";
		
		final Double elementId = BigDecimal.ONE.doubleValue();
		final Double childId = (double) Integer.MIN_VALUE;
		
		final int beforeRemove = 1;
		final int afterRemove = 1;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionId, Directory.class);
		
		Element<Directory> element = manager.createElement(elementId, null,
				null);
		Element<Directory> child = manager.createElement(childId, null, null);
		Element<Directory> nullChild = null;
		
		element.addChild(child);
		element.addChild(nullChild);
		assertEquals(beforeRemove, element.getChildren().size());
		element.removeChild(nullChild);
		assertEquals(afterRemove, element.getChildren().size());
	}
	
	/**
	 * Test for the {@link Element#removeChild(Object)} and
	 * {@link Element#addChild(Element)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to remove an
	 * element with a <code>null</code> Id value.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to remove an element with a <code>null</code> Id value.
	 * <p><b>Expected:</b></p>
	 * One element inside the children list after adding two elements, one valid
	 * and the other is <code>null</code>, even after trying to remove with a
	 * <code>null</code> Id.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session;</li>
	 * 	<li>Create three elements, one parent and two children, with one of them
	 * 	with a <code>null</code> value;</li>
	 * 	<li>Verify if the children list has only one element;</li>
	 * 	<li>Try to remove by invoking {@link Element#removeChild(Object)} with
	 * 	a <code>null</code> Id;</li>
	 * 	<li>Verify if the children list has only one element again.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void removeChild_nullArgId() throws TreeException {
		final String sessionId = "removeChild_nullArgId";
		
		final Double elementId = BigDecimal.ONE.doubleValue();
		final Double childId = (double) Integer.MIN_VALUE;
		final Double nullableChildId = null;
		
		final int beforeRemove = 1;
		final int afterRemove = 1;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionId, Directory.class);
		
		Element<Directory> element = manager.createElement(elementId, null,
				null);
		Element<Directory> child = manager.createElement(childId, null, null);
		Element<Directory> nullChild = null;
		
		element.addChild(child);
		element.addChild(nullChild);
		assertEquals(beforeRemove, element.getChildren().size());
		element.removeChild(nullableChildId);
		assertEquals(afterRemove, element.getChildren().size());
	}
	
	/**
	 * Test for the {@link Element#removeChild(Element)} operation.
	 * 
	 * <p>Alternative scenario for this operation when trying to remove an
	 * element that does not exist into the children list.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to remove an element that does not exist into the children list.
	 * <p><b>Expected:</b></p>
	 * One element inside the children list after adding two elements, one valid
	 * and the other that does not exist into the children list, even after
	 * trying to remove the not existing element.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session;</li>
	 * 	<li>Create three elements, one parent and two children, with one of them
	 * 	that does not exist into the children list;</li>
	 * 	<li>Verify if the children list has only one element;</li>
	 * 	<li>Try to remove the not existing element;</li>
	 * 	<li>Verify if the children list has only one element again.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void removeChild_notExistingChild() throws TreeException {
		final String sessionId = "removeChild_notExistingChild";

		final Integer elementId = Integer.MAX_VALUE;
		final Integer childId = Integer.MIN_VALUE;
		final Integer notExistingChildId = 0;

		final int beforeRemove = 1;
		final int afterRemove = 1;

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		transaction.initializeSession(sessionId, Directory.class);

		Element<Directory> element = manager.createElement(elementId, null,
				null);
		Element<Directory> child = manager.createElement(childId, null, null);
		Element<Directory> notExistingChild = manager.createElement(
				notExistingChildId, null, null);

		element.addChild(child);
		assertEquals(beforeRemove, element.getChildren().size());
		element.removeChild(notExistingChild);
		assertEquals(afterRemove, element.getChildren().size());
	}
	
	/**
	 * Test for the {@link Element#wrap(Object)} and {@link Element#unwrap()}.
	 * 
	 * <p>Alternative scenario for this operation when trying to set a
	 * <code>null</code> wrapped node.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to set a wrapped node with <code>null</code> value.
	 * <p><b>Expected:</b></p>
	 * Receive a <code>null</code> value when trying to unwrap the object.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Set a <code>null</code> {@link Directory} object;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session;</li>
	 * 	<li>Create an element;</li>
	 * 	<li>Wrap the <code>null</code> object inside of the element;</li>
	 * 	<li>Unwrap the object node from the element;</li>
	 * 	<li>Verify if the object is <code>null</code>.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void unwrap_nullWrappedNode() throws TreeException {
		final String sessionId = "unwrap_nullWrappedNode";
		final long elementId = Integer.MAX_VALUE;
		Directory nullableDirectory = null;

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		transaction.initializeSession(sessionId, Directory.class);
		Element<Directory> element = manager.createElement(elementId, null,
				null);

		element.wrap(nullableDirectory);

		Directory wrappedDirectory = element.unwrap();
		assertNull(wrappedDirectory);
	}

	@Test
	public void unwrap_changedNode_wrap() throws TreeException {
		final String sessionId = "unwrap_changedNode_wrap";
		final long adobeId = 24935L;

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Collection<Directory> directoryTree = TreeAssembler.getDirectoryTree();

		transaction.initializeSession(sessionId, directoryTree);

		Element<Directory> adobe = manager.getElementById(adobeId);

		Directory beforeUpdate = adobe.unwrap();
		beforeUpdate.setName("Adobe Inc.");
		Directory afterUpdate = beforeUpdate;

		adobe = manager.getElementById(adobeId);
		assertEquals("Adobe", adobe.unwrap().getName());

		adobe.wrap(afterUpdate);

		manager.updateElement(adobe);

		adobe = manager.getElementById(adobeId);
		assertEquals("Adobe Inc.", adobe.unwrap().getName());
	}
	
	/**
	 * Test for the {@link Element#unwrap()} operation.
	 * 
	 * <p>Test for the {@code TestUtil} class that assists the element to store
	 * a deep cloned instance of the wrapped object node.</p>
	 * 
	 * <p>This test is a mock test to cover unreachable code. It uses the
	 * {@link TreeUnitTestHelper} class to access an internal method of the
	 * {@code TestUtil} class.</p>
	 */
	@Test
	public void unwrap_deepCopyObject() {
		final String fqn = "com.madzera.happytree.core.TreeUtil";

		try {
			boolean result = TreeUnitTestHelper.executeInternalMethod(fqn,
					"runMock");
			assertEquals(Boolean.TRUE, result);
		} catch (ReflectiveOperationException e) {
			assertThrows(TreeException.class, () -> {
				throw new TreeException();
			});
		}
	}

	/**
	 * Test for the {@link Element#toJSON()} operation.
	 * 
	 * <p>Alternative scenario for this operation when trying to print the JSON
	 * element when the element has a <code>null</code> wrapped node.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to print the JSON element when the wrapped original object is
	 * <code>null</code>.
	 * <p><b>Expected:</b></p>
	 * The JSON representation should be an empty object &quot;{}&quot;.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session;</li>
	 * 	<li>Create a new element without a wrapped object;</li>
	 * 	<li>Convert the element to JSON;</li>
	 * 	<li>Confirm that the JSON representation is an empty object
	 * 	&quot;{}&quot;.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void toJSON_nullWrappedNode() throws TreeException {
		final String json = "{}";

		final String sessionId = "toJson_nullWrappedNode";
		final long elementId = Integer.MAX_VALUE;
		Directory nullableDirectory = null;

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		transaction.initializeSession(sessionId, Directory.class);
		Element<Directory> element = manager.createElement(elementId, null,
				nullableDirectory);

		String jsonOutput = element.toJSON();
		assertEquals(json, jsonOutput);
	}
	
	/**
	 * Test for the {@link Element#toJSON()} operation.
	 * 
	 * <p>Alternative scenario for this operation when trying to print the JSON
	 * element when there is a child in the hierarchy that have a
	 * <code>null</code> wrapped object node.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to print the JSON element which exists a child in the hierarchy that
	 * have a <code>null</code> wrapped object node.
	 * <p><b>Expected:</b></p>
	 * The JSON representation should be an empty object &quot;{}&quot;.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session;</li>
	 * 	<li>Create a new element (Illustrator) and its child (Illustrator.exe) -
	 * 	with a <code>null</code> object node - to be added inside of (Adobe)
	 * 	element;</li>
	 * 	<li>Persist the new element (Illustrator) with its child
	 * 	(Illustrator.exe) which does not have an object node;</li>
	 * 	<li>Add the new element (Illustrator) with its child (Illustrator.exe)
	 * 	to (Adobe) element;</li>
	 * 	<li>Update the (Adobe) element;</li>
	 * 	<li>Confirm that the JSON representation is an empty object
	 * 	&quot;{}&quot;.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void toJSON_nullWrappedChildNode() throws TreeException {
		final String json = "{}";

		final String sessionId = "toJson_nullWrappedChildNode";
		final long adobeId = 24935L;
		final long illustratorId = 2378472L;
		final long illustratorExeId = 2378473L;
		final long programFilesId = 42345L;
	
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
	
		Directory illustratorDir = new Directory(illustratorId, programFilesId,
				"Illustrator");
		Collection<Directory> directoryTree = TreeAssembler.getDirectoryTree();
	
		transaction.initializeSession(sessionId, directoryTree);

		Element<Directory> illustrator = manager.createElement(illustratorId,
				adobeId, illustratorDir);
		Element<Directory> illustratorExe = manager.createElement(
				illustratorExeId, illustratorId, null);
		
		illustrator.addChild(illustratorExe);

		illustrator = manager.persistElement(illustrator);

		Element<Directory> adobe = manager.getElementById(adobeId);
		adobe.addChild(illustrator);

		adobe = manager.updateElement(adobe);

		String jsonOutput = adobe.toJSON();
		assertEquals(json, jsonOutput);
	}
	
	/**
	 * Test for the {@link Element#toJSON()} operation.
	 * 
	 * <p>Alternative scenario for this operation when trying to print the JSON
	 * element after adding and removing a child element.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to print the JSON element after adding and removing a child element.
	 * <p><b>Expected:</b></p>
	 * The JSON representation should reflect the current state of the element.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Declare the expected JSON from the (Adobe) element through the
	 * 	{@link TreeAssembler#getDirectoryTree()} representing the current state
	 * 	of this element;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session;</li>
	 * 	<li>Create a new element (Illustrator) and its child (Illustrator.exe)
	 * 	to be added inside of (Adobe) element;</li>
	 * 	<li>Persist the new element (Illustrator) with its child
	 * 	(Illustrator.exe);</li>
	 * 	<li>Before adding the new element (Illustrator) to (Adobe) element,
	 * 	prove that the JSON representation reflects the current state of (Adobe)
	 * 	element;</li>
	 * 	<li>Add the new element (Illustrator) to (Adobe) element;</li>
	 * 	<li>Before updating the (Adobe) element, confirm that the JSON
	 * 	representation doesn’t reflect the current state of the element, proving
	 * 	that the {@link Element#toJSON()} method does not depend on the
	 * 	element's state.</li>
	 * 	<li>Update the (Adobe) element;</li>
	 * 	<li>Confirm that the JSON representation still doesn’t reflect the
	 * 	current state of the (Adobe) element;</li>
	 * 	<li>Remove the new element (Illustrator) from (Adobe) element;</li>
	 * 	<li>Before updating the (Adobe) element, confirm that the JSON
	 * 	representation reflects the current state of the element, proving
	 * 	that the {@link Element#toJSON()} method does not depend on the
	 * 	element's state.</li>
	 * 	<li>Update the (Adobe) element;</li>
	 * 	<li>Confirm that the JSON representation reflects the current state of
	 * 	the (Adobe) element;</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void toJSON_addRemoveElement() throws TreeException {
		final String json = "{\"identifier\":24935,\"parentIdentifier\":42345,"
				+ "\"name\":\"Adobe\",\"children\":[{\"identifier\":502010,"
				+ "\"parentIdentifier\":24935,\"name\":\"Dreamweaver\","
				+ "\"children\":[{\"identifier\":8935844,"
				+ "\"parentIdentifier\":502010,\"name\":\"dreamweaver.exe\","
				+ "\"children\":[]}]},{\"identifier\":909443,"
				+ "\"parentIdentifier\":24935,\"name\":\"Photoshop\","
				+ "\"children\":[{\"identifier\":4950243,"
				+ "\"parentIdentifier\":909443,\"name\":\"photoshop.exe\","
				+ "\"children\":[]}]},{\"identifier\":403940,"
				+ "\"parentIdentifier\":24935,\"name\":\"Reader\","
				+ "\"children\":[{\"identifier\":8493845,"
				+ "\"parentIdentifier\":403940,\"name\":\"reader.exe\","
				+ "\"children\":[]}]}]}";

		final String sessionId = "toJson_addRemoveElement";
		final long adobeId = 24935L;
		final long illustratorId = 2378472L;
		final long illustratorExeId = 2378473L;
		final long programFilesId = 42345L;

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Directory illustratorDir = new Directory(illustratorId, programFilesId,
				"Illustrator");
		Directory illustratorExeDir = new Directory(illustratorExeId,
				illustratorId, "Illustrator.exe");
		Collection<Directory> directoryTree = TreeAssembler.getDirectoryTree();

		transaction.initializeSession(sessionId, directoryTree);

		Element<Directory> adobe = manager.getElementById(adobeId);
		String jsonOutput = adobe.toJSON();
		assertEquals(json, jsonOutput);

		Element<Directory> illustrator = manager.createElement(
				illustratorId, programFilesId, illustratorDir);
		Element<Directory> illustratorExe = manager.createElement(
				illustratorExeId, illustratorId, illustratorExeDir);
		illustrator.addChild(illustratorExe);

		illustrator = manager.persistElement(illustrator);

		adobe.addChild(illustrator);
		jsonOutput = adobe.toJSON();
		assertNotEquals(json, jsonOutput);

		adobe = manager.updateElement(adobe);
		jsonOutput = adobe.toJSON();
		assertNotEquals(json, jsonOutput);

		adobe.removeChild(illustrator);
		jsonOutput = adobe.toJSON();
		assertEquals(json, jsonOutput);

		adobe = manager.updateElement(adobe);
		jsonOutput = adobe.toJSON();
		assertEquals(json, jsonOutput);
	}
	
	/**
	 * Test for the {@link Element#toXML()} operation.
	 * 
	 * <p>Alternative scenario for this operation when trying to print the XML
	 * when the element has a <code>null</code> wrapped node.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to print the XML element when the wrapped original object is
	 * <code>null</code>.
	 * <p><b>Expected:</b></p>
	 * The XML content should be an empty tag <pre>&lt;element/&gt;</pre>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session;</li>
	 * 	<li>Create a new element without a wrapped object;</li>
	 * 	<li>Convert the element to XML;</li>
	 * 	<li>Confirm that the XML content is an empty tag.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void toXML_nullWrappedNode() throws TreeException {
		final String xml = "<element/>";

		final String sessionId = "toXML_nullWrappedNode";
		final long elementId = Integer.MAX_VALUE;
		Directory nullableDirectory = null;

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		transaction.initializeSession(sessionId, Directory.class);
		Element<Directory> element = manager.createElement(elementId, null,
				nullableDirectory);

		String xmlOutput = element.toXML();
		assertEquals(xml, xmlOutput);
	}

	/**
	 * Test for the {@link Element#toXML()} operation.
	 * 
	 * <p>Alternative scenario for this operation when trying to print the XML
	 * element when there is a child in the hierarchy that have a
	 * <code>null</code> wrapped object node.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to print the XML element which exists a child in the hierarchy that
	 * have a <code>null</code> wrapped object node.
	 * <p><b>Expected:</b></p>
	 * The XML content should be an empty tag <pre>&lt;element/&gt;</pre>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session;</li>
	 * 	<li>Create a new element (Illustrator) and its child (Illustrator.exe) -
	 * 	with a <code>null</code> object node - to be added inside of (Adobe)
	 * 	element;</li>
	 * 	<li>Persist the new element (Illustrator) with its child
	 * 	(Illustrator.exe) which does not have an object node;</li>
	 * 	<li>Add the new element (Illustrator) with its child (Illustrator.exe)
	 * 	to (Adobe) element;</li>
	 * 	<li>Update the (Adobe) element;</li>
	 * 	<li>Confirm that the XML content is an empty tag.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void toXML_nullWrappedChildNode() throws TreeException {
		final String xml = "<element/>";

		final String sessionId = "toXML_nullWrappedChildNode";
		final long adobeId = 24935L;
		final long illustratorId = 2378472L;
		final long illustratorExeId = 2378473L;
		final long programFilesId = 42345L;

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Directory illustratorDir = new Directory(illustratorId, programFilesId,
				"Illustrator");
		Collection<Directory> directoryTree = TreeAssembler.getDirectoryTree();

		transaction.initializeSession(sessionId, directoryTree);

		Element<Directory> illustrator = manager.createElement(illustratorId,
				adobeId, illustratorDir);
		Element<Directory> illustratorExe = manager.createElement(
				illustratorExeId, illustratorId, null);

		illustrator.addChild(illustratorExe);

		illustrator = manager.persistElement(illustrator);

		Element<Directory> adobe = manager.getElementById(adobeId);
		adobe.addChild(illustrator);

		adobe = manager.updateElement(adobe);

		String xmlOutput = adobe.toXML();
		assertEquals(xml, xmlOutput);
	}
	
	/**
	 * Test for the {@link Element#toXML()} operation.
	 * 
	 * <p>Alternative scenario for this operation when trying to print the XML
	 * element after adding and removing a child element.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to print the XML element after adding and removing a child element.
	 * <p><b>Expected:</b></p>
	 * The XML content should reflect the current state of the element.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Declare the expected XML from the (Adobe) element through the
	 * 	{@link TreeAssembler#getDirectoryTree()} representing the current state
	 * 	of this element;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session;</li>
	 * 	<li>Create a new element (Illustrator) and its child (Illustrator.exe)
	 * 	to be added inside of (Adobe) element;</li>
	 * 	<li>Persist the new element (Illustrator) with its child
	 * 	(Illustrator.exe);</li>
	 * 	<li>Before adding the new element (Illustrator) to (Adobe) element,
	 * 	prove that the XML content reflects the current state of (Adobe)
	 * 	element;</li>
	 * 	<li>Add the new element (Illustrator) to (Adobe) element;</li>
	 * 	<li>Before updating the (Adobe) element, confirm that the XML content
	 * 	doesn’t reflect the current state of the element, proving
	 * 	that the {@link Element#toXML()} method does not depend on the
	 * 	element's state.</li>
	 * 	<li>Update the (Adobe) element;</li>
	 * 	<li>Confirm that the XML content still doesn’t reflect the current state
	 * 	of the (Adobe) element;</li>
	 * 	<li>Remove the new element (Illustrator) from (Adobe) element;</li>
	 * 	<li>Before updating the (Adobe) element, confirm that the XML content
	 * 	reflects the current state of the element, proving
	 * 	that the {@link Element#toXML()} method does not depend on the
	 * 	element's state.</li>
	 * 	<li>Update the (Adobe) element;</li>
	 * 	<li>Confirm that the XML content reflects the current state of
	 * 	the (Adobe) element;</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void toXML_addRemoveElement() throws TreeException {
		final String xml = "<element><identifier>24935</identifier>" +
				"<parentIdentifier>42345</parentIdentifier><name>Adobe</name>" +
				"<children><element><identifier>502010</identifier>" +
				"<parentIdentifier>24935</parentIdentifier><name>Dreamweaver" +
				"</name><children><element><identifier>8935844</identifier>" +
				"<parentIdentifier>502010</parentIdentifier>" +
				"<name>dreamweaver.exe</name><children/></element>" +
				"</children></element><element><identifier>909443" +
				"</identifier><parentIdentifier>24935</parentIdentifier>" +
				"<name>Photoshop</name><children><element>" +
				"<identifier>4950243</identifier><parentIdentifier>909443" +
				"</parentIdentifier><name>photoshop.exe</name><children/>" +
				"</element></children></element><element>" +
				"<identifier>403940</identifier><parentIdentifier>24935" +
				"</parentIdentifier><name>Reader</name><children><element>" +
				"<identifier>8493845</identifier><parentIdentifier>403940" +
				"</parentIdentifier><name>reader.exe</name><children/>" +
				"</element></children></element></children></element>";

		final String sessionId = "toXML_addRemoveElement";
		final long adobeId = 24935L;
		final long illustratorId = 2378472L;
		final long illustratorExeId = 2378473L;
		final long programFilesId = 42345L;

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Directory illustratorDir = new Directory(illustratorId, programFilesId,
				"Illustrator");
		Directory illustratorExeDir = new Directory(illustratorExeId,
				illustratorId, "Illustrator.exe");
		Collection<Directory> directoryTree = TreeAssembler.getDirectoryTree();

		transaction.initializeSession(sessionId, directoryTree);

		Element<Directory> adobe = manager.getElementById(adobeId);
		String xmlOutput = adobe.toXML();
		assertEquals(xml, xmlOutput);

		Element<Directory> illustrator = manager.createElement(
				illustratorId, programFilesId, illustratorDir);
		Element<Directory> illustratorExe = manager.createElement(
				illustratorExeId, illustratorId, illustratorExeDir);
		illustrator.addChild(illustratorExe);

		illustrator = manager.persistElement(illustrator);

		adobe.addChild(illustrator);
		xmlOutput = adobe.toXML();
		assertNotEquals(xml, xmlOutput);

		adobe = manager.updateElement(adobe);
		xmlOutput = adobe.toXML();
		assertNotEquals(xml, xmlOutput);

		adobe.removeChild(illustrator);
		xmlOutput = adobe.toXML();
		assertEquals(xml, xmlOutput);

		adobe = manager.updateElement(adobe);
		xmlOutput = adobe.toXML();
		assertEquals(xml, xmlOutput);
	}

	/**
	 * Test for the {@link Element#toJSON()} operation.
	 * 
	 * <p>Alternative scenario for this operation when trying to print the JSON
	 * element from the root element.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Print the JSON element from the root element.
	 * <p><b>Expected:</b></p>
	 * A JSON string representing the root element.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Declare the expected JSON from the root element through the
	 * 	{@link TreeAssembler#getDirectoryTree()};</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session;</li>
	 * 	<li>Get the element which represents the root element through the
	 * 	{@link TreeManager#root()};</li>
	 * 	<li>Convert this element into JSON format by invoking
	 * 	{@link Element#toJSON()};</li>
	 * 	<li>Compare the resulting JSON string with the expected.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void toJSON_rootElement() throws TreeException {
		final String sessionId = "toJSON_rootElement";

		final String json = "{\"children\":[{\"identifier\":38923," +
				"\"parentIdentifier\":0,\"name\":\"Users\",\"children\":[" +
				"{\"identifier\":47592,\"parentIdentifier\":38923," +
				"\"name\":\"administrator\",\"children\":[]}," +
				"{\"identifier\":48224,\"parentIdentifier\":38923," +
				"\"name\":\"foo\",\"children\":[{\"identifier\":583950," +
				"\"parentIdentifier\":48224,\"name\":\"tmp\"," +
				"\"children\":[]}]}]},{\"identifier\":93832," +
				"\"parentIdentifier\":0,\"name\":\"Devel\",\"children\":[" +
				"{\"identifier\":45930,\"parentIdentifier\":93832," +
				"\"name\":\"database\",\"children\":[]}," +
				"{\"identifier\":84709,\"parentIdentifier\":93832," +
				"\"name\":\"sdk_dev\",\"children\":[{\"identifier\":983533," +
				"\"parentIdentifier\":84709,\"name\":\"jdk1.6\"," +
				"\"children\":[]}]},{\"identifier\":13823," +
				"\"parentIdentifier\":93832,\"name\":\"ide\",\"children\":[" +
				"{\"identifier\":583852,\"parentIdentifier\":13823," +
				"\"name\":\"eclipse\",\"children\":[{\"identifier\":8483742," +
				"\"parentIdentifier\":583852,\"name\":\"eclipse.exe\"," +
				"\"children\":[]}]},{\"identifier\":482043," +
				"\"parentIdentifier\":13823,\"name\":\"netbeans\"," +
				"\"children\":[{\"identifier\":4859304," +
				"\"parentIdentifier\":482043,\"name\":\"netbeans.exe\"," +
				"\"children\":[]}]}]},{\"identifier\":93209," +
				"\"parentIdentifier\":93832,\"name\":\"projects\"," +
				"\"children\":[{\"identifier\":859452," +
				"\"parentIdentifier\":93209,\"name\":\"happytree\"," +
				"\"children\":[]}]}]},{\"identifier\":42345," +
				"\"parentIdentifier\":0,\"name\":\"Program Files\"," +
				"\"children\":[{\"identifier\":53024," +
				"\"parentIdentifier\":42345,\"name\":\"Office\"," +
				"\"children\":[{\"identifier\":674098," +
				"\"parentIdentifier\":53024,\"name\":\"Word\"," +
				"\"children\":[{\"identifier\":4611329," +
				"\"parentIdentifier\":674098,\"name\":\"word.exe\"," +
				"\"children\":[]}]},{\"identifier\":843566," +
				"\"parentIdentifier\":53024,\"name\":\"Excel\"," +
				"\"children\":[{\"identifier\":3964602," +
				"\"parentIdentifier\":843566,\"name\":\"excel.exe\"," +
				"\"children\":[]}]}]},{\"identifier\":94034," +
				"\"parentIdentifier\":42345,\"name\":\"Realtek\"," +
				"\"children\":[{\"identifier\":495833," +
				"\"parentIdentifier\":94034,\"name\":\"readme.txt\"," +
				"\"children\":[]},{\"identifier\":113009," +
				"\"parentIdentifier\":94034,\"name\":\"sdk\"," +
				"\"children\":[{\"identifier\":8484934," +
				"\"parentIdentifier\":113009,\"name\":\"files\"," +
				"\"children\":[]}]},{\"identifier\":220332," +
				"\"parentIdentifier\":94034,\"name\":\"drivers\"," +
				"\"children\":[{\"identifier\":7753032," +
				"\"parentIdentifier\":220332,\"name\":\"bin\"," +
				"\"children\":[{\"identifier\":77530344," +
				"\"parentIdentifier\":7753032,\"name\":\"entry\"," +
				"\"children\":[]}]}]}]},{\"identifier\":32099," +
				"\"parentIdentifier\":42345,\"name\":\"Winamp\"," +
				"\"children\":[{\"identifier\":395524," +
				"\"parentIdentifier\":32099,\"name\":\"winamp.exe\"," +
				"\"children\":[]}]},{\"identifier\":10239," +
				"\"parentIdentifier\":42345,\"name\":\"VLC\"," +
				"\"children\":[{\"identifier\":848305," +
				"\"parentIdentifier\":10239,\"name\":\"recorded\"," +
				"\"children\":[{\"identifier\":1038299," +
				"\"parentIdentifier\":848305,\"name\":\"5093049239.mp4\"," +
				"\"children\":[]},{\"identifier\":3840200," +
				"\"parentIdentifier\":848305,\"name\":\"4959344545.mp4\"," +
				"\"children\":[]}]}]},{\"identifier\":24935," +
				"\"parentIdentifier\":42345,\"name\":\"Adobe\"," +
				"\"children\":[{\"identifier\":502010," +
				"\"parentIdentifier\":24935,\"name\":\"Dreamweaver\"," +
				"\"children\":[{\"identifier\":8935844," +
				"\"parentIdentifier\":502010,\"name\":\"dreamweaver.exe\"," +
				"\"children\":[]}]},{\"identifier\":909443," +
				"\"parentIdentifier\":24935,\"name\":\"Photoshop\"," +
				"\"children\":[{\"identifier\":4950243," +
				"\"parentIdentifier\":909443,\"name\":\"photoshop.exe\"," +
				"\"children\":[]}]},{\"identifier\":403940," +
				"\"parentIdentifier\":24935,\"name\":\"Reader\"," +
				"\"children\":[{\"identifier\":8493845," +
				"\"parentIdentifier\":403940,\"name\":\"reader.exe\"," +
				"\"children\":[]}]}]}]}]}";

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Collection<Directory> directoryTree = TreeAssembler.getDirectoryTree();

		transaction.initializeSession(sessionId, directoryTree);

		Element<Directory> root = manager.root();
		String jsonOutput = root.toJSON();

		assertEquals(json, jsonOutput);
	}

	/**
	 * Test for the {@link Element#toXML()} operation.
	 * 
	 * <p>Alternative scenario for this operation when trying to print the XML
	 * content from the root element.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Print the XML content from the root element.
	 * <p><b>Expected:</b></p>
	 * A XML content representing the root element.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Declare the expected XML from the root element through the
	 * 	{@link TreeAssembler#getDirectoryTree()};</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session;</li>
	 * 	<li>Get the element which represents the root element through the
	 * 	{@link TreeManager#root()};</li>
	 * 	<li>Convert this element into XML format by invoking
	 * 	{@link Element#toXML()};</li>
	 * 	<li>Compare the resulting XML content with the expected.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void toXML_rootElement() throws TreeException {
		final String sessionId = "toXML_rootElement";

		final String xml = "<element><children><element><identifier>38923" +
				"</identifier><parentIdentifier>0</parentIdentifier>" +
				"<name>Users</name><children><element><identifier>47592" +
				"</identifier><parentIdentifier>38923</parentIdentifier>" +
				"<name>administrator</name><children/></element><element>" +
				"<identifier>48224</identifier><parentIdentifier>38923" +
				"</parentIdentifier><name>foo</name><children><element>" +
				"<identifier>583950</identifier><parentIdentifier>48224" +
				"</parentIdentifier><name>tmp</name><children/></element>" +
				"</children></element></children></element><element>" +
				"<identifier>93832</identifier><parentIdentifier>0" +
				"</parentIdentifier><name>Devel</name><children><element>" +
				"<identifier>45930</identifier><parentIdentifier>93832" +
				"</parentIdentifier><name>database</name><children/>" +
				"</element><element><identifier>84709</identifier>" +
				"<parentIdentifier>93832</parentIdentifier><name>sdk_dev" +
				"</name><children><element><identifier>983533</identifier>" +
				"<parentIdentifier>84709</parentIdentifier><name>jdk1.6" +
				"</name><children/></element></children></element><element>" +
				"<identifier>13823</identifier><parentIdentifier>93832" +
				"</parentIdentifier><name>ide</name><children><element>" +
				"<identifier>583852</identifier><parentIdentifier>13823" +
				"</parentIdentifier><name>eclipse</name><children><element>" +
				"<identifier>8483742</identifier><parentIdentifier>583852" +
				"</parentIdentifier><name>eclipse.exe</name><children/>" +
				"</element></children></element><element><identifier>482043" +
				"</identifier><parentIdentifier>13823</parentIdentifier>" +
				"<name>netbeans</name><children><element><identifier>4859304" +
				"</identifier><parentIdentifier>482043</parentIdentifier>" +
				"<name>netbeans.exe</name><children/></element></children>" +
				"</element></children></element><element><identifier>93209" +
				"</identifier><parentIdentifier>93832</parentIdentifier>" +
				"<name>projects</name><children><element><identifier>859452" +
				"</identifier><parentIdentifier>93209</parentIdentifier>" +
				"<name>happytree</name><children/></element></children>" +
				"</element></children></element><element><identifier>42345" +
				"</identifier><parentIdentifier>0</parentIdentifier>" +
				"<name>Program Files</name><children><element>" +
				"<identifier>53024</identifier><parentIdentifier>42345" +
				"</parentIdentifier><name>Office</name><children><element>" +
				"<identifier>674098</identifier><parentIdentifier>53024" +
				"</parentIdentifier><name>Word</name><children><element>" +
				"<identifier>4611329</identifier><parentIdentifier>674098" +
				"</parentIdentifier><name>word.exe</name><children/>" +
				"</element></children></element><element><identifier>843566" +
				"</identifier><parentIdentifier>53024</parentIdentifier>" +
				"<name>Excel</name><children><element><identifier>3964602" +
				"</identifier><parentIdentifier>843566</parentIdentifier>" +
				"<name>excel.exe</name><children/></element></children>" +
				"</element></children></element><element><identifier>94034" +
				"</identifier><parentIdentifier>42345</parentIdentifier>" +
				"<name>Realtek</name><children><element><identifier>495833" +
				"</identifier><parentIdentifier>94034</parentIdentifier>" +
				"<name>readme.txt</name><children/></element><element>" +
				"<identifier>113009</identifier><parentIdentifier>94034" +
				"</parentIdentifier><name>sdk</name><children><element>" +
				"<identifier>8484934</identifier><parentIdentifier>113009" +
				"</parentIdentifier><name>files</name><children/></element>" +
				"</children></element><element><identifier>220332" +
				"</identifier><parentIdentifier>94034</parentIdentifier>" +
				"<name>drivers</name><children><element><identifier>7753032" +
				"</identifier><parentIdentifier>220332</parentIdentifier>" +
				"<name>bin</name><children><element><identifier>77530344" +
				"</identifier><parentIdentifier>7753032</parentIdentifier>" +
				"<name>entry</name><children/></element></children></element>" +
				"</children></element></children></element><element>" +
				"<identifier>32099</identifier><parentIdentifier>42345" +
				"</parentIdentifier><name>Winamp</name><children><element>" +
				"<identifier>395524</identifier><parentIdentifier>32099" +
				"</parentIdentifier><name>winamp.exe</name><children/>" +
				"</element></children></element><element><identifier>10239" +
				"</identifier><parentIdentifier>42345</parentIdentifier>" +
				"<name>VLC</name><children><element><identifier>848305" +
				"</identifier><parentIdentifier>10239</parentIdentifier>" +
				"<name>recorded</name><children><element><identifier>1038299" +
				"</identifier><parentIdentifier>848305</parentIdentifier>" +
				"<name>5093049239.mp4</name><children/></element><element>" +
				"<identifier>3840200</identifier><parentIdentifier>848305" +
				"</parentIdentifier><name>4959344545.mp4</name><children/>" +
				"</element></children></element></children></element>" +
				"<element><identifier>24935</identifier>" +
				"<parentIdentifier>42345</parentIdentifier><name>Adobe" +
				"</name><children><element><identifier>502010</identifier>" +
				"<parentIdentifier>24935</parentIdentifier>" +
				"<name>Dreamweaver</name><children><element>" +
				"<identifier>8935844</identifier><parentIdentifier>502010" +
				"</parentIdentifier><name>dreamweaver.exe</name><children/>" +
				"</element></children></element><element>" +
				"<identifier>909443</identifier><parentIdentifier>24935" +
				"</parentIdentifier><name>Photoshop</name><children>" +
				"<element><identifier>4950243</identifier>" +
				"<parentIdentifier>909443</parentIdentifier>" +
				"<name>photoshop.exe</name><children/></element></children>" +
				"</element><element><identifier>403940</identifier>" +
				"<parentIdentifier>24935</parentIdentifier><name>Reader" +
				"</name><children><element><identifier>8493845</identifier>" +
				"<parentIdentifier>403940</parentIdentifier>" +
				"<name>reader.exe</name><children/></element></children>" +
				"</element></children></element></children></element>" +
				"</children></element>";

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Collection<Directory> directoryTree = TreeAssembler.getDirectoryTree();

		transaction.initializeSession(sessionId, directoryTree);

		Element<Directory> root = manager.root();
		String xmlOutput = root.toXML();

		assertEquals(xml, xmlOutput);
	}
	
	/**
	 * Test for the {@link Element#apply(Consumer)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to apply a
	 * function that transforms the name of each element to upper case from the
	 * root element.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Apply a function to all elements within a tree. This function will be
	 * performed for each element within the tree from the root element. As an
	 * example, the name of each {@link Directory} element will be transformed
	 * to upper case.
	 * <p><b>Expected:</b></p>
	 * All elements within the tree must have their names in upper case (Adobe
	 * and Foo to be verified), except the root element because it has no
	 * wrapped object node.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session;</li>
	 * 	<li>Get the root element;</li>
	 * 	<li>Apply the function on the root element to upper case all elements
	 * 	within the tree (except the own root element);</li>
	 * 	<li>Update all children of root element (it is not allowed to handle
	 * 	root element directly);</li>
	 * 	<li>Get the element which represents the (Adobe) {@link Directory}
	 * 	element;</li>
	 * 	<li>Get the element which represents the (Foo) {@link Directory}
	 * 	element;</li>
	 * 	<li>Verify if both elements which are placed in different positions in
	 * 	the tree have upper case their names.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void apply_rootElement() throws TreeException {
		final String sessionId = "apply_rootElement";
		final Long adobeId = 24935L;
		final Long fooId = 48224L;

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Collection<Directory> directoryTree = TreeAssembler.getDirectoryTree();

		transaction.initializeSession(sessionId, directoryTree);

		Element<Directory> root = manager.root();

		root.apply(element -> applyUpperCaseDirectoryName(element));

		/*
		 * It is not allowed to handle root element directly, so update all
		 * children of root element.
		 */
		root.getChildren().forEach(element -> {
			try {
				manager.updateElement(element);
			} catch (TreeException e) {
				/* Ignored */
			}
		});
		
		Element<Directory> adobe = manager.getElementById(adobeId);
		Element<Directory> foo = manager.getElementById(fooId);

		/*
		 * Verify if the (Adobe) element and its children have upper case
		 * names.
		 */
		assertEquals("ADOBE", adobe.unwrap().getName());
		for (Element<Directory> child : adobe.getChildren()) {
			assertEquals(
					child.unwrap().getName().toUpperCase(),
					child.unwrap().getName());
			for (Element<Directory> grandChild : child.getChildren()) {
				assertEquals(
						grandChild.unwrap().getName().toUpperCase(),
						grandChild.unwrap().getName());
			}
		}

		/*
		 * Verify if the (Foo) element and its children have upper case
		 * names.
		 */
		assertEquals("FOO", foo.unwrap().getName());
		for (Element<Directory> child : foo.getChildren()) {
			assertEquals(
					child.unwrap().getName().toUpperCase(),
					child.unwrap().getName());
		}
	}

	/**
	 * Test for the {@link Element#apply(Consumer, Predicate)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to apply a
	 * function that transforms the name of each element to upper case given a
	 * certain condition. The search starts from the root element.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Apply a function to all elements within a tree given a certain condition.
	 * This function will be performed for each element within the tree from the
	 * root element, but only for those elements that match the condition. As an
	 * example, the name of each {@link Directory} element that satisfies the
	 * condition will be transformed to upper case.
	 * <p><b>Expected:</b></p>
	 * Only the (Adobe and foo) elements will have their names transformed into
	 * upper case.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session;</li>
	 * 	<li>Get the root element;</li>
	 * 	<li>Apply the function on the root element to upper case all elements
	 * 	within the tree that satisfy the condition (except the own root element);
	 * 	</li>
	 * 	<li>Update all children of root element (it is not allowed to handle
	 * 	root element directly);</li>
	 * 	<li>Get the element which represents the (Adobe) {@link Directory}
	 * 	element;</li>
	 * 	<li>Get the element which represents the (foo) {@link Directory}
	 * 	element;</li>
	 * 	<li>Verify if both elements which are placed in different positions in
	 * 	the tree have upper case their names and elements that don't match the
	 * 	condition remain unchanged.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void apply_withCondition_rootElement() throws TreeException {
		final String sessionId = "apply_withCondition_rootElement";
		final Long adobeId = 24935L;
		final Long fooId = 48224L;

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Collection<Directory> directoryTree = TreeAssembler.getDirectoryTree();

		transaction.initializeSession(sessionId, directoryTree);

		Element<Directory> root = manager.root();

		root.apply(element -> applyUpperCaseDirectoryName(element),
				element -> element.unwrap().getName().equals("Adobe") ||
						element.unwrap().getName().equals("foo"));

		root.getChildren().forEach(element -> {
			try {
				manager.updateElement(element);
			} catch (TreeException e) {
				/* Ignored */
			}
		});

		Element<Directory> adobe = manager.getElementById(adobeId);
		Element<Directory> foo = manager.getElementById(fooId);

		/*
		 * Verify if the (Adobe) children have NOT upper case names (only
		 * Adobe element was affected by the function).
		 */
		assertEquals("ADOBE", adobe.unwrap().getName());
		for (Element<Directory> child : adobe.getChildren()) {
			assertNotEquals(
					child.unwrap().getName().toUpperCase(),
					child.unwrap().getName());
			for (Element<Directory> grandChild : child.getChildren()) {
				assertNotEquals(
						grandChild.unwrap().getName().toUpperCase(),
						grandChild.unwrap().getName());
			}
		}

		/*
		 * Verify if the (foo) children have NOT upper case names (only
		 * foo element was affected by the function).
		 */
		assertEquals("FOO", foo.unwrap().getName());
		for (Element<Directory> child : foo.getChildren()) {
			assertNotEquals(
					child.unwrap().getName().toUpperCase(),
					child.unwrap().getName());
		}
	}

	/**
	 * Test for the {@link Element#apply(Consumer)} operation.
	 * 
	 * <p>Alternative scenario for this operation when trying to apply an action
	 * with a <code>null</code> consumer parameter. This test verifies that the
	 * element remains unchanged when a <code>null</code> action is applied.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to apply a <code>null</code> action to a specific element.
	 * <p><b>Expected:</b></p>
	 * No changes should be made to the element. The original element name and
	 * structure should remain completely unchanged.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session by API Transformation Process using the
	 * 	previous assembled tree;</li>
	 * 	<li>Get a specific element (Photoshop) from the tree;</li>
	 * 	<li>Try to apply a <code>null</code> consumer action to the element;
	 * 	</li>
	 * 	<li>Update the element;</li>
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

		Element<Directory> photoshop = manager.getElementById(photoshopId);

		Consumer<Element<Directory>> nullConsumer = null;
		photoshop.apply(nullConsumer);
		manager.updateElement(photoshop);

		photoshop = manager.getElementById(photoshopId);

		assertEquals("Photoshop", photoshop.unwrap().getName());
		assertEquals("photoshop.exe", photoshop.getChildren().iterator()
				.next().unwrap().getName());
	}

	/**
	 * Test for the {@link Element#apply(Consumer, Predicate)} operation.
	 * 
	 * <p>Alternative scenario for this operation when trying to apply an action
	 * with null consumer parameter and null predicate condition. This test
	 * verifies the behavior when null values are passed to the apply method
	 * in different combinations.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to apply actions and conditions with null values in different
	 * combinations on a specific element.
	 * <p><b>Expected:</b></p>
	 * No changes should be made to the element when null consumer or null
	 * predicate is passed. The original element name should remain unchanged.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session by API Transformation Process using the
	 * 	previous assembled tree;</li>
	 * 	<li>Get a specific element (Photoshop) from the tree;</li>
	 * 	<li>Try to apply with a null consumer and a valid condition that checks
	 * 	if directory name starts with "Photo";</li>
	 * 	<li>Verify that the element name remains unchanged since the action is
	 * 	null;</li>
	 * 	<li>Try to apply with a valid action and a condition that returns null;
	 * 	</li>
	 * 	<li>Verify that the element name remains unchanged since the condition
	 * 	returns null;</li>
	 * 	<li>Try to apply with a valid action and a null predicate;</li>
	 * 	<li>Verify that the element name remains unchanged since the predicate
	 * 	is null.</li>
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

		Element<Directory> photoshop = manager.getElementById(photoshopId);

		Consumer<Element<Directory>> nullConsumer = null;
		photoshop.apply(
				nullConsumer,
				element -> directoryNameStartsWithPhoto(element));
		manager.updateElement(photoshop);
		
		/*
		 * The name keeps the same, as the action is null so it does nothing.
		 */
		photoshop = manager.getElementById(photoshopId);
		assertEquals("Photoshop", photoshop.unwrap().getName());
		assertEquals("photoshop.exe", photoshop.getChildren().iterator()
				.next().unwrap().getName());

		photoshop.apply(
				element -> applyUpperCaseDirectoryName(element),
				element -> nullCondition());
		manager.updateElement(photoshop);

		photoshop = manager.getElementById(photoshopId);

		/*
		 * The name keeps the same, as the condition has a predicate inside that
		 * returns null and does nothing.
		 */
		assertEquals("Photoshop", photoshop.unwrap().getName());
		assertEquals("photoshop.exe", photoshop.getChildren().iterator()
				.next().unwrap().getName());

		Predicate<Element<Directory>> nullPredicate = null;
		photoshop.apply(
				element -> applyUpperCaseDirectoryName(element),
				nullPredicate);
		manager.updateElement(photoshop);
		
		photoshop = manager.getElementById(photoshopId);

		/*
		 * The name keeps the same, as the condition is null and does nothing.
		 */
		assertEquals("Photoshop", photoshop.unwrap().getName());
		assertEquals("photoshop.exe", photoshop.getChildren().iterator()
				.next().unwrap().getName());

	}
	
	/**
	 * Test for the {@link Object#equals(Object)} local implementation.
	 * 
	 * <p><b>Test:</b></p>
	 * Compare inequality between two <code>TreeElement</code> objects.
	 * <p><b>Expected:</b></p>
	 * Two <code>TreeElement</code> objects not equals in those scenarios:
	 * <ul>
	 * 	<li>The element passed as parameter has a different class type;</li>
	 * 	<li>The element passed as parameter is <code>null</code>;</li>
	 * 	<li>Both elements have same identifier and same parent but into in
	 * 	different sessions;</li>
	 * 	<li>Both element, contained into the same session, have the same
	 * 	identifier but different parents;</li>
	 * 	<li>Both element, contained into the same session, have same parent but
	 * 	different identifiers.</li>
	 * </ul>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize the <code>equals_notEquals1</code> session;</li>
	 * 	<li>Create the <code>element1</code>;</li>
	 * 	<li>Assert that the <code>element1</code> is not equal to a new
	 * 	<code>Object</code> neither <code>null</code>;</li>
	 * 	<li>Initialize the <code>equals_notEquals2</code> session;</li>
	 * 	<li>Create the <code>element2</code> with the same identifier as the
	 * 	<code>element1</code>;</li>
	 * 	<li>Assert that the both elements are no equals because have different
	 * 	sessions;</li>
	 * 	<li>Force the both of elements to use the same session;</li>
	 * 	<li>Assert that the both elements are not equals when the
	 * 	<code>element1</code> has a <code>null</code> parent;</li>
	 * 	<li>Assert that the both elements are not equals when the
	 * 	<code>element1</code> and <code>element2</code> have different parents;
	 * 	</li>
	 * 	<li>Force the both of elements to have the same parent;</li>
	 * 	<li>Reassign the <code>element2</code> with a different identifier
	 * 	compared to the <code>element1</code>;</li>
	 * 	<li>Assert that they are not equals.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void equals_notEquals() throws TreeException {
		final String sessionId1 = "equals_notEquals1";
		final String sessionId2 = "equals_notEquals2";

		final String elementId1 = "elementId1";
		final String elementId2 = "elementId2";

		final String sameParentId = "sameParentId";
		final String parentId1 = "parentId1";
		final String parentId2 = "parentId2";

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		transaction.initializeSession(sessionId1, Directory.class);
		Element<Directory> element1 = manager.createElement(elementId1,
			parentId1, null);
		
		assertNotEquals(element1, new Object());
		assertNotEquals(element1, null);

		transaction.initializeSession(sessionId2, Directory.class);
		Element<Directory> element2 = manager.createElement(elementId1,
			parentId1, null);
		
		assertNotEquals(element1, element2);

		transaction.destroySession(sessionId2);
		transaction.sessionCheckout(sessionId1);

		element1.setParent(null);
		element2 = manager.createElement(elementId1, parentId2, null);
		assertNotEquals(element1, element2);

		element1.setParent(parentId1);
		assertNotEquals(element1, element2);

		element1.setParent(sameParentId);
		element2.setParent(sameParentId);

		element2 = manager.createElement(elementId2, element2.getParent(), null);
		assertNotEquals(element1, element2);
	}

	/**
	 * Test for the {@link Object#toString(Object)} local implementation.
	 * 
	 * <p><b>Test:</b></p>
	 * Compare equality between the <code>toString()</code> method from the
	 * element and the &quot;[null]&quot; value.
	 * <p><b>Expected:</b></p>
	 * When an element has no any wrapped node, then its <code>toString()</code>
	 * implementation must print the &quot;[null]&quot; value.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session;</li>
	 * 	<li>Create an empty element without a node inside of it;</li>
	 * 	<li>Confirm that the <code>toString()</code> method from the element
	 *  prints &quot;[null]&quot;.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void elementToString_nullWrappedNode() throws TreeException {
		final String sessionId = "elementToString_nullWrappedNode";
		final String nullToString = "[null]";

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		transaction.initializeSession(sessionId, Directory.class);
		Element<Directory> element = manager.createElement(1, null, null);

		assertEquals(nullToString, element.toString());
	}
}
