package com.miuey.happytree.core;

import java.util.Collection;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeSession;
import com.miuey.happytree.TreeTransaction;
import com.miuey.happytree.exception.TreeException;

@SuppressWarnings("unchecked")
class TreeManagerCore implements TreeManager {

	private TreeValidatorFacade validatorFacade = TreeFactory.facadeFactory().
			createValidatorFacade(this);
	
	private TreeTransactionCore transaction = TreeFactory.serviceFactory().
			createTreeTransaction(this);
	
	
	TreeManagerCore() {}
	
	
	@Override
	public <T> Element<T> cut(Element<T> from, Element<T> to)
			throws TreeException {
		validatorFacade.validateCutOperation(from, to);

		TreeElementCore<T> source = (TreeElementCore<T>) this.searchElement(
				from.getId());
		
		if (source != null) {
			TreeElementCore<T> parent = (TreeElementCore<T>) this.
					searchElement(source.getParent());
			
			if (parent != null) {
				parent.removeChild(source);
			}
			
			TreeSessionCore session = (TreeSessionCore) getTransaction().
					currentSession();
			TreeElementCore<T> target = null;
			
			if (to == null) {
				TreeElementCore<T> root = (TreeElementCore<T>) tree();
				root.addChild(source);
				transaction.commitTransaction();
			} else {
				/*
				 * The session of the target can be different, so it is
				 * necessary to checkout the respective session.
				 */
				TreeSessionCore targetSession = (TreeSessionCore)
						transaction.sessionCheckout(to.attachedTo().
								getSessionId());
				try {
					validatorFacade.validateSessionTransaction();
				} catch (TreeException e) {
					/*
					 * Rollback.
					 */
					getTransaction().sessionCheckout(session.getSessionId());
					if (parent != null) {
						parent.addChild(source);
					}
					throw e;
				}
				
				target = (TreeElementCore<T>) this.searchElement(to.getId());
				target.addChild(source);
				
				/*
				 * Swap the session of the from element.
				 */
				session.delete(source.getId());
				source.changeSession(targetSession);
				
				transaction.commitTransaction();
				transaction.sessionCheckout(session.getSessionId());
			}
			
		}
		
		return source != null ? source.cloneElement() : source;
	}

	@Override
	public <T> Element<T> cut(Object from, Object to) throws TreeException {
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
			validatorFacade.validateCutOperation(fromElement, toElement);
		}
		
		Element<T> source = this.searchElement(from);
		Element<T> target = this.searchElement(to);
		
