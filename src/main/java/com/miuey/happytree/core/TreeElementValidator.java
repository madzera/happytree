package com.miuey.happytree.core;

import com.miuey.happytree.TreeManager;
import com.miuey.happytree.exception.TreeException;

abstract class TreeElementValidator extends TreeValidator {
	
	static final String SOURCE_ELEMENT_KEY = "sourceElement";
	static final String TARGET_ELEMENT_KEY = "targetElement";
	
	
	TreeElementValidator(TreeManager manager) {
		super(manager);
	}

	
	void validateHandleRootElement(TreePipeline pipeline)
			throws TreeException {
		TreeElementCore<?> source = (TreeElementCore<?>) pipeline.getAttribute(
				SOURCE_ELEMENT_KEY);
		if (source != null && source.isRoot()) {
			throw this.throwTreeException(TreeRepositoryMessage.
					IMPOSSIBLE_HANDLE_ROOT);
		}
	}
	
	void validateDetachedElement(TreePipeline pipeline) throws TreeException {
		TreeElementCore<?> source = (TreeElementCore<?>) pipeline.getAttribute(
				SOURCE_ELEMENT_KEY);
		TreeElementCore<?> target = (TreeElementCore<?>) pipeline.getAttribute(
				TARGET_ELEMENT_KEY);
		Operation operation = (Operation) pipeline.getAttribute("operation");
		
		if (!source.getState().canExecuteOperation(operation)
				|| Recursivity.iterateForInvalidStateOperationValidation(source.
						getChildren(), operation)) {
			throw this.throwTreeException(TreeRepositoryMessage.
					DETACHED_ELEMENT);
		}
		
		if ((target != null)
				&& (!target.getState().canExecuteOperation(operation)
				|| Recursivity.iterateForInvalidStateOperationValidation(target.
						getChildren(), operation))) {
			throw this.throwTreeException(TreeRepositoryMessage.
					DETACHED_ELEMENT);
		}
	}

	void validateMismatchParameterizedType(TreePipeline pipeline)
			throws TreeException {
		TreeElementCore<?> source = (TreeElementCore<?>) pipeline.getAttribute(
				SOURCE_ELEMENT_KEY);
		TreeElementCore<?> target = (TreeElementCore<?>) pipeline.getAttribute(
				TARGET_ELEMENT_KEY);
		
		if (source != null && target != null) {
			Class<?> sourceType = source.getType();
			Class<?> targetType = target.getType();
			if (sourceType != null && targetType != null && !sourceType.equals(
					targetType)) {
				throw this.throwTreeException(TreeRepositoryMessage.
						MISMATCH_TYPE_ELEMENT);
			}
		}
	}
	
	abstract void validateDuplicatedIdElement(TreePipeline pipeline)
			throws TreeException;
}
