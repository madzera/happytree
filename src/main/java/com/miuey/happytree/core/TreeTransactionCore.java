package com.miuey.happytree.core;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.miuey.happytree.TreeSession;
import com.miuey.happytree.TreeTransaction;
import com.miuey.happytree.core.TreeFactory.ServiceFactory;
import com.miuey.happytree.core.TreeFactory.ServiceValidatorFactory;
import com.miuey.happytree.exception.TreeException;

class TreeTransactionCore implements TreeTransaction {
	
	private Map<String, TreeSession> sessions = TreeFactory.mapFactory().
			createHashMap();
	
	/*
	 * The session that will be used as checked out session.
	 */
	private TreeSession currentSession;
	
	
	TreeTransactionCore() {}

	
	@Override
	public <T> void initializeSession(String identifier, T type)
			throws TreeException {
		/*
		 * Initial validation processes.
		 */
		TreePipeline pipeline = TreeFactory.pipelineFactory().
				createPipelineValidator();
		pipeline.addAttribute("sessions", this.sessions);
		pipeline.addAttribute("sessionIdentifier", identifier);
		validateInitializeSession(pipeline);
		
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
		TreeElementCore<T> root = serviceFactory.createElement(null, null);
		
		/*
		 * Activate the new session with the root element.
		 */
		newSession.setActive(Boolean.TRUE);
		newSession.setRoot(root);
		
		sessions.put(identifier, newSession);
		
		/*
		 * Define this new session as the current session to be worked. When the
		 * user API starts a new session, <b>always</b> the new session turn on
		 * the current session.
		 */
		this.sessionCheckout(identifier);
	}

	@Override
	public void initializeSession(String identifier, Collection<?> objects)
			throws TreeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroySession(String identifier) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroySession() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroyAllSessions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TreeSession sessionCheckout(String identifier) {
		this.currentSession = sessions.get(identifier);
		return this.getCurrentSession();
	}

	@Override
	public void activateSession(String identifier) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void activateSession() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deactivateSession(String identifier) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deactivateSession() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<TreeSession> sessions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cloneSession(String from, String to) {
		// TODO Auto-generated method stub
		
	}
	
	TreeSession getCurrentSession() {
		return this.currentSession;
	}
	
	private void validateInitializeSession(TreePipeline pipeline)
			throws TreeException {
		ServiceValidatorFactory validatorFactory = TreeFactory.
				serviceValidatorFactory();
		
		TreeServiceValidator inputValidator = validatorFactory.
				createNotNullInputValidator();
		TreeServiceValidator duplicatedSessionValidator = validatorFactory.
				createNotDuplicatedSessionValidator();
		
		inputValidator.next(duplicatedSessionValidator);
		
		inputValidator.validate(pipeline);
	}
}
