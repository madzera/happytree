package com.madzera.happytree.transaction;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.junit.Test;

import com.madzera.happytree.TreeManager;
import com.madzera.happytree.TreeTransaction;
import com.madzera.happytree.core.HappyTree;
import com.madzera.happytree.demo.model.Directory;
import com.madzera.happytree.demo.model.node.Node;
import com.madzera.happytree.demo.model.node.Node_MismatchId;
import com.madzera.happytree.demo.model.node.Node_NoId;
import com.madzera.happytree.demo.model.node.Node_NoParent;
import com.madzera.happytree.demo.model.node.Node_NoTree;
import com.madzera.happytree.exception.TreeException;

/**
 * Test class for {@link TreeTransaction} operations.
 * 
 * <p>This test class represents the <i>error scenarios</i> for the operations
 * of {@link TreeTransaction}.</p>
 * 
 * <p>This test class also includes tests related to the API Transformation
 * Process.</p>
 * 
 * @author Diego Madson de Andrade Nóbrega
 *
 */
public class TreeTransactionErrorTest {

	/**
	 * Test for the {@link TreeTransaction#initializeSession(String, Class)}.
	 * 
	 * <p>Error scenario for this operation when the session identifier is
	 * <code>null</code>.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to initialize a session with a <code>null</code> identifier.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>IllegalArgumentException</code>
	 * with the message:
	 * <i>&quot;Invalid null/empty argument(s).&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create a identifier with <code>null</code> value;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session with the <code>null</code> argument;</li>
	 * 	<li>Catch the <code>IllegalArgumentException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 * @throws TreeException in case of an error
	 */
	@Test
	public void initializeSession_nullSessionIdentifier() throws TreeException {
		final String nameTree = null;
		final String messageError = "Invalid null/empty argument(s).";
		String error = null;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		try {
			transaction.initializeSession(nameTree, Directory.class);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		} finally {
			assertEquals(messageError, error);
		}
	}
	
	/**
	 * Test for the {@link TreeTransaction#initializeSession(String, Class)}.
	 * 
	 * <p>Error scenario for this operation when the type of session is
	 * <code>null</code>.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to initialize a session with a <code>null</code> type of session.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>IllegalArgumentException</code>
	 * with the message:
	 * <i>&quot;Invalid null/empty argument(s).&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create a identifier;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session with the <code>null</code> type of session;
	 * 	</li>
	 * 	<li>Catch the <code>IllegalArgumentException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void initializeSession_nullTypeSession() throws TreeException {
		final String nameTree = "initializeSession_nullTypeSession";
		Class<?> nullableTypeSession = null;
		
		final String messageError = "Invalid null/empty argument(s).";
		String error = null;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		try {
			transaction.initializeSession(nameTree, nullableTypeSession);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		} finally {
			assertEquals(messageError, error);
		}
	}
	
	/**
	 * Test for the {@link TreeTransaction#initializeSession(String, Class)}.
	 * 
	 * <p>Error scenario for this operation when the session identifier is
	 * already initialized.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to initialize sessions with the same identifiers.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code> with the
	 * message:
	 * <i>&quot;Already existing initialized session.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create two variables with the same identifiers values;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session with the first identifier;</li>
	 * 	<li>Execute the {@link TreeTransaction#sessionCheckout(String)} to
	 * 	refresh the current session;</li>
	 * 	<li>Initialize a new session with the second identifier;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 */
	@Test
	public void initializeSession_duplicateSessionIdentifier() {
		final String nameTree = "duplicateSessionIdentifier";
		final String nameTreeDuplicate = nameTree;
		final String messageError = "Already existing initialized session.";
		String error = null;
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		try {
			transaction.initializeSession(nameTree, Directory.class);
			transaction.sessionCheckout(nameTree);
			
			/*
			 * Try to init an existing identifier session.
			 */
			transaction.initializeSession(nameTreeDuplicate, Directory.class);
		} catch (TreeException e) {
			error = e.getMessage();
		} finally {
			assertEquals(messageError, error);
		}
	}
	
	/**
	 * Test for the {@link TreeTransaction#initializeSession(String, Collection)}.
	 * 
	 * <p>Error scenario for this operation when trying to initialize a session
	 * by API Transformation Process using a model class which there is no
	 * {@code @Tree}} annotation.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to initialize a session by API Transformation Process using a model
	 * class without the {@code @Tree} annotation.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code> with the
	 * message:
	 * <i>&quot;There is no {@code @TREE} associated.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create a {@link Node_NoTree} object with no {@code @Tree}
	 * 	annotation;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session by API Transformation Process;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 */
	@Test
	public void initializeSession_atpNoTree() {
		final String sessionId = "ATP_WITH_NO_TREE_ANNOTATION";
		final String messageError = "There is no @TREE associated.";
		String error = null;
		
		Collection<Node_NoTree> nodes = new ArrayList<Node_NoTree>();
		Node_NoTree node = new Node_NoTree();
		node.setId((int) new Random().nextInt(10) * 100);
		node.setParent(null);
		node.setName("foo");
		nodes.add(node);
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		try {
			transaction.initializeSession(sessionId, nodes);
		} catch (TreeException e) {
			error = e.getMessage();
		} finally {
			assertEquals(messageError, error);
		}
	}
	
