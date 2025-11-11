package com.madzera.happytree.core.atp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.madzera.happytree.core.ATPPhase;
import com.madzera.happytree.exception.TreeException;

/*
 * This factory must be public to the super package (core) be able to
 * instantiate the ATP Phases, by the TreeFactory class.
 */
public class ATPFactory {
	private static ExceptionFactory exceptionFactory;
	private static MapFactory mapFactory;
	private static CollectionFactory collectionFactory;
	
	
	protected ATPFactory() {}
	
	
	/*
	 * Decision which phase will be instantiate according the phase parameter.
	 * This method must be public to be accessible inside of TreeFactory from
	 * the super package (core)
	 */
	protected <T> ATPPhase<T> getPhaseInstance(ATPPhaseInstance phase) {
		ATPPhase<T> instance = null;
		if (ATPPhaseInstance.PRE_VALIDATION.equals(phase)) {
			instance = new PreValidation<>();
		}
		if (ATPPhaseInstance.EXTRACTION.equals(phase)) {
			instance = new Extraction<>();
		}
		if (ATPPhaseInstance.INITIALIZATION.equals(phase)) {
			instance = new Initialization<>();
		}
		if (ATPPhaseInstance.BINDING.equals(phase)) {
			instance = new Binding<>();
		}
		return instance;
	}

	static ExceptionFactory exceptionFactory() {
		if (exceptionFactory == null) {
			exceptionFactory = new ATPFactory().new ExceptionFactory();
		}
		return exceptionFactory;
	}
	
	static MapFactory mapFactory() {
		if (mapFactory == null) {
			mapFactory = new ATPFactory().new MapFactory();
		}
		return mapFactory;
	}
	
	static CollectionFactory collectionFactory() {
		if (collectionFactory == null) {
			collectionFactory = new ATPFactory().new CollectionFactory();
		}
		return collectionFactory;
	}
	
	class ExceptionFactory extends ATPFactory {
		ExceptionFactory() {}
		
		IllegalArgumentException createIllegalArgumentException(String message) {
			return new IllegalArgumentException(message);
		}
		
		TreeException createTreeException(String message) {
			return new TreeException(message);
		}
	}
	
	class MapFactory extends ATPFactory {
		MapFactory() {}
		
		<K,V> Map<K,V> createHashMap() {
			return new HashMap<>();
		}
	}
	
	class CollectionFactory extends ATPFactory {
		CollectionFactory() {}
		
		<T> List<T> createArrayList() {
			return new ArrayList<>();
		}
	}
	
	protected enum ATPPhaseInstance {
		PRE_VALIDATION,
		EXTRACTION,
		INITIALIZATION,
		BINDING;
	}
}
