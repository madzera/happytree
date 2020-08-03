package com.miuey.happytree.core;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeSession;
import com.miuey.happytree.TreeTransaction;
import com.miuey.happytree.exception.TreeException;

class TreeCutValidator extends TreeElementValidator {

	protected TreeCutValidator(TreeManager manager) {
		super(manager);
	}

	
	@Override
	void validateMandatoryElementId(TreePipeline pipeline) {
		Element<?> source = (Element<?>) pipeline.getAttribute(
				SOURCE_ELEMENT_KEY);
		if (source == null || source.getId() == null) {
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
		if ((target != null) && 
				(!target.isAttached() && target.attachedTo() != null)) {
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
		
		/*
		 * It is only possible to cut an element for inside another tree
		 * session. If there is no target tree session, then it does not need to
		 * be validated.
		 */
		if (target != null) {
			String sourceSessionId = source.attachedTo();
			String targetSessionId = target.attachedTo();
			if (!sourceSessionId.equals(targetSessionId)) {
				Object id = source.getId();
				
				TreeManager manager = getManager();
				TreeTransaction transaction = manager.getTransaction();
				
				try {
					/*
					 * Verify if the target session already has the Id.
					 */
					transaction.sessionCheckout(targetSessionId);
					Element<?> duplicatedElement = manager.getElementById(id);
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
	}
}
