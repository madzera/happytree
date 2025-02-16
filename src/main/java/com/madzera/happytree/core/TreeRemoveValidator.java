package com.madzera.happytree.core;

import com.madzera.happytree.TreeManager;
import com.madzera.happytree.exception.TreeException;

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
		
		if (Recursion.iterateForInvalidStateOperationValidation(element.
				getChildren(), operation)) {
			throw this.throwTreeException(TreeRepositoryMessage.
					DETACHED_ELEMENT);
		}
	}
}
