package com.miuey.happytree.core;


import java.util.Collection;

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
		validatorFacade.validateTransaction();
		validatorFacade.validateCutCopyOperation(from, to, Boolean.FALSE);
		
		TreeSessionCore session = (TreeSessionCore) this.getTransaction().
				currentSession();
		Element<T> parentFrom = this.getElementById(from.getParent());
		if (parentFrom != null) {
			parentFrom.removeChild(from);
		}
		
		if (to != null) {
			to.addChild(from);
			from.setParent(to.getId());
			TreeElementCore<T> fromCore = (TreeElementCore<T>) from;
			fromCore.changeSession(to.attachedTo());
		} else {
			Element<T> root = this.root();
			root.addChild(from);
			from.setParent(root.getId());
			synchronizeElements(root);
		}
		synchronizeElements(parentFrom, from, to);
		transaction.sessionCheckout(session.getSessionId());
		session.remove(from.getId());
		return from;
	}

	@Override
	public <T> Element<T> cut(Object from, Object to) throws TreeException {
		validatorFacade.validateTransaction();
		
		Element<T> fromElement = this.getElementById(from);
		Element<T> toElement = this.getElementById(to);
		
		return this.cut(fromElement, toElement);
	}

	@Override
	public <T> Element<T> copy(Element<T> from, Element<T> to)
			throws TreeException {
		validatorFacade.validateTransaction();
		validatorFacade.validateCutCopyOperation(from, to, Boolean.TRUE);
		
		Element<T> clonedElement = this.createElement(from.getId(), to.getId());
		clonedElement.addChildren(from.getChildren());
		clonedElement.wrap(from.unwrap());
		TreeElementCore<T> clonedElementCore = (TreeElementCore<T>) 
				clonedElement;
		clonedElementCore.changeSession(to.attachedTo());
		
		clonedElement.setParent(to.getId());
		to.addChild(clonedElement);
		
		synchronizeElements(clonedElement, to);
		
		return clonedElement;
	}

	@Override
	public <T> Element<T> removeElement(Element<T> element)
			throws TreeException {
		validatorFacade.validateTransaction();
		validatorFacade.validateRemoveOperation(element);
		
		TreeElementCore<T> removedElement = null;
		
		if (element != null) {
			Element<T> searchElement = this.getElementById(element.getId());
			removedElement = (TreeElementCore<T>) searchElement;
			if (removedElement != null) {
				Element<T> parent = this.getElementById(element.getParent());
				parent.removeChild(removedElement);
				removedElement.detach();
				removedElement.changeSession(null);
				synchronizeElements(parent);
			}
		}
		return removedElement;
	}

	@Override
	public <T> Element<T> removeElement(Object id) throws TreeException {
		validatorFacade.validateTransaction();
		
		Element<T> removedElement = null;
		
		if (id != null) {
			Element<T> element = this.getElementById(id);
			TreeElementCore<T> convertedElement = (TreeElementCore<T>)
					element;
			if (element != null && !convertedElement.isRoot()) {
				Element<T> parent = this.getElementById(element.getParent());
				parent.removeChild(convertedElement);
				convertedElement.detach();
				convertedElement.changeSession(null);
				synchronizeElements(parent);
				removedElement = convertedElement;
			}
		}
		return removedElement;
	}

	@Override
	public <T> Element<T> getElementById(Object id) throws TreeException {
		validatorFacade.validateTransaction();
		Element<T> element = null;
		if (id != null) {
			TreeSessionCore session = (TreeSessionCore) this.getTransaction().
					currentSession();
			element = session.get(id);
			if (element == null) {
				Element<T> root = session.tree();
				element = searchElement(root.getChildren(), id);
				
				/*
				 * Add to the cache.
				 */
				if (element != null) {
					session.add(element.getId(), element);
				}
			}
		}
		return element;
	}

	@Override
	public <T> boolean containsElement(Element<T> parent, Element<T> descendant)
			throws TreeException {
		validatorFacade.validateTransaction();
		boolean result = Boolean.FALSE;
		
		TreeElementCore<T> parentElement = (TreeElementCore<T>) parent;
		TreeElementCore<T> descendantElement = (TreeElementCore<T>) descendant;
		
		String sessionId = this.getTransaction().currentSession().getSessionId();
		if (parent != null && descendant != null && parentElement.isAttached()
				&& descendantElement.isAttached() 
				&& parent.attachedTo().equals(sessionId) 
				&& descendant.attachedTo().equals(sessionId)) {
			Element<T> element = this.searchElement(parent.getChildren(),
					descendant.getId());
			result = element != null;
		}
		return result;
	}

	@Override
	public boolean containsElement(Object parent, Object descendant)
			throws TreeException {
		validatorFacade.validateTransaction();
		boolean result = Boolean.FALSE;
		if (parent != null && descendant != null) {
			Element<?> parentElement = this.getElementById(parent);
			Element<?> descendantElement = this.getElementById(descendant);
			
			if (parentElement != null && descendantElement != null) {
				Element<?> element = this.searchElement(parentElement.
						getChildren(), descendantElement.getId());
				result = element != null;
			}
		}
		return result;
	}

	@Override
	public boolean containsElement(Element<?> element) throws TreeException {
		validatorFacade.validateTransaction();
		boolean result = Boolean.FALSE;
		if (element != null) {
			TreeElementCore<?> convertedElement = (TreeElementCore<?>) element;
			String sessionId = this.getTransaction().currentSession().
					getSessionId();
			if (convertedElement.isAttached() 
					&& convertedElement.attachedTo().equals(sessionId)) {
				Element<?> foundElement = this.searchElement(this.root().
						getChildren(), element.getId());
				result = foundElement != null;
			}
		}
		return result;
	}

	@Override
	public boolean containsElement(Object id) throws TreeException {
		validatorFacade.validateTransaction();
		boolean result = Boolean.FALSE;
		if (id != null) {
			Element<?> element = this.getElementById(id);
			result = element != null;
		}
		return result;
	}

	@Override
	public <T> Element<T> createElement(Object id, Object parent)
			throws TreeException {
		/*
		 * Initial validation processes.
		 */
		validatorFacade.validateTransaction();
		
		return TreeFactory.serviceFactory().createElement(id, parent);
	}

	@Override
	public <T> Element<T> persistElement(Element<T> newElement)
			throws TreeException {
		validatorFacade.validateTransaction();
		validatorFacade.validatePersistOperation(newElement);
		
		Object parentId = newElement.getParent();
		Element<T> parent = null;
		if (parentId != null) {
			parent = this.getElementById(parentId);
		} else {
			parent = this.root();
			newElement.setParent(parent.getId());
			parent.wrap(newElement.unwrap());
		}
		parent.addChild(newElement);
		TreeElementCore<T> element = (TreeElementCore<T>) newElement;
		element.changeSession(this.getTransaction().currentSession().
				getSessionId());
		synchronizeElements(newElement, parent);
		
		return newElement;
	}

	@Override
	public <T> Element<T> updateElement(Element<T> element)
			throws TreeException {
		validatorFacade.validateTransaction();
		validatorFacade.validateUpdateOperation(element);
		
		Element<T> snapshot = ((TreeElementCore<T>) element).snapshot();
		
		if (snapshot != null && !snapshot.equals(element)) {
			Object oldParentId = snapshot.getParent();
			Object newParentId = element.getParent();
			
			Element<T> oldParent = null;
			if (!oldParentId.equals(newParentId)) {
				oldParent = this.getElementById(oldParentId);
				if (oldParent != null) {
					oldParent.removeChild(snapshot.getId());
					((TreeElementCore<T>) oldParent).clearSnapshot();
					((TreeElementCore<T>) element).clearSnapshot();
				}
			}
			
			Element<T> newParent = this.getElementById(newParentId);
			if (newParent == null) {
				newParent = this.root();
			}
			newParent.addChild(element);
			synchronizeElements(oldParent, newParent, element);
		}
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

	@SafeVarargs
	private final <T> void synchronizeElements(Element<T>... elements) {
		String sessionId = null;
		TreeElementCore<T> element = null;
		TreeSession currentSession = getTransaction().currentSession();
		for (Element<T> iterator : elements) {
			element = (TreeElementCore<T>) iterator;
			if (element != null) {
				sessionId = element.attachedTo();
				TreeSessionCore session = (TreeSessionCore) getTransaction().
						sessionCheckout(sessionId);
				element.attach(sessionId);
				session.add(element.getId(), element);
			}
		}
		getTransaction().sessionCheckout(currentSession.getSessionId());
	}
	
	private <T> Element<T> searchElement(Collection<Element<T>> elements,
			Object id) {
		Element<T> result = null;
		if (elements == null || elements.isEmpty()) {
			return null;
		}
		for (Element<T> element : elements) {
			if (result != null) {
				return result;
			}
			if (element.getId().equals(id)) {
				result = element;
				return result;
			}
			result = searchElement(element.getChildren(), id);
		}
		return result;
	}
}
