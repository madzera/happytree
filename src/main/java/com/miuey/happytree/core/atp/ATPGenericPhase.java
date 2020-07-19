package com.miuey.happytree.core.atp;

import com.miuey.happytree.core.ATPPhase;
import com.miuey.happytree.exception.TreeException;

abstract class ATPGenericPhase<T> extends ATPPhase<T> {

	protected ATPGenericPhase() {}
	
	
	protected IllegalArgumentException throwIllegalArgumentException(
			final ATPRepositoryMessage error) {
		return ATPExceptionFactory.runtimeExceptionFactory().
				createIllegalArgumentException(this.getMessageError(error));
	}
	
	protected TreeException throwTreeException(
			final ATPRepositoryMessage error) {
		return ATPExceptionFactory.treeExceptionFactory().createTreeException(
				this.getMessageError(error));
	}
	
	private String getMessageError(ATPRepositoryMessage error) {
		return error.getMessageError();
	}
}
