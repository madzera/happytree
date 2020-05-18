package com.miuey.happytree.core;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeTransaction;
import com.miuey.happytree.exception.TreeException;

class TreeManagerCore implements TreeManager {
	
	/*
	 * Protect this constructor. A single manager is sufficiently responsible
	 * to manage all the sessions.
	 */
	private static TreeManager instance;
	
	
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Element<T> root() throws TreeException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * <p>A tree manager must be a singleton. This type of object is responsible
	 * to store all session tree states and handle them. Each session match with
	 * its respective tree and each session belongs to its respective client of 
	 * this API. It does not make sense to provide several instances of 
	 * {@code TreeManager} objects because a only one instance is enough to 
	 * store and handle all trees. Furthermore, if there had too much instance 
	 * of this object, it will overload the memory with unnecessary managers 
	 * processing each one his core in a redundant way.</p>
	 *   
	 * @return instance <code>TreeManager</code>. The core interface responsible
	 * to provide operations over the trees stored inside sessions.
	 */
	static TreeManager getTreeManagerInstance() {
		if (instance == null) {
			instance = new TreeManagerCore();
		}
		return instance;
	}
}
