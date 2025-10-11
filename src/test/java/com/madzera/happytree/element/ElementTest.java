package com.madzera.happytree.element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Test;

import com.madzera.happytree.Element;
import com.madzera.happytree.TreeManager;
import com.madzera.happytree.TreeTransaction;
import com.madzera.happytree.common.TreeCommonTestHelper;
import com.madzera.happytree.core.HappyTree;
import com.madzera.happytree.demo.model.Directory;
import com.madzera.happytree.demo.util.TreeAssembler;
import com.madzera.happytree.exception.TreeException;

/**
 * 
 * Test class for {@link Element} operations.
 * 
 * <p>This test class represents the <i>happy scenario</i> for all operations of
 * {@link Element}.</p>
 * 
 * @author Diego Madson de Andrade Nóbrega
 *
 */
public class ElementTest extends TreeCommonTestHelper {

	/**
	 * Test for the {@link Element#getId()}.
	 * 
	 * <p>Happy scenario for this operation</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Get the element identifier.
	 * <p><b>Expected:</b></p>
	 * Match the element identifier at the creation with the return of
	 * {@link Element#getId()}.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session;</li>
	 * 	<li>Create an element with the Id;</li>
	 * 	<li>Compare the result of {@link Element#getId()} with the previous Id.
	 * 	</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void getId() throws TreeException {
		final String sessionId = "getId";
		final BigDecimal elementId = new BigDecimal((Math.random() * 100));
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionId, Directory.class);
		Element<Directory> element = manager.createElement(elementId, null,
				null);
		
		assertNotNull(element);
		assertEquals(elementId, element.getId());
	}
	
	/**
	 * Test for the {@link Element#setId(Object)}.
	 * 
	 * <p>Happy scenario for this operation</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Set the element identifier. Setting the id of element will not change its
	 * real id. This must be updated for that. So, the id will keep the same
	 * value even trying to change the id.
	 * <p><b>Expected:</b></p>
	 * The id keeps the value before invoking {@link #setId()}.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session;</li>
	 * 	<li>Create an element with the Id;</li>
	 * 	<li>Compare the result of {@link Element#getId()} with the previous Id;
	 * 	</li>
	 * 	<li>Set the element Id;</li>
	 * 	<li>Verify if the value still is the same.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void setId() throws TreeException {
		final String sessionId = "setId";
		final String elementId1 = "foo";
		final String elementId2 = "bar";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionId, Directory.class);
		
		Element<Directory> element = manager.createElement(elementId1, null,
				null);
		
		assertNotNull(element);
		assertEquals(elementId1, element.getId());
		
		/*
		 * The id will change after the update, not at this time.
		 */
		element.setId(elementId2);
		
