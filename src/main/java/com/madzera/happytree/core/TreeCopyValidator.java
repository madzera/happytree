package com.madzera.happytree.core;

import com.madzera.happytree.Element;
import com.madzera.happytree.TreeManager;
import com.madzera.happytree.TreeSession;
import com.madzera.happytree.exception.TreeException;

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
		
		if (Recursion.iterateForDuplicatedId(source, targetRoot)) {
			throw this.throwTreeException(TreeRepositoryMessage.
					DUPLICATED_ELEMENT);
		}
	}
}
