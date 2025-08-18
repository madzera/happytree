package com.madzera.happytree.core;

import com.madzera.happytree.TreeManager;
import com.madzera.happytree.exception.TreeException;

abstract class TreeElementValidator extends TreeValidator {
	
	TreeElementValidator(TreeManager manager) {
		super(manager);
	}


	void validateSessionElement(TreePipeline pipeline) throws TreeException {
		TreeElementCore<?> source = (TreeElementCore<?>) pipeline.getAttribute(
				TreePipelineAttributes.SOURCE_ELEMENT);
		TreeSessionCore session = (TreeSessionCore) pipeline.getAttribute(
				TreePipelineAttributes.CURRENT_SESSION);
		TreeElementCore<?> target = (TreeElementCore<?>) pipeline.getAttribute(
				TreePipelineAttributes.TARGET_ELEMENT);
		
		if (!source.attachedTo().equals(session)) {
			throw this.throwTreeException(TreeRepositoryMessage.
					NOT_BELONG_SESSION);
		}
		
		/**
		 * When trying to cut/copy, if the target session element is not active
		 * then this is no possible to cut or copy for their elements.
		 */
		if (target != null && !target.attachedTo().isActive()) {
			throw this.throwTreeException(TreeRepositoryMessage.
					NO_ACTIVE_SESSION);
		}
	}
	
	void validateHandleRootElement(TreePipeline pipeline)
			throws TreeException {
		TreeElementCore<?> source = (TreeElementCore<?>) pipeline.getAttribute(
				TreePipelineAttributes.SOURCE_ELEMENT);
		
		if (source != null && source.isRoot()) {
			throw this.throwTreeException(TreeRepositoryMessage.
					IMPOSSIBLE_HANDLE_ROOT);
		}
	}
	
	void validateDetachedElement(TreePipeline pipeline) throws TreeException {
		TreeElementCore<?> source = (TreeElementCore<?>) pipeline.getAttribute(
				TreePipelineAttributes.SOURCE_ELEMENT);
		TreeElementCore<?> target = (TreeElementCore<?>) pipeline.getAttribute(
				TreePipelineAttributes.TARGET_ELEMENT);
		Operation operation = (Operation) pipeline.getAttribute(
				TreePipelineAttributes.OPERATION);
		
		if (!source.getState().canExecuteOperation(operation)
				|| Recursion.iterateForInvalidStateOperationValidation(source.
						getChildren(), operation)) {
			throw this.throwTreeException(TreeRepositoryMessage.
					DETACHED_ELEMENT);
		}
		
		if ((target != null)
				&& (!target.getState().canExecuteOperation(operation)
				|| Recursion.iterateForInvalidStateOperationValidation(target.
						getChildren(), operation))) {
			throw this.throwTreeException(TreeRepositoryMessage.
					DETACHED_ELEMENT);
		}
	}

	void validateMismatchParameterizedType(TreePipeline pipeline)
			throws TreeException {
		TreeElementCore<?> source = (TreeElementCore<?>) pipeline.getAttribute(
				TreePipelineAttributes.SOURCE_ELEMENT);
		TreeElementCore<?> target = (TreeElementCore<?>) pipeline.getAttribute(
				TreePipelineAttributes.TARGET_ELEMENT);
		TreeSessionCore session = (TreeSessionCore) pipeline.getAttribute(
				TreePipelineAttributes.CURRENT_SESSION);
		
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
	
	void validateMismatchObjectIdType(TreePipeline pipeline)
			throws TreeException {
		Object sourceObjectId = pipeline.getAttribute(TreePipelineAttributes.
				SOURCE_OBJECT_ID);
		Object targetObjectId = pipeline.getAttribute(TreePipelineAttributes.
				TARGET_OBJECT_ID);
		
		Class<?> sourceClass = sourceObjectId.getClass();
		Class<?> targetClass = targetObjectId != null?
				targetObjectId.getClass() : null;

		if (targetClass != null && !sourceClass.equals(targetClass)) {
			throw this.throwTreeException(TreeRepositoryMessage.
							MISMATCH_TYPE_ELEMENT);
		}
	}

	void validateIfSourceElementExistsById(TreePipeline pipeline)
			throws TreeException {
		TreeElementCore<?> source = (TreeElementCore<?>) pipeline.getAttribute(
				TreePipelineAttributes.SOURCE_ELEMENT);
		
		if (source == null) {
			throw this.throwTreeException(TreeRepositoryMessage.NOTFOUND_ELEMENT);
		}
	}

	abstract void validateDuplicateIdElement(TreePipeline pipeline)
			throws TreeException;

}
