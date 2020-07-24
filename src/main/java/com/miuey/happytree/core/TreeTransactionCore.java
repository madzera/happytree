package com.miuey.happytree.core;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeSession;
import com.miuey.happytree.TreeTransaction;
import com.miuey.happytree.core.TreeFactory.ATPLifecycleFactory;
import com.miuey.happytree.core.TreeFactory.ServiceFactory;
import com.miuey.happytree.exception.TreeException;

class TreeTransactionCore implements TreeTransaction {

	private static final String DEF_ROOT_IDENTIFIER = "HAPPYTREE_ROOT";
	
	private Map<String, TreeSession> sessions = TreeFactory.mapFactory().
			createHashMap();
	/*
	 * The session that will be used as checked out session.
	 */
	private TreeSession currentSession;
	private TreeManager associatedManager;
	

	TreeTransactionCore(TreeManager manager) {
		this.associatedManager = manager;
	}

	
	@Override
	public <T> void initializeSession(String identifier, T type)
			throws TreeException {
		/*
		 * Initial validation processes.
		 */
		validateInitializeSession(identifier);
		
		/*
		 * Session creation processes.
		 */
		ServiceFactory serviceFactory = TreeFactory.serviceFactory();
		/*
		 * Inside the core implementation only core implementations should be
		 * used instead the interfaces type.
		 */
		TreeSessionCore newSession = serviceFactory.createTreeSession(
				identifier);
		TreeElementCore<T> root = serviceFactory.createElement(
				DEF_ROOT_IDENTIFIER, null);
		
		Collection<Element<T>> rootChildren = TreeFactory.collectionFactory().
				createHashSet();
		/*
		 * Set the root element with a empty tree.
		 */
		newSession.setRoot(root, rootChildren);
		
		sessions.put(identifier, newSession);
		
		/*
		 * Define this new session as the current session to be worked. When the
		 * user API starts a new session, <b>always</b> the new session turn on
		 * the current session.
		 */
		this.sessionCheckout(identifier);
	}

	@Override
	public <T> void initializeSession(String identifier, Collection<T> objects)
			throws TreeException {
		/*
		 * Initial validation processes.
		 */
		validateInitializeSession(identifier);
		
		TreePipeline pipeline = TreeFactory.pipelineFactory().
				createPipelineValidator();
		pipeline.addAttribute("sessionId", identifier);
		pipeline.addAttribute("objects", objects);
		pipeline.addAttribute("manager", associatedManager());
		
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
	public void cloneSession(String from, String to) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public TreeSession currentSession() {
		return this.currentSession;
	}
	
	TreeManager associatedManager() {
		return this.associatedManager;
	}
	
	private void validateInitializeSession(String identifier)
			throws TreeException {
		TreePipeline pipeline = TreeFactory.pipelineFactory().
				createPipelineValidator();
		pipeline.addAttribute("sessionId", identifier);
		
		TreeSessionValidator validator = TreeFactory.validatorFactory().
				createSessionValidator(associatedManager());
		
		validator.validateMandatorySessionId(pipeline);
		validator.validateDuplicatedSessionId(pipeline);
	}
}
