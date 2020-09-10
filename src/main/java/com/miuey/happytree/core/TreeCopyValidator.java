package com.miuey.happytree.core;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeSession;
import com.miuey.happytree.exception.TreeException;

class TreeCopyValidator extends TreeElementValidator {

	protected TreeCopyValidator(TreeManager manager) {
		super(manager);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	void validateDuplicatedIdElement(TreePipeline pipeline) throws TreeException {
		Element<Object> source = (Element<Object>) pipeline.getAttribute(
				TreePipelineAttributes.SOURCE_ELEMENT);
		Element<Object> target = (Element<Object>) pipeline.getAttribute(
				TreePipelineAttributes.TARGET_ELEMENT);
		
		TreeSession targetSession = target.attachedTo();
		Element<Object> targetRoot = targetSession.tree();
		
		if (Recursivity.iterateForDuplicatedId(source, targetRoot)) {
			throw this.throwTreeException(TreeRepositoryMessage.
					DUPLICATED_ELEMENT);
		}
	}
}
