package com.miuey.happytree.core.validator;

import com.miuey.happytree.core.TreePipeline;
import com.miuey.happytree.exception.TreeException;

public class NotNullValidator extends GenericServiceValidator {

	@Override
	protected void validate(TreePipeline pipeline) throws TreeException {
		String input = (String) pipeline.getAttribute("sessionIdentifier");
		
		if (input == null || input.length() == 0) {
			throw this.throwIllegalArgumentException(RepositoryMessage.
					INVALID_INPUT);		
		}
		doChain(pipeline);
	}
}
