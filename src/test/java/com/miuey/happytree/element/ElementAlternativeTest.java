package com.miuey.happytree.element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

import com.miuey.happytree.Directory;
import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeTransaction;
import com.miuey.happytree.core.HappyTree;
import com.miuey.happytree.exception.TreeException;

/**
 * Test class for {@link Element} operations.
 * 
 * <p>This test class represents the <i>alternative scenarios</i> for the
 * operations of {@link Element}.</p>
 * 
 * @author Diego Nóbrega
 * @author Miuey
 *
 */
public class ElementAlternativeTest {

	/**
	 * Test for the {@link Element#setId(Object)}.
	 * 
	 * <p>Alternative scenario for this operation when trying to set an element
	 * Id with a <code>null</code> value.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to set an element Id with a <code>null</code> value.
	 * <p><b>Expected:</b></p>
	 * Receive a <code>null</code> value when trying to get the element Id.
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
	 * @throws TreeException
	 */
	@Test
	public void setId_nullArg() throws TreeException {
		final String sessionId = "setId";
		final Date nullableId = null;
		
		final Date elementId = new Date();
		final Date randomParentId = new Date();
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		/*
		 * It is necessary to have any active session to use any operations of
		 * TreeManager
		 */
		transaction.initializeSession(sessionId, Directory.class);
		
		Element<Directory> element = manager.createElement(elementId,
				randomParentId);
		
		assertNotNull(element);
		assertNotNull(element.getParent());
		assertNotNull(element.getId());
		
		/*
		 * At the Element detached context, it is allowed accept null Id.
		 */
		element.setId(nullableId);
		assertNull(element.getId());
		assertNotNull(element.getParent());
	}
	
	/**
	 * Test for the {@link Element#setParent(Object)}.
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
	 * @throws TreeException
	 */
	@Test
	public void setParent_nullArg() throws TreeException {
		final String sessionId = "setParent";
		final Long elementId = 100L;
		final Long parentId = 101L;
		
		final Long nullableParentId = null;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		/*
		 * It is necessary to have any active session to use any operations of
		 * TreeManager
		 */
		transaction.initializeSession(sessionId, Directory.class);
		
		Element<Directory> element = manager.createElement(elementId,
				parentId);
		
		assertNotNull(element);
		assertNotNull(element.getId());
		assertNotNull(element.getParent());
		
		element.setParent(nullableParentId);
		assertNotNull(element.getId());
		assertNull(element.getParent());
	}
	
	/**
	 * Test for the {@link Element#removeChild(Element)}.
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
	 * @throws TreeException
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
		
		Element<Directory> element = manager.createElement(elementId, null);
		Element<Directory> child = manager.createElement(childId, null);
		Element<Directory> nullChild = null;
		
		element.addChild(child);
		element.addChild(nullChild);
		assertEquals(beforeRemove, element.getChildren().size());
		element.removeChild(nullChild);
		assertEquals(afterRemove, element.getChildren().size());
	}
	
	/**
	 * Test for the {@link Element#removeChild(Object)}.
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
	 * @throws TreeException
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
		
		Element<Directory> element = manager.createElement(elementId, null);
		Element<Directory> child = manager.createElement(childId, null);
		Element<Directory> nullChild = null;
		
		element.addChild(child);
		element.addChild(nullChild);
		assertEquals(beforeRemove, element.getChildren().size());
		element.removeChild(nullableChildId);
		assertEquals(afterRemove, element.getChildren().size());
	}
}
