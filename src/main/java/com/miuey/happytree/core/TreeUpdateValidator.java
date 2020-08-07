package com.miuey.happytree.core;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeSession;
import com.miuey.happytree.exception.TreeException;

class TreeUpdateValidator extends TreeElementValidator {

	TreeUpdateValidator(TreeManager manager) {
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
		Element<?> source = (Element<?>) pipeline.getAttribute(
				SOURCE_ELEMENT_KEY);
		TreeElementCore<?> element = (TreeElementCore<?>) source;
		TreeSession session = getManager().getTransaction().currentSession();
		
		String elementSession = element.attachedTo();
		if (elementSession == null 
				|| !session.getSessionId().equals(elementSession)) {
			throw this.throwTreeException(TreeRepositoryMessage.
					NOT_EXISTED_ELEMENT);
		}
	}

	@Override
	void validateDuplicatedElement(TreePipeline pipeline) throws TreeException {
		//TODO
	}
	
	<T> void validateTypeOfElement(TreePipeline pipeline) throws TreeException {
		@SuppressWarnings("unchecked")
		Element<T> source = (Element<T>) pipeline.getAttribute(
				SOURCE_ELEMENT_KEY);
		T unwrappedObj = source.unwrap();
		if (unwrappedObj != null) {
			TreeManager manager = getManager();
			TreeSessionCore session = (TreeSessionCore) manager.
					getTransaction().currentSession();
			if (!unwrappedObj.getClass().equals(session.getTypeTree())) {
				throw this.throwTreeException(TreeRepositoryMessage.
						MISMATCH_TYPE_ELEMENT);
			}
		}
	}
}
