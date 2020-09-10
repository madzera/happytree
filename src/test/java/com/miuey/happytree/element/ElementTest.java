package com.miuey.happytree.element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeTransaction;
import com.miuey.happytree.core.HappyTree;
import com.miuey.happytree.example.Directory;
import com.miuey.happytree.exception.TreeException;

/**
 * 
 * Test class for {@link Element} operations.
 * 
 * <p>This test class represents the <i>happy scenario</i> for all operations of
 * {@link Element}.</p>
 * 
 * @author Diego Nóbrega
 * @author Miuey
 *
 */
public class ElementTest {

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
	 * @throws TreeException 
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
	 * Test for the {@link Element#setId()}.
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
	 * @throws TreeException 
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
	 * @throws TreeException 
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
	 * Test for the {@link Element#setParent()}.
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
	 * @throws TreeException 
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
	 * @throws TreeException
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
	 * @throws TreeException
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
	 * @throws TreeException
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
	 * @throws TreeException
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
	 * @throws TreeException
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
	 * @throws TreeException
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
	 * @throws TreeException
	 */
	@Test
	public void wrap_unwrap() throws TreeException {
		final String sessionId = "wrap_unwrap";
		final long elementId = Integer.MAX_VALUE;
		
		final String directoryName = "Photos";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionId, Directory.class);
		
		Directory directory = new Directory(elementId, 0, directoryName);
		Element<Directory> element = manager.createElement(elementId, null,
				null);
		
		element.wrap(directory);
		
		Directory wrappedDirectory = element.unwrap();
		
		assertNotNull(wrappedDirectory);
		assertEquals(directoryName, wrappedDirectory.getName());
	}
}
