package com.madzera.happytree.core;

import com.madzera.happytree.Element;
import com.madzera.happytree.TreeManager;
import com.madzera.happytree.TreeSession;
import com.madzera.happytree.exception.TreeException;

class TreePersistValidator extends TreeElementValidator {

	protected TreePersistValidator(TreeManager manager) {
		super(manager);
	}
	

	@Override
	void validateDetachedElement(TreePipeline pipeline) throws TreeException {
		TreeElementCore<?> element = (TreeElementCore<?>) pipeline.getAttribute(
				TreePipelineAttributes.SOURCE_ELEMENT);
		Operation operation = (Operation) pipeline.getAttribute(
				TreePipelineAttributes.OPERATION);
		
		if (!element.getState().canExecuteOperation(operation)) {
			throw this.throwTreeException(TreeRepositoryMessage
					.ATTACHED_ELEMENT);
		}
		
		if (Recursion.iterateForInvalidStateOperationValidation(element.
				getChildren(), operation)) {
			throw this.throwTreeException(TreeRepositoryMessage
					.ATTACHED_ELEMENT);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	void validateDuplicateIdElement(TreePipeline pipeline) throws TreeException {
		Element<Object> source = (Element<Object>) pipeline.getAttribute(
				TreePipelineAttributes.SOURCE_ELEMENT);
		
		TreeSession session = source.attachedTo();
		Element<Object> root = session.tree();
		
		if (Recursion.iterateForDuplicateId(source, root)) {
			throw this.throwTreeException(TreeRepositoryMessage
					.DUPLICATE_ELEMENT);
		}
	}
}
