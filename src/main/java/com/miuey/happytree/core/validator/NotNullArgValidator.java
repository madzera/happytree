package com.miuey.happytree.core.validator;

import com.miuey.happytree.core.TreePipeline;
import com.miuey.happytree.exception.TreeException;

public class NotNullArgValidator extends ValidatorGenericService {

	@Override
	protected void validate(TreePipeline pipeline) throws TreeException {
		Object arg = pipeline.getAttribute("arg");
		
		if ((arg == null) || 
				(arg instanceof String && ((String) arg).length() == 0)) {
			throw this.throwIllegalArgumentException(ValidatorRepositoryMessage.
					INVALID_INPUT);
		}
		doChain(pipeline);
	}
}
