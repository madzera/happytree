package com.madzera.happytree.core;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.madzera.happytree.Element;
import com.madzera.happytree.TreeManager;
import com.madzera.happytree.TreeSession;
import com.madzera.happytree.TreeTransaction;
import com.madzera.happytree.exception.TreeException;

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
		/*
		 * Input validation.
		 */
		validatorFacade.validateCutOperation(from, to);

		/*
		 * Get both of sessions: source and target sessions.
		 */
		TreeSessionCore sourceSession = (TreeSessionCore) from.attachedTo();
		TreeSessionCore targetSession = to != null ?
				(TreeSessionCore) to.attachedTo() : null;
		
		/*
		 * Get the source element.
		 */
		TreeElementCore<T> source = this.searchElement(from.getId());
		
		/*
		 * Remove the element from the source parent.
		 */
		TreeElementCore<T> sourceParent = this.searchElement(source.getParent());

		/*
		 * If the sourceParent is null, it means that the source element is
		 * placed in the root level. In this case, remove it from the root
		 * element. Otherwise, remove it from its parent element.
		 */
		if (sourceParent == null) {
			Element<T> root = this.tree();
			root.removeChild(source);
		} else {
			sourceParent.removeChild(source);
		}
		
		/*
		 * If the source and target are from different tree session, then swap
		 * the transaction to the target session and add the source element into
		 * the target element inside of the target session. Otherwise, insert
		 * the source element into the target element (root or not) in the same
		 * source session.
		 */
		TreeElementCore<T> target = null;
		if (targetSession != null && !sourceSession.equals(targetSession)) {
			transaction.rollbackElement(source);
			
			transaction.sessionCheckout(targetSession.getSessionId());
			target = this.searchElement(to.getId());
			
			target.addChild(source);
			source.changeSession(targetSession);
			
			transaction.commitTransaction();
			
			transaction.sessionCheckout(sourceSession.getSessionId());
		} else {
			target = to != null ? (TreeElementCore<T>)
					this.searchElement(to.getId()) : null;
			
			target = target == null ? (TreeElementCore<T>)
					this.tree() : target;
			
			target.addChild(source);
			
			transaction.commitTransaction();
		}
		
		return source.cloneElement();
	}

	@Override
	public <T> Element<T> cut(Object from, Object to) throws TreeException {
		/*
		 * Input validation.
		 */
		validatorFacade.validateCutOperation(from, to);

		Element<T> source = this.searchElement(from);
		Element<T> target = this.searchElement(to);

		return this.cut(source, target);
	}

	@Override
	public <T> Element<T> copy(Element<T> from, Element<T> to)
			throws TreeException {
		/*
		 * Input validation.
		 */
		validatorFacade.validateCopyOperation(from, to);

		/*
		 * Obtains both of sessions: source and target sessions.
		 */
		TreeSessionCore sourceSession = (TreeSessionCore) from.attachedTo();
		TreeSessionCore targetSession = (TreeSessionCore) to.attachedTo();
		
		/*
		 * Obtains the source element.
		 */
		TreeElementCore<T> source = this.searchElement(from.getId());
		
		/*
		 * Clones the source element.
		 */
		TreeElementCore<T> clonedSource = source.cloneElement();
		
		/*
		 * Checkout the target session.
		 */
		transaction.sessionCheckout(targetSession.getSessionId());
		
		TreeElementCore<T> target = this.searchElement(to.getId());
		
		/*
		 * Setup the copy process.
		 */
		clonedSource.changeSession(targetSession);
		target.addChild(clonedSource);
		
		/*
		 * Save changes.
		 */
		transaction.commitTransaction();
		transaction.sessionCheckout(sourceSession.getSessionId());
		
		return clonedSource.cloneElement();
	}

	@Override
	public <T> Element<T> removeElement(Element<T> element)
			throws TreeException {
		/*
		 * Validates whether the current session is valid.
		 */
		validatorFacade.validateSessionTransaction();
		
		/*
		 * The removed element to be returned.
		 */
		TreeElementCore<T> removedElement = null;
		
		if (element != null) {
			
			/*
			 * If the input is not null, this is necessary to valid it.
			 */
			validatorFacade.validateRemoveOperation(element);
			
			/*
			 * Obtains the element and its parent element in the tree.
			 */
			removedElement = this.searchElement(element.getId());
			TreeElementCore<T> parentElement = this.searchElement(
					removedElement.getParent());
			
			/*
			 * Removes the element from its parent and from the cache.
			 */
			parentElement.removeChild(removedElement);
			transaction.rollbackElement(removedElement);
			
			/*
			 * Save changes.
			 */
			transaction.commitTransaction();
		}
		
		/*
		 * This is not necessary to return the cloned element because the
		 * element is not attached in the tree session anymore.
		 */
		return removedElement;
	}

	@Override
	public <T> Element<T> removeElement(Object id) throws TreeException {
		validatorFacade.validateSessionTransaction();
		
		Element<T> element = this.searchElement(id);
		element = element != null && !((TreeElementCore<T>) element).isRoot() ? 
				element : null;
		return this.removeElement(element);
	}

	@Override
	public <T> Element<T> getElementById(Object id) throws TreeException {
		TreeElementCore<T> element = null;
		
		validatorFacade.validateSessionTransaction();
		
		if (id == null) {
			return element;
		}

		element = this.searchElement(id);
		return element != null ? element.cloneElement() : element;
	}

	@Override
	public <T> boolean containsElement(Element<T> parent, Element<T> descendant)
			throws TreeException {
		/*
		 * Validates whether the current session is valid.
		 */
		validatorFacade.validateSessionTransaction();
		
		boolean containsChild = Boolean.FALSE;
		
		/*
		 * Null arguments must return false.
		 */
		if (parent == null || descendant == null) {
			return containsChild;
		}
		
		TreeElementCore<T> parentCore = (TreeElementCore<T>) parent;
		TreeElementCore<T> childCore = (TreeElementCore<T>) descendant;
		
		/*
		 * Detached elements must return false.
		 */
		if (!parentCore.getState().equals(ElementState.ATTACHED)
				|| !childCore.getState().equals(ElementState.ATTACHED)) {
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
		/*
		 * Validates whether the current session is valid.
		 */
		validatorFacade.validateSessionTransaction();
		boolean containsChild = Boolean.FALSE;
		
		/*
		 * Null arguments must return false.
		 */
		if (parent == null || descendant == null) {
			return containsChild;
		}
		
		TreeElementCore<?> parentElement = this.searchElement(parent);
		TreeElementCore<?> child = this.searchElement(descendant);
		
		/*
		 * Not found elements must return false.
		 */
		if (parentElement == null || child == null) {
			return containsChild;
		}
		
		return parentElement.getElementById(child.getId()) != null;
	}

	@Override
	public boolean containsElement(Element<?> element) throws TreeException {
		final Operation operation = Operation.CONTAINS;
		
		/*
		 * Validates whether the current session is valid.
		 */
		validatorFacade.validateSessionTransaction();

		boolean containsElement = Boolean.FALSE;
		
		TreeElementCore<?> source = (TreeElementCore<?>) element;
		
		TreeSession currentSession = getTransaction().currentSession();
		
		/*
		 * If the input element does not belong to the current session and it is
		 * not attached to the current tree session, then false must be
		 * returned.
		 */
		if (source != null && currentSession.equals(source.attachedTo())) {
			boolean isAttached = source.getState().canExecuteOperation(operation);
			
			if (isAttached) {
				containsElement = Boolean.TRUE;
			}
		}
		
		return containsElement;
	}

	@Override
	public boolean containsElement(Object id) throws TreeException {
		/*
		 * Validates whether the current session is valid.
		 */
		validatorFacade.validateSessionTransaction();
		
		if (id == null) {
			return Boolean.FALSE;
		}

		return this.searchElement(id) != null;
	}

	@Override
	public <T> Element<T> createElement(Object id, Object parent,
			T wrappedNode) throws TreeException {
		/*
		 * Input validation.
		 */
		validatorFacade.validateSessionTransaction();
		validatorFacade.validateMandatory(id);
		
		TreeSession session = getTransaction().currentSession();
		return TreeFactory.serviceFactory().createElement(id, parent,
				wrappedNode, session);
	}

	@Override
	public <T> Element<T> persistElement(Element<T> newElement)
			throws TreeException {
		/*
		 * Input validation.
		 */
		validatorFacade.validatePersistOperation(newElement);
		
		/*
		 * Clone the input element so as not to save real instances in the tree.
		 */
		newElement = ((TreeElementCore<T>) newElement).cloneElement();
		
		/*
		 * Group the child and parent elements.
		 */
		TreeElementCore<T> parent = this.searchElement(newElement.getParent());
		TreeElementCore<T> child = (TreeElementCore<T>) newElement;
		
		/*
		 * If the parent referenced by @Parent annotation is not found, then
		 * this element will be moved to the root level.
		 */
		if (parent == null) {
			parent = (TreeElementCore<T>) this.tree();
		}
		parent.addChild(child);
		
		/*
		 * Save changes.
		 */
		transaction.commitTransaction();

		return child.cloneElement();
	}

	@Override
	public <T> Element<T> updateElement(Element<T> element)
			throws TreeException {
		/*
		 * Input validation.
		 */
		validatorFacade.validateUpdateOperation(element);
		
		TreeElementCore<T> updatedElement = (TreeElementCore<T>) element;
		
		TreeElementCore<T> source = this.searchElement(element.getId());
		
		Object oldParentId = source.getParent();
		
		/*
		 * References the id of old parent.
		 */
		Object updatedParentId = updatedElement.getParent();
		
		/*
		 * There is a possibility of the element to be moved to the root level
		 */
		TreeElementCore<T> root = (TreeElementCore<T>) this.tree();
		
		/*
		 * If there is a change to the parent of this element, then remove this
		 * element from inside of its old parent and insert it inside of the
		 * new parent one.
		 */
		if (!oldParentId.equals(updatedParentId)) {
			TreeElementCore<T> oldParent = (TreeElementCore<T>) this.
					searchElement(oldParentId);
			TreeElementCore<T> newParent = (TreeElementCore<T>) this.
					searchElement(updatedParentId);
			
			/*
			 * In a case of a non-existent or parent not found, then this
			 * element will be moved to the root level.
			 */
			if (newParent == null) {
				newParent = root;
			}
			
			oldParent.removeChild(source);
			newParent.addChild(source);
			newParent.getChildren();
		}
		
		/*
		 * This loop guarantees that the old child's parent removes the
		 * reference from the child.
		 */
		for (Element<T> iterator : updatedElement.getChildren()) {
			TreeElementCore<T> child = (TreeElementCore<T>) iterator;
			Object oldParentChild = child.getOldParentId();
			
			Element<T> oldParent = this.searchElement(oldParentChild);
				
			HashSet<Element<T>> childrenParent = (HashSet<Element<T>>)
					oldParent.getChildren();
			Element<T> childParent = this.searchElement(child.getId());
			childrenParent.remove(childParent);
				
			child.syncParentId();
		}
		
		Collection<Element<T>> updatedChildren = updatedElement.getChildren();
		Collection<Element<T>> sourceChildren = source.getChildren();
		
		/*
		 * Update children.
		 */
		if (!updatedChildren.equals(sourceChildren)) {
			for (Element<T> child : sourceChildren) {
				child.setParent(null);
				transaction.rollbackElement(child);
			}
			sourceChildren.clear();
			sourceChildren.addAll(updatedElement.getChildren());
		}

		/* Update the wrapped node of the element and all of its descendants. */
		this.updateWrappedNodeDescendants(updatedElement, source);
		
		/*
		 * Obtains the id to be updated. If it is not null then implies that
		 * there is a change in the id attribute.
		 */
		Object updatedId = updatedElement.getUpdatedId();
		
		/*
		 * If there is a change to the element id, refresh the id and reference
		 * the parent of each child element for this one.
		 */
		if (updatedId != null) {
			source.mergeUpdatedId(updatedId);
		}
		
		source.syncParentId();
		
		/*
		 * Save changes in the root element. There is a possibility of the 
		 * element to be moved to the root level. So, this is necessary to
		 * invoke this method instead commitTransaction().
		 */
		transaction.commitElement(root);
		
		return source.cloneElement();
	}

	@Override
	public TreeTransaction getTransaction() {
		return this.transaction;
	}

	@Override
	public <T> Element<T> root() throws TreeException {
		/*
		 * Validates whether the current session is valid.
		 */
		validatorFacade.validateSessionTransaction();
		
		/*
		 * Obtains the root of the tree.
		 */
		TreeElementCore<T> root = (TreeElementCore<T>) this.tree();
		
		return root.cloneElement();
	}

	@Override
	public <T> void apply(Consumer<Element<T>> action) throws TreeException {
		/*
		 * Validates whether the current session is valid.
		 */
		validatorFacade.validateSessionTransaction();

		if (action == null) {
			return;
		}

		Element<T> root = this.root();
		root.apply(action);
		
		Element<T> originalRoot = this.tree();

		this.updateWrappedNodeDescendants(root, originalRoot);
		transaction.commitElement(originalRoot);
	}

	@Override
	public <T> void apply(Consumer<Element<T>> action,
			Predicate<Element<T>> condition) throws TreeException {
		/*
		 * Validates whether the current session is valid.
		 */
		validatorFacade.validateSessionTransaction();

		if (action == null || condition == null) {
			return;
		}

		Element<T> root = this.root();
		root.apply(action, condition);

		Element<T> originalRoot = this.tree();
	
		this.updateWrappedNodeDescendants(root, originalRoot);
		transaction.commitElement(originalRoot);
	}
	
	static TreeManager getTreeManagerInstance() {
		return TreeFactory.serviceFactory().createTreeManagerCore();
	}
	
	/*
	 * Brings up the element from the stored tree session.
	 */
	private <T> TreeElementCore<T> searchElement(Object id) {
		return transaction.refreshElement(id);
	}
	
	/*
	 * Brings up the root element from the stored tree session.
	 */
	private <T> Element<T> tree() {
		return transaction.refresh();
	}

	/*
	 * Update the wrapped node of the element and all of its descendants.
	 */
	private <T> void updateWrappedNodeDescendants(Element<T> originalElement,
			Element<T> elementToApply) {
		Collection<Element<T>> descendants = Recursion.toPlainList(
				originalElement);
		descendants.forEach(descendant -> {
			TreeElementCore<T> descendantCore = (TreeElementCore<T>) descendant;
			T updatedWrappedNode = descendantCore.getUpdatedWrappedNode();

			/*
			 * Update the wrapped node, even if it is null. In this case, the
			 * element will have its wrapped node with null value.
			 */
			TreeElementCore<T> elementCoreToApply = (TreeElementCore<T>)
					elementToApply.getElementById(descendantCore.getId());
			
			/*
			 * If the element core to apply is null, so the element to apply the
			 * function is not found, e.g., root element.
			 */
			if (elementCoreToApply != null) {
				elementCoreToApply.mergeUpdatedWrappedNode(updatedWrappedNode);
			}
		});
	}
}
