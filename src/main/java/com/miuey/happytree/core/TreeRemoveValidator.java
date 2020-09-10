package com.miuey.happytree.core;

import com.miuey.happytree.TreeManager;
import com.miuey.happytree.exception.TreeException;

class TreeRemoveValidator extends TreeCutValidator {

	protected TreeRemoveValidator(TreeManager manager) {
		super(manager);
	}

	
	@Override
	void validateDetachedElement(TreePipeline pipeline) throws TreeException {
		TreeElementCore<?> element = (TreeElementCore<?>) pipeline.getAttribute(
				TreePipelineAttributes.SOURCE_ELEMENT);
		
		Operation operation = (Operation) pipeline.getAttribute(
				TreePipelineAttributes.OPERATION);
		
		if (!element.getState().canExecuteOperation(operation)) {
			throw this.throwTreeException(TreeRepositoryMessage.
					DETACHED_ELEMENT);
		}
		
		if (Recursivity.iterateForInvalidStateOperationValidation(element.
				getChildren(), operation)) {
			throw this.throwTreeException(TreeRepositoryMessage.
					DETACHED_ELEMENT);
		}
	}
}
