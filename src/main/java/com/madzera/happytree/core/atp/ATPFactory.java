package com.madzera.happytree.core.atp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
	
	
	private ATPFactory() {}
	
	
	/*
	 * Decision which phase will be instantiate according the phase parameter.
	 * This method must be public to be accessible inside of TreeFactory from
	 * the super package (core)
	 */
	public static <T> ATPPhase<T> getPhaseInstance(ATPPhaseInstance phase) {
		switch (phase) {
			case PRE_VALIDATION: 	return new PreValidation<>();
			case EXTRACTION:		return new Extraction<>();
			case INITIALIZATION: 	return new Initialization<>();
			case BINDING:			return new Binding<>();
			case POST_VALIDATION:	return new PostValidation<>();
		}
		return null;
	}
	
	static ExceptionFactory exceptionFactory() {
		if (exceptionFactory == null) {
			exceptionFactory = new ATPFactory().new 
					ExceptionFactory();
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
		
		<T> Set<T> createHashSet() {
			return new HashSet<>();
		}
	}
	
	public enum ATPPhaseInstance {
		PRE_VALIDATION,
		EXTRACTION,
		INITIALIZATION,
		BINDING,
		POST_VALIDATION;
	}
}
