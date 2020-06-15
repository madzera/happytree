package com.miuey.happytree.core;


import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeTransaction;
import com.miuey.happytree.exception.TreeException;

class TreeManagerCore implements TreeManager {

	/*
	 * The transaction associated to this manager. A manager is always related
	 * to its transaction. The cardinality is always 1:1.
	 */
	private TreeTransaction transaction = TreeFactory.serviceFactory().
			createTreeTransaction();
	
	
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
		// TODO Auto-generated method stub
		return null;
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
}
