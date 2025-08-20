package com.madzera.happytree.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

import com.madzera.happytree.Element;
import com.madzera.happytree.TreeManager;
import com.madzera.happytree.TreeTransaction;
import com.madzera.happytree.core.HappyTree;
import com.madzera.happytree.demo.model.Directory;
import com.madzera.happytree.demo.model.Metadata;
import com.madzera.happytree.demo.util.TreeAssembler;
import com.madzera.happytree.exception.TreeException;

/**
 * Test class for {@link TreeManager} operations.
 * 
 * <p>This test class represents the <i>error scenarios</i> for the operations
 * of {@link TreeManager}.</p>
 * 
 * @author Diego Madson de Andrade Nóbrega
 */
public class TreeManagerErrorTest {

	/**
	 * Test for the {@link TreeManager#createElement(Object, Object, Object)}
	 * operation.
	 * 
	 * <p>Error scenario for the create an element operation when the
	 * transaction has no defined session to run the operation.</p>
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
	 * 	<li>Verify the message error;</li>
	 * 	<li>Throw a new instance of <code>TreeException</code>.</li>
	 * </ol>
	 */
	@Test
	public void createElement_noDefinedSession() {
		final String messageError = "No defined session.";

		final String elementId = "foo";
		final String parentElementId = "bar";

		String error = null;

		try {
			TreeManager manager = HappyTree.createTreeManager();

			/*
			 * All TreeManager operations must work under a defined session.
			 */
			manager.createElement(elementId, parentElementId, null);
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw new TreeException();
			});
		}
	}
	
	/**
	 * Test for the {@link TreeManager#copy(Element, Element)} operation.
	 * 
	 * <p>Error scenario for the copy operation when the transaction has no
	 * defined session to run this operation.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to copy an element after destroying all previous sessions.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code> with the
	 * message: <i>&quot;No defined session.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the manager;</li>
	 * 	<li>Initialize two sessions, one for the source element to be copied and
	 * 	the other one for the target element;</li>
	 * 	<li>Invoke the {@link TreeTransaction#destroyAllSessions()} method;</li>
	 * 	<li>Try to copy the source element;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 */
	@Test
	public void copy_noDefinedSession() {
		final String sessionSourceId = "copy_noDefinedSession_source";
		final String sessionTargetId = "copy_noDefinedSession_target";

		final String messageError = "No defined session.";

		final long fooId = 48224;
		final long systemId = 100;

		String error = null;

		try {
			TreeManager manager = HappyTree.createTreeManager();
			TreeTransaction transaction = manager.getTransaction();

			Collection<Directory> sourceDirectoryTree = TreeAssembler.getDirectoryTree();

			Collection<Directory> targetDirectoryTree = TreeAssembler.getSimpleDirectoryTree();

			transaction.initializeSession(sessionSourceId, sourceDirectoryTree);
			Element<Directory> foo = manager.getElementById(fooId);
			transaction.initializeSession(sessionTargetId, targetDirectoryTree);
			Element<Directory> system = manager.getElementById(systemId);

			transaction.destroyAllSessions();

			manager.copy(foo, system);
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw new TreeException();
			});
		}
	}
	
	/**
	 * Test for the {@link TreeManager#cut(Element, Element)} operation.
	 * 
	 * <p>Error scenario for the cut operation when the transaction has no
	 * defined session to run this operation.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to cut an element after destroying all previous sessions.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code> with the
	 * message: <i>&quot;No defined session.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the manager;</li>
	 * 	<li>Initialize a session;</li>
	 * 	<li>Invoke the {@link TreeTransaction#destroyAllSessions()} method;</li>
	 * 	<li>Try to cut the element;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 */
	@Test
	public void cut_noDefinedSession() {
		final String sessionId = "cut_noDefinedSession";

		final String messageError = "No defined session.";

		final long fooId = 48224;
		final long happytreeId = 859452;

		String error = null;

		try {
			TreeManager manager = HappyTree.createTreeManager();
			TreeTransaction transaction = manager.getTransaction();

			Collection<Directory> directories = TreeAssembler.getDirectoryTree();

			transaction.initializeSession(sessionId, directories);
			Element<Directory> foo = manager.getElementById(fooId);
			Element<Directory> happytree = manager.getElementById(happytreeId);

			transaction.destroyAllSessions();

			manager.cut(foo, happytree);
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw new TreeException();
			});
		}
	}
	
	/**
	 * Test for the {@link TreeManager#cut(Object, Object)} operation.
	 * 
	 * <p>Error scenario for the cut operation when the transaction has no
	 * defined session to run this operation.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to cut an element by its ID after destroying all previous sessions.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code> with the
	 * message: <i>&quot;No defined session.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the manager;</li>
	 * 	<li>Initialize a session;</li>
	 * 	<li>Invoke the {@link TreeTransaction#destroyAllSessions()} method;</li>
	 * 	<li>Try to cut the element by its ID;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 */
	@Test
	public void cut_objectIdNoDefinedSession() {
		final String sessionId = "cut_objectIdNoDefinedSession";

		final String messageError = "No defined session.";

		final long fooId = 48224;
		final long happytreeId = 859452;

		String error = null;

		try {
			TreeManager manager = HappyTree.createTreeManager();
			TreeTransaction transaction = manager.getTransaction();

			Collection<Directory> directories = TreeAssembler.getDirectoryTree();

			transaction.initializeSession(sessionId, directories);
			transaction.destroyAllSessions();

			manager.cut(fooId, happytreeId);
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw new TreeException();
			});
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
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
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
			manager.createElement(elementId, parentElementId, null);
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw new TreeException();
			});
		}
	}
	
	/**
	 * Test for the {@link TreeManager#createElement(Object, Object, Object)}.
	 * 
	 * <p>Error scenario for the operations when trying to create an element
	 * with <code>null</code> id.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * try to create an element with <code>null</code> id.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>IllegalArgumentException</code>
	 * with the message: <i>&quot;Invalid null/empty argument(s).&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create the identifier for the new session;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session;</li>
	 * 	<li>Create an element with <code>null</code> id;</li>
	 * 	<li>Catch the <code>IllegalArgumentException</code>;</li>
	 * 	<li>Verify the message error;</li>
	 * 	<li>Throw a new instance of <code>TreeException</code> wrapping the
	 * 	original exception.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void createElement_nullElementId() throws TreeException {
		final String sessionId = "createElement_nullElementId";
		final String messageError = "Invalid null/empty argument(s).";
		
		String error = null;
		
		final String nullableElementId = null;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		try {
			transaction.initializeSession(sessionId, Directory.class);
			manager.createElement(nullableElementId, null, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw new TreeException(e);
			});
		}
	}

	/**
	 * Test for the {@link TreeManager#cut(Element, Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to cut an element which
	 * this element does not belong to the current session.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to cut an element inside of an incorrect session.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code>
	 * with the message: <i>&quot;Element not defined in this session.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize the source session;</li>
	 * 	<li>Get an element from the source session;</li>
	 * 	<li>Initialize the target session;</li>
	 * 	<li>Still in target session, try to cut the first element from the
	 * 	source session;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 */
	@Test
	public void cut_wrongSessionElement() {
		final String sourceSession = "source";
		final String targetSession = "target";
		
		final String messageError = "Element not defined in this session.";
		String error = null;
		
		final Long dreamweaverId = 502010l;
		final Long driversId = 1076l;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.
				getDirectoryTree();
		Collection<Directory> simpleDirectories = TreeAssembler.
				getSimpleDirectoryTree();
		
		try {
			transaction.initializeSession(sourceSession, directories);
			Element<Directory> dreamweaver = manager.getElementById(
					dreamweaverId);
			
			transaction.initializeSession(targetSession, simpleDirectories);
			Element<Directory> drivers = manager.getElementById(
					driversId);
			
			/*
			 * The current session is pointing to the target, but the
			 * Dreamweaver element belongs to source session.
			 */
			manager.cut(dreamweaver, drivers);
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
		}
	}
	
	/**
	 * Test for the {@link TreeManager#cut(Element, Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to cut an element for
	 * inside of another element in other tree which this tree is not active.
	 * </p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to cut an element for inside of another element in other tree which
	 * this tree is not active.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code>
	 * with the message: <i>&quot;No active session.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize the source session;</li>
	 * 	<li>Get an element from the source session;</li>
	 * 	<li>Initialize the target session;</li>
	 * 	<li>Get an element from the target session;</li>
	 * 	<li>Deactivate the target session by invoking
	 * 	{@link TreeTransaction#deactivateSession(String)};</li>
	 * 	<li>Still in target session, try to cut the source element from the
	 * 	source session for inside of the target session, which is deactivated;
	 * 	</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void cut_deactivatedTargetSession() throws TreeException {
		final String sourceSession = "source";
		final String targetSession = "target";
		
		final String messageError = "No active session.";
		String error = null;
		
		final Long administratorId = 47592l;
		final Long systemId = 100l;
		final Long usersId = 38923l;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.
				getDirectoryTree();
		Collection<Directory> simpleDirectories = TreeAssembler.
				getSimpleDirectoryTree();
		
		try {
			transaction.initializeSession(sourceSession, directories);
			Element<Directory> administrator = manager.getElementById(
					administratorId);
			
			transaction.initializeSession(targetSession, simpleDirectories);
			Element<Directory> system = manager.getElementById(
					systemId);
			
			transaction.sessionCheckout(sourceSession);
			transaction.deactivateSession(targetSession);
			
			manager.cut(administrator, system);
		} catch (TreeException e) {
			error = e.getMessage();
			assertTrue(manager.containsElement(usersId, administratorId));
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
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
	 * 	<li>Verify the message error;</li>
	 * 	<li>Throw a new instance of <code>TreeException</code> wrapping the
	 * 	original exception.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
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
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw new TreeException(e);
			});
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
	 * 	<li>Verify the message error;</li>
	 * 	<li>Throw a new instance of <code>TreeException</code> wrapping the
	 * 	original exception.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
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
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw new TreeException(e);
			});
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
	 * 	<li>Verify the message error;</li>
	 * 	<li>Throw a new instance of <code>TreeException</code> wrapping the
	 * 	original exception.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
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
		
		Collection<Directory> directories = TreeAssembler.getDirectoryTree();
		
		try {
			transaction.initializeSession(sessionId, directories);
			manager.cut(nullableId, programFilesId);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw new TreeException(e);
			});
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
	 * 	<li>Verify the message error;</li>
	 * 	<li>Throw a new instance of <code>TreeException</code> wrapping the
	 * 	original exception.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void cut_notExistingFromObjectId() throws TreeException {
		final String sessionId = "cut_notExistingFromObjectId";
		final String messageError = "No possible to copy/cut elements. "
			+ "Source element not found.";
		
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
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw new TreeException(e);
			});
		}
	}
	
	/**
	 * Test for the {@link TreeManager#cut(Object, Object)}.
	 * 
	 * <p>Error scenario for this operation when trying to cut an element
	 * which one is an {@link Element} object and the other one is a
	 * <code>Long</code> type representing its own object ID, so both are
	 * different from each other. It occurs because the Java implicitly assign
	 * the method invocation for {@link TreeManager#cut(Object, Object)} version.
	 * </p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to cut the <code>from</code> element inside of <code>to</code>
	 * element when they have different types.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code> with the
	 * message: <i>&quot;Mismatch type error. Incompatible parameterized type
	 * tree.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Declare two object IDs;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session by API Transformation Process using a
	 * 	previous assembled tree;</li>
	 * 	<li>Get an element by ID through the (tmpId) object ID;</li>
	 * 	<li>Try to cut by using the {@link TreeManager#cut(Object, Object)} 
	 * 	version which the first argument is the element obtained in the previous
	 * 	step and the second one is the own object ID from (administratorId)
	 * 	element;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error;</li>
	 * 	<li>Throw a new instance of <code>TreeException</code> wrapping the
	 * 	original exception.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void cut_diffArgsTypes() throws TreeException {
		final String sourceSession = "cut_diffArgsTypes";

		final String messageError = "Mismatch type error. Incompatible"
				+ " parameterized type tree.";

		String error = null;

		final Long tmpId = 583950L;
		final Long administratorId = 47592L;

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		Collection<Directory> directories = TreeAssembler.getDirectoryTree();

		try {
			transaction.initializeSession(sourceSession, directories);
			Element<Directory> tmp = manager.getElementById(tmpId);

			manager.cut(tmp, administratorId);
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
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
	 * with the message: <i>&quot;No possible to handle the root of the tree.
	 * Consider using a transaction to clone trees.&quot;</i>
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
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 */
	@Test
	public void cut_rootToElement() {
		final String sourceSessionId = "source";
		final String targetSessionId = "target";
		
		final long windowsId = 1;
		
		final String messageError = "No possible to handle the root of the tree."
				+ " Consider using a transaction to clone trees.";
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
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
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
	 * message: <i>&quot;No possible to copy/cut/remove elements. Invalid
	 * lifecycle state.&quot;</i>
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
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 */
	@Test
	public void cut_detachedElement() {
		final String sessionId = "cut_detachedElement";
		final String messageError = "No possible to copy/cut/remove elements."
				+ " Invalid lifecycle state.";
		
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
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
		}
	}
	
	/**
	 * Test for the {@link TreeManager#cut(Element, Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to cut an element
	 * which one of its children was changed (detached). Impossible to cut
	 * detached children elements. The detached element needs to be updated
	 * before.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to cut an element with a detached child.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code> with the
	 * message: <i>&quot;No possible to copy/cut/remove elements. Invalid
	 * lifecycle state.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get the element to be cut;</li>
	 * 	<li>Change one of its children to become it as &quot;detached&quot;;
	 * 	</li>
	 * 	<li>Try to cut the element;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 */
	@Test
	public void cut_detachedChild() {
		final String sessionId = "cut_detachedChild";
		
		final String messageError = "No possible to copy/cut/remove elements."
				+ " Invalid lifecycle state.";
		String error = null;
		
		final long sdkId = 113009;
		final long binId = 7753032;
		final String filesName = "files";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> sourceDir = TreeAssembler.getDirectoryTree();
		
		try {
			transaction.initializeSession(sessionId, sourceDir);
			Element<Directory> sdk = manager.getElementById(sdkId);
			Element<Directory> bin = manager.getElementById(binId);
			
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
			manager.cut(sdk, bin);
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
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
	 * message: <i>&quot;Duplicate ID.&quot;</i>
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
	 * 	&quot;System32&quot; element of the target tree;</li>
	 * 	<li>Catch the <code>TreeException</code> because &quot;Entry&quot;
	 * 	element already exists in target tree;</li>
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 */
	@Test
	public void cut_toAnotherTreeDuplicateId() {
		final String sourceSessionId = "source";
		final String targetSessionId = "target";
		
		final String messageError = "Duplicate ID.";
		
		String error = null;
		final long entryId = 77530344;
		final long system32Id = 1000;
		
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
			Element<Directory> system32 = manager.getElementById(system32Id);
			assertNotNull(system32);
			
			transaction.sessionCheckout(sourceSessionId);
			
			/*
			 * Duplicate Id error.
			 */
			manager.cut(sourceEntry, system32);
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
		}
	}
	
	/**
	 * Test for the {@link TreeManager#cut(Element, Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to cut an element for
	 * inside of another element in another tree which this element has the
	 * same id.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try cut an element for inside of another element in another tree
	 * which this element has the same id.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code> with the
	 * message: <i>&quot;Duplicate ID.&quot;</i>
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
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 */
	@Test
	public void cut_toAnotherTreeElementDuplicateId() {
		final String sourceSessionId = "source";
		final String targetSessionId = "target";
		
		final String messageError = "Duplicate ID.";
		
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
			 * Duplicate Id error.
			 */
			manager.cut(sourceEntry, targetEntry);
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
		}
	}
	
	/**
	 * Test for the {@link TreeManager#cut(Element, Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to cut an element for
	 * inside of another tree which the source element has a child with the same
	 * id as one of the children of the target element.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to cut an element for inside of another tree which the source
	 * element has a child with the same id as one of the children of the target
	 * element.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code>
	 * with the message: <i>&quot;Duplicate ID.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize the target session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get the target element which the source element will be inside;</li>
	 * 	<li>Initialize the source session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get the source element that one of its children will have the same
	 * 	id as one of the children of the target element;</li>
	 * 	<li>Try to cut the source element into the target element;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 */
	@Test
	public void cut_duplicateChildId() {
		final String sourceSessionId = "source";
		final String targetSessionId = "target";
		
		final String messageError = "Duplicate ID.";
		String error = null;
		
		final long sourceDriversId = 220332;
		final long targetDriversId = 1076;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> sourceDir = TreeAssembler.getDirectoryTree();
		Collection<Directory> targetDir = TreeAssembler.getSimpleDirectoryTree();
		
		try {
			transaction.initializeSession(targetSessionId, targetDir);
			Element<Directory> targetDrivers = manager.getElementById(
					targetDriversId);
			transaction.initializeSession(sourceSessionId, sourceDir);
			Element<Directory> sourceDrivers = manager.getElementById(
					sourceDriversId);
			
			/*
			 * An error will occur here, because the source drivers directory
			 * has a child with the same id than the target drivers directory
			 * 'Entry'.
			 */
			manager.cut(sourceDrivers, targetDrivers);
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
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
	 * inside of another tree which this tree has different type of object
	 * (<code>Directory</code> -&gt; <code>Metadata</code>).</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>,
	 * <code>Metadata</code> and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to cut an element for inside of another tree containing a different
	 * type of object.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code> with the
	 * message: <i>&quot;Mismatch type error. Incompatible parameterized type
	 * tree.&quot;</i>
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
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 * 
	 */
	@Test
	public void cut_toAnotherTreeType() {
		final String source = "source";
		final String target = "target";
		final String messageError = "Mismatch type error. Incompatible"
				+ " parameterized type tree.";
		
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
			 * instead cut(Element, Element).
			 */
			manager.cut(mp4, type);
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
		}
	}
	
	/**
	 * Test for the {@link TreeManager#copy(Element, Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to copy an element which
	 * this element does not belong to the current session.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to copy an element inside of an incorrect current session.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code>
	 * with the message: <i>&quot;Element not defined in this session.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize the source session;</li>
	 * 	<li>Get an element from the source session;</li>
	 * 	<li>Initialize the target session;</li>
	 * 	<li>Still in target session, try to copy the first element from the
	 * 	source session into the target element through of incorrect current
	 * 	session;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 */
	@Test
	public void copy_wrongSessionElement() {
		final String sourceSession = "source";
		final String targetSession = "target";
		
		final String messageError = "Element not defined in this session.";
		String error = null;
		
		final Long dreamweaverId = 502010l;
		final Long driversId = 1076l;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.
				getDirectoryTree();
		Collection<Directory> simpleDirectories = TreeAssembler.
				getSimpleDirectoryTree();
		
		try {
			transaction.initializeSession(sourceSession, directories);
			Element<Directory> dreamweaver = manager.getElementById(
					dreamweaverId);
			
			transaction.initializeSession(targetSession, simpleDirectories);
			Element<Directory> drivers = manager.getElementById(
					driversId);
			
			/*
			 * The current session is pointing to the target, but the
			 * Dreamweaver element belongs to source session.
			 */
			manager.copy(dreamweaver, drivers);
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
		}
	}
	
	/**
	 * Test for the {@link TreeManager#copy(Element, Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to copy an element for
	 * inside of another element in other tree which this tree is not active.
	 * </p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to copy an element for inside of another element in other tree which
	 * this tree is not active.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code>
	 * with the message: <i>&quot;No active session.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize the source session;</li>
	 * 	<li>Get an element from the source session;</li>
	 * 	<li>Initialize the target session;</li>
	 * 	<li>Get an element from the target session;</li>
	 * 	<li>Deactivate the target session by invoking
	 * 	{@link TreeTransaction#deactivateSession(String)};</li>
	 * 	<li>Still in target session, try to copy the source element from the
	 * 	source session for inside of the target session, which is deactivated;
	 * 	</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void copy_deactivatedTargetSession() throws TreeException {
		final String sourceSession = "source";
		final String targetSession = "target";
		
		final String messageError = "No active session.";
		String error = null;
		
		final Long administratorId = 47592l;
		final Long systemId = 100l;
		final Long usersId = 38923l;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.
				getDirectoryTree();
		Collection<Directory> simpleDirectories = TreeAssembler.
				getSimpleDirectoryTree();
		
		try {
			transaction.initializeSession(sourceSession, directories);
			Element<Directory> administrator = manager.getElementById(
					administratorId);
			
			transaction.initializeSession(targetSession, simpleDirectories);
			Element<Directory> system = manager.getElementById(
					systemId);
			
			transaction.sessionCheckout(sourceSession);
			transaction.deactivateSession(targetSession);
			
			manager.copy(administrator, system);
		} catch (TreeException e) {
			error = e.getMessage();
			assertTrue(manager.containsElement(usersId, administratorId));
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
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
	 * 	<li>Verify the message error;</li>
	 * 	<li>Throw a new instance of <code>TreeException</code> wrapping the
	 * 	message error and original exception.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
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
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw new TreeException(messageError, e);
			});
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
	 * 	<li>Verify the message error;</li>
	 * 	<li>Throw a new instance of <code>TreeException</code> wrapping the
	 * 	message error and original exception.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
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
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw new TreeException(messageError, e);
			});
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
	 * with the message: <i>&quot;No possible to handle the root of the tree.
	 * Consider using a transaction to clone trees.&quot;</i>
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
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 */
	@Test
	public void copy_rootToElement() {
		final String sourceSessionId = "source";
		final String targetSessionId = "target";
		
		final long windowsId = 1;
		
		final String messageError = "No possible to handle the root of the tree."
				+ " Consider using a transaction to clone trees.";
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
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
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
	 * with the message: <i>&quot;No possible to copy/cut/remove elements.
	 * Invalid lifecycle state.&quot;</i>
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
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 */
	@Test
	public void copy_detachedFromElement() {
		final String sourceSessionId = "source";
		final String targetSessionId = "target";
		
		final String messageError = "No possible to copy/cut/remove elements."
				+ " Invalid lifecycle state.";
		
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
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
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
	 * with the message: <i>&quot;No possible to copy/cut/remove elements.
	 * Invalid lifecycle state.&quot;</i>
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
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 */
	@Test
	public void copy_detachedToElement() {
		final String sourceSessionId = "source";
		final String targetSessionId = "target";
		
		final String messageError = "No possible to copy/cut/remove elements."
				+ " Invalid lifecycle state.";
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
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
		}
	}
	
	/**
	 * Test for the {@link TreeManager#copy(Element, Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to copy an element for
	 * inside of another one which one of its children was changed (detached).
	 * The target detached element needs to be updated before.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to copy an element for inside another one, which this one has one of
	 * its children detached.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code> with the
	 * message: <i>&quot;No possible to copy/cut/remove elements. Invalid
	 * lifecycle state.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize the source session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get the source element to be copied;</li>
	 * 	<li>Initialize the target session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get the target element which will contain the copied element;</li>
	 * 	<li>Change one of the target children to become it as
	 * 	&quot;detached&quot;;</li>
	 * 	<li>Change the session for the source session;</li>
	 * 	<li>Try to copy the source element;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 */
	@Test
	public void copy_detachedChild() {
		final String sourceSessionId = "source";
		final String targetSessionId = "target";
		
		final String messageError = "No possible to copy/cut/remove elements."
				+ " Invalid lifecycle state.";
		String error = null;
		
		final long sdkId = 113009;
		final long system32Id = 1000;
		final String driversName = "drivers";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> sourceDir = TreeAssembler.getDirectoryTree();
		Collection<Directory> targetDir = TreeAssembler.getSimpleDirectoryTree();
		
		try {
			transaction.initializeSession(sourceSessionId, sourceDir);
			Element<Directory> sdk = manager.getElementById(sdkId);
			
			transaction.initializeSession(targetSessionId, targetDir);
			Element<Directory> system32 = manager.getElementById(system32Id);
			
			Element<Directory> drivers = null;
			for (Element<Directory> child : system32.getChildren()) {
				drivers = child;
			}
			
			assertNotNull(drivers);
			assertEquals(driversName, drivers.unwrap().getName());
			
			/*
			 * Here the child of the target element is ready to be detached.
			 */
			drivers.setId(Long.MAX_VALUE);
			
			transaction.sessionCheckout(sourceSessionId);
			manager.copy(sdk, system32);
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
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
	 * with the message: <i>&quot;Duplicate ID.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize the source session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get the source element which will have the same id in the target
	 * 	tree;</li>
	 * 	<li>Initialize the target session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get a target element which the tree where it belongs already have
	 * 	the same source id element;</li>
	 * 	<li>Change the session for the source session;</li>
	 * 	<li>Try to copy the source element into the target element;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 */
	@Test
	public void copy_toAnotherTreeDuplicateId() {
		final String sourceSessionId = "source";
		final String targetSessionId = "target";
		
		final String messageError = "Duplicate ID.";
		String error = null;
		
		final long duplicateEntryId = 77530344;
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
			Element<Directory> duplicateEntry = manager.getElementById(
					duplicateEntryId);
			
			transaction.initializeSession(targetSessionId, targetDir);
			Element<Directory> system32 = manager.getElementById(system32Id);
			
			transaction.sessionCheckout(sourceSessionId);
			
			/*
			 * Error trying to copy an element to the target tree session
			 * because there is an another element in the target tree with the
			 * same id.
			 */
			manager.copy(duplicateEntry, system32);
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
		}
	}
	
	/**
	 * Test for the {@link TreeManager#copy(Element, Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to copy an element for
	 * inside of another element in another tree which this element has the
	 * same id.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to copy an element for inside of another element in another tree
	 * which this element already have same id.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code>
	 * with the message: <i>&quot;Duplicate ID.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize the source session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get the source element which will have the same id in the target
	 * 	tree;</li>
	 * 	<li>Initialize the target session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get a target element which the tree where it belongs already have
	 * 	the same source id element;</li>
	 * 	<li>Change the session for the source session;</li>
	 * 	<li>Try to copy the source element into the target element;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 */
	@Test
	public void copy_toAnotherTreeElementDuplicateId() {
		final String sourceSessionId = "source";
		final String targetSessionId = "target";
		
		final String messageError = "Duplicate ID.";
		
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
			 * Duplicate Id error.
			 */
			manager.copy(sourceEntry, targetEntry);
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
		}
	}
	
	/**
	 * Test for the {@link TreeManager#copy(Element, Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to copy an element for
	 * inside of another tree which the source element has a child with the same
	 * id as one of the children of the target element.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to copy an element for inside of another tree which the source
	 * element has a child with the same id as one of the children of the target
	 * element.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code>
	 * with the message: <i>&quot;Duplicate ID.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize the target session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get the target element which the source element will be inside;</li>
	 * 	<li>Initialize the source session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get the source element that one of its children will have the same
	 * 	id as one of the children of the target element;</li>
	 * 	<li>Try to copy the source element into the target element;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 */
	@Test
	public void copy_duplicateChildId() {
		final String sourceSessionId = "source";
		final String targetSessionId = "target";
		
		final String messageError = "Duplicate ID.";
		String error = null;
		
		final long sourceDriversId = 220332;
		final long targetDriversId = 1076;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> sourceDir = TreeAssembler.getDirectoryTree();
		Collection<Directory> targetDir = TreeAssembler.getSimpleDirectoryTree();
		
		try {
			transaction.initializeSession(targetSessionId, targetDir);
			Element<Directory> targetDrivers = manager.getElementById(
					targetDriversId);
			transaction.initializeSession(sourceSessionId, sourceDir);
			Element<Directory> sourceDrivers = manager.getElementById(
					sourceDriversId);
			
			/*
			 * An error will occur here, because the source drivers directory
			 * has a child with the same id than the target drivers directory.
			 */
			manager.copy(sourceDrivers, targetDrivers);
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
		}
	}

	/**
	 * Test for the {@link TreeManager#copy(Element, Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to copy an element for
	 * inside of the same tree. Impossible to copy for the same tree because
	 * it always will generate duplicate id.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to copy an element for inside of the same tree.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code>
	 * with the message: <i>&quot;Duplicate ID.&quot;</i>
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
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 */
	@Test
	public void copy_duplicateIdSameTree() {
		final String sessionId = "duplicateExample";
		
		final String messageError = "Duplicate ID.";
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
			 * duplicate id. 
			 */
			manager.copy(realtek, readme);
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
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
	 * message: <i>&quot;No possible to copy/cut/remove elements. Invalid
	 * lifecycle state.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get the element to be removed;</li>
	 * 	<li>Change the element to become it as &quot;detached&quot;;</li>
	 * 	<li>Try to remove this element;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 */
	@Test
	public void removeElement_detachedElement() {
		final String sessionId = "removeElement_detachedElement";
		
		final String messageError = "No possible to copy/cut/remove elements."
				+ " Invalid lifecycle state.";
		String error = null;
		
		final long readmeId = 495833;
		final long adobeId = 24935;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.getDirectoryTree();
		
		try {
			transaction.initializeSession(sessionId, directories);
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
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
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
	 * message: <i>&quot;No possible to copy/cut/remove elements. Invalid
	 * lifecycle state.&quot;</i>
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
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 */
	@Test
	public void removeElement_detachedChild() {
		final String sessionId = "remove_detachedChild";
		
		final String messageError = "No possible to copy/cut/remove elements."
				+ " Invalid lifecycle state.";
		String error = null;
		
		final long sdkId = 113009;
		final String filesName = "files";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.getDirectoryTree();
		
		try {
			transaction.initializeSession(sessionId, directories);
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
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
		}
	}
	
	/**
	 * Test for the {@link TreeManager#removeElement(Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to remove the root of
	 * the tree.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to remove the root element.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code> with the
	 * message: <i>&quot;No possible to handle the root of the tree. Consider
	 * using a transaction to clone trees.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get the root of the tree;</li>
	 * 	<li>Try to remove the root element;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 */
	@Test
	public void removeElement_rootElement() {
		final String sessionId = "removeElement_rootElement";
		
		final String messageError = "No possible to handle the root of the tree."
				+ " Consider using a transaction to clone trees.";
		String error = null;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.getDirectoryTree();
		
		try {
			transaction.initializeSession(sessionId, directories);
			Element<Directory> root = transaction.currentSession().tree();
			
			/*
			 * Impossible to remove the root element.
			 */
			manager.removeElement(root);
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
		}
	}
	
	/**
	 * Test for the {@link TreeManager#removeElement(Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to remove an element
	 * from inside of a tree which this tree has different type of wrapped
	 * object (<code>Directory</code> != <code>Metadata</code>).</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>,
	 * <code>Metadata</code> and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to remove an element from inside of a tree which this tree has 
	 * different type of object.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code> with the
	 * message: <i>&quot;Mismatch type error. Incompatible parameterized type
	 * tree.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize two sessions, the respective source and target. Both
	 * 	previously loaded from <code>TreeAssembler</code>;</li>
	 * 	<li>Get the element typified by <code>Metadata</code>;</li>
	 * 	<li>Change the session for the tree typified by <code>Directory</code>;
	 * 	</li>
	 * 	<li>Try to remove an element typified by <code>Metadata</code> from
	 * 	inside of the tree typified by <code>Directory</code>;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 * 
	 */
	@Test
	public void removeElement_mismatchElement() {
		final String source = "source";
		final String target = "target";
		final String messageError = "Mismatch type error. Incompatible"
				+ " parameterized type tree.";
		
		String error = null;
		
		final String typeId = "type";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.
				getDirectoryTree();
		Collection<Metadata> metadata = TreeAssembler.
				getMetadataTree();
		
		try {
			transaction.initializeSession(source, directories);
			transaction.initializeSession(target, metadata);
			Element<Metadata> type = manager.getElementById(typeId);
			
			transaction.sessionCheckout(source);
			manager.removeElement(type);
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
		}
	}
	
	/**
	 * Test for the {@link TreeManager#persistElement(Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to persist an element
	 * with a <code>null</code> argument value.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to persist an element with a <code>null</code> argument value.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>IllegalArgumentException</code>
	 * with the message: <i>&quot;Invalid null/empty argument(s).&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session;</li>
	 * 	<li>Declare a <code>null</code> element;</li>
	 * 	<li>Try to persist the element;</li>
	 * 	<li>Catch the <code>IllegalArgumentException</code>;</li>
	 * 	<li>Verify the message error;</li>
	 * 	<li>Throw a new instance of <code>TreeException</code> wrapping the
	 * 	message error and original exception.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void persistElement_nullElement() throws TreeException {
		final String sessionId = "persistElement_nullElement";
		final String messageError = "Invalid null/empty argument(s).";
		String error = null;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		try {
			transaction.initializeSession(sessionId, Directory.class);
			Element<Directory> nullableElement = null;
			manager.persistElement(nullableElement);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw new TreeException(messageError, e);
			});
		}
	}
	
	/**
	 * Test for the {@link TreeManager#persistElement(Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to persist an element
	 * with a <code>null</code> id.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to persist an element with a <code>null</code> id.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>IllegalArgumentException</code>
	 * with the message: <i>&quot;Invalid null/empty argument(s).&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session;</li>
	 * 	<li>Create an element with <code>null</code> id attribute value;</li>
	 * 	<li>Try to persist the element;</li>
	 * 	<li>Catch the <code>IllegalArgumentException</code>;</li>
	 * 	<li>Verify the message error;</li>
	 * 	<li>Throw a new instance of <code>TreeException</code> wrapping the
	 * 	message error and original exception.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void persistElement_nullIdElement() throws TreeException {
		final String sessionId = "persistElement_nullIdElement";
		final String messageError = "Invalid null/empty argument(s).";
		String error = null;
		
		Object nullableId = null;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		try {
			transaction.initializeSession(sessionId, Directory.class);
			Element<Directory> element = manager.createElement(nullableId,
					Long.MAX_VALUE, null);
			manager.persistElement(element);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw new TreeException(messageError, e);
			});
		}
	}
	
	/**
	 * Test for the {@link TreeManager#persistElement(Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to persist an element
	 * for inside the tree which it already has another element with the same id.
	 * </p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to persist an element with duplicate id.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code>
	 * with the message: <i>&quot;Duplicate ID.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Create an element with duplicate id;</li>
	 * 	<li>Try to persist this element;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 */
	@Test
	public void persistElement_duplicateId() {
		final String sessionId = "persistElement_nullElement";
		
		final String messageError = "Duplicate ID.";
		String error = null;
		
		Object duplicateWinampExeId = 395524L;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.getDirectoryTree();
		
		try {
			transaction.initializeSession(sessionId, directories);
			Element<Directory> element = manager.createElement(
					duplicateWinampExeId, null, null);
			manager.persistElement(element);
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
		}
	}
	
	/**
	 * Test for the {@link TreeManager#persistElement(Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to persist an element
	 * into a tree which the element to be persisted has a child with the same
	 * id as one of the elements in the tree.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to persist an element into a tree which the element to be persisted
	 * has a child with the same id as one of the elements in the tree.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code>
	 * with the message: <i>&quot;Duplicate ID.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Create two elements, which one of them has the duplicate id related
	 * 	to the tree which this element will be persisted;</li>
	 * 	<li>Put the element with the duplicate id as the child of the other;
	 * 	</li>
	 * 	<li>Try to persist the element that its child has the duplicate id;
	 * 	</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 */
	@Test
	public void persistElement_duplicateChildId() {
		final String sessionId = "persistElement_duplicateChildId";
		
		final String messageError = "Duplicate ID.";
		String error = null;
		
		final long id = Long.MAX_VALUE;
		final long duplicateId = 77530344;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.getDirectoryTree();
		
		try {
			transaction.initializeSession(sessionId, directories);
			
			/*
			 * These elements, when created, they have the NOT_EXISTED state.
			 */
			Element<Directory> element = manager.createElement(id, null, null);
			Element<Directory> childElement = manager.createElement(
					duplicateId, id, null);
			
			element.addChild(childElement);
			
			/*
			 * Error trying to persist an element which this element has a child
			 * with duplicate id.
			 */
			manager.persistElement(element);
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
		}
	}

	/**
	 * Test for the {@link TreeManager#persistElement(Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to persist an element
	 * into a tree which this element to be persisted has a child with the same
	 * id as one of the elements inside of its own children list.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Metadata</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to persist an element into a tree which the element to be persisted
	 * has a child with the same id as one of the elements inside of its own
	 * children list.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code>
	 * with the message: <i>&quot;Duplicate ID.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Create four elements in which two of them have duplicate IDs;</li>
	 * 	<li>Put the elements inside each other and so on;</li>
	 * 	<li>Try to persist the element that represents the parent element of
	 * 	others;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 */
	@Test
	public void persistElement_duplicateOwnChildId() {
		final String sessionId = "persistElement_duplicateOwnChildId";
		final String messageError = "Duplicate ID.";
		String error = null;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		final String musicId = "Music";
		final String categoryId = "Category";
		final String albumId = "AlbumId";
		final String duplicateCategoryId = categoryId;
		
		try {
			transaction.initializeSession(sessionId, Metadata.class);
			
			Element<Metadata> music = manager.createElement(musicId, null,
					null);
			Element<Metadata> category = manager.createElement(categoryId, null,
					null);
			Element<Metadata> album = manager.createElement(albumId, null, null);
			Element<Metadata> duplicateCategory = manager.createElement(
					duplicateCategoryId, null, null);
			
			music.addChild(category);
			category.addChild(album);
			album.addChild(duplicateCategory);
			
			/*
			 * Error trying to persist an element which the own element has
			 * duplicate children Id.
			 */
			manager.persistElement(music);
			
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
		}
	}
	
	/**
	 * Test for the {@link TreeManager#persistElement(Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to persist an element
	 * which already existed in the tree.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to persist an element which already existed in the tree.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code>
	 * with the message: <i>&quot;No possible to persist the element. Invalid
	 * lifecycle state.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get an existing element from this tree;</li>
	 * 	<li>Change the id of this element with a not existing id, to change the
	 * 	element status from &quot;ATTACHED&quot; to &quot;DETACHED&quot;;</li>
	 * 	<li>Try to persist the element;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 */
	@Test
	public void persistElement_existedElement() {
		final String sessionId = "persistElement_existedElement";
		
		final String messageError = "No possible to persist the element. Invalid"
				+ " lifecycle state.";
		String error = null;
		
		final long filesId = 8484934;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.getDirectoryTree();
		
		try {
			transaction.initializeSession(sessionId, directories);
			
			Element<Directory> element = manager.getElementById(filesId);
			
			/*
			 * Here, the element pass from ATTACHED to DETACHED.
			 */
			element.setId(Long.MAX_VALUE);
			
			/*
			 * Error trying to persist an detached element. This is no possible
			 * to persist ATTACHED neither DETACHED elements, only NOT_EXISTED.
			 */
			manager.persistElement(element);
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
		}
	}
	
	/**
	 * Test for the {@link TreeManager#persistElement(Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to persist an element
	 * into a tree which the tree has different type of object.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>,
	 * <code>Metadata</code> and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to persist an element into a tree which the tree has different type
	 * of object.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code> with the
	 * message: <i>&quot;Mismatch type error. Incompatible parameterized type
	 * tree.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session, previously loaded from
	 * 	<code>TreeAssembler</code> with the <code>Directory</code> type;</li>
	 * 	<li>Create an element with the <code>Metadata</code> type;</li>
	 * 	<li>Try to persist the mismatch element;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 */
	@Test
	public void persistElement_mismatchElement() {
		final String sessionId = "persistElement_mismatchElement";
		
		final String messageError = "Mismatch type error. Incompatible"
				+ " parameterized type tree.";
		String error = null;
		
		final String foo = "foo";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.getDirectoryTree();
		
		try {
			transaction.initializeSession(sessionId, directories);
			Element<Metadata> mismatchElement = manager.createElement(foo, null,
					null);
			Metadata metadata = new Metadata(foo, null, foo);
			mismatchElement.wrap(metadata);
			
			manager.persistElement(mismatchElement);
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
		}
	}
	
	/**
	 * Test for the {@link TreeManager#persistElement(Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to persist an attached
	 * element as a child of a new (and detached) element to be persisted.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to persist an element that has a child which is already attached.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code>
	 * with the message: <i>&quot;No possible to persist the element. Invalid 
	 * lifecycle state.&quot;
	 * </i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session by API Transformation Process using a
	 * 	previous assembled tree;</li>
	 * 	<li>Create two elements, &quot;foo&quot and &quot;bar&quot;;</li>
	 * 	<li>Persist the &quot;bar&quot; element;</li>
	 * 	<li>Add the &quot;bar&quot; element as a child of &quot;foo&quot;;</li>
	 * 	<li>Try to persist the &quot;foo&quot; element;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 */
	@Test
	public void persistElement_detachedChildElement() {
		final String sessionId = "persistElement_detachedChildElement";
		
		final String messageError = "No possible to persist the element. "
			+ "Invalid lifecycle state.";
		String error = null;
		
		final String foo = "foo";
		final String bar = "bar";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.getDirectoryTree();
		
		try {
			transaction.initializeSession(sessionId, directories);
			Element<Metadata> mismatchElement = manager.createElement(foo, null,
					null);
			Element<Metadata> attachedElement = manager.createElement(bar, null,
					null);
			
			attachedElement = manager.persistElement(attachedElement);

			mismatchElement.addChild(attachedElement);

			manager.persistElement(mismatchElement);
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
		}
	}
	
	/**
	 * Test for the {@link TreeManager#updateElement(Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to update an element
	 * with a <code>null</code> argument value.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>,
	 * <code>Metadata</code> and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to update an element with a <code>null</code> argument value.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>IllegalArgumentException</code>
	 * with the message: <i>&quot;Invalid null/empty argument(s).&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Try to get an element through a not existing id;</li>
	 * 	<li>Try to update an element with <code>null</code> argument value;</li>
	 * 	<li>Catch the <code>IllegalArgumentException</code>;</li>
	 * 	<li>Verify the message error;</li>
	 * 	<li>Throw a new instance of <code>TreeException</code> wrapping the
	 * 	message error and original exception.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void updateElement_nullElement() throws TreeException {
		final String sessionId = "updateElement_nullElement";
		final String messageError = "Invalid null/empty argument(s).";
		String error = null;
		
		Object notExistingId = Long.MAX_VALUE;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		try {
			Collection<Directory> directories = TreeAssembler.getDirectoryTree();
			transaction.initializeSession(sessionId, directories);
			Element<Directory> nullableElement = manager.getElementById(
					notExistingId);
			
			manager.updateElement(nullableElement);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw new TreeException(messageError, e);
			});
		}
	}
	
	/**
	 * Test for the {@link TreeManager#updateElement(Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to update an id of
	 * element which this new id has the same id than another element inside of
	 * the tree.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to update an id of element with this id already existing.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code>
	 * with the message: <i>&quot;Duplicate ID.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get an existing element;</li>
	 * 	<li>Change its id to another existing id inside of the tree;</li>
	 * 	<li>Try to update this element;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 */
	@Test
	public void updateElement_duplicateId() {
		final String sessionId = "updateElement_duplicateId";
		
		final String messageError = "Duplicate ID.";
		String error = null;
		
		final long ideId = 13823;
		final long readmeDuplicateId = 495833;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.getDirectoryTree();
		
		try {
			transaction.initializeSession(sessionId, directories);
			Element<Directory> ide = manager.getElementById(ideId);
			
			/*
			 * Setting the new id with the same id value than the reader
			 * element.
			 */
			ide.setId(readmeDuplicateId);
			
			manager.updateElement(ide);
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
		}
	}
	
	/**
	 * Test for the {@link TreeManager#updateElement(Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to update an id of a
	 * grand child element which this new id has the same id than another
	 * element inside of the tree.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to update an id of a grand child element with this id already
	 * existing.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code>
	 * with the message: <i>&quot;Duplicate ID.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get an existing element;</li>
	 * 	<li>Change the id of a grand child to another existing id inside of the
	 * 	tree;</li>
	 * 	<li>Try to update the element which its grand child has now the
	 * 	duplicate id;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 */
	@Test
	public void updateElement_duplicateGrandChildId() {
		final String sessionId = "updateElement_duplicateGrandChildId";
		
		final String messageError = "Duplicate ID.";
		String error = null;
		
		final long ideId = 13823;
		final long realtekId = 94034;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.getDirectoryTree();
		
		try {
			transaction.initializeSession(sessionId, directories);
			Element<Directory> realtek = manager.getElementById(realtekId);
			Collection<Element<Directory>> children = realtek.getChildren();
			Element<Directory> child = null;
			Element<Directory> files = null;
			
			for (Element<Directory> iterator : children) {
				Directory sdkDirectory = iterator.unwrap();
				if (sdkDirectory.getName().equals("sdk")) {
					child = iterator;
				}
			}
			for (Element<Directory> granChild : child.getChildren()) {
				files = granChild;
			}
			/*
			 * Changing the id of the sdk's child pointing to the same id than
			 * the IDE element.
			 */
			files.setId(ideId);
			
			/*
			 * Duplicate id error because child of sdk element has now a
			 * duplicate id.
			 */
			manager.updateElement(realtek);
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
		}
	}
	
	/**
	 * Test for the {@link TreeManager#updateElement(Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to update the root of
	 * the tree.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>
	 * and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to update the root element.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code> with the
	 * message: <i>&quot;No possible to handle the root of the tree. Consider
	 * using a transaction to clone trees.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session, previously loaded from
	 * 	<code>TreeAssembler</code>;</li>
	 * 	<li>Get the root of the tree;</li>
	 * 	<li>Change the id of the root (not necessary);</li>
	 * 	<li>Try to update the root element;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 */
	@Test
	public void updateElement_rootElement() {
		final String sessionId = "updateElement_rootElement";
		
		final String messageError = "No possible to handle the root of the tree."
				+ " Consider using a transaction to clone trees.";
		String error = null;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.getDirectoryTree();
		
		try {
			transaction.initializeSession(sessionId, directories);
			Element<Directory> root = transaction.currentSession().tree();
			
			root.setId("bar");
			
			/*
			 * Impossible to update the root element.
			 */
			manager.updateElement(root);
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
		}
	}
	
	/**
	 * Test for the {@link TreeManager#updateElement(Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to update a not existed
	 * element in the tree.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to update a not existing element in the tree.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code>
	 * with the message: <i>&quot;No possible to update the element. Invalid
	 * lifecycle state.&quot;
	 * </i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session;</li>
	 * 	<li>Create a new element;</li>
	 * 	<li>Try to update this element, which has not been persisted in the tree
	 * 	before;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 */
	@Test
	public void updateElement_notExistedElement() {
		final String sessionId = "updateElement_notExistedElement";
		
		final String messageError = "No possible to update the element. Invalid"
				+ " lifecycle state.";
		String error = null;
		
		final long id = Long.MAX_VALUE;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		try {
			transaction.initializeSession(sessionId, Directory.class);
			Element<Directory> element = manager.createElement(id, null, null);
			
			/*
			 * Error trying to update a not existed element.
			 */
			manager.updateElement(element);
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
		}
	}

	/**
	 * Test for the {@link TreeManager#updateElement(Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to update an element
	 * which it has different type of wrapped node.</p>
	 * 
	 * <p>For more details about this test, see also the <code>Directory</code>,
	 * <code>Metadata</code> and <code>TreeAssembler</code> sample classes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to update an element from a tree inside of another one.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code> with the
	 * message: <i>&quot;Mismatch type error. Incompatible parameterized type
	 * tree.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize the source session, previously loaded from
	 * 	<code>TreeAssembler</code> for the <code>Directory</code> object;
	 * 	<li>Get an element from this tree session;</li>
	 * 	<li>Initialize the target session, previously loaded from
	 * 	<code>TreeAssembler</code> for the <code>Metadata</code> object;
	 * 	<li>Invoke the {@link TreeManager#updateElement(Element)} passing
	 * 	through the element from the source tree session;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 */
	@Test
	public void updateElement_mismatchElement() {
		final String sourceSessionId = "Directory_Session";
		final String targetSessionId = "Metadata_Session";

		final String messageError = "Mismatch type error. Incompatible"
				+ " parameterized type tree.";
		String error = null;

		final long eclipseExeId = 8483742;

		Collection<Directory> directories = TreeAssembler.getDirectoryTree();
		Collection<Metadata> metadata = TreeAssembler.getMetadataTree();

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		try {
			transaction.initializeSession(sourceSessionId, directories);
			Element<Directory> eclipseExe = manager.getElementById(eclipseExeId);

			transaction.initializeSession(targetSessionId, metadata);

			/*
			 * Here, there is an attempt to update an element from a tree inside
			 * of another tree session. Mismatch error.
			 */
			manager.updateElement(eclipseExe);
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
		}
	}
	
	/**
	 * Test for the {@link TreeManager#updateElement(Element)} operation.
	 * 
	 * <p>Error scenario for this operation when trying to update an attached
	 * element when it receives a new (and detached) element as a child.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to create a new element without persisting it and add it to an
	 * attached element. After that, try to update the attached element that
	 * received the new element.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code>
	 * with the message: <i>&quot;No possible to update the element. Invalid 
	 * lifecycle state.&quot;
	 * </i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session by API Transformation Process using a
	 * 	previous assembled tree;</li>
	 * 	<li>Get the element <i>happytree</i> inside of <i>project</i> element
	 *  which is inside of the <i>devel</i> element, all of them through the
	 *  {@link TreeManager#getElementById(Element)} invocations;</li>
	 * 	<li>Create a new element and add it as a child of the <i>happytree</i>
	 * 	element;</li>
	 * 	<li>Try to update the <i>devel</i> element;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error;</li>
	 * 	<li>Assert that the exception thrown is of <code>TreeException</code>
	 * 	type.</li>
	 * </ol>
	 */
	@Test
	public void updateElement_detachedChildElement() {
		final String sessionId = "updateElement_detachedChildElement";
		final String messageError = "No possible to update the element. "
			+ "Invalid lifecycle state.";

		final long develId = 93832;
		final long projectsId = 93209;
		final long happytreeId = 859452;

		String error = null;
		Collection<Directory> directoryTree = TreeAssembler.getDirectoryTree();

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		try {
			transaction.initializeSession(sessionId, directoryTree);
			Element<Directory> develElement = manager.getElementById(develId);
			Element<Directory> projectsElement = develElement.getElementById(
				projectsId);
			Element<Directory> happytreeElement = projectsElement.
				getElementById(happytreeId);
			
			Element<Directory> detachedElement = manager.createElement(
				Integer.MAX_VALUE,
				happytreeId,
				new Directory(
					Integer.MAX_VALUE,
					happytreeId,
					"Detached Element"));
			happytreeElement.addChild(detachedElement);

			manager.updateElement(develElement);
		} catch (TreeException e) {
			error = e.getMessage();
			assertEquals(messageError, error);
			assertThrows(TreeException.class, () -> {
				throw e;
			});
		}
	}
}
