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
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Element<T> cut(Element<T> from, Element<T> to)
			throws TreeException {
		final Operation operation = Operation.CUT;
		validatorFacade.validateTransaction();
		validatorFacade.validateMandatory(from);
		validatorFacade.validateCutCopyOperation(from, to, operation);

		TreeElementCore<T> source = (TreeElementCore<T>) this.getElementById(
				from.getId());
		
		TreeElementCore<T> parent = (TreeElementCore<T>) this.getElementById(
				source.getParent());
		
		if (parent != null) {
			parent.removeChild(source);
			parent.setState(ElementState.ATTACHED);
		}
		
		TreeSessionCore session = (TreeSessionCore) getTransaction().
				currentSession();
		TreeElementCore<T> target = null;
		
		if (to == null) {
			TreeElementCore<T> root = (TreeElementCore<T>) root();
			root.addChild(source);
			root.setState(ElementState.ATTACHED);
		} else {
			/*
			 * The session of the target can be different, so it is necessary to
			 * checkout the respective session.
			 */
			TreeSessionCore targetSession = (TreeSessionCore) getTransaction().
					sessionCheckout(to.attachedTo().getSessionId());
			target = (TreeElementCore<T>) this.getElementById(to.getId());
			target.addChild(source);
			target.setState(ElementState.ATTACHED);
			
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
		
		source.setState(ElementState.ATTACHED);
		return source.cloneElement();
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
			validatorFacade.validateCutCopyOperation(fromElement, toElement,
					operation);
		}
		
		Element<T> source = this.getElementById(from);
		Element<T> target = this.getElementById(to);
		
		return this.cut(source, target);
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
