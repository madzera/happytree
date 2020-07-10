package com.miuey.happytree.element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Test;

import com.miuey.happytree.Directory;
import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeTransaction;
import com.miuey.happytree.core.HappyTree;
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
	 * Match the element identifier at creation with the return of
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
		Element<Directory> element = manager.createElement(elementId, null);
		
		assertNotNull(element);
		assertEquals(elementId, element.getId());
	}
	
	/**
	 * Test for the {@link Element#setId()}.
	 * 
	 * <p>Happy scenario for this operation</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Set the element identifier.
	 * <p><b>Expected:</b></p>
	 * Match the element identifier when it changes with the return of
	 * {@link Element#getId()}.
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session;</li>
	 * 	<li>Create an element with the Id;</li>
	 * 	<li>Compare the result of {@link Element#getId()} with the previous Id;
	 * 	</li>
	 * 	<li>Set the element Id;</li>
	 * 	<li>Compare again the identifiers.</li>
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
		
		/*
		 * It is necessary to have any active session to use any operations of
		 * TreeManager
		 */
		transaction.initializeSession(sessionId, Directory.class);
		
		Element<Directory> element = manager.createElement(elementId1, null);
		
		assertNotNull(element);
		assertEquals(elementId1, element.getId());
		
		element.setId(elementId2);
		assertEquals(elementId2, element.getId());
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
		Element<Directory> element = manager.createElement(elementId, parentId);
		
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
		
		/*
		 * It is necessary to have any active session to use any operations of
		 * TreeManager
		 */
		transaction.initializeSession(sessionId, Directory.class);
		
		Element<Directory> element = manager.createElement(elementId1,
				parentId1);
		
		assertNotNull(element);
		assertEquals(parentId1, element.getParent());
		
		element.setParent(parentId2);
		assertEquals(parentId2, element.getParent());
	}
}