	/**
	 * Test for the {@link TreeTransaction#initializeSession(String, Collection)}.
	 * 
	 * <p>Error scenario for this operation when trying to initialize a session
	 * by API Transformation Process using a model class which there is no
	 * {@code @Id} annotation.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to initialize a session by API Transformation Process using a model
	 * class without the {@code @Id} annotation.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code> with the
	 * message:
	 * <i>&quot;There is no {@code @ID} associated.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create a {@link Node_NoId} object with no {@code @Id}
	 * 	annotation;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session by API Transformation Process;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 */
	@Test
	public void initializeSession_atpNoId() {
		final String sessionId = "ATP_WITH_NO_ID_ANNOTATION";
		final String messageError = "There is no @ID associated.";
		String error = null;
		
		Collection<Node_NoId> nodes = new ArrayList<Node_NoId>();
		Node_NoId node = new Node_NoId();
		node.setId((int) new Random().nextInt(10) * 100);
		node.setParent(null);
		node.setName("foo");
		nodes.add(node);
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		try {
			transaction.initializeSession(sessionId, nodes);
		} catch (TreeException e) {
			error = e.getMessage();
		} finally {
			assertEquals(messageError, error);
		}
	}
	
	/**
	 * Test for the {@link TreeTransaction#initializeSession(String, Collection)}.
	 * 
	 * <p>Error scenario for this operation when trying to initialize a session
	 * by API Transformation Process using a model class which there is no
	 * {@code @Parent} annotation.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to initialize a session by API Transformation Process using a model
	 * class without the {@code @Parent} annotation.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code> with the
	 * message:
	 * <i>&quot;There is no {@code @PARENT} associated.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create a {@link Node_NoParent} object with no {@code @Parent}
	 * 	annotation;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session by API Transformation Process;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 */
	@Test
	public void initializeSession_atpNoParent() {
		final String sessionId = "ATP_WITH_NO_PARENT_ANNOTATION";
		final String messageError = "There is no @PARENT associated.";
		String error = null;
		
		Collection<Node_NoParent> nodes = new ArrayList<Node_NoParent>();
		Node_NoParent node = new Node_NoParent();
		node.setId((int) new Random().nextInt(10) * 100);
		node.setParent(null);
		node.setName("foo");
		nodes.add(node);
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		try {
			transaction.initializeSession(sessionId, nodes);
		} catch (TreeException e) {
			error = e.getMessage();
		} finally {
			assertEquals(messageError, error);
		}
	}
	
	/**
	 * Test for the {@link TreeTransaction#initializeSession(String, Collection)}.
	 * 
	 * <p>Error scenario for this operation when trying to initialize a session
	 * by API Transformation Process using a source object/model input which
	 * there is a mismatch type {@code @Id} and {@code @Parent}
	 * attributes.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to initialize a session by API Transformation Process using a model
	 * class with mismatch type attributes annotated by {@code @Id} and
	 * {@code @Parent}.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code> with the
	 * message:
	 * <i>&quot;Mismatch type ID error.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create a {@link Node_MismatchId} object with mismatch type
	 * 	{@code @Id} and {@code @Parent} attributes;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session by API Transformation Process;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 */
	@Test
	public void initializeSession_atpMismatchId() {
		final String sessionId = "ATP_WITH_MISMATCH_ID_ANNOTATION";
		final String messageError = "Mismatch type ID error.";
		String error = null;
		
		Collection<Node_MismatchId> nodes = new ArrayList<Node_MismatchId>();
		Node_MismatchId node = new Node_MismatchId();
		node.setId((int) new Random().nextInt(10) * 100);
		node.setParent(0);
		node.setName("foo");
		nodes.add(node);
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		try {
			transaction.initializeSession(sessionId, nodes);
		} catch (TreeException e) {
			error = e.getMessage();
		} finally {
			assertEquals(messageError, error);
		}
	}
	
