package com.madzera.happytree.core;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.madzera.happytree.Element;
import com.madzera.happytree.TreeManager;
import com.madzera.happytree.TreeSession;
import com.madzera.happytree.TreeTransaction;
import com.madzera.happytree.core.TreeFactory.ATPLifecycleFactory;
import com.madzera.happytree.core.TreeFactory.ServiceFactory;
import com.madzera.happytree.exception.TreeException;

class TreeTransactionCore implements TreeTransaction {

	private Map<String, TreeSessionCore> sessions = TreeFactory.mapFactory().
			createHashMap();
	
	private TreeSessionCore currentSession;
	private TreeManager associatedManager;
	

	TreeTransactionCore(TreeManager manager) {
		this.associatedManager = manager;
	}

	
	@Override
	public <T> void initializeSession(String identifier, Class<T> type)
			throws TreeException {
		TreeValidatorFacade validatorFacade = TreeFactory.facadeFactory().
				createValidatorFacade(this.associatedManager());
		validatorFacade.validateSessionInitialization(identifier, type);
		
		ServiceFactory serviceFactory = TreeFactory.serviceFactory();
		
		TreeSessionCore newSession = serviceFactory.createTreeSession(
				identifier, type);
		TreeElementCore<T> root = serviceFactory.createElement(
				identifier, null, null, newSession);
		
		root.setRoot(Boolean.TRUE);
		
		Collection<TreeElementCore<T>> rootChildren = TreeFactory.
				collectionFactory().createHashSet();
		
		newSession.setRoot(root, rootChildren);
		
		sessions.put(identifier, newSession);
		
		this.sessionCheckout(identifier);
	}

	@Override
	public <T> void initializeSession(String identifier, Collection<T> nodes)
			throws TreeException {
		TreeValidatorFacade validatorFacade = TreeFactory.facadeFactory().
				createValidatorFacade(this.associatedManager());
		
		validatorFacade.validateSessionInitialization(identifier);
		
		TreePipeline pipeline = TreeFactory.pipelineFactory().
				createPipelineValidator();
		
		pipeline.addAttribute(TreePipelineAttributes.SESSION_ID, identifier);
		pipeline.addAttribute(TreePipelineAttributes.NODES, nodes);
		pipeline.addAttribute(TreePipelineAttributes.MANAGER,
				this.associatedManager());
		
		ATPLifecycleFactory lifecycleFactory= TreeFactory.lifecycleFactory();
		ATPLifecycle<T> lifecycle = lifecycleFactory.createLifecycle(pipeline);
		
		lifecycle.run();
	}

	@Override
	public void destroySession(String identifier) {
		TreeSession session = this.sessions.get(identifier);
		if (session != null && session == this.currentSession) {
			this.currentSession = null;
		}
		this.sessions.remove(identifier);
	}

	@Override
	public void destroySession() {
		TreeSession session = this.currentSession;
		if (session != null) {
			this.sessions.remove(session.getSessionId());
			this.currentSession = null;
		}
	}

	@Override
	public void destroyAllSessions() {
		this.sessions.clear();
	}

	@Override
	public TreeSession sessionCheckout(String identifier) {
		this.currentSession = sessions.get(identifier);
		return this.currentSession();
	}

	@Override
	public void activateSession(String identifier) {
		TreeSession session = sessions.get(identifier);
		
		if (session != null) {
			TreeSessionCore sessionCore = (TreeSessionCore) session;
			sessionCore.setActive(Boolean.TRUE);
		}
	}

	@Override
	public void activateSession() {
		TreeSession session = this.currentSession();
		
		if (session != null) {
			TreeSessionCore sessionCore = (TreeSessionCore) session;
			sessionCore.setActive(Boolean.TRUE);
		}
	}

	@Override
	public void deactivateSession(String identifier) {
		TreeSession session = sessions.get(identifier);
		
		if (session != null) {
			TreeSessionCore sessionCore = (TreeSessionCore) session;
			sessionCore.setActive(Boolean.FALSE);
		}
	}

	@Override
	public void deactivateSession() {
		TreeSession session = this.currentSession();
		
		if (session != null) {
			TreeSessionCore sessionCore = (TreeSessionCore) session;
			sessionCore.setActive(Boolean.FALSE);
		}
	}

	@Override
	public List<TreeSession> sessions() {
		List<TreeSession> listSessions = TreeFactory.collectionFactory().
				createArrayList();
		
		listSessions.addAll(sessions.values());
		
		return listSessions;
	}

	@Override
	public TreeSession cloneSession(String from, String to) {
		TreeSessionCore clonedSession = null;
		
		TreeSessionCore sourceSession = sessions.get(from);
		
		clonedSession = (TreeSessionCore) this.cloneSession(sourceSession, to);
		
		return clonedSession;
	}
	
	@Override
	public TreeSession cloneSession(TreeSession from, String to) {
		TreeSessionCore clonedSession = null;
		
		TreeSessionCore sourceSession = (TreeSessionCore) from;
		
		if (from != null && to != null) {
			clonedSession = sourceSession.cloneSession(to);
			sessions.put(clonedSession.getSessionId(), clonedSession);
		}
		
		return clonedSession;
	}
	
	@Override
	public TreeSession currentSession() {
		return this.currentSession;
	}
	
	TreeManager associatedManager() {
		return this.associatedManager;
	}
	
	<T> TreeElementCore<T> refreshElement(Object id) {
		return currentSession.get(id);
	}
	
	<T> void rollbackElement(Element<T> element) {
		currentSession.delete(element.getId());
	}
	
	<T> void commitElement(Element<T> element) {
		currentSession.save(element);
	}
	
	void commitTransaction() {
		currentSession.save(currentSession.tree());
	}
	
	@SuppressWarnings("unchecked")
	<T> TreeElementCore<T> refresh() {
		return (TreeElementCore<T>) currentSession.tree();
	}

	/*
	 * This is a helper method to test the inconsistency between the size of
	 * the resulting node list and the size of the resulting element list when
	 * the tree is being assembled in those ATP phases.
	 * 
	 * This helper method force an error on ATP post validation phase, as it
	 * represents a phase to confirm the consistency of the resulting element
	 * list.
	 * 
	 * Theoretically, this phase should not throw any exception, because all
	 * elements of the session was created in previous phases. So, to guarantee
	 * the quality of the Tree assembly, this phase is necessary, as if an error,
	 * by a unknown reason, occurs, the PostValidation class inside of the ATP
	 * lifecycle will be prepared to throw an exception.
	 * 
	 * Note: This method is not used in the main code, it is just for test
	 * purposes.
	 */
	void testInitializeSessionWithErrorATPPostValidationPhase() {
		Collection<Object> nodes = TreeFactory.collectionFactory().
			createArrayList();
		Collection<Object> nodesMap = TreeFactory.collectionFactory().
			createArrayList();
		TreePipeline pipeline = TreeFactory.pipelineFactory().
				createPipelineValidator();

		pipeline.addAttribute(TreePipelineAttributes.SESSION_ID, "identifier");
		pipeline.addAttribute(TreePipelineAttributes.NODES, nodes);
		
		final String NODES_MAP = "nodesMap";
		pipeline.addAttribute(NODES_MAP, nodesMap);

		ATPLifecycleFactory lifecycleFactory= TreeFactory.lifecycleFactory();
		ATPLifecycle<Object> lifecycle = lifecycleFactory.createLifecycle(
			pipeline);
		
		
	}
}
