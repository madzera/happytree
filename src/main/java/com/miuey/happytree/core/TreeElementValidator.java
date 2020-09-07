package com.miuey.happytree.core;

import com.miuey.happytree.TreeManager;
import com.miuey.happytree.exception.TreeException;

abstract class TreeElementValidator extends TreeValidator {
	
	
	TreeElementValidator(TreeManager manager) {
		super(manager);
	}


	void validateSessionElement(TreePipeline pipeline) throws TreeException {
		TreeElementCore<?> source = (TreeElementCore<?>) pipeline.getAttribute(
				SOURCE_ELEMENT);
		TreeSessionCore session = (TreeSessionCore) pipeline.getAttribute(
				CURRENT_SESSION);
		if (!source.attachedTo().equals(session)) {
			throw this.throwTreeException(TreeRepositoryMessage.
					NOT_BELONG_SESSION);
		}
	}
	
	void validateHandleRootElement(TreePipeline pipeline)
			throws TreeException {
		TreeElementCore<?> source = (TreeElementCore<?>) pipeline.getAttribute(
				SOURCE_ELEMENT);
		if (source != null && source.isRoot()) {
			throw this.throwTreeException(TreeRepositoryMessage.
					IMPOSSIBLE_HANDLE_ROOT);
		}
	}
	
	void validateDetachedElement(TreePipeline pipeline) throws TreeException {
		TreeElementCore<?> source = (TreeElementCore<?>) pipeline.getAttribute(
				SOURCE_ELEMENT);
		TreeElementCore<?> target = (TreeElementCore<?>) pipeline.getAttribute(
				TARGET_ELEMENT);
		Operation operation = (Operation) pipeline.getAttribute(OPERATION);
		
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
				SOURCE_ELEMENT);
		TreeElementCore<?> target = (TreeElementCore<?>) pipeline.getAttribute(
				TARGET_ELEMENT);
		TreeSessionCore session = (TreeSessionCore) pipeline.getAttribute(
				CURRENT_SESSION);
		
		Class<?> sourceType = source.getType();
		Class<?> sessionType = session.getTypeTree();
		
		if (sourceType != null
				&& !sourceType.equals(sessionType)) {
			throw this.throwTreeException(TreeRepositoryMessage.
					MISMATCH_TYPE_ELEMENT);
		}
		
		if (target != null) {
			TreeSessionCore targetSession = (TreeSessionCore) target.
					attachedTo();
			
			if (targetSession != null) {
				Class<?> targetType = target.getType();
				
				if (targetType != null
						&& !targetType.equals(sessionType)) {
					
					throw this.throwTreeException(TreeRepositoryMessage.
							MISMATCH_TYPE_ELEMENT);
				}
			}
		}
	}
	
	abstract void validateDuplicatedIdElement(TreePipeline pipeline)
			throws TreeException;
}
