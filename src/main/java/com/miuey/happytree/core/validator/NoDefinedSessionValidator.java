package com.miuey.happytree.core.validator;

import com.miuey.happytree.TreeSession;
import com.miuey.happytree.core.TreePipeline;
import com.miuey.happytree.exception.TreeException;

public class NoDefinedSessionValidator extends GenericServiceValidator {

	@Override
	protected void validate(TreePipeline pipeline) throws TreeException {
		TreeSession session = (TreeSession) pipeline.getAttribute("session");
		
		if (session == null) {
			throw this.throwTreeException(RepositoryMessage.NO_DEFINED_SESSION);
		}
		doChain(pipeline);
	}
}
