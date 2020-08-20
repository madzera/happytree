package com.miuey.happytree.core;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.miuey.happytree.Element;

class Recursivity {

	private Recursivity() {}
	
	
	static <T> Element<T> searchElementById(Collection<Element<T>> elements,
			Object id) {
		Element<T> result = null;
		
		if (elements == null || elements.isEmpty()) {
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
	
	static <T> Collection<Element<T>> toPlainList(Element<T> element) {
		List<Element<T>> elements = TreeFactory.collectionFactory().
				createArrayList();
		elements.add(element);
		treePlain(element.getChildren(), elements);
		return elements;
	}
	
	static <T> boolean iterateForNullIdValidation(
			Collection<Element<T>> elements) {
		boolean treeHasNullId = Boolean.FALSE;
		if (elements == null || elements.isEmpty()) {
			return treeHasNullId;
		}
		
		for (Element<T> element : elements) {
			if (element.getId() == null) {
				treeHasNullId = Boolean.TRUE;
				return treeHasNullId;
			}
			treeHasNullId = iterateForNullIdValidation(element.getChildren());
		}
		return treeHasNullId;
	}
	
	static <T> boolean iterateForInvalidStateOperationValidation(
			Collection<Element<T>> elements, Operation operation) {
		boolean treeHasInvalidState = Boolean.FALSE;
		
		if (elements == null || elements.isEmpty()) {
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
	
	static <T> boolean iterateForDuplicatedId(Element<T> element) {
		Set<Object> ids = TreeFactory.collectionFactory().createHashSet();
		Collection<Element<T>> plainTree = toPlainList(element);
		
		for (Element<T> iterator : plainTree) {
			Object iteratorId = iterator.getId();
			if (ids.contains(iteratorId)) {
				return Boolean.TRUE;
			}
			ids.add(iteratorId);
		}
		
		return Boolean.FALSE;
	}
	
	static <T> boolean iterateForDuplicatedId(Element<T> source,
			Element<T> target) {
		Set<Object> targetIds = TreeFactory.collectionFactory().createHashSet();
		
		Collection<Element<T>> targetPlainTree = toPlainList(target);
		for (Element<T> targetElement : targetPlainTree) {
			targetIds.add(targetElement.getId());
		}
		
		Collection<Element<T>> sourcePlainTree = toPlainList(source);
		for (Element<T> sourceElement : sourcePlainTree) {
			Object iteratorId = sourceElement.getId();
			if (targetIds.contains(iteratorId)) {
				return Boolean.TRUE;
			}
		}
		
		return Boolean.FALSE;
	}
	
	private static <T> Element<T> treePlain(Collection<Element<T>> elements,
			List<Element<T>> elementsToAdd) {
		Element<T> result = null;
		
		if (elements == null || elements.isEmpty()) {
			return null;
		}
		
		for (Element<T> element : elements) {
			if (result != null) {
				return result;
			}
			elementsToAdd.add(element);
			result = treePlain(element.getChildren(), elementsToAdd);
		}
		return result;
	}
}
