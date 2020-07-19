package com.miuey.happytree.core.atp;

import com.miuey.happytree.exception.TreeException;

class ATPExceptionFactory {
	private static ATPExceptionFactory treeExceptionFactory;
	private static RuntimeExceptionFactory runtimeExceptionFactory;
	
	
	private ATPExceptionFactory() {}
	
	
	static RuntimeExceptionFactory runtimeExceptionFactory() {
		if (runtimeExceptionFactory == null) {
			runtimeExceptionFactory = new ATPExceptionFactory().new 
					RuntimeExceptionFactory();
		}
		return runtimeExceptionFactory;
	}
	
	static ATPExceptionFactory treeExceptionFactory() {
		if (treeExceptionFactory == null) {
			treeExceptionFactory = new ATPExceptionFactory();
		}
		return treeExceptionFactory;
	}
	
	class RuntimeExceptionFactory extends ATPExceptionFactory {
		RuntimeExceptionFactory() {}
		
		IllegalArgumentException createIllegalArgumentException(String message) {
			return new IllegalArgumentException(message);
		}
	}
	
	TreeException createTreeException(String message) {
		return new TreeException(message);
	}
}
