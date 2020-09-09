package com.miuey.happytree.core;

import com.miuey.happytree.TreeManager;
import com.miuey.happytree.exception.TreeException;

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
