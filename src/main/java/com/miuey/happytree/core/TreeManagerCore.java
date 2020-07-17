package com.miuey.happytree.core;


import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeTransaction;
import com.miuey.happytree.core.TreeFactory.ServiceValidatorFactory;
import com.miuey.happytree.exception.TreeException;

class TreeManagerCore implements TreeManager {

	/*
	 * The transaction associated to this manager. A manager is always related
	 * to its transaction. The cardinality is always 1:1.
	 */
	private TreeTransaction transaction = TreeFactory.serviceFactory().
			createTreeTransaction(this);
	
	
	TreeManagerCore() {}
	
	
	@Override
	public <T> Element<T> cut(Element<T> from, Element<T> to)
			throws TreeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Element<T> cut(Object from, Object to) throws TreeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Element<T> copy(Element<T> from, Element<T> to)
			throws TreeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeElement(Element<?> element) throws TreeException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeElement(Object id) throws TreeException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> Element<T> getElementById(Object id) throws TreeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsElement(Element<?> parent, Element<?> descendant)
			throws TreeException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsElement(Object parent, Object descendant)
			throws TreeException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsElement(Element<?> element) throws TreeException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsElement(Object id) throws TreeException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> Element<T> createElement(Object id, Object parent)
			throws TreeException {
		/*
		 * Initial validation processes.
		 */
		this.validateTransaction();
		
		return TreeFactory.serviceFactory().createElement(id, parent);
	}

	@Override
	public <T> Element<T> persistElement(Element<?> newElement)
			throws TreeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Element<T> updateElement(Element<?> element)
			throws TreeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TreeTransaction getTransaction() {
		return this.transaction;
	}

	@Override
	public <T> Element<T> root() throws TreeException {
		// TODO Auto-generated method stub
		return null;
	}

	static TreeManager getTreeManagerInstance() {
		return TreeFactory.serviceFactory().createTreeManagerCore();
	}
	
	private void validateTransaction() throws TreeException {
		TreePipeline pipeline = TreeFactory.pipelineFactory().
				createPipelineValidator();
		
		pipeline.addAttribute("session", this.getTransaction().currentSession());
		
		ServiceValidatorFactory validatorFactory = TreeFactory.
				serviceValidatorFactory();
		
		TreeServiceValidator noDefinedSessionValidator = validatorFactory.
				createNoDefinedSessionValidator();
		TreeServiceValidator noActiveSessionValidator = validatorFactory.
				createNoActiveSessionValidator();
		
		noDefinedSessionValidator.next(noActiveSessionValidator);
		noDefinedSessionValidator.validate(pipeline);
	}
}
