package com.miuey.happytree.core.validator;

import com.miuey.happytree.core.TreeServiceValidator;
import com.miuey.happytree.exception.TreeException;

abstract class GenericServiceValidator extends TreeServiceValidator {

	protected GenericServiceValidator() {}
	
	
	protected IllegalArgumentException throwIllegalArgumentException(
			final RepositoryMessage error) {
		return ExceptionFactory.runtimeExceptionFactory().
				createIllegalArgumentException(this.getMessageError(error));
	}
	
	protected TreeException throwTreeException(
			final RepositoryMessage error) {
		return ExceptionFactory.treeExceptionFactory().createTreeException(
				this.getMessageError(error));
	}
	
	private String getMessageError(RepositoryMessage error) {
		return error.getMessageError();
	}
}
