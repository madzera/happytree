package com.miuey.happytree.core.validator;

import com.miuey.happytree.TreeSession;
import com.miuey.happytree.core.TreePipeline;
import com.miuey.happytree.exception.TreeException;

public class NoDefinedSessionValidator extends ValidatorGenericService {

	@Override
	protected void validate(TreePipeline pipeline) throws TreeException {
		TreeSession session = (TreeSession) pipeline.getAttribute("session");
		
		if (session == null) {
			throw this.throwTreeException(ValidatorRepositoryMessage.NO_DEFINED_SESSION);
		}
		doChain(pipeline);
	}
}
