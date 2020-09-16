package com.miuey.happytree.core;

import java.util.Map;

/*
 * All searches about elements in the tree are actually done inside this cache.
 * 
 * Each session has its respective cache.
 */
class Cache {

	private Map<Object, TreeElementCore<?>> cacheElements = 
			TreeFactory.mapFactory().createHashMap();
	
	
	Cache() {}
	
	
	void write(Object id, TreeElementCore<?> element) {
		this.cacheElements.put(id, element);
	}
	
	@SuppressWarnings("unchecked")
	<T> TreeElementCore<T> read(Object id) {
		return (TreeElementCore<T>) this.cacheElements.get(id);
	}
	
	void delete(Object id) {
		this.cacheElements.remove(id);
	}
}
