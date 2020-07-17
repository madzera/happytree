package com.miuey.happytree.core.validator;

import java.util.Map;

import com.miuey.happytree.TreeSession;
import com.miuey.happytree.core.TreePipeline;
import com.miuey.happytree.exception.TreeException;

public class NotDuplicatedSessionValidator extends ValidatorGenericService {

	@Override
	protected void validate(TreePipeline pipeline) throws TreeException {
		@SuppressWarnings("unchecked")
		Map<String, TreeSession> sessions = (Map<String, TreeSession>) 
				pipeline.getAttribute("sessions");
		String sessionIdentifier = (String) pipeline.getAttribute("arg");
		
		if (sessions.get(sessionIdentifier) != null) {
			throw this.throwTreeException(ValidatorRepositoryMessage.
					DUPLICATED_SESSION);
		}
		
		doChain(pipeline);
	}

}
