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
	 * 	<li>Unwrap the node object from the element;</li>
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
