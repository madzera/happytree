package com.miuey.happytree.core;

import java.util.Map;

import com.miuey.happytree.Element;

class Cache {

	private Map<Object, Element<?>> cacheElements = TreeFactory.mapFactory().
			createHashMap();
	
	
	Cache() {}
	
	
	void write(Object id, Element<?> element) {
		this.cacheElements.put(id, element);
	}
	
	Element<?> read(Object id) {
		return this.cacheElements.get(id);
	}
	
	void delete(Object id) {
		this.cacheElements.remove(id);
	}
}
