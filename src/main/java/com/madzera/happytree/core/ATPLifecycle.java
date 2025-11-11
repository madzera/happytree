package com.madzera.happytree.core;

import java.util.List;

import com.madzera.happytree.TreeManager;
import com.madzera.happytree.TreeTransaction;
import com.madzera.happytree.core.TreeFactory.ATPLifecycleFactory;
import com.madzera.happytree.exception.TreeException;

/**
 * Life cycle of the API Transformation Process. Its objective is to transform a
 * linear structure of objects that behaves like a tree into a structural tree
 * itself.
 * 
 * @author Diego Madson de Andrade Nóbrega
 *
 * @param <T> the type of wrapped node inside of <code>Element</code>
 */
class ATPLifecycle<T> {

	private static final String SESSION_KEY = "sessionId";
	private static final String MANAGER_KEY = "manager";
	private static final String ROOT_KEY = "tree";
	
	private TreePipeline pipeline;
	
	/*
	 * Pipeline represents an object that will be taken from the beginning to
	 * the end of the processing chain for the entire life cycle.
	 */
	ATPLifecycle(TreePipeline pipeline) {
		this.pipeline = pipeline;
	}
	
	/*
	 * Life cycle purpose:
	 * 
	 * 1. PreValidation
	 * 2. Extraction
	 * 3. Initialization
	 * 4. Binding
	 */
	void run() throws TreeException {
		ATPLifecycleFactory lifecycleFactory= TreeFactory.lifecycleFactory();
		
		ATPPhase<T> preValidation = lifecycleFactory.initPreValidation();
		ATPPhase<T> extraction = lifecycleFactory.initExtraction();
		ATPPhase<T> initialization = lifecycleFactory.initInitialization();
		ATPPhase<T> binding = lifecycleFactory.initBinding();
		
		preValidation.next(extraction);
		extraction.next(initialization);
		initialization.next(binding);
		
		try {
			preValidation.run(pipeline);
			prepareInitializedSession();
		} catch (TreeException e) {
			/*
			 * Do not let the failed session alive.
			 */
			closeResources();
			throw e;
		}
	}
	
	/*
	 * Prepare the elements to be attached in the tree and the root element to
	 * be made available in this session.
	 */
	@SuppressWarnings("unchecked")
	private void prepareInitializedSession() {
		List<TreeElementCore<T>> tree = (List<TreeElementCore<T>>)
				pipeline.getAttribute(ROOT_KEY);
		TreeManager manager = (TreeManager) pipeline.getAttribute(MANAGER_KEY);
		
		TreeTransactionCore transaction = (TreeTransactionCore)
				manager.getTransaction();
		TreeSessionCore session = (TreeSessionCore) transaction.currentSession();
		
		/*
		 * The root element has no <code>@Id</code>, <code>@Parent</code> and
		 * wrapped object node.
		 */
		TreeElementCore<T> root = (TreeElementCore<T>) TreeFactory
				.serviceFactory().createElement(session);
		
		/*
		 * Root configuration.
		 */
		session.setRoot(root, tree);
		
		/*
		 * Save changes.
		 */
		transaction.commitTransaction();
	}

	/*
	 * Roll back in case some error occurs after the session initialization,
	 * then the own session needs to be destroyed.
	 */
	private void closeResources() {
		String sessionId = (String) pipeline.getAttribute(SESSION_KEY);
		TreeManager manager = (TreeManager) pipeline.getAttribute(MANAGER_KEY);
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.destroySession(sessionId);
	}
}
