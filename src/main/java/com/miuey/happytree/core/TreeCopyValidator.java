package com.miuey.happytree.core;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.exception.TreeException;

class TreeCopyValidator extends TreeElementValidator {

	protected TreeCopyValidator(TreeManager manager) {
		super(manager);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	void validateDuplicatedIdElement(TreePipeline pipeline) throws TreeException {
		Element<Object> source = (Element<Object>) pipeline.getAttribute(
				SOURCE_ELEMENT_KEY);
		Element<Object> target = (Element<Object>) pipeline.getAttribute(
				TARGET_ELEMENT_KEY);
		
		if (Recursivity.iterateForDuplicatedId(source, target)) {
			throw this.throwTreeException(TreeRepositoryMessage.
					DUPLICATED_ELEMENT);
		}
	}
}
