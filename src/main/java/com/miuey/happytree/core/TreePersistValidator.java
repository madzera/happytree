package com.miuey.happytree.core;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeSession;
import com.miuey.happytree.exception.TreeException;

class TreePersistValidator extends TreeElementValidator {

	TreePersistValidator(TreeManager manager) {
		super(manager);
	}
	

	@Override
	void validateDetachedElement(TreePipeline pipeline) throws TreeException {
		TreeElementCore<?> element = (TreeElementCore<?>) pipeline.getAttribute(
				SOURCE_ELEMENT_KEY);
		Operation operation = (Operation) pipeline.getAttribute("operation");
		
		if (!element.getState().canExecuteOperation(operation)) {
			throw this.throwTreeException(TreeRepositoryMessage.
					ATTACHED_ELEMENT);
		}
		
		if (Recursivity.iterateForInvalidStateOperationValidation(element.
				getChildren(), operation)) {
			throw this.throwTreeException(TreeRepositoryMessage.
					ATTACHED_ELEMENT);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	void validateDuplicatedIdElement(TreePipeline pipeline) throws TreeException {
		Element<Object> source = (Element<Object>) pipeline.getAttribute(
				SOURCE_ELEMENT_KEY);
		TreeSession session = source.attachedTo();
		Element<Object> root = session.tree();
		if (Recursivity.iterateForDuplicatedId(source, root)) {
			throw this.throwTreeException(TreeRepositoryMessage.
					DUPLICATED_ELEMENT);
		}
	}
}
