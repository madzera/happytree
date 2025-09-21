package com.madzera.happytree.core;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.madzera.happytree.Element;

/*
 * Utility that assists the operations that involves trees.
 */
class Recursion {

	private Recursion() {}
	
	
	/*
	 * Searches inside of the tree for an element by the id.
	 */
	static <T> Element<T> searchElementById(Collection<Element<T>> elements,
			Object id) {
		Element<T> result = null;
		
		if (elements.isEmpty()) {
			return null;
		}
		
		for (Element<T> element : elements) {
			if (result != null) {
				return result;
			}
			if (element.getId().equals(id)) {
				result = element;
				return result;
			}
			result = searchElementById(element.getChildren(), id);
		}
		
		return result;
	}
	
	/*
	 * Transforms a tree structure into a plain list structure, but keeping the
	 * references inside each Element object.
	 */
	static <T> Collection<Element<T>> toPlainList(Element<T> element) {
		List<Element<T>> elements = TreeFactory.collectionFactory().
				createArrayList();
		elements.add(element);
		treePlain(element.getChildren(), elements);
		
		return elements;
	}
	
	/*
	 * Iterates over the tree and verifies that each element inside of the tree
	 * can run a determined operation according the its life cycle state.
	 */
	static <T> boolean iterateForInvalidStateOperationValidation(
			Collection<Element<T>> elements, Operation operation) {
		boolean treeHasInvalidState = Boolean.FALSE;
		
		if (elements.isEmpty()) {
			return treeHasInvalidState;
		}
		
		for (Element<T> element : elements) {
			TreeElementCore<T> elementCore = (TreeElementCore<T>) element;
			
			if (!elementCore.getState().canExecuteOperation(operation)) {
				treeHasInvalidState = Boolean.TRUE;
				return treeHasInvalidState;
			}
			treeHasInvalidState = iterateForInvalidStateOperationValidation(
					element.getChildren(), operation);
		}
		return treeHasInvalidState;
	}
	
	/*
	 * Verifies inside of the tree the existence of duplicate id.
	 */
	static <T> boolean iterateForDuplicateId(Element<T> source,
			Element<T> target) {
		Set<Object> sourceIds = TreeFactory.collectionFactory().createHashSet();
		Set<Object> targetIds = TreeFactory.collectionFactory().createHashSet();

		Collection<Element<T>> targetPlainTree = toPlainList(target);

		for (Element<T> targetElement : targetPlainTree) {
			targetIds.add(targetElement.getId());
		}

		Collection<Element<T>> sourcePlainTree = toPlainList(source);
		for (Element<T> sourceElement : sourcePlainTree) {
			Object iteratorId = sourceElement.getId();

			if (targetIds.contains(iteratorId)
					|| sourceIds.contains(iteratorId)) {
				return Boolean.TRUE;
			}

			sourceIds.add(iteratorId);
		}

		return Boolean.FALSE;
	}
	
	/*
	 * Verifies in the tree the existence of nullable wrapped object node.
	 */
	static <T> boolean iterateForNullWrappedNode(
			Collection<Element<T>> elements) {
		boolean treeHasNullWrappedNode = Boolean.FALSE;

		if (elements.isEmpty()) {
			return treeHasNullWrappedNode;
		}

		for (Element<T> element : elements) {
			if (element.unwrap() == null) {
				treeHasNullWrappedNode = Boolean.TRUE;
				return treeHasNullWrappedNode;
			}
			if (!treeHasNullWrappedNode) {
				treeHasNullWrappedNode = iterateForNullWrappedNode(
						element.getChildren());
			}
		}
		return treeHasNullWrappedNode;
	}
	
	private static <T> Element<T> treePlain(Collection<Element<T>> elements,
			List<Element<T>> elementsToAdd) {
		Element<T> result = null;
		
		if (elements.isEmpty()) {
			return null;
		}
		
		for (Element<T> element : elements) {
			elementsToAdd.add(element);
			result = treePlain(element.getChildren(), elementsToAdd);
		}
		
		return result;
	}
}
