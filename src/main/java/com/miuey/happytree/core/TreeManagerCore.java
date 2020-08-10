package com.miuey.happytree.core;


import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeSession;
import com.miuey.happytree.TreeTransaction;
import com.miuey.happytree.exception.TreeException;

class TreeManagerCore implements TreeManager {

	private TreeValidatorFacade validatorFacade = TreeFactory.facadeFactory().
			createValidatorFacade(this);
	
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
		final Operation operation = Operation.CUT;
		
		return null;
	}

	@Override
	public <T> Element<T> cut(Object from, Object to) throws TreeException {
		return null;
	}

	@Override
	public <T> Element<T> copy(Element<T> from, Element<T> to)
			throws TreeException {
		final Operation operation = Operation.COPY;
		
		return null;
	}

	@Override
	public <T> Element<T> removeElement(Element<T> element)
			throws TreeException {
		final Operation operation = Operation.REMOVE;
		
		return null;
	}

	@Override
	public <T> Element<T> removeElement(Object id) throws TreeException {
		return null;
	}

	@Override
	public <T> Element<T> getElementById(Object id) throws TreeException {
		return null;
	}

	@Override
	public <T> boolean containsElement(Element<T> parent, Element<T> descendant)
			throws TreeException {
		return false;
	}

	@Override
	public boolean containsElement(Object parent, Object descendant)
			throws TreeException {
		return false;
	}

	@Override
	public boolean containsElement(Element<?> element) throws TreeException {
		return false;
	}

	@Override
	public boolean containsElement(Object id) throws TreeException {
		return false;
	}

	@Override
	public <T> Element<T> createElement(Object id, Object parent,
			T wrappedObject) throws TreeException {
		validatorFacade.validateTransaction();
		validatorFacade.validateMandatory(id);
		
		TreeSession session = getTransaction().currentSession();
		return TreeFactory.serviceFactory().createElement(id, parent,
				wrappedObject, session);
	}

	@Override
	public <T> Element<T> persistElement(Element<T> newElement)
			throws TreeException {
		final Operation operation = Operation.PERSIST;
		
		return null;
	}

	@Override
	public <T> Element<T> updateElement(Element<T> element)
			throws TreeException {
		final Operation operation = Operation.UPDATE;
		
		return null;
	}

	@Override
	public TreeTransaction getTransaction() {
		return this.transaction;
	}

	@Override
	public <T> Element<T> root() throws TreeException {
		validatorFacade.validateTransaction();
		TreeSession session = this.getTransaction().currentSession();
		return session.tree();
	}

	static TreeManager getTreeManagerInstance() {
		return TreeFactory.serviceFactory().createTreeManagerCore();
	}
}
