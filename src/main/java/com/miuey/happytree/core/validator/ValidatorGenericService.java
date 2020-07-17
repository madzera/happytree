package com.miuey.happytree.core.validator;

import com.miuey.happytree.core.TreeServiceValidator;
import com.miuey.happytree.exception.TreeException;

abstract class ValidatorGenericService extends TreeServiceValidator {

	protected ValidatorGenericService() {}
	
	
	protected IllegalArgumentException throwIllegalArgumentException(
			final ValidatorRepositoryMessage error) {
		return ExceptionFactory.runtimeExceptionFactory().
				createIllegalArgumentException(this.getMessageError(error));
	}
	
	protected TreeException throwTreeException(
			final ValidatorRepositoryMessage error) {
		return ExceptionFactory.treeExceptionFactory().createTreeException(
				this.getMessageError(error));
	}
	
	private String getMessageError(ValidatorRepositoryMessage error) {
		return error.getMessageError();
	}
}
