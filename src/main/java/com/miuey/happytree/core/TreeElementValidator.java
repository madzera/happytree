package com.miuey.happytree.core;

import java.util.Collection;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.exception.TreeException;

abstract class TreeElementValidator extends TreeValidator {
	
	static final String SOURCE_ELEMENT_KEY = "sourceElement";
	static final String TARGET_ELEMENT_KEY = "targetElement";
	
	
	TreeElementValidator(TreeManager manager) {
		super(manager);
	}

	
	void validateCutCopyRemoveRootElement(TreePipeline pipeline)
			throws TreeException {
		TreeElementCore<?> source = (TreeElementCore<?>) pipeline.getAttribute(
				SOURCE_ELEMENT_KEY);
		if (source != null && source.isRoot()) {
			throw this.throwTreeException(TreeRepositoryMessage.
					IMPOSSIBLE_HANDLE_ROOT);
		}
	}
	
	<T> Element<T> validateDuplicatedChildrenId(Collection<Element<T>> elements)
			throws TreeException {
		Element<T> result = null;
		if (elements == null || elements.isEmpty()) {
			return null;
		}
		for (Element<T> element : elements) {
			if (result != null) {
				return result;
			}
			Object id = element.getId();
			Element<T> duplicatedElement = getManager().getElementById(id);
			if (duplicatedElement != null) {
				throw this.throwTreeException(TreeRepositoryMessage.
						DUPLICATED_ELEMENT);
			}
			result = validateDuplicatedChildrenId(element.getChildren());
		}
		return result;
	}
	
	abstract void validateMandatoryElementId(TreePipeline pipeline);
	abstract void validateDetachedElement(TreePipeline pipeline)
			throws TreeException;
	abstract void validateDuplicatedElement(TreePipeline pipeline)
			throws TreeException;
}
