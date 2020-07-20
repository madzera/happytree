package com.miuey.happytree.manager;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Test;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeTransaction;
import com.miuey.happytree.core.HappyTree;
import com.miuey.happytree.example.Directory;
import com.miuey.happytree.example.TreeDirectoryAssembler;
import com.miuey.happytree.exception.TreeException;

/**
 * Test class for {@link TreeManager} operations.
 * 
 * <p>This test class represents the <i>error scenarios</i> for the operations
 * of {@link TreeManager}.</p>
 * 
 * @author Diego Nóbrega
 * @author Miuey
 *
 */
public class TreeManagerErrorTest {

	/**
	 * Test for almost all operations for {@link TreeManager} interface.
	 * 
	 * <p>Error scenario for the operations when the transaction has no defined
	 * session to run the operations.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to create an element without check out a session.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code> with the
	 * message: <i>&quot;No defined session.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the manager;</li>
	 * 	<li>Try to create an element;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 */
	@Test
	public void noDefinedSession() {
		final String messageError = "No defined session.";
		
		final String elementId = "foo";
		final String parentElementId = "bar";
		
		String error = null;

		try {
			TreeManager manager = HappyTree.createTreeManager();
			
			/*
			 * All TreeManager operations must work under a defined session.
			 */
			manager.createElement(elementId, parentElementId);
		} catch (TreeException e) {
			error = e.getMessage();
		} finally {
			assertEquals(messageError, error);
		}
	}
	
	/**
	 * Test for almost all operations for {@link TreeManager} interface.
	 * 
	 * <p>Error scenario for the operations when the transaction has no active
	 * selected session to run the operations.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to create an element with a deactivated session.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code> with the
	 * message: <i>&quot;No active session.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a session;</li>
	 * 	<li>Deactivate the same session;</li>
	 * 	<li>Try to create an element;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 */
	@Test
	public void noActiveSession() {
		final String sessionId = "noActiveSession";
		final String messageError = "No active session.";
		
		final String elementId = "foo";
		final String parentElementId = "bar";
		
		String error = null;

		try {
			TreeManager manager = HappyTree.createTreeManager();
			TreeTransaction transaction = manager.getTransaction();
			
			transaction.initializeSession(sessionId, Directory.class);
			transaction.deactivateSession();
			
			/*
			 * All TreeManager operations must work under a defined session.
			 */
			manager.createElement(elementId, parentElementId);
		} catch (TreeException e) {
			error = e.getMessage();
		} finally {
			assertEquals(messageError, error);
		}
	}
	
	/**
	 * Test for the {@link TreeManager#cut(Element, Element)} and
	 * {@link TreeManager#copy(Element, Element)} operations.
	 * 
	 * <p>Error scenario for these operation when trying to cut/copy an element
	 * with a <code>null</code> argument value.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeDirectoryAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to invoke {@link TreeManager#cut(Element, Element)} with a
	 * <code>null</code> argument value.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>IllegalArgumentException</code>
	 * with the message: <i>&quot;Invalid null/empty argument(s).&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session by API Transformation Process using a
	 * 	previous assembled tree;</li>
	 * 	<li>Try to invoke {@link TreeManager#cut(Element, Element)} with a
	 * 	<code>null</code> value in the <code>from</code> parameter;</li>
	 * 	<li>Catch the <code>IllegalArgumentException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void cutCopy_nullFromElement() throws TreeException {
		final String sessionId = "cutCopy_nullFromElement";
		final String messageError = "Invalid null/empty argument(s).";
		
		String error = null;
		
		final Element<Directory> nullableElement = null;
		final Long programFilesId = 42345l;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeDirectoryAssembler.
				getDirectoryTree();
		
		try {
			transaction.initializeSession(sessionId, directories);
			Element<Directory> programFiles = manager.getElementById(
					programFilesId);
			manager.cut(nullableElement, programFiles);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		} finally {
			assertEquals(messageError, error);
		}
	}
	
	/**
	 * Test for the {@link TreeManager#cut(Object, Object)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to cut an element by its
	 * id with a <code>null</code> argument value.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeDirectoryAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to invoke {@link TreeManager#cut(Object, Object)} with a
	 * <code>null</code> argument value.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>IllegalArgumentException</code>
	 * with the message: <i>&quot;Invalid null/empty argument(s).&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session by API Transformation Process using a
	 * 	previous assembled tree;</li>
	 * 	<li>Try to invoke {@link TreeManager#cut(Object, Object)} with a
	 * 	<code>null</code> value in the <code>from</code> parameter;</li>
	 * 	<li>Catch the <code>IllegalArgumentException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void cut_nullFromObjectId() throws TreeException {
		final String sessionId = "cut_nullFromObjectId";
		final String messageError = "Invalid null/empty argument(s).";
		
		String error = null;
		
		final Object nullableId = null;
		final Long programFilesId = 42345l;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeDirectoryAssembler.
				getDirectoryTree();
		
		try {
			transaction.initializeSession(sessionId, directories);
			manager.cut(nullableId, programFilesId);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		} finally {
			assertEquals(messageError, error);
		}
	}
	
	/**
	 * Test for the {@link TreeManager#cut(Element, Element)} and
	 * {@link TreeManager#copy(Element, Element)} operations.
	 * 
	 * <p>Error scenario for these operation when trying to cut/copy any changed
	 * attached element.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeDirectoryAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try cut a detached element.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code> with the
	 * message: <i>&quot;Detached element. Not possible to copy/cut elements not
	 * synchronized inside of the tree.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session by API Transformation Process using a
	 * 	previous assembled tree;</li>
	 * 	<li>Get the &quot;tmp&quot; element;</li>
	 * 	<li>Change its parent id pointing to &quot;administrator&quot; instead
	 * 	&quot;foo&quot; element;</li>
	 * 	<li>Try now to cut this changed element;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 */
	@Test
	public void detachedElement() {
		final String sessionId = "noActiveSession";
		final String messageError = "Detached element. Not possible to copy/cut"
				+ "elements not synchronized inside of the tree.";
		
		String error = null;
		
		final long tmpId = 47592;
		final long administratorId = 47592;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeDirectoryAssembler.
				getDirectoryTree();
		try {
			transaction.initializeSession(sessionId, directories);
			
			Element<Directory> tmp = manager.getElementById(tmpId);
			Element<Directory> administrator = manager.getElementById(
					administratorId);
			
			/*
			 * In this line, because a modification, this element turns on
			 * detached. It would need a update() operation to be synchronized.
			 */
			tmp.setParent(administrator.getId());
			
			manager.cut(tmp, administrator);
		} catch (TreeException e) {
			error = e.getMessage();
		} finally {
			assertEquals(messageError, error);
		}
	}
}
