package com.miuey.happytree.core;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.exception.TreeException;

class TreeCutValidator extends TreeElementValidator {

	protected TreeCutValidator(TreeManager manager) {
		super(manager);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	void validateDuplicatedIdElement(TreePipeline pipeline) throws TreeException {
		Element<Object> source = (Element<Object>) pipeline.getAttribute(
				SOURCE_ELEMENT_KEY);
		Element<Object> target = (Element<Object>) pipeline.getAttribute(
				TARGET_ELEMENT_KEY);
		
		/*
		 * Just validate when the target element is null. When cutting an
		 * element, the target element can be null, meaning that the source
		 * element will be cut to the root level of the same tree. If the target
		 * is not null it is necessary to validate if in the target tree there
		 * is duplicated id.
		 */
		if (target != null && !source.attachedTo().equals(target.attachedTo())
				&& Recursivity.iterateForDuplicatedId(source, target)) {
			throw this.throwTreeException(TreeRepositoryMessage.
					DUPLICATED_ELEMENT);
		}
	}
}