	/**
	 * Test for the {@link TreeTransaction#initializeSession(String, Collection)}.
	 * 
	 * <p>Error scenario for this operation when trying to initialize a session
	 * by API Transformation Process using an object which would will
	 * transformed with a <code>null</code> attribute {@code @Id} value.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to initialize a session by API Transformation Process using a model
	 * class with a <code>null</code> {@code @Id} value.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code> with the
	 * message:
	 * <i>&quot;Invalid null/empty argument(s).&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create a {@link Node} object with a <code>null</code> {@code @Id}
	 * 	attribute value;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session by API Transformation Process;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error
	 */
	@Test
	public void initializeSession_atpNullElementId() throws TreeException {
		final String sessionId = "ATP_WITH_NULL_ELEMENT_ID";
		final String messageError = "Invalid null/empty argument(s).";
		String error = null;
		
		Collection<Node> nodes = new ArrayList<Node>();
		Node node = new Node();
		node.setId(null);	//null Id
		node.setParent(0);
		node.setName("foo");
		nodes.add(node);
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		try {
			transaction.initializeSession(sessionId, nodes);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		} finally {
			assertEquals(messageError, error);
		}
	}
	
	/**
	 * Test for the {@link TreeTransaction#initializeSession(String, Collection)}.
	 * 
	 * <p>Error scenario for this operation when trying to initialize a session
	 * by API Transformation Process using a collection of objects having the
	 * same {@code @Id} attribute value.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to initialize a session by API Transformation Process using two
	 * objects with the same {@code @Id} attribute value.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>TreeException</code> with the
	 * message:
	 * <i>&quot;Duplicate ID.&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create two {@link Node} objects, both with the same {@code @Id}
	 * 	attribute value;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session by API Transformation Process;</li>
	 * 	<li>Catch the <code>TreeException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 */
	@Test
	public void initializeSession_atpDuplicateElementId() {
		final String sessionId = "atpDuplicateElementId";
		final String messageError = "Duplicate ID.";
		String error = null;
		
		Collection<Node> nodes = new ArrayList<Node>();
		Node node = new Node();
		node.setId(Integer.MAX_VALUE); //Same Id.
		node.setParent(null);
		node.setName("foo");
		nodes.add(node);
		
		Node node2 = new Node();
		node2.setId(Integer.MAX_VALUE); //Same Id.
		node2.setParent(null);
		node2.setName("bar");
		nodes.add(node2);
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		try {
			transaction.initializeSession(sessionId, nodes);
		} catch (TreeException e) {
			error = e.getMessage();
		} finally {
			assertEquals(messageError, error);
		}
	}

	/**
	 * Test for the {@link TreeTransaction#initializeSession(String, Collection)}.
	 * 
	 * <p>Error scenario for this operation when trying to initialize a session
	 * by API Transformation Process using a <code>null</code> collection of
	 * objects.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to initialize a session by API Transformation Process using a
	 * <code>null</code> collection of objects that would be transformed into
	 * elements.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>IllegalArgumentException</code>
	 * with the message:
	 * <i>&quot;Invalid null/empty argument(s).&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create a <code>null</code> collection variable;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session by API Transformation Process;</li>
	 * 	<li>Catch the <code>IllegalArgumentException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error different from the expected
	 */
	@Test
	public void initializeSession_atpNullCollection() throws TreeException {
		final String sessionId = "nullCollection";
		final String messageError = "Invalid null/empty argument(s).";
		
		Collection<Node> nodes = null;

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		try {
			transaction.initializeSession(sessionId, nodes);
		} catch (IllegalArgumentException e) {
			assertEquals(messageError, e.getMessage());
		}	
	}

	/**
	 * Test for the {@link TreeTransaction#initializeSession(String, Collection)}.
	 * 
	 * <p>Error scenario for this operation when trying to initialize a session
	 * by API Transformation Process using a empty collection of objects.</p>
	 * 
	 * <p><b>Test:</b></p>
	 * Try to initialize a session by API Transformation Process using a empty
	 * collection of objects that would be transformed into elements.
	 * <p><b>Expected:</b></p>
	 * An error is threw and caught by <code>IllegalArgumentException</code>
	 * with the message:
	 * <i>&quot;Invalid null/empty argument(s).&quot;</i>
	 * <p><b>Steps:</b></p>
	 * <ol>
	 * 	<li>Create a empty collection;</li>
	 * 	<li>Get the transaction;</li>
	 * 	<li>Initialize a new session by API Transformation Process;</li>
	 * 	<li>Catch the <code>IllegalArgumentException</code>;</li>
	 * 	<li>Verify the message error.</li>
	 * </ol>
	 * 
	 * @throws TreeException in case of an error different from the expected
	 */
	@Test
	public void initializeSession_atpEmptyCollection() throws TreeException {
		final String sessionId = "emptyCollection";
		final String messageError = "Invalid null/empty argument(s).";
		
		Collection<Node> nodes = new ArrayList<Node>();

		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();

		try {
			transaction.initializeSession(sessionId, nodes);
		} catch (IllegalArgumentException e) {
			assertEquals(messageError, e.getMessage());
		}	
	}
}
