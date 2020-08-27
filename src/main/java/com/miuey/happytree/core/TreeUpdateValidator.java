package com.miuey.happytree.core;

import java.util.Collection;
import java.util.Set;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeSession;
import com.miuey.happytree.exception.TreeException;

class TreeUpdateValidator extends TreeElementValidator {

	TreeUpdateValidator(TreeManager manager) {
		super(manager);
	}
	
	
	@Override
	void validateDetachedElement(TreePipeline pipeline) throws TreeException {
		TreeElementCore<?> element = (TreeElementCore<?>) pipeline.getAttribute(
				SOURCE_ELEMENT_KEY);
		Operation operation = (Operation) pipeline.getAttribute("operation");
		
		if (!element.getState().canExecuteOperation(operation)) {
			throw this.throwTreeException(TreeRepositoryMessage.
					NOT_EXISTED_ELEMENT);
		}
		
		if (Recursivity.iterateForInvalidStateOperationValidation(element.
				getChildren(), operation)) {
			throw this.throwTreeException(TreeRepositoryMessage.
					NOT_EXISTED_ELEMENT);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	void validateDuplicatedIdElement(TreePipeline pipeline) throws TreeException {
		Element<Object> source = (Element<Object>) pipeline.getAttribute(
				SOURCE_ELEMENT_KEY);
		TreeSession session = source.attachedTo();
		Element<Object> root = session.tree();
		
		Set<Object> rootIds = TreeFactory.collectionFactory().createHashSet();
		
		Collection<Element<Object>> targetPlainTree = Recursivity.toPlainList(
				root);
		for (Element<Object> rootElement : targetPlainTree) {
			TreeElementCore<Object> rootChild = (TreeElementCore<Object>)
					rootElement;
			rootIds.add(rootChild.getId());
		}
		
		Collection<Element<Object>> sourcePlainTree = Recursivity.toPlainList(
				source);
		for (Element<Object> sourceElement : sourcePlainTree) {
			TreeElementCore<Object> sourceChild = (TreeElementCore<Object>)
					sourceElement;
			if (rootIds.contains(sourceChild.getUpdatedId())) {
				throw this.throwTreeException(TreeRepositoryMessage.
						DUPLICATED_ELEMENT);
			}
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
