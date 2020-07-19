package com.miuey.happytree.core.atp;

import java.util.Map;
import java.util.Set;

import com.miuey.happytree.core.ATPPhase;
import com.miuey.happytree.exception.TreeException;

abstract class ATPGenericPhase<T> extends ATPPhase<T> {

	protected ATPGenericPhase() {}
	
	
	protected IllegalArgumentException throwIllegalArgumentException(
			final ATPRepositoryMessage error) {
		return ATPFactory.exceptionFactory().
				createIllegalArgumentException(this.getMessageError(error));
	}
	
	protected TreeException throwTreeException(
			final ATPRepositoryMessage error) {
		return ATPFactory.exceptionFactory().createTreeException(
				this.getMessageError(error));
	}
	
	protected <K,V> Map<K,V> createHashMap() {
		return ATPFactory.mapFactory().createHashMap();
	}
	
	protected <E> Set<E> createHashSet() {
		return ATPFactory.collectionFactory().createHashSet();
	}
	
	private String getMessageError(ATPRepositoryMessage error) {
		return error.getMessageError();
	}
}
