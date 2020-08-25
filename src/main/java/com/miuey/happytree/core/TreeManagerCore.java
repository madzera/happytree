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
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Element<T> cut(Element<T> from, Element<T> to)
			throws TreeException {
		final Operation operation = Operation.CUT;
		validatorFacade.validateTransaction();
		validatorFacade.validateMandatory(from);
		validatorFacade.validateCutCopyOperation(from, getTransaction().
				currentSession(), to, operation);

		TreeElementCore<T> source = (TreeElementCore<T>) this.getElementById(
				from.getId());
		
		if (source != null) {
			TreeElementCore<T> parent = (TreeElementCore<T>) this.
					getElementById(source.getParent());
			
			if (parent != null) {
				parent.removeChild(source);
				parent.transitionState(ElementState.ATTACHED);
			}
			
			TreeSessionCore session = (TreeSessionCore) getTransaction().
					currentSession();
			TreeElementCore<T> target = null;
			
			if (to == null) {
				TreeElementCore<T> root = (TreeElementCore<T>) root();
				root.addChild(source);
				root.transitionState(ElementState.ATTACHED);
			} else {
				/*
				 * The session of the target can be different, so it is
				 * necessary to checkout the respective session.
				 */
				TreeSessionCore targetSession = (TreeSessionCore)
						getTransaction().sessionCheckout(to.attachedTo().
								getSessionId());
				target = (TreeElementCore<T>) this.getElementById(to.getId());
				target.addChild(source);
				target.transitionState(ElementState.ATTACHED);
				
				/*
				 * Swap the session of the from element.
				 */
				session.remove(source.getId());
				source.changeSession(targetSession);
				
				/*
				 * Add the from element to the target cache session.
				 */
				targetSession.add(source.getId(), source);
				
				getTransaction().sessionCheckout(session.getSessionId());
			}
			
			source.transitionState(ElementState.ATTACHED);
		}
		
		return source;
	}

	@Override
	public <T> Element<T> cut(Object from, Object to) throws TreeException {
		final Operation operation = Operation.CUT;
		
		Element<?> fromElement = null;
		Element<?> toElement = null;
		
		/*
		 * Mismatch parameterized type error. invoking with the parameters
		 * Element<X> and Element<Y> this method is called instead
		 * cut(Element, Element).
		 */
		if (from instanceof Element<?> && to instanceof Element<?>) {
			fromElement = (Element<?>) from;
			toElement = (Element<?>) to;
			validatorFacade.validateCutCopyOperation(fromElement,
					getTransaction().currentSession(), toElement, operation);
		}
		
		Element<T> source = this.getElementById(from);
		Element<T> target = this.getElementById(to);
		
		return this.cut(source, target);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Element<T> copy(Element<T> from, Element<T> to)
			throws TreeException {
		final Operation operation = Operation.COPY;
		validatorFacade.validateTransaction();
		validatorFacade.validateMandatory(from);
		validatorFacade.validateMandatory(to);
		validatorFacade.validateCutCopyOperation(from, getTransaction().
				currentSession(), to, operation);
		
		TreeElementCore<T> fromElement = (TreeElementCore<T>) this.
				getElementById(from.getId());
		
		if (fromElement != null) {
			TreeElementCore<T> clonedFrom = (TreeElementCore<T>) fromElement.
					cloneElement();
			
			TreeSession sourceSession = from.attachedTo();
			TreeSessionCore targetSession = (TreeSessionCore) to.attachedTo();
			
			transaction.sessionCheckout(targetSession.getSessionId());
			TreeElementCore<T> targetElement = (TreeElementCore<T>) this.
					getElementById(to.getId());
			if (targetElement != null) {
				clonedFrom.changeSession(targetSession);
				targetElement.addChild(clonedFrom);
				
				targetElement.transitionState(ElementState.ATTACHED);
				clonedFrom.transitionState(ElementState.ATTACHED);
				
				targetSession.add(clonedFrom.getId(), clonedFrom);
			}
			transaction.sessionCheckout(sourceSession.getSessionId());
		}
		
		return fromElement;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Element<T> removeElement(Element<T> element)
			throws TreeException {
		final Operation operation = Operation.REMOVE;
		
		validatorFacade.validateTransaction();
		
		TreeElementCore<T> elementToRemove = null;
		if (element != null) {
			validatorFacade.validateRemoveOperation(element, getTransaction().
					currentSession(), operation);
			TreeSessionCore session = (TreeSessionCore) getTransaction().
					currentSession();
			elementToRemove = (TreeElementCore<T>)  this.getElementById(element.
					getId());
			
			if (elementToRemove != null) {
				TreeElementCore<T> parentElement = (TreeElementCore<T>) this.
						getElementById(elementToRemove.getParent());
				
				parentElement.removeChild(elementToRemove);
				
				Collection<Element<T>> descendants = Recursivity.toPlainList(
						elementToRemove);
				
				for (Element<T> iterator : descendants) {
					TreeElementCore<T> child = (TreeElementCore<T>) iterator;
					child.transitionState(ElementState.NOT_EXISTED);
				}
				parentElement.transitionState(ElementState.ATTACHED);
				
				session.remove(elementToRemove.getId());
			}
		}
		
		return elementToRemove;
	}

	@Override
	public <T> Element<T> removeElement(Object id) throws TreeException {
		Element<T> element = this.getElementById(id);
		return this.removeElement(element);
	}

	@Override
	public <T> Element<T> getElementById(Object id) throws TreeException {
		validatorFacade.validateTransaction();
		
		TreeSessionCore session = (TreeSessionCore) getTransaction().
				currentSession();
		
		return session.get(id);
	}

	@Override
	public <T> boolean containsElement(Element<T> parent, Element<T> descendant)
			throws TreeException {
		boolean containsChild = Boolean.FALSE;
		validatorFacade.validateTransaction();
		
		if (parent == null || descendant == null) {
			return containsChild;
		}
		
		TreeElementCore<T> parentCore = (TreeElementCore<T>) parent;
		TreeElementCore<T> childCore = (TreeElementCore<T>) descendant;
		
		if (!parentCore.getState().equals(ElementState.ATTACHED)
				|| !childCore.getState().equals(ElementState.ATTACHED)) {
			return containsChild;
		}
		
		Element<T> parentElement = this.getElementById(parentCore.getId());
		Element<T> childElement = this.getElementById(childCore.getId());
		
		return parentElement != null 
				&& parentElement.getElementById(childElement.getId()) != null;
	}

	@Override
	public boolean containsElement(Object parent, Object descendant)
			throws TreeException {
		boolean containsChild = Boolean.FALSE;
		validatorFacade.validateTransaction();
		
		if (parent == null || descendant == null) {
			return containsChild;
		}
		
		TreeElementCore<?> parentElement = (TreeElementCore<?>) this.
				getElementById(parent);
		TreeElementCore<?> child = (TreeElementCore<?>) this.getElementById(
				descendant);
		
		if (parentElement == null || child == null
				|| !parentElement.getState().equals(ElementState.ATTACHED)
				|| !child.getState().equals(ElementState.ATTACHED)) {
			return containsChild;
		}
		return parentElement.getElementById(child.getId()) != null;
	}

	@Override
	public boolean containsElement(Element<?> element) throws TreeException {
		final Operation operation = Operation.CONTAINS;
		validatorFacade.validateTransaction();

		boolean containsElement = Boolean.FALSE;
		
		TreeElementCore<?> source = (TreeElementCore<?>) element;
		boolean isAttached = source.getState().canExecuteOperation(operation)
				&& !Recursivity.iterateForInvalidStateOperationValidation(source.
						getChildren(), operation);
		
		if (isAttached) {
			containsElement = this.getElementById(element.getId()) != null;
		}
		return containsElement;
	}

	@Override
	public boolean containsElement(Object id) throws TreeException {
		validatorFacade.validateTransaction();
		
		return this.getElementById(id) != null;
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

	@SuppressWarnings("unchecked")
	@Override
	public <T> Element<T> persistElement(Element<T> newElement)
			throws TreeException {
		final Operation operation = Operation.PERSIST;
		validatorFacade.validateTransaction();
		validatorFacade.validateMandatory(newElement);
		validatorFacade.validatePersistOperation(newElement, getTransaction().
				currentSession(), operation);
		
		TreeElementCore<T> parent = (TreeElementCore<T>) this.getElementById(
				newElement.getParent());
		TreeElementCore<T> child = (TreeElementCore<T>) newElement;
		
		TreeSessionCore session = (TreeSessionCore) getTransaction().
				currentSession();
		if (parent == null) {
			parent = (TreeElementCore<T>) this.root();
		}
		
		child.changeSession(session);
		parent.addChild(child);
		
		parent.transitionState(ElementState.ATTACHED);
		child.transitionState(ElementState.ATTACHED);
		Collection<Element<T>> descendants = Recursivity.toPlainList(child);
		
		for (Element<T> iterator : descendants) {
			TreeElementCore<T> descendant = (TreeElementCore<T>) iterator;
			descendant.transitionState(ElementState.ATTACHED);
		}
		
		session.add(child.getId(), child);
		return child;
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
