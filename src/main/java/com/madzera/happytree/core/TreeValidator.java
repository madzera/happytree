package com.madzera.happytree.core;

import com.madzera.happytree.TreeManager;
import com.madzera.happytree.exception.TreeException;

/*
 * All specific validations class must inheritance this one. This must be no
 * possible to instantiate this class.
 */
abstract class TreeValidator {
	
	private TreeManager manager;
	
	
	protected TreeValidator(TreeManager manager) {
		this.manager = manager;
	}
	
	
	protected IllegalArgumentException throwIllegalArgumentException(
			final TreeRepositoryMessage error) {
		return TreeFactory.exceptionFactory().createRuntimeException(
				this.getMessageError(error));
	}
	
	protected TreeException throwTreeException(
			final TreeRepositoryMessage error) {
		return TreeFactory.exceptionFactory().createTreeException(
				this.getMessageError(error));
	}

	protected TreeManager getManager() {
		return this.manager;
	}
	
	private String getMessageError(TreeRepositoryMessage error) {
		return error.getMessageError();
	}
}
