package com.miuey.happytree.core.atp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.miuey.happytree.exception.TreeException;

class ATPFactory {
	private static ExceptionFactory exceptionFactory;
	private static MapFactory mapFactory;
	private static CollectionFactory collectionFactory;
	
	
	private ATPFactory() {}
	
	
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
}
