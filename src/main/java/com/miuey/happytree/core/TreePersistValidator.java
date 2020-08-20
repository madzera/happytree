package com.miuey.happytree.core;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
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
		
		if (Recursivity.iterateForDuplicatedId(source)) {
			throw this.throwTreeException(TreeRepositoryMessage.
					DUPLICATED_ELEMENT);
		}
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
