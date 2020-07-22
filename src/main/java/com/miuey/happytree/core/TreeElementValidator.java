package com.miuey.happytree.core;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeSession;
import com.miuey.happytree.exception.TreeException;

class TreeElementValidator extends TreeValidator {
	
	private static final String ELEMENT_KEY = "element";

	
	protected TreeElementValidator(TreeManager manager) {
		super(manager);
	}

	
	void validateMandatoryElementId(TreePipeline pipeline) {
		Element<?> element = (Element<?>) pipeline.getAttribute(ELEMENT_KEY);
		
		if (element == null || element.getId() == null) {
			throw this.throwIllegalArgumentException(TreeRepositoryMessage.
					INVALID_INPUT);
		}
	}
	
	void validateDetachedElement(TreePipeline pipeline) throws TreeException {
		TreeElementCore<?> element = (TreeElementCore<?>) pipeline.
				getAttribute(ELEMENT_KEY);
		TreeSession session = getManager().getTransaction().currentSession();
		String sessionId = session.getSessionId();
		
		if (!element.isAttached() || !sessionId.equals(element.attachedTo())) {
			throw this.throwTreeException(TreeRepositoryMessage.
					DETACHED_ELEMENT);
		}
	}
	
	void validateDuplicatedElement(TreePipeline pipeline) throws TreeException {
		Element<?> element = (Element<?>) pipeline.getAttribute(ELEMENT_KEY);
		TreeManager manager = getManager();
		
		Element<?> duplicatedElement = manager.getElementById(element.getId());
		
		if (duplicatedElement != null) {
			throw this.throwTreeException(TreeRepositoryMessage.
					DUPLICATED_ELEMENT);
		}
	}
}
