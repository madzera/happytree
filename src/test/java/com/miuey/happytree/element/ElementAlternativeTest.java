package com.miuey.happytree.element;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
	 * Test for the {@link Element##setId(Object)}.
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
	 * Test for the {@link Element##setParent(Object)}.
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
}