		return this.cut(source, target);
	}

	@Override
	public <T> Element<T> copy(Element<T> from, Element<T> to)
			throws TreeException {
		validatorFacade.validateCopyOperation(from, to);
		
		TreeElementCore<T> fromElement = (TreeElementCore<T>) this.
				searchElement(from.getId());
		
		if (fromElement != null) {
			TreeElementCore<T> clonedFrom = (TreeElementCore<T>) fromElement.
					cloneElement();
			
			TreeSession sourceSession = from.attachedTo();
			TreeSessionCore targetSession = (TreeSessionCore) to.attachedTo();
			
			transaction.sessionCheckout(targetSession.getSessionId());
			
			try {
				validatorFacade.validateSessionTransaction();
			} catch (TreeException e) {
				/*
				 * Rollback.
				 */
				transaction.sessionCheckout(sourceSession.getSessionId());
				throw e;
			}
			
			TreeElementCore<T> targetElement = (TreeElementCore<T>) this.
					searchElement(to.getId());
			if (targetElement != null) {
				clonedFrom.changeSession(targetSession);
				targetElement.addChild(clonedFrom);
				
				targetElement.transitionState(ElementState.ATTACHED);
				clonedFrom.transitionState(ElementState.ATTACHED);
				
				targetSession.save(clonedFrom);
			}
			transaction.sessionCheckout(sourceSession.getSessionId());
		}
		
		return fromElement != null ? fromElement.cloneElement() : fromElement;
	}

	@Override
	public <T> Element<T> removeElement(Element<T> element)
			throws TreeException {
		validatorFacade.validateSessionTransaction();
		
		TreeElementCore<T> elementToRemove = null;
		if (element != null) {
			validatorFacade.validateRemoveOperation(element);
			TreeSessionCore session = (TreeSessionCore) getTransaction().
					currentSession();
			elementToRemove = (TreeElementCore<T>) this.searchElement(element.
					getId());
			
			if (elementToRemove != null) {
				TreeElementCore<T> parentElement = (TreeElementCore<T>) this.
						searchElement(elementToRemove.getParent());
				
				parentElement.removeChild(elementToRemove);
				
				Collection<Element<T>> descendants = Recursivity.toPlainList(
						elementToRemove);
				
				for (Element<T> iterator : descendants) {
					TreeElementCore<T> child = (TreeElementCore<T>) iterator;
					child.transitionState(ElementState.NOT_EXISTED);
				}
				parentElement.transitionState(ElementState.ATTACHED);
				
				session.delete(elementToRemove.getId());
			}
		}
		
		/*
		 * This is not necessary to return the cloned element because the
		 * element is not attached in the tree session anymore.
		 */
		return elementToRemove;
	}

	@Override
	public <T> Element<T> removeElement(Object id) throws TreeException {
		Element<T> element = this.searchElement(id);
		return this.removeElement(element);
	}

	@Override
	public <T> Element<T> getElementById(Object id) throws TreeException {
		validatorFacade.validateSessionTransaction();
		
		TreeSessionCore session = (TreeSessionCore) getTransaction().
				currentSession();
		TreeElementCore<T> element = (TreeElementCore<T>) session.get(id);
		
		return element != null ? element.cloneElement() : element;
	}

	@Override
	public <T> boolean containsElement(Element<T> parent, Element<T> descendant)
			throws TreeException {
		final Operation operation = Operation.CONTAINS;
		validatorFacade.validateSessionTransaction();
		
		boolean containsChild = Boolean.FALSE;
		
		if (parent == null || descendant == null) {
			return containsChild;
		}
		
		TreeElementCore<T> parentCore = (TreeElementCore<T>) parent;
		TreeElementCore<T> childCore = (TreeElementCore<T>) descendant;
		
		if (!parentCore.getState().equals(ElementState.ATTACHED)
				|| !childCore.getState().equals(ElementState.ATTACHED)) {
			return containsChild;
		}
		
		boolean notAttachedParent = Recursivity.
				iterateForInvalidStateOperationValidation(
						parentCore.getChildren(), operation);
		boolean notAttachedChild = Recursivity.
				iterateForInvalidStateOperationValidation(
						childCore.getChildren(), operation);
		
		if (notAttachedParent || notAttachedChild) {
			return containsChild;
		}
		
		Element<T> parentElement = this.searchElement(parentCore.getId());
		Element<T> childElement = this.searchElement(childCore.getId());
		
		containsChild = parentElement != null && childElement != null
				&& parentElement.getElementById(childElement.getId()) != null;
		
		return containsChild;
	}

	@Override
	public boolean containsElement(Object parent, Object descendant)
			throws TreeException {
		validatorFacade.validateSessionTransaction();
		boolean containsChild = Boolean.FALSE;
		
		if (parent == null || descendant == null) {
			return containsChild;
		}
		
		TreeElementCore<?> parentElement = (TreeElementCore<?>) this.
				searchElement(parent);
		TreeElementCore<?> child = (TreeElementCore<?>) this.searchElement(
				descendant);
		
		if (parentElement == null || child == null) {
			return containsChild;
		}
		
		return parentElement.getElementById(child.getId()) != null;
	}

	@Override
	public boolean containsElement(Element<?> element) throws TreeException {
		final Operation operation = Operation.CONTAINS;
		validatorFacade.validateSessionTransaction();

		boolean containsElement = Boolean.FALSE;
		
		TreeElementCore<?> source = (TreeElementCore<?>) element;
		
		TreeSession currentSession = getTransaction().currentSession();
		
		if (source != null && currentSession.equals(source.attachedTo())) {
			boolean isAttached = source.getState().canExecuteOperation(
					operation)
					&& !Recursivity.iterateForInvalidStateOperationValidation(
							source.	getChildren(), operation);
			
			if (isAttached) {
				containsElement = this.searchElement(element.getId()) != null;
			}
		}
		
		return containsElement;
	}

	@Override
	public boolean containsElement(Object id) throws TreeException {
		validatorFacade.validateSessionTransaction();
		
		return this.searchElement(id) != null;
	}

	@Override
	public <T> Element<T> createElement(Object id, Object parent,
			T wrappedNode) throws TreeException {
		validatorFacade.validateSessionTransaction();
		validatorFacade.validateMandatory(id);
		
		TreeSession session = getTransaction().currentSession();
		return TreeFactory.serviceFactory().createElement(id, parent,
				wrappedNode, session);
	}

	@Override
	public <T> Element<T> persistElement(Element<T> newElement)
			throws TreeException {
		validatorFacade.validatePersistOperation(newElement);
		
		TreeElementCore<T> parent = (TreeElementCore<T>) this.searchElement(
				newElement.getParent());
		TreeElementCore<T> child = (TreeElementCore<T>) newElement;
		
		TreeSessionCore session = (TreeSessionCore) getTransaction().
				currentSession();
		if (parent == null) {
			parent = (TreeElementCore<T>) this.tree();
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
		
		session.save(child);
		
		return child.cloneElement();
	}

	@Override
	public <T> Element<T> updateElement(Element<T> element)
			throws TreeException {
		validatorFacade.validateUpdateOperation(element);
		
		TreeElementCore<T> updateElement = (TreeElementCore<T>) element; 
		TreeElementCore<T> original = (TreeElementCore<T>) this.searchElement(
				element.getId());
		
		Object oldParentId = original.getParent();
		
		Object updatedId = updateElement.getUpdatedId();
		Object updatedParentId = updateElement.getParent();
		
		if (updatedId != null) {
			original.refreshUpdatedId(updatedId);
		}
		
		TreeSessionCore session = (TreeSessionCore) getTransaction().
				currentSession();
		
		if (!oldParentId.equals(updatedParentId)) {
			TreeElementCore<T> oldParent = (TreeElementCore<T>) this.
					searchElement(oldParentId);
			TreeElementCore<T> newParent = (TreeElementCore<T>) this.
					searchElement(updatedParentId);
			
			oldParent.removeChild(original);
			newParent.addChild(original);
			
			oldParent.transitionState(ElementState.ATTACHED);
			newParent.transitionState(ElementState.ATTACHED);
			
			session.save(oldParent);
			session.save(newParent);
		}
		original.transitionState(ElementState.ATTACHED);
		
		session.save(original);
		
		return original;
	}

	@Override
	public TreeTransaction getTransaction() {
		return this.transaction;
	}

	@Override
	public <T> Element<T> root() throws TreeException {
		validatorFacade.validateSessionTransaction();
		TreeSession session = this.getTransaction().currentSession();
		TreeElementCore<T> root = (TreeElementCore<T>) session.tree();
		
		return root.cloneElement();
	}

	static TreeManager getTreeManagerInstance() {
		return TreeFactory.serviceFactory().createTreeManagerCore();
	}
	
	private <T> Element<T> searchElement(Object id) {
		TreeSessionCore session = (TreeSessionCore) getTransaction().
				currentSession();
		
		return session.get(id);
	}
	
	private <T> Element<T> tree() {
		TreeSession session = this.getTransaction().currentSession();
		return session.tree();
	}
}
