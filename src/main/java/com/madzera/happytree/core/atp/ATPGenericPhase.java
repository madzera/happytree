package com.madzera.happytree.core.atp;

import java.util.List;
import java.util.Map;

import com.madzera.happytree.core.ATPPhase;
import com.madzera.happytree.exception.TreeException;

abstract class ATPGenericPhase<T> extends ATPPhase<T> {

	/*
	 * Protects this constructor. The API Client must not initialization no one
	 * ATP Phase. This constructor should be protected into the children classes.
	 */
	protected ATPGenericPhase() {}
	
	
	protected IllegalArgumentException throwIllegalArgumentException(
			final ATPRepositoryMessage error) {
		return ATPFactory.exceptionFactory().createIllegalArgumentException(
				this.getMessageError(error));
	}
	
	protected TreeException throwTreeException(
			final ATPRepositoryMessage error) {
		return ATPFactory.exceptionFactory().createTreeException(
				this.getMessageError(error));
	}
	
	protected <K, V> Map<K, V> createLinkedHashMap() {
		return ATPFactory.mapFactory().createLinkedHashMap();
	}
	
	protected <E> List<E> createArrayList() {
		return ATPFactory.collectionFactory().createArrayList();
	}
	
	private String getMessageError(ATPRepositoryMessage error) {
		return error.getMessageError();
	}
}
