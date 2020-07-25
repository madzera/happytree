package com.miuey.happytree.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.junit.Test;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeTransaction;
import com.miuey.happytree.core.HappyTree;
import com.miuey.happytree.example.Directory;
import com.miuey.happytree.example.Metadata;
import com.miuey.happytree.example.TreeAssembler;
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
	 * Test for the {@link TreeManager#cut(Element, Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to cut an element with a
	 * <code>null</code> argument value.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
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
	public void cut_nullFromElement() throws TreeException {
		final String sessionId = "cut_nullFromElement";
		final String messageError = "Invalid null/empty argument(s).";
		
		String error = null;
		
		final Element<Directory> nullableElement = null;
		final Long programFilesId = 42345l;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.
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
	 * Test for the {@link TreeManager#cut(Element, Element)}.
	 * 
	 * <p>Error scenario for this operation when trying to cut an element which
	 * does not exists in the tree.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to cut an element which does not exists in the tree.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>IllegalArgumentException</code>
	 * with the message: <i>&quot;Invalid null/empty argument(s).&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session by API Transformation Process using a
	 * 	previous assembled tree;</li>
	 * 	<li>Get the element to be cut through a not existing id;</li>
	 * 	<li>Try to cut this element (<code>null</code>) for inside of an existed
	 * 	element;</li>
	 * 	<li>Catch the <code>IllegalArgumentException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void cut_notExistingFromElement() throws TreeException {
		final String sessionId = "cut_nullFromElement";
		final String messageError = "Invalid null/empty argument(s).";
		
		String error = null;
		
		Long notExistingId = Long.MAX_VALUE;
		final Long programFilesId = 42345l;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.
				getDirectoryTree();
		
		try {
			transaction.initializeSession(sessionId, directories);
			Element<Directory> programFiles = manager.getElementById(
					programFilesId);
			Element<Directory> nullableElement = manager.getElementById(
					notExistingId);
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
	 * and <code>TreeAssembler</code> sample classes.</p>
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
		
		Collection<Directory> directories = TreeAssembler.
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
	 * Test for the {@link TreeManager#cut(Object, Object)}.
	 * 
	 * <p>Error scenario for this operation when trying to cut an element
	 * by only its id which the id from this element (<code>from</code>) does
	 * not exists in the tree.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to cut an element only by its id which this id does not exists in the
	 * tree.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>IllegalArgumentException</code>
	 * with the message: <i>&quot;Invalid null/empty argument(s).&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session by API Transformation Process using a
	 * 	previous assembled tree;</li>
	 * 	<li>Try to cut a not existed element by its id for inside of an existed
	 * 	element;</li>
	 * 	<li>Catch the <code>IllegalArgumentException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void cut_notExistingFromObjectId() throws TreeException {
		final String sessionId = "cut_notExistingFromObjectId";
		final String messageError = "Invalid null/empty argument(s).";
		
		final long notExistingFromId = Long.MAX_VALUE;
		final long wordExeId = 4611329;
		
		String error = null;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.
				getDirectoryTree();
		try {
			transaction.initializeSession(sessionId, directories);
			manager.cut(notExistingFromId, wordExeId);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		} finally {
			assertEquals(messageError, error);
		}
	}
	
	/**
	 * Test for the {@link TreeManager#cut(Element, Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to cut the entire root.
	 * This is impossible cut the tree. There is already an implementation that
	 * clone (copy) the tree, by invoking
	 * {@link TreeTransaction#cloneSession(String, String)}.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to cut the root element.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code>
	 * with the message: <i>&quot;No possible to cut/copy root. Consider using a
	 * transaction to clone trees.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize the source session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get the source root element that will be cut;</li>
	 * 	<li>Initialize the target session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get the target element which the source root element will be
	 * 	inserted;</li>
	 * 	<li>Change the session for the source session;</li>
	 * 	<li>Try to cut the source root element into the target element;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 */
	@Test
	public void cut_rootToElement() {
		final String sourceSessionId = "source";
		final String targetSessionId = "target";
		
		final long windowsId = 1;
		
		final String messageError = "No possible to cut/copy root. Consider"
				+ " using a transaction to clone trees.";
		String error = null;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> sourceDir = TreeAssembler.getDirectoryTree();
		Collection<Directory> targetDir = TreeAssembler.getSimpleDirectoryTree();
		
		try {
			transaction.initializeSession(sourceSessionId, sourceDir);
			Element<Directory> root = transaction.currentSession().tree();
			
			transaction.initializeSession(targetSessionId, targetDir);
			Element<Directory> windows = manager.getElementById(windowsId);
			
			transaction.sessionCheckout(sourceSessionId);
			/*
			 * Impossible to cut the root element. Instead that, consider using
			 * the TreeTransaction.cloneSession(). 
			 */
			manager.cut(root, windows);
		} catch (TreeException e) {
			error = e.getMessage();
		} finally {
			assertEquals(messageError, error);
		}
	}
	
	/**
	 * Test for the {@link TreeManager#cut(Element, Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to cut any changed
	 * attached element.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
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
	public void cut_detachedElement() {
		final String sessionId = "cut_detachedElement";
		final String messageError = "Detached element. Not possible to copy/cut"
				+ " elements not synchronized inside of the tree.";
		
		String error = null;
		
		final long tmpId = 47592;
		final long administratorId = 47592;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.
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
	
	/**
	 * Test for the {@link TreeManager#cut(Element, Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to cut an element for
	 * inside of another tree which this tree already have an element with the
	 * same id.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try cut an element for inside of another tree containing an element with
	 * the same id.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code> with the
	 * message: <i>&quot;Duplicated ID.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize two sessions, the respective source and target. Both
	 * 	previously loaded from <code>TreeAssembler</code>;</li>
	 * 	<li>Get the element (&quot;Entry&quot;) which its id already exists in
	 * 	the target tree;</li>
	 * 	<li>Change the session for the source tree by invoking
	 * 	{@link TreeTransaction#sessionCheckout(String)};</li>
	 * 	<li>Try to cut the &quot;Entry&quot; element from the source tree to
	 * 	&quot;Entry&quot; element of the target tree;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 */
	@Test
	public void cut_toAnotherTreeDuplicatedId() {
		final String sourceSessionId = "source";
		final String targetSessionId = "target";
		
		final String messageError = "Duplicated ID.";
		
		String error = null;
		final long entryId = 77530344;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> sourceDir = TreeAssembler.
				getDirectoryTree();
		Collection<Directory> targetDir = TreeAssembler.
				getSimpleDirectoryTree();
		
		try {
			transaction.initializeSession(sourceSessionId, sourceDir);
			Element<Directory> sourceEntry = manager.getElementById(entryId);
			assertNotNull(sourceEntry);
			
			transaction.initializeSession(targetSessionId, targetDir);
			
			/*
			 * Same Id.
			 */
			Element<Directory> targetEntry = manager.getElementById(entryId);
			assertNotNull(targetEntry);
			
			transaction.sessionCheckout(sourceSessionId);
			
			/*
			 * Duplicated Id error.
			 */
			manager.cut(sourceEntry, targetEntry);
		} catch (TreeException e) {
			error = e.getMessage();
		} finally {
			assertEquals(messageError, error);
		}
	}
	
	/**
	 * Test for the {@link TreeManager#cut(Object, Object)} operation. Even
	 * sending the <code>Element</code> instead <code>Object</code>, because
	 * they are of different type, the operation
	 * {@link TreeManager#cut(Object, Object)} is invoked instead
	 * {@link TreeManager#cut(Element, Element)}
	 * 
	 * <p>Error scenario for this operation when trying to cut an element for
	 * inside of another tree which this tree has different type
	 * (<code>Directory</code> -> <code>Metadata</code>).</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>,
	 * <code>Metadata</code> and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try cut an element for inside of another tree containing a different type.
	 * <p><b>Expected:</b></p>
	 * Because the {@link TreeManager#cut(Object, Object)} is invoked, in this
	 * case the <code>from</code> argument of this operation is
	 * <code>null</code>, thus, throwing the
	 * <code>IllegalArgumentException</code> with the message:
	 * <i>&quot;Invalid null/empty argument(s).&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize two sessions, the respective source and target. Both
	 * 	previously loaded from <code>TreeAssembler</code>;</li>
	 * 	<li>Get the source and target element;</li>
	 * 	<li>Change the session for the source tree by invoking
	 * 	{@link TreeTransaction#sessionCheckout(String)};</li>
	 * 	<li>Try to cut the source element typified by <code>Directory</code>
	 * 	inside of the target typified by <code>Metadata</code>;</li>
	 * 	<li>Catch the <code>IllegalArgumentException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 * 
	 * @throws TreeException 
	 */
	@Test
	public void cut_toAnotherTreeWithDiffType() throws TreeException {
		final String source = "source";
		final String target = "target";
		final String messageError = "Invalid null/empty argument(s).";
		
		String error = null;
		
		final long recMp4 = 3840200;
		final String typeId = "type";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.
				getDirectoryTree();
		Collection<Metadata> metadata = TreeAssembler.
				getMetadataTree();
		
		try {
			transaction.initializeSession(source, directories);
			Element<Directory> mp4 = manager.getElementById(recMp4);
			
			transaction.initializeSession(target, metadata);
			Element<Metadata> type = manager.getElementById(typeId);
			
			transaction.sessionCheckout(source);
			
			/*
			 * Error trying to insert an element inside of a tree with different
			 * type Directory - Metadata. It will invoke cut(Object, Object)
			 * instead cur(Element, Element), thus throwing
			 * IllegalArgumentException.
			 */
			manager.cut(mp4, type);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		} finally {
			assertEquals(messageError, error);
		}
	}
	
	/**
	 * Test for the {@link TreeManager#copy(Element, Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to copy a not existing
	 * element in the source tree session.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to copy a not existing element.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>IllegalArgumentException</code>
	 * with the message: <i>&quot;Invalid null/empty argument(s).&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize the target session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get the target element by invoking
	 * 	{@link TreeManager#getElementById(Object)};</li>
	 * 	<li>Initialize the source session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get the source element by invoking
	 * 	{@link TreeManager#getElementById(Object)} through a not existing id;
	 * 	</li>
	 * 	<li>Try to copy the source element;</li>
	 * 	<li>Catch the <code>IllegalArgumentException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void copy_nullFromElement() throws TreeException {
		final String sourceSessionId = "source";
		final String targetSessionId = "target";
		
		final String messageError = "Invalid null/empty argument(s).";
		String error = null;
		
		final long notExistingId = Long.MAX_VALUE;
		final long  driversId = 1076;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> sourceDir = TreeAssembler.getDirectoryTree();
		Collection<Directory> targetDir = TreeAssembler.getSimpleDirectoryTree();
		
		try {
			transaction.initializeSession(targetSessionId, targetDir);
			Element<Directory> drivers = manager.getElementById(driversId);
			
			transaction.initializeSession(sourceSessionId, sourceDir);
			Element<Directory> nullableElement = manager.getElementById(
					notExistingId);
			
			/*
			 * Error trying to copy a not existing element.
			 */
			manager.copy(nullableElement, drivers);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		} finally {
			assertEquals(messageError, error);
		}
	}
	
	/**
	 * Test for the {@link TreeManager#copy(Element, Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to copy an element for
	 * inside of a not existing element in the target tree session.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to copy an element for inside of a not existing target element.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>IllegalArgumentException</code>
	 * with the message: <i>&quot;Invalid null/empty argument(s).&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize the target session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get the target element by invoking
	 * 	{@link TreeManager#getElementById(Object)} through a not existing id;
	 * 	</li>
	 * 	<li>Initialize the source session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get the source element by invoking
	 * 	{@link TreeManager#getElementById(Object)};
	 * 	</li>
	 * 	<li>Try to copy the source element into the not existing target element;
	 * 	</li>
	 * 	<li>Catch the <code>IllegalArgumentException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 * 
	 * @throws TreeException
	 */
	@Test
	public void copy_nullToElement() throws TreeException {
		final String sourceSessionId = "source";
		final String targetSessionId = "target";
		
		final String messageError = "Invalid null/empty argument(s).";
		String error = null;
		
		final long readmeId = 495833;
		final long notExistingId = Long.MAX_VALUE;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> sourceDir = TreeAssembler.getDirectoryTree();
		Collection<Directory> targetDir = TreeAssembler.getSimpleDirectoryTree();
		
		try {
			transaction.initializeSession(targetSessionId, targetDir);
			Element<Directory> nullableElement = manager.getElementById(
					notExistingId);
			
			transaction.initializeSession(sourceSessionId, sourceDir);
			Element<Directory> readme = manager.getElementById(readmeId);
			
			/*
			 * Error trying to copy a not existing target element.
			 */
			manager.copy(readme, nullableElement);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		} finally {
			assertEquals(messageError, error);
		}
	}
	
	/**
	 * Test for the {@link TreeManager#copy(Element, Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to copy the entire root.
	 * This is impossible copy the tree. There is already an implementation that
	 * clone (copy) the tree, by invoking
	 * {@link TreeTransaction#cloneSession(String, String)}.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to copy the root element.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code>
	 * with the message: <i>&quot;No possible to cut/copy root. Consider using a
	 * transaction to clone trees.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize the source session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get the source root element;</li>
	 * 	<li>Initialize the target session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get the target element which the source root element will be
	 * 	inserted;</li>
	 * 	<li>Change the session for the source session;</li>
	 * 	<li>Try to copy the source root element into the target element;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 */
	@Test
	public void copy_rootToElement() {
		final String sourceSessionId = "source";
		final String targetSessionId = "target";
		
		final long windowsId = 1;
		
		final String messageError = "No possible to cut/copy root. Consider"
				+ " using a transaction to clone trees.";
		String error = null;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> sourceDir = TreeAssembler.getDirectoryTree();
		Collection<Directory> targetDir = TreeAssembler.getSimpleDirectoryTree();
		
		try {
			transaction.initializeSession(sourceSessionId, sourceDir);
			Element<Directory> root = transaction.currentSession().tree();
			
			transaction.initializeSession(targetSessionId, targetDir);
			Element<Directory> windows = manager.getElementById(windowsId);
			
			transaction.sessionCheckout(sourceSessionId);
			/*
			 * Impossible to copy the root element. Instead that, consider using
			 * the TreeTransaction.cloneSession(). 
			 */
			manager.copy(root, windows);
		} catch (TreeException e) {
			error = e.getMessage();
		} finally {
			assertEquals(messageError, error);
		}
	}
	
	/**
	 * Test for the {@link TreeManager#copy(Element, Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to copy an element from
	 * the source tree which it was changed (detached) before to be copied.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to copy a detached element.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code>
	 * with the message: <i>&quot;Detached element. Not possible to copy/cut
	 * elements not synchronized inside of the tree.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize the source session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get the source element;</li>
	 * 	<li>Change the source element by invoking for example
	 * 	{@link Element#setParent(Object)}. At this point, the element is
	 * 	detached now;</li>
	 * 	<li>Initialize the target session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get the target element;</li>
	 * 	<li>Change the session for the source session;</li>
	 * 	<li>Try to copy the source element which was detached;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 */
	@Test
	public void copy_detachedFromElement() {
		final String sourceSessionId = "source";
		final String targetSessionId = "target";
		
		final String messageError = "Detached element. Not possible to copy/cut"
				+ " elements not synchronized inside of the tree.";
		
		String error = null;
		final long officeId = 53024;
		final long driversId = 1076;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> sourceDir = TreeAssembler.getDirectoryTree();
		Collection<Directory> targetDir = TreeAssembler.getSimpleDirectoryTree();
		try {
			transaction.initializeSession(sourceSessionId, sourceDir);
			Element<Directory> office = manager.getElementById(officeId);
			
			office.setParent(Long.MAX_VALUE);
			
			transaction.initializeSession(targetSessionId, targetDir);
			Element<Directory> drivers = manager.getElementById(driversId);
			
			transaction.sessionCheckout(sourceSessionId);
			
			/*
			 * An error will be threw here. The office element was detached when
			 * invoked the setParent(). It would need to update() before using
			 * any TreeManager operation.
			 */
			manager.copy(office, drivers);
		} catch (TreeException e) {
			error = e.getMessage();
		} finally {
			assertEquals(messageError, error);
		}
	}
	
	/**
	 * Test for the {@link TreeManager#copy(Element, Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to copy an element for
	 * inside of another element which was changed (detached) before copying.
	 * </p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to copy an element for inside of a detached element.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code>
	 * with the message: <i>&quot;Detached element. Not possible to copy/cut
	 * elements not synchronized inside of the tree.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize the target session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get the target element;</li>
	 * 	<li>Change the target element by invoking for example
	 * 	{@link Element#setParent(Object)}. At this point, the target element is
	 * 	detached now;</li>
	 * 	<li>Initialize the source session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get the source element;</li>
	 * 	<li>Try to copy the source element into the detached target element;
	 * 	</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 */
	@Test
	public void copy_detachedToElement() {
		final String sourceSessionId = "source";
		final String targetSessionId = "target";
		
		final String messageError = "Detached element. Not possible to copy/cut"
				+ " elements not synchronized inside of the tree.";
		String error = null;
		
		final long officeId = 53024;
		final long driversId = 1076;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> sourceDir = TreeAssembler.getDirectoryTree();
		Collection<Directory> targetDir = TreeAssembler.getSimpleDirectoryTree();
		
		try {
			transaction.initializeSession(targetSessionId, targetDir);
			Element<Directory> drivers = manager.getElementById(driversId);
			drivers.setParent(Long.MAX_VALUE);
			
			transaction.initializeSession(sourceSessionId, sourceDir);
			Element<Directory> office = manager.getElementById(officeId);
			
			/*
			 * An error will be threw here. The drivers element was detached
			 * when invoked the setParent(). It would need to update() before
			 * using any TreeManager operation.
			 */
			manager.copy(office, drivers);
		} catch (TreeException e) {
			error = e.getMessage();
		} finally {
			assertEquals(messageError, error);
		}
	}
	
	/**
	 * Test for the {@link TreeManager#copy(Element, Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to copy an element for
	 * inside of another tree which this tree already have an element with the
	 * same id.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to copy an element for inside of a tree which this tree already have
	 * an element with the same id.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code>
	 * with the message: <i>&quot;Duplicated ID.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize the source session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get the source element which will have the same id in the target
	 * 	tree;</li>
	 * 	<li>Initialize the target session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get the target element which have the same id than the source
	 * 	element;</li>
	 * 	<li>Change the session for the source session;</li>
	 * 	<li>Try to copy the source element into the target element;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 */
	@Test
	public void copy_duplicatedIdAnotherTree() {
		final String sourceSessionId = "source";
		final String targetSessionId = "target";
		
		final String messageError = "Duplicated ID.";
		String error = null;
		
		final long duplicatedEntryId = 77530344;
		final long system32Id = 1000;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> sourceDir = TreeAssembler.getDirectoryTree();
		Collection<Directory> targetDir = TreeAssembler.getSimpleDirectoryTree();
		
		try {
			transaction.initializeSession(sourceSessionId, sourceDir);
			
			/*
			 * The entry element already exists with the same id in the target
			 * tree session.
			 */
			Element<Directory> duplicatedEntry = manager.getElementById(
					duplicatedEntryId);
			
			transaction.initializeSession(targetSessionId, targetDir);
			Element<Directory> system32 = manager.getElementById(system32Id);
			
			transaction.sessionCheckout(sourceSessionId);
			
			/*
			 * Error trying to copy an element to the target tree session
			 * because there is an another element in the target tree with the
			 * same id.
			 */
			manager.copy(duplicatedEntry, system32);
		} catch (TreeException e) {
			error = e.getMessage();
		} finally {
			assertEquals(messageError, error);
		}
	}
	
	/**
	 * Test for the {@link TreeManager#copy(Element, Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to copy an element for
	 * inside of the same tree. Impossible to copy for the same tree because
	 * it always will generate duplicated id.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to copy an element for inside of the same tree.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code>
	 * with the message: <i>&quot;Duplicated ID.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get an element to be copied;</li>
	 * 	<li>Get another element inside of the same tree as the previous one;
	 * 	</li>
	 * 	<li>Try to copy the element. Both belong to the same tree;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 */
	@Test
	public void copy_duplicatedIdSameTree() {
		final String sessionId = "duplicatedExample";
		
		final String messageError = "Duplicated ID.";
		String error = null;
		
		final long realtekId = 94034;
		final long readmeId = 495833;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> sourceDir = TreeAssembler.getDirectoryTree();
		
		try {
			transaction.initializeSession(sessionId, sourceDir);
			Element<Directory> realtek = manager.getElementById(realtekId);
			Element<Directory> readme = manager.getElementById(readmeId);
			
			/*
			 * Impossible to copy for inside of the same tree. It always have
			 * duplicated id. 
			 */
			manager.copy(realtek, readme);
		} catch (TreeException e) {
			error = e.getMessage();
		} finally {
			assertEquals(messageError, error);
		}
	}
	
	/**
	 * Test for the {@link TreeManager#removeElement(Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to remove a detached
	 * element from the tree. Impossible to remove detached elements. The
	 * detached element needs to be updated before.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to remove a detached element from the tree.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code> with the
	 * message: <i>&quot;Detached element. Not possible to copy/cut elements not
	 * synchronized inside of the tree.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get the element to be removed;</li>
	 * 	<li>Change the element to become it as &quot;detached&quot;;</li>
	 * 	<li>Try to remove this element;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 */
	@Test
	public void removeElement_detached() {
		final String sessionId = "removeElement_detached";
		
		final String messageError = "Detached element. Not possible to copy/cut"
				+ " elements not synchronized inside of the tree.";
		String error = null;
		
		final long readmeId = 495833;
		final long adobeId = 24935;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> sourceDir = TreeAssembler.getDirectoryTree();
		
		try {
			transaction.initializeSession(sessionId, sourceDir);
			Element<Directory> readme = manager.getElementById(readmeId);
			
			/*
			 * At this point, this element becomes detached.
			 */
			readme.setParent(adobeId);
			
			/*
			 * No possible to handle trees with detached elements.
			 */
			manager.removeElement(readme);
		} catch (TreeException e) {
			error = e.getMessage();
		} finally {
			assertEquals(messageError, error);
		}
	}

	/**
	 * Test for the {@link TreeManager#removeElement(Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to remove an element
	 * which one of its children was changed (detached). Impossible to remove
	 * detached children elements. The detached element needs to be updated
	 * before.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to remove an element with a detached child.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code> with the
	 * message: <i>&quot;Detached element. Not possible to copy/cut elements not
	 * synchronized inside of the tree.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get the element to be removed;</li>
	 * 	<li>Change one of its children to become it as &quot;detached&quot;;
	 * 	</li>
	 * 	<li>Try to remove the element;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 */
	@Test
	public void remove_detachedChild() {
		final String sessionId = "remove_detachedChild";
		
		final String messageError = "Detached element. Not possible to copy/cut"
				+ " elements not synchronized inside of the tree.";
		String error = null;
		
		final long sdkId = 113009;
		final String filesName = "files";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> sourceDir = TreeAssembler.getDirectoryTree();
		
		try {
			transaction.initializeSession(sessionId, sourceDir);
			Element<Directory> sdk = manager.getElementById(sdkId);
			
			Element<Directory> files = null;
			for (Element<Directory> child : sdk.getChildren()) {
				files = child;
			}
			
			assertNotNull(files);
			assertEquals(filesName, files.unwrap().getName());
			
			/*
			 * At this point, the parent element should be detached because one
			 * of its children was changed.
			 */
			files.setId(Long.MAX_VALUE);
			
			/*
			 * No possible to handle trees with detached elements.
			 */
			manager.removeElement(sdk);
		} catch (TreeException e) {
			error = e.getMessage();
		} finally {
			assertEquals(messageError, error);
		}
	}
}
