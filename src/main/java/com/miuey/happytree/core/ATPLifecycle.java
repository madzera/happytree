package com.miuey.happytree.core;

import java.util.Set;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeTransaction;
import com.miuey.happytree.core.TreeFactory.ATPLifecycleFactory;
import com.miuey.happytree.exception.TreeException;

/**
 * Life cycle of the API Transformation Process. Its objective is to transform a
 * linear structure of objects that behaves like a tree into a structural tree
 * itself.
 * 
 * @author Diego Nóbrega
 *
 * @param <T> the type of object wrapped inside of <code>Element</code>
 */
class ATPLifecycle<T> {

	private static final String SESSION_KEY = "sessionId";
	private static final String MANAGER_KEY = "manager";
	private static final String ROOT_KEY = "tree";
	
	private TreePipeline pipeline;
	
	/*
	 * Pipeline represents an object that will be taken from the beginning to
	 * the end of the processing chain for the entire life cycle
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
	 * 5. Post-Validation
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
			closeResources();
			throw e;
		}
	}
	
	/*
	 * Assemble the root element to be made available in the session.
	 */
	private void prepareInitializedSession() throws TreeException {
		@SuppressWarnings("unchecked")
		Set<Element<T>> tree = (Set<Element<T>>) pipeline.getAttribute(
				ROOT_KEY);
		TreeManager manager = (TreeManager) pipeline.getAttribute(MANAGER_KEY);
		TreeTransaction transaction = manager.getTransaction();
		TreeSessionCore session = (TreeSessionCore) transaction.currentSession();
		
		Element<T> root = manager.createElement("HAPPYTREE_ROOT", null);
		session.setRoot(root, tree);
	}

	/*
	 * If some error occurs after the session initialization, then the own
	 * session needs to be destroyed.
	 */
	private void closeResources() {
		String sessionId = (String) pipeline.getAttribute(SESSION_KEY);
		TreeManager manager = (TreeManager) pipeline.getAttribute(MANAGER_KEY);
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.destroySession(sessionId);
	}
}
