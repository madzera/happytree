package com.miuey.happytree.core;

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
					IMPOSSIBLE_COPY_ROOT);
		}
	}
	
	abstract void validateMandatoryElementId(TreePipeline pipeline);
	abstract void validateDetachedElement(TreePipeline pipeline)
			throws TreeException;
	abstract void validateDuplicatedElement(TreePipeline pipeline)
			throws TreeException;
}
