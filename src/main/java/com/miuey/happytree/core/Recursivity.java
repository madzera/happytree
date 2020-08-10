package com.miuey.happytree.core;

import java.util.Collection;
import java.util.List;

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
		cycle(element.getChildren(), elements);
		return elements;
	}
	
	private static <T> Element<T> cycle(Collection<Element<T>> elements,
			List<Element<T>> elementsToAdd) {
		Element<T> result = null;
		
		if (elements == null || elements.isEmpty()) {
			return null;
		}
		
		for (Element<T> element : elements) {
			if (result != null) {
				return result;
			}
			elementsToAdd.add(result);
			result = searchElementById(element.getChildren(), elementsToAdd);
		}
		return result;
	}
}