		assertNotEquals(elementId2, element.getId());
		assertEquals(elementId1, element.getId());
	}
	
	/**
	 * Test for the {@link Element#getParent()}.
	 * 
	 * <p>Happy scenario for this operation</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Get the parent identifier of the element.
	 * <p><b>Expected:</b></p>
	 * Match the parent identifier of the element at creation with the return of
	 * {@link Element#getParent()}.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session;</li>
	 * 	<li>Create an element with the Id and parent Id;</li>
	 * 	<li>Compare the result of {@link Element#getParent()} with the previous
	 * 	parent Id.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void getParent() throws TreeException {
		final String sessionId = "getParent";
		final Long elementId = 1000L;
		final Long parentId = System.currentTimeMillis();
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionId, Directory.class);
		Element<Directory> element = manager.createElement(elementId, parentId,
				null);
		
		assertNotNull(element);
		assertEquals(parentId, element.getParent());
	}
	
	/**
	 * Test for the {@link Element#setParent(Object)}.
	 * 
	 * <p>Happy scenario for this operation</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Set the parent identifier of the element.
	 * <p><b>Expected:</b></p>
	 * Match the parent identifier when it changes with the return of
	 * {@link Element#getParent()}.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session;</li>
	 * 	<li>Create an element with the Id and parent Id;</li>
	 * 	<li>Compare the result of {@link Element#getParent()} with the previous
	 * 	parent Id;</li>
	 * 	<li>Set the parent element Id;</li>
	 * 	<li>Compare again the parent identifiers.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void setParent() throws TreeException {
		final String sessionId = "setParent";
		final String elementId1 = "foo";
		
		final String parentId1 = "loren";
		final String parentId2 = "ipsum";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionId, Directory.class);
		
		Element<Directory> element = manager.createElement(elementId1,
				parentId1, null);
		
		assertNotNull(element);
		assertEquals(parentId1, element.getParent());
		
		element.setParent(parentId2);
		assertEquals(parentId2, element.getParent());
	}
	
	/**
	 * Test for the {@link Element#getChildren()}.
	 * 
	 * <p>Happy scenario for this operation</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Get the element children.
	 * <p><b>Expected:</b></p>
	 * Two child elements when invoking {@link Element#getChildren()}.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session;</li>
	 * 	<li>Create three elements represented by a parent and two child
	 * 	elements;</li>
	 * 	<li>Add the child elements into the parent;</li>
	 * 	<li>Invoke {@link Element#getChildren()} to obtain the children;</li>
	 * 	<li>Compare the resulting list with the two child elements.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void getChildren() throws TreeException {
		final String sessionId = "getChildren";
		
		final String elementId = "foo";
		final String childElement1 = "firstElement";
		final String childElement2 = "secondElement";
		
		final int expected = 2;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionId, Directory.class);
		Element<Directory> element = manager.createElement(elementId, null,
				null);
		Element<Directory> secondChild = manager.createElement(childElement2,
				elementId, null);
		Element<Directory> firstChild = manager.createElement(childElement1,
				elementId, null);
		
		element.addChild(firstChild);
		element.addChild(secondChild);
		
		Collection<Element<Directory>> children = element.getChildren();
		
		assertEquals(expected, children.size());
	}
	
	/**
	 * Test for the {@link Element#addChild(Element)}.
	 * 
	 * <p>Happy scenario for this operation</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Add a child element.
	 * <p><b>Expected:</b></p>
	 * Three child elements when invoking {@link Element#getChildren()}.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session;</li>
	 * 	<li>Create six elements represented by a parent and three child elements
	 * 	and more two free elements;
	 * 	<li>Add three child elements into the parent;</li>
	 * 	<li>Invoke {@link Element#getChildren()} to obtain the children list;
	 * 	</li>
	 * 	<li>Verify if the resulting list has three elements.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void addChild() throws TreeException {
		final String sessionId = "addChild";
		
		final String elementId = "foo";
		final String childElement1 = "firstElement";
		final String childElement2 = "secondElement";
		final String childElement3 = "thirdElement";
		final String childElement4 = "fourthElement";
		final String childElement5 = "fifthElement";
		
		final int expected = 3;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionId, Directory.class);
		
		manager.createElement(childElement4, elementId, null);
		manager.createElement(childElement2, elementId, null);
		
		Element<Directory> element = manager.createElement(elementId, null,
				null);
		Element<Directory> firstChild = manager.createElement(childElement1,
				elementId, null);
		Element<Directory> thirdChild = manager.createElement(childElement3,
				elementId, null);
		Element<Directory> fifthChild = manager.createElement(childElement5,
				elementId, null);
		
		element.addChild(firstChild);
		element.addChild(thirdChild);
		element.addChild(fifthChild);
		
		Collection<Element<Directory>> children = element.getChildren();
		
		assertEquals(expected, children.size());
	}
	
	/**
	 * Test for the {@link Element#addChildren(Collection)}.
	 * 
	 * <p>Happy scenario for this operation</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Add a children element list.
	 * <p><b>Expected:</b></p>
	 * Five child elements when invoking {@link Element#getChildren()}.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session;</li>
	 * 	<li>Create 6 elements represented by a parent and three child elements
	 * 	and more two free elements;
	 * 	</li>
	 * 	<li>Add two child elements within a list;</li>
	 * 	<li>Add three child elements into the parent;</li>
	 * 	<li>Add the list of two child elements previously created within the
	 * 	element by invoking {@link Element#addChildren(Collection)};</li>
	 * 	<li>Invoke {@link Element#getChildren()} to obtain the children list;
	 * 	</li>
	 * 	<li>Verify if the resulting list has five elements.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void addChildren() throws TreeException {
		final String sessionId = "addChildren";
		
		final String elementId = "foo";
		final String childElement1 = "firstElement";
		final String childElement2 = "secondElement";
		final String childElement3 = "thirdElement";
		final String childElement4 = "fourthElement";
		final String childElement5 = "fifthElement";
		
		final int expected = 5;
		
		List<Element<Directory>> toBeAdded = new ArrayList<Element<Directory>>();
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionId, Directory.class);
		
		Element<Directory> fourthChild = manager.createElement(childElement4,
				elementId, null);
		Element<Directory> secondChild = manager.createElement(childElement2,
				elementId, null);
		
		toBeAdded.add(fourthChild);
		toBeAdded.add(secondChild);
		
		Element<Directory> element = manager.createElement(elementId, null,
				null);
		Element<Directory> firstChild = manager.createElement(childElement1,
				elementId, null);
		Element<Directory> thirdChild = manager.createElement(childElement3,
				elementId, null);
		Element<Directory> fifthChild = manager.createElement(childElement5,
				elementId, null);
		
		element.addChild(firstChild);
		element.addChild(thirdChild);
		element.addChild(fifthChild);
		element.addChildren(toBeAdded);
		
		Collection<Element<Directory>> children = element.getChildren();
		
		assertEquals(expected, children.size());
	}
	
	/**
	 * Test for the {@link Element#removeChildren(Collection)}.
	 * 
	 * <p>Happy scenario for this operation</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Remove a list of children.
	 * <p><b>Expected:</b></p>
	 * Three child elements when invoking {@link Element#getChildren()}.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session;</li>
	 * 	<li>Create six elements represented by a parent and three child elements
	 * 	and more two free elements;
	 * 	</li>
	 * 	<li>Add two child elements within a list to be removed;</li>
	 * 	<li>Add all elements into the parent;</li>
	 * 	<li>Remove the list of two child elements previously created by invoking
	 * 	{@link Element#removeChildren(Collection)};</li>
	 * 	<li>Invoke {@link Element#getChildren()} to obtain the children list;
	 * 	</li>
	 * 	<li>Verify if the resulting list has three elements.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void removeChildren() throws TreeException {
		final String sessionId = "removeChildren";
		
		final String elementId = "foo";
		final String childElement1 = "firstElement";
		final String childElement2 = "secondElement";
		final String childElement3 = "thirdElement";
		final String childElement4 = "fourthElement";
		final String childElement5 = "fifthElement";
		
		final int total = 5;
		final int expected = 3;
		
		List<Element<Directory>> toBeRemoved = 
				new ArrayList<Element<Directory>>();
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionId, Directory.class);
		
		Element<Directory> element = manager.createElement(elementId, null,
				null);
		Element<Directory> fourthChild = manager.createElement(childElement4,
				elementId, null);
		Element<Directory> secondChild = manager.createElement(childElement2,
				elementId, null);
		Element<Directory> firstChild = manager.createElement(childElement1,
				elementId, null);
		Element<Directory> thirdChild = manager.createElement(childElement3,
				elementId, null);
		Element<Directory> fifthChild = manager.createElement(childElement5,
				elementId, null);
		
		element.addChild(firstChild);
		element.addChild(thirdChild);
		element.addChild(fifthChild);
		element.addChild(secondChild);
		element.addChild(fourthChild);

		toBeRemoved.add(fourthChild);
		toBeRemoved.add(secondChild);
		
		Collection<Element<Directory>> children = element.getChildren();
		assertEquals(total, children.size());
		
		element.removeChildren(toBeRemoved);
		
		children = element.getChildren();
		assertEquals(expected, children.size());
	}

	/**
	 * Test for the {@link Element#removeChild(Element)}.
	 * 
	 * <p>Happy scenario for this operation</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Remove a child from the element.
	 * <p><b>Expected:</b></p>
	 * An element without children.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session;</li>
	 * 	<li>Create two elements represented by a parent its child element;
	 * 	</li>
	 * 	<li>Add the child into the parent;</li>
	 * 	<li>Verify if the children list has one element.</li>
	 * 	<li>Remove the element by invoking {@link Element#removeChild(Element)};
	 * 	</li>
	 * 	<li>Verify if the children list has no one element.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void removeChild() throws TreeException {
		final String sessionId = "removeChild";
		
		final String elementId = "bar";
		final String childElementId = "child";
		
		final int beforeRemove = 1;
		final int afterRemove = 0;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionId, Directory.class);
		
		Element<Directory> element = manager.createElement(elementId, null,
				null);
		Element<Directory> child = manager.createElement(childElementId, null,
				null);
		
		element.addChild(child);
		
		assertEquals(beforeRemove, element.getChildren().size());
		
		element.removeChild(child);
		
		assertEquals(afterRemove, element.getChildren().size());
	}
	
	/**
	 * Test for the {@link Element#removeChild(Object)}.
	 * 
	 * <p>Happy scenario for this operation</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Remove a child from the element using the Id.
	 * <p><b>Expected:</b></p>
	 * An element without children.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session;</li>
	 * 	<li>Create two elements represented by a parent and its child element;
	 * 	</li>
	 * 	<li>Add the child into the parent;</li>
	 * 	<li>Verify if the children list has only one element.</li>
	 * 	<li>Remove the element by invoking {@link Element#removeChild(Object)};
	 * 	</li>
	 * 	<li>Verify if the children list has no one element.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void removeChildById() throws TreeException {
		final String sessionId = "removeChild";
		
		final Object elementId = BigDecimal.ONE.doubleValue();
		final Object childElementId = Integer.MAX_VALUE + (Math.random() * 10);
		
		final int beforeRemove = 1;
		final int afterRemove = 0;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionId, Directory.class);
		
		Element<Directory> element = manager.createElement(elementId, null,
				null);
		Element<Directory> child = manager.createElement(childElementId, null,
				null);
		element.addChild(child);
		
		assertEquals(beforeRemove, element.getChildren().size());
		
		element.removeChild(childElementId);
		
		assertEquals(afterRemove, element.getChildren().size());
	}
	
	/**
	 * Test for the {@link Element#wrap(Object)} and {@link Element#unwrap()}.
	 * 
	 * <p>Happy scenario for this operation</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Wrap and unwrap an object inside this element.
	 * <p><b>Expected:</b></p>
	 * A not <code>null</code> wrapped node represented by {@link Directory}.
	 * And this object must have named by <b>&quot;Photos&quot;</b>.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session;</li>
	 * 	<li>Create a <code>Directory</code> object named by &quot;Photos&quot;;
	 * 	</li>
	 * 	<li>Create an element;</li>
	 * 	<li>Wrap the object inside of element;</li>
	 * 	<li>Unwrap the object;</li>
	 * 	<li>Verify if the object is not <code>null</code>;</li>
	 * 	<li>Verify if the object is named by <b>&quot;Photos&quot;</b>.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void wrap_unwrap() throws TreeException {
		final String sessionId = "wrap_unwrap";
		final long elementId = Integer.MAX_VALUE;

		final String directoryName = "Photos";

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		transaction.initializeSession(sessionId, Directory.class);

		Directory directory = new Directory(elementId, 0L, directoryName);
		Element<Directory> element = manager.createElement(elementId, null,
				null);

		element.wrap(directory);

		Directory wrappedDirectory = element.unwrap();

		assertNotNull(wrappedDirectory);
		assertEquals(directoryName, wrappedDirectory.getName());
	}

	/**
	 * Test for the {@link Element#toJSON()}.
	 * 
	 * <p>Happy scenario for this operation</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Convert an element tree into JSON format.
	 * <p><b>Expected:</b></p>
	 * A JSON string representing the element tree.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Declare the expected JSON from the (Adobe) element through the
	 * 	{@link TreeAssembler#getDirectoryTree()};</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session;</li>
	 * 	<li>Get the element which represents the (Adobe) directory;</li>
	 * 	<li>Convert this element into JSON format by invoking
	 * 	{@link Element#toJSON()};</li>
	 * 	<li>Compare the resulting JSON string with the expected.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void toJSON() throws TreeException {
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

		final String sessionId = "toJSON";
		final long adobeId = 24935L;

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Collection<Directory> directoryTree = TreeAssembler.getDirectoryTree();

		transaction.initializeSession(sessionId, directoryTree);

		Element<Directory> adobe = manager.getElementById(adobeId);
		String jsonOutput = adobe.toJSON();

		assertEquals(json, jsonOutput);
	}

	/**
	 * Test for the {@link Element#toPrettyJSON()}.
	 * 
	 * <p>Happy scenario for this operation</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Convert an element tree into a well formatted JSON.
	 * <p><b>Expected:</b></p>
	 * A well formatted JSON string representing the element tree.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Declare the expected JSON from the (Adobe) element through the
	 * 	{@link TreeAssembler#getDirectoryTree()};</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session;</li>
	 * 	<li>Get the element which represents the (Adobe) directory;</li>
	 * 	<li>Convert this element into JSON format by invoking
	 * 	{@link Element#toJSON()};</li>
	 * 	<li>Compare the resulting JSON string with the expected.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void toPrettyJSON() throws TreeException {
		final String json = "{" + breakLine() + //
				"  \"identifier\" : 24935," + breakLine() + //
				"  \"parentIdentifier\" : 42345," + breakLine() + //
				"  \"name\" : \"Adobe\"," + breakLine() + //
				"  \"children\" : [ {" + breakLine() + //
				"    \"identifier\" : 502010," + breakLine() + //
				"    \"parentIdentifier\" : 24935," + breakLine() + //
				"    \"name\" : \"Dreamweaver\"," + breakLine() + //
				"    \"children\" : [ {" + breakLine() + //
				"      \"identifier\" : 8935844," + breakLine() + //
				"      \"parentIdentifier\" : 502010," + breakLine() + //
				"      \"name\" : \"dreamweaver.exe\"," + breakLine() + //
				"      \"children\" : [ ]" + breakLine() + //
				"    } ]" + breakLine() + //
				"  }, {" + breakLine() + //
				"    \"identifier\" : 909443," + breakLine() + //
				"    \"parentIdentifier\" : 24935," + breakLine() + //
				"    \"name\" : \"Photoshop\"," + breakLine() + //
				"    \"children\" : [ {" + breakLine() + //
				"      \"identifier\" : 4950243," + breakLine() + //
				"      \"parentIdentifier\" : 909443," + breakLine() + //
				"      \"name\" : \"photoshop.exe\"," + breakLine() + //
				"      \"children\" : [ ]" + breakLine() + //
				"    } ]" + breakLine() + //
				"  }, {" + breakLine() + //
				"    \"identifier\" : 403940," + breakLine() + //
				"    \"parentIdentifier\" : 24935," + breakLine() + //
				"    \"name\" : \"Reader\"," + breakLine() + //
				"    \"children\" : [ {" + breakLine() + //
				"      \"identifier\" : 8493845," + breakLine() + //
				"      \"parentIdentifier\" : 403940," + breakLine() + //
				"      \"name\" : \"reader.exe\"," + breakLine() + //
				"      \"children\" : [ ]" + breakLine() + //
				"    } ]" + breakLine() + //
				"  } ]" + breakLine() + //
				"}";

		final String sessionId = "toPrettyJSON";
		final long adobeId = 24935L;

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Collection<Directory> directoryTree = TreeAssembler.getDirectoryTree();

		transaction.initializeSession(sessionId, directoryTree);

		Element<Directory> adobe = manager.getElementById(adobeId);
		String jsonOutput = adobe.toPrettyJSON();

		assertEquals(json, jsonOutput);
	}

	/**
	 * Test for the {@link Element#toXML()}.
	 * 
	 * <p>Happy scenario for this operation</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Convert an element tree into XML format.
	 * <p><b>Expected:</b></p>
	 * A XML string representing the element tree.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Declare the expected JSON from the (Adobe) element through the
	 * 	{@link TreeAssembler#getDirectoryTree()};</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session;</li>
	 * 	<li>Get the element which represents the (Adobe) directory;</li>
	 * 	<li>Convert this element into XMK format by invoking
	 * 	{@link Element#toXML()};</li>
	 * 	<li>Compare the resulting XML string with the expected.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void toXML() throws TreeException {
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

		final String sessionId = "toXML";
		final long adobeId = 24935L;

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Collection<Directory> directoryTree = TreeAssembler.getDirectoryTree();

		transaction.initializeSession(sessionId, directoryTree);

		Element<Directory> adobe = manager.getElementById(adobeId);
		String xmlOutput = adobe.toXML();

		assertEquals(xml, xmlOutput);
	}

	/**
	 * Test for the {@link Element#toPrettyXML()}.
	 * 
	 * <p>Happy scenario for this operation</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Convert an element tree into a well formatted XML.
	 * <p><b>Expected:</b></p>
	 * A well formatted XML content of the element tree.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Declare the expected XML from the (Adobe) element through the
	 * 	{@link TreeAssembler#getDirectoryTree()};</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session;</li>
	 * 	<li>Get the element which represents the (Adobe) directory;</li>
	 * 	<li>Convert this element into XML format by invoking
	 * 	{@link Element#toXML()};</li>
	 * 	<li>Compare the resulting XML content with the expected.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void toPrettyXML() throws TreeException {
		final String xml = "<element>" + breakLine() + //
				"  <identifier>24935</identifier>" + breakLine() + //
				"  <parentIdentifier>42345</parentIdentifier>" + breakLine() + //
				"  <name>Adobe</name>" + breakLine() + //
				"  <children>" + breakLine() + //
				"    <element>" + breakLine() + //
				"      <identifier>502010</identifier>" + breakLine() + //
				"      <parentIdentifier>24935</parentIdentifier>" + breakLine() + //
				"      <name>Dreamweaver</name>" + breakLine() + //
				"      <children>" + breakLine() + //
				"        <element>" + breakLine() + //
				"          <identifier>8935844</identifier>" + breakLine() + //
				"          <parentIdentifier>502010</parentIdentifier>" + breakLine() + //
				"          <name>dreamweaver.exe</name>" + breakLine() + //
				"          <children/>" + breakLine() + //
				"        </element>" + breakLine() + //
				"      </children>" + breakLine() + //
				"    </element>" + breakLine() + //
				"    <element>" + breakLine() + //
				"      <identifier>909443</identifier>" + breakLine() + //
				"      <parentIdentifier>24935</parentIdentifier>" + breakLine() + //
				"      <name>Photoshop</name>" + breakLine() + //
				"      <children>" + breakLine() + //
				"        <element>" + breakLine() + //
				"          <identifier>4950243</identifier>" + breakLine() + //
				"          <parentIdentifier>909443</parentIdentifier>" + breakLine() + //
				"          <name>photoshop.exe</name>" + breakLine() + //
				"          <children/>" + breakLine() + //
				"        </element>" + breakLine() + //
				"      </children>" + breakLine() + //
				"    </element>" + breakLine() + //
				"    <element>" + breakLine() + //
				"      <identifier>403940</identifier>" + breakLine() + //
				"      <parentIdentifier>24935</parentIdentifier>" + breakLine() + //
				"      <name>Reader</name>" + breakLine() + //
				"      <children>" + breakLine() + //
				"        <element>" + breakLine() + //
				"          <identifier>8493845</identifier>" + breakLine() + //
				"          <parentIdentifier>403940</parentIdentifier>" + breakLine() + //
				"          <name>reader.exe</name>" + breakLine() + //
				"          <children/>" + breakLine() + //
				"        </element>" + breakLine() + //
				"      </children>" + breakLine() + //
				"    </element>" + breakLine() + //
				"  </children>" + breakLine() + //
				"</element>" + breakLine();

		final String sessionId = "toPrettyXML";
		final long adobeId = 24935L;

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Collection<Directory> directoryTree = TreeAssembler.getDirectoryTree();

		transaction.initializeSession(sessionId, directoryTree);

		Element<Directory> adobe = manager.getElementById(adobeId);
		String xmlOutput = adobe.toPrettyXML();

		assertEquals(xml, xmlOutput);
	}

	/**
	 * Test for the {@link Element#apply(Consumer)}.
	 * 
	 * <p>Happy scenario for this operation</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Apply a function to all elements within the own element itself. This
	 * function will be performed for each element within the own element. As an
	 * example, the name of each {@link Directory} element will be transformed
	 * to upper case.
	 * <p><b>Expected:</b></p>
	 * All elements within the own element must have its name in upper case.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session;</li>
	 * 	<li>Get the element which represents the (Adobe) {@link Directory}
	 * 	element;</li>
	 * 	<li>Apply the function, by invoking the {@link Element#apply(Consumer)}
	 * 	then all elements within the (Adobe) element, including itself will have
	 * 	the name transformed to upper case;
	 * 	</li>
	 * 	<li>Update the (Adobe) element by invoking
	 * 	{@link TreeManager#updateElement(Element)};</li>
	 * 	<li>Verify if all elements within the (Adobe) element, including itself
	 * 	have its name in upper case.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void apply() throws TreeException {
		final String sessionId = "apply";
		final Long adobeId = 24935L;

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Collection<Directory> directoryTree = TreeAssembler.getDirectoryTree();

		transaction.initializeSession(sessionId, directoryTree);

		Element<Directory> adobe = manager.getElementById(adobeId);
		
		/*
		 * Apply the name transformation for all elements within the (Adobe)
		 * element, including itself.
		 */
		adobe.apply(element -> {
			Directory directory = element.unwrap();
			directory.transformNameToUpperCase();
			element.wrap(directory);
		});

		assertEquals("Adobe", adobe.unwrap().getName());

		adobe = manager.updateElement(adobe);

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
	}

	/**
	 * Test for the {@link Element#apply(Consumer, Predicate)}.
	 * 
	 * <p>Happy scenario for this operation</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Apply a function to elements within the own element itself that satisfy a
	 * specific condition. This function will be performed only for each element
	 * within the own element that matches the given predicate. As an example,
	 * the name of each {@link Directory} element will be transformed to upper
	 * case, but only for those elements whose name contains "Photo" or "photo".
	 * Note that unlike the {@link Element#apply(Consumer)} method, the function
	 * is applied only for elements that satisfy the condition passed as
	 * parameter.
	 * <p><b>Expected:</b></p>
	 * Only elements within the own element that contain "Photo" or "photo" in
	 * their name must have their name transformed to upper case, while other
	 * elements remain unchanged.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session;</li>
	 * 	<li>Get the element which represents the (Adobe) {@link Directory}
	 * 	element;</li>
	 * 	<li>Apply conditional function, by invoking the
	 * 	{@link Element#apply(Consumer, Predicate)} with a condition that matches
	 * 	elements containing "Photo" or "photo" in their name. Only those
	 * 	elements will have their name transformed to upper case;</li>
	 * 	<li>Confirm that the elements have not been transformed as they were not
	 * 	updated yet;</li>
	 * 	<li>Update the (Adobe) element by invoking
	 * 	{@link TreeManager#updateElement(Element)};</li>
	 * 	<li>Verify that only elements containing "Photo" or "photo" within the
	 * 	(Adobe) element have now their name in upper case, while others remain
	 * 	unchanged.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void apply_withConditions() throws TreeException {
		final String sessionId = "apply_withConditions";
		final Long adobeId = 24935L;
		final long photoshopId = 909443L;
		final long readerId = 403940L;
		final long dreamweaverId = 502010L;

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Collection<Directory> directoryTree = TreeAssembler.getDirectoryTree();

		transaction.initializeSession(sessionId, directoryTree);

		Element<Directory> adobe = manager.getElementById(adobeId);

		/*
		 * Apply the name transformation only for those elements whose name
		 * starts with "Photo" or "photo".
		 */
		adobe.apply(
			element -> applyUpperCaseDirectoryName(element),
			element -> directoryNameStartsWithPhoto(element)
		);

		/*
		 * Invoking unwrap() method brings the actual state of the element, so
		 * at this point, none of the elements must have been transformed yet,
		 * since the (Adobe) element has not been updated yet.
		 */
		assertEquals("Adobe", adobe.unwrap().getName());
		Element<Directory> photoshop = manager.getElementById(photoshopId);
		assertEquals("Photoshop", photoshop.unwrap().getName());
		assertEquals("photoshop.exe", photoshop.getChildren().iterator()
				.next().unwrap().getName());

		adobe = manager.updateElement(adobe);

		assertEquals("Adobe", adobe.unwrap().getName());

		/*
		 * Verify if the elements that contains "Photo" or "photo" within the
		 * (Adobe) element were transformed to upper case, and the others
		 * elements that don't match the condition remain unchanged.
		 */
		assertEquals("Adobe", adobe.unwrap().getName());
		photoshop = manager.getElementById(photoshopId);
		assertEquals("PHOTOSHOP", photoshop.unwrap().getName());
		assertEquals("PHOTOSHOP.EXE", photoshop.getChildren().iterator()
				.next().unwrap().getName());
		Element<Directory> reader = manager.getElementById(readerId);
		assertEquals("Reader", reader.unwrap().getName());
		Element<Directory> dreamweaver = manager.getElementById(dreamweaverId);
		assertEquals("Dreamweaver", dreamweaver.unwrap().getName());
	}
	
	/**
	 * Test for the {@link Object#hashCode()} local implementation.
	 * 
	 * <p><b>Test:</b></p>
	 * Compare hash codes from 4 different decimal Java types and 2 different
	 * floating point types. The comparison occurs between the same values for
	 * each different decimal type. For decimal type, the value is the maximum
	 * capacity of the <code>byte</code> type (127). 
	 * <p><b>Expected:</b></p>
	 * The test consists in proving that the decimal types have the same hash
	 * when using the same value (<code>Byte.MAX_VALUE</code>). Opposing to this,
	 * the floating point types have different hash codes when using the same
	 * value.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Declare 4 variable with the maximum <code>byte</code> type capacity,
	 * 	each of one with different types: byte, short, int and long;</li>
	 * 	<li>Declare one <code>long</code variable with the maximum
	 * 	<code>byte</code> value type + 1;</li>
	 * 	<li>Declare another 2 variable with different floating point types: 
	 * 	float and double;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session;</li>
	 * 	<li>Create the elements with the identifiers specified above;</li>
	 * 	<li>Create more two elements representing the maximum <code>byte</code>
	 * 	value + 1 and a hypothetical parent of all of them;</li>
	 * 	<li>Assert that these 4 elements, without parent, have the same hash
	 * 	code and different hash code from that has the maximum <code>byte</code>
	 * 	value + 1 element;</li>
	 * 	<li>Assert that the those elements representing the <code>float</code
	 * 	and <code>double</code> types have different hash codes even though they
	 * 	have the same value;</li>
	 * 	<li>Set the parent with a random <code>double</code> value for all of
	 * 	them;</li>
	 * 	<li>Repeat exactly the same asserts.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void elementHashCode() throws TreeException {
		final String sessionId = "elementHashCode";

		final int elementId1 = (int) Byte.MAX_VALUE;
		final short elementId2 = (short) Byte.MAX_VALUE;
		final byte elementId3 = Byte.MAX_VALUE;
		final long elementId4 = Byte.MAX_VALUE;
		final long elementId5 = Byte.MAX_VALUE+1;
		
		final float elementId6 = 3478524.35924f;
		final float elementId7 = 3478524.35924f;
		final double elementId8 = 3478524.35924;

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		transaction.initializeSession(sessionId, Directory.class);

		Element<Directory> element1 = manager.createElement(elementId1,
		null, null);		
		Element<Directory> element2 = manager.createElement(elementId2,
		null, null);
		Element<Directory> element3 = manager.createElement(elementId3,
		null, null);
		Element<Directory> element4 = manager.createElement(elementId4,
		null, null);
		Element<Directory> element5 = manager.createElement(elementId5,
		null, null);

		Element<Directory> element6 = manager.createElement(elementId6,
		null, null);
		Element<Directory> element7 = manager.createElement(elementId7,
		null, null);
		Element<Directory> element8 = manager.createElement(elementId8,
		null, null);

		Element<Directory> parent = manager.createElement(Math.random(),
			null, null);

		validateHashCodes(
			element1,
			element2,
			element3,
			element4,
			element5,
			element6,
			element7,
			element8
		);

		element1.setParent(parent.getId());
		element2.setParent(parent.getId());
		element3.setParent(parent.getId());
		element4.setParent(parent.getId());

		element6.setParent(parent.getId());
		element7.setParent(parent.getId());

		validateHashCodes(
			element1,
			element2,
			element3,
			element4,
			element5,
			element6,
			element7,
			element8
		);
	}

	/**
	 * Test for the {@link Object#equals(Object)} local implementation.
	 * 
	 * <p><b>Test:</b></p>
	 * Compare equality between two <code>TreeElement</code> objects.
	 * <p><b>Expected:</b></p>
	 * Two <code>TreeElement</code> objects are equal when they have the same
	 * identifier, same parent and are contained in the same session.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session;</li>
	 * 	<li>Create two elements with the same identifier;</li>
	 * 	<li>Assert that the elements are equals when their parents are
	 * 	<code>null</code>;</li>
	 * 	<li>Also, create an element who will represent the parent for both of
	 * 	them;</li>
	 * 	<li>Assert that they are equals;</li>
	 * 	<li>Assign one of those elements to a third variable, occasioning two
	 * 	variables pointing to a same object instance;</li>
	 * 	<li>Confirm also that they are equals;</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void elementEquals() throws TreeException {
		final String sessionId = "elementEquals";

		final String sameId = "sameId";
		final String parentId = "parentId1";

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		transaction.initializeSession(sessionId, Directory.class);	

		Element<Directory> element1 = manager.createElement(sameId,
		null, null);
		Element<Directory> element2 = manager.createElement(sameId,
		null, null);

		assertEquals(element1, element2);

		Element<Directory> parentElement = manager.createElement(parentId,
		null, null);

		parentElement.addChild(element1);
		parentElement.addChild(element2);
		
		assertEquals(element1, element2);
		assertEquals(element1, element2);

		Element<Directory> element3 = element1;
		assertEquals(element1, element3);
	}

	/**
	 * Test for the {@link Object#toString(Object)} local implementation.
	 * 
	 * <p><b>Test:</b></p>
	 * Compare equality between the <code>toString()</code> method from the
	 * element and the <code>toString()</code> method from the wrapped node
	 * inside of this element.
	 * <p><b>Expected:</b></p>
	 * The <code>toString()</code> method from the wrapped node must be the same
	 * as the <code>toString()</code> method from the {@link Element} object
	 * when the wrapped node is not <code>null</code>.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create an object from the <code>Directory</code> class with the
	 * 	attribute name &quot;Photos&quot;;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session;</li>
	 * 	<li>Create an element with the wrapped object at the step 1;</li>
	 * 	<li>Confirm that the <code>toString()</code> methods of the wrapped
	 * 	object and the element are equals.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void elementToString() throws TreeException {
		final String sessionId = "elementToString";

	Directory directory = new Directory(1L, 0L, "Photos");
		String toStringDirectory = directory.toString();

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		transaction.initializeSession(sessionId, Directory.class);
		Element<Directory> element = manager.createElement(1,null, directory);

		assertEquals(toStringDirectory, element.toString());
	}

	private void validateHashCodes(
		Element<Directory> element1,
		Element<Directory> element2,
		Element<Directory> element3,
		Element<Directory> element4,
		Element<Directory> element5,
		Element<Directory> element6,
		Element<Directory> element7,
		Element<Directory> element8) {
		assertEquals(element1.hashCode(), element2.hashCode());
		assertEquals(element1.hashCode(), element3.hashCode());
		assertEquals(element2.hashCode(), element3.hashCode());
		assertEquals(element1.hashCode(), element4.hashCode());
		assertEquals(element2.hashCode(), element4.hashCode());
		assertEquals(element3.hashCode(), element4.hashCode());

		assertNotEquals(element1.hashCode(), element5.hashCode());
		assertNotEquals(element2.hashCode(), element5.hashCode());
		assertNotEquals(element3.hashCode(), element5.hashCode());
		assertNotEquals(element4.hashCode(), element5.hashCode());

		assertEquals(element6.hashCode(), element7.hashCode());
		assertNotEquals(element7.hashCode(), element8.hashCode());
	}
	
	private String breakLine() {
		return System.lineSeparator();
	}
}
