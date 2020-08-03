package com.miuey.happytree.core;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeSession;
import com.miuey.happytree.TreeTransaction;
import com.miuey.happytree.exception.TreeException;

class TreeCopyValidator extends TreeElementValidator {

	protected TreeCopyValidator(TreeManager manager) {
		super(manager);
	}

	
	@Override
	void validateMandatoryElementId(TreePipeline pipeline) {
		Element<?> source = (Element<?>) pipeline.getAttribute(
				SOURCE_ELEMENT_KEY);
		Element<?> target = (Element<?>) pipeline.getAttribute(
				TARGET_ELEMENT_KEY);
		
		if (source == null || source.getId() == null ||
				target == null || target.getId() == null) {
			throw this.throwIllegalArgumentException(TreeRepositoryMessage.
					INVALID_INPUT);
		}
	}
	
	@Override
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
		if (!target.isAttached() && target.attachedTo() != null) {
			throw this.throwTreeException(TreeRepositoryMessage.
					DETACHED_ELEMENT);
		}
	}
	
	@Override
	void validateDuplicatedElement(TreePipeline pipeline) throws TreeException {
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
		
		TreeTransaction transaction = getManager().getTransaction();
		try {
			transaction.sessionCheckout(targetSessionId);
			
			Element<?> duplicatedElement = getManager().getElementById(source.
					getId());
			if (duplicatedElement != null) {
				throw this.throwTreeException(TreeRepositoryMessage.
						DUPLICATED_ELEMENT);
			}
			validateDuplicatedChildrenId(source.getChildren());
		} finally {
			/*
			 * Roll back to the source session.
			 */
			transaction.sessionCheckout(sourceSessionId);
		}
	}
}
