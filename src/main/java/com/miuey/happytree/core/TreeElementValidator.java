package com.miuey.happytree.core;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeSession;
import com.miuey.happytree.TreeTransaction;
import com.miuey.happytree.exception.TreeException;

class TreeElementValidator extends TreeValidator {
	
	private static final String SOURCE_ELEMENT_KEY = "sourceElement";
	private static final String TARGET_ELEMENT_KEY = "targetElement";
	
	
	protected TreeElementValidator(TreeManager manager) {
		super(manager);
	}

	
	void validateMandatoryElementId(TreePipeline pipeline) {
		Element<?> source = (Element<?>) pipeline.getAttribute(
				SOURCE_ELEMENT_KEY);
		if (source == null || source.getId() == null) {
			throw this.throwIllegalArgumentException(TreeRepositoryMessage.
					INVALID_INPUT);
		}
	}
	
	void validateDetachedElement(TreePipeline pipeline) throws TreeException {
		TreeElementCore<?> source = (TreeElementCore<?>) pipeline.getAttribute(
				SOURCE_ELEMENT_KEY);
		TreeElementCore<?> target = (TreeElementCore<?>) pipeline.getAttribute(
				TARGET_ELEMENT_KEY);
		
		TreeSession session = getManager().getTransaction().currentSession();
		String sessionId = session.getSessionId();
		
		if (!source.isAttached() || !sessionId.equals(source.attachedTo())) {
			throw this.throwTreeException(TreeRepositoryMessage.
					DETACHED_ELEMENT);
		}
		if ((target != null) && 
				(!target.isAttached() && target.attachedTo() != null)) {
			throw this.throwTreeException(TreeRepositoryMessage.
					DETACHED_ELEMENT);
		}
	}
	
	void validateDuplicatedElementToBeCut(TreePipeline pipeline)
			throws TreeException {
		Element<?> source = (Element<?>) pipeline.getAttribute(
				SOURCE_ELEMENT_KEY);
		Element<?> target = (Element<?>) pipeline.getAttribute(
				TARGET_ELEMENT_KEY);
		
		String sourceSessionId = source.attachedTo();
		String targetSessionId = target.attachedTo();
		if (!sourceSessionId.equals(targetSessionId)) {
			Object id = source.getId();
			
			TreeManager manager = getManager();
			TreeTransaction transaction = manager.getTransaction();
			
			/*
			 * Verify if the target session already has the Id.
			 */
			transaction.sessionCheckout(targetSessionId);
			
			Element<?> duplicatedElement = manager.getElementById(id);
			
			/*
			 * Roll back to the source session.
			 */
			transaction.sessionCheckout(sourceSessionId);
			if (duplicatedElement != null) {
				throw this.throwTreeException(TreeRepositoryMessage.
						DUPLICATED_ELEMENT);
			}
		}
	}
	
	void validateDuplicatedElementToBeCopied(TreePipeline pipeline)
			throws TreeException {
		Element<?> source = (Element<?>) pipeline.getAttribute(
				SOURCE_ELEMENT_KEY);
		Element<?> target = (Element<?>) pipeline.getAttribute(
				TARGET_ELEMENT_KEY);
		
		String sourceSessionId = source.attachedTo();
		String targetSessionId = target.attachedTo();
		if (sourceSessionId.equals(targetSessionId)) {
			throw this.throwTreeException(TreeRepositoryMessage.
					DUPLICATED_ELEMENT);
		}
	}
}
