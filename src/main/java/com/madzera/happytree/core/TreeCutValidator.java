package com.madzera.happytree.core;

import com.madzera.happytree.Element;
import com.madzera.happytree.TreeManager;
import com.madzera.happytree.TreeSession;
import com.madzera.happytree.exception.TreeException;

class TreeCutValidator extends TreeElementValidator {

	protected TreeCutValidator(TreeManager manager) {
		super(manager);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	void validateDuplicatedIdElement(TreePipeline pipeline)
			throws TreeException {
		Element<Object> source = (Element<Object>) pipeline.getAttribute(
				TreePipelineAttributes.SOURCE_ELEMENT);
		Element<Object> target = (Element<Object>) pipeline.getAttribute(
				TreePipelineAttributes.TARGET_ELEMENT);
		
		/*
		 * Just validate when the target element is null. When cutting an
		 * element, the target element can be null, meaning that the source
		 * element will be cut to the root level of the same tree. If the target
		 * is not null it is necessary to validate if in the target tree there
		 * is duplicated id. It is not necessary validate duplicated id from
		 * the source element because cut/copy operations only allow attached
		 * elements, then while trying to force a duplicated id, these elements
		 * will turn on as detached, stopping on previous validation.
		 */
		if (target != null) {
			TreeSession targetSession = target.attachedTo();
			
			if (!source.attachedTo().equals(targetSession)) {
				Element<Object> targetRoot = targetSession.tree();
				
				if (Recursivity.iterateForDuplicatedId(source, targetRoot)) {
					throw this.throwTreeException(TreeRepositoryMessage.
							DUPLICATED_ELEMENT);
				}
			}
		}
	}
}
