package com.madzera.happytree.core;

import java.util.Collection;
import java.util.Set;

import com.madzera.happytree.Element;
import com.madzera.happytree.TreeManager;
import com.madzera.happytree.TreeSession;
import com.madzera.happytree.exception.TreeException;

class TreeUpdateValidator extends TreeElementValidator {

	TreeUpdateValidator(TreeManager manager) {
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
					NOT_EXISTED_ELEMENT);
		}
		
		if (Recursion.iterateForInvalidStateOperationValidation(element.
				getChildren(), operation)) {
			throw this.throwTreeException(TreeRepositoryMessage.
					NOT_EXISTED_ELEMENT);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	void validateDuplicateIdElement(TreePipeline pipeline) throws TreeException {
		Element<Object> source = (Element<Object>) pipeline.getAttribute(
				TreePipelineAttributes.SOURCE_ELEMENT);
		
		TreeSession session = source.attachedTo();
		Element<Object> root = session.tree();
		
		Set<Object> rootIds = TreeFactory.collectionFactory().createHashSet();
		
		Collection<Element<Object>> targetPlainTree = Recursion.toPlainList(
				root);
		
		/*
		 * Disconsider root element.
		 */
		targetPlainTree.removeIf(e -> ((TreeElementCore<Object>) e).isRoot());

		for (Element<Object> rootElement : targetPlainTree) {
			TreeElementCore<Object> rootChild = (TreeElementCore<Object>)
					rootElement;
			rootIds.add(rootChild.getId());
		}
		
		Collection<Element<Object>> sourcePlainTree = Recursion.toPlainList(
				source);
		for (Element<Object> sourceElement : sourcePlainTree) {
			TreeElementCore<Object> sourceChild = (TreeElementCore<Object>)
					sourceElement;
			if (rootIds.contains(sourceChild.getUpdatedId())) {
				throw this.throwTreeException(TreeRepositoryMessage.
					DUPLICATE_ELEMENT);
			}
		}
	}
}
