package com.madzera.happytree.element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;

import com.madzera.happytree.Element;
import com.madzera.happytree.TreeManager;
import com.madzera.happytree.TreeTransaction;
import com.madzera.happytree.core.HappyTree;
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
public class ElementAlternativeTest {

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
				+ "\"parentIdentifier\":24935,\"name\":\"Dremweaver\","
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
		final String xml = "<element><identifier>24935</identifier>"
				+ "<parentIdentifier>42345</parentIdentifier><name>Adobe</name>"
				+ "<children><identifier>502010</identifier>"
				+ "<parentIdentifier>24935</parentIdentifier><name>Dremweaver</name>"
				+ "<children><identifier>8935844</identifier>"
				+ "<parentIdentifier>502010</parentIdentifier>"
				+ "<name>dreamweaver.exe</name></children></children><children>"
				+ "<identifier>909443</identifier>"
				+ "<parentIdentifier>24935</parentIdentifier><name>Photoshop</name>"
				+ "<children><identifier>4950243</identifier>"
				+ "<parentIdentifier>909443</parentIdentifier><name>photoshop.exe</name>"
				+ "</children></children><children><identifier>403940</identifier>"
				+ "<parentIdentifier>24935</parentIdentifier><name>Reader</name>"
				+ "<children><identifier>8493845</identifier>"
				+ "<parentIdentifier>403940</parentIdentifier><name>reader.exe</name>"
				+ "</children></children></element>";

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

	@Test
	public void toJSON_rootElement() throws TreeException {
		final String sessionId = "toJSON_rootElement";

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Collection<Directory> directoryTree = TreeAssembler.getDirectoryTree();

		transaction.initializeSession(sessionId, directoryTree);
		
		Element<Directory> root = manager.root();
		String jsonOutput = root.toJSON();
		
		assertNotNull(jsonOutput);
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
