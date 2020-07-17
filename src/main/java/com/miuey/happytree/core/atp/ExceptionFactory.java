package com.miuey.happytree.core.atp;

import com.miuey.happytree.exception.TreeException;

class ExceptionFactory {
	private static ExceptionFactory treeExceptionFactory;
	private static RuntimeExceptionFactory runtimeExceptionFactory;
	
	
	private ExceptionFactory() {}
	
	
	static RuntimeExceptionFactory runtimeExceptionFactory() {
		if (runtimeExceptionFactory == null) {
			runtimeExceptionFactory = new ExceptionFactory().new 
					RuntimeExceptionFactory();
		}
		return runtimeExceptionFactory;
	}
	
	static ExceptionFactory treeExceptionFactory() {
		if (treeExceptionFactory == null) {
			treeExceptionFactory = new ExceptionFactory();
		}
		return treeExceptionFactory;
	}
	
	class RuntimeExceptionFactory extends ExceptionFactory {
		RuntimeExceptionFactory() {}
		
		IllegalArgumentException createIllegalArgumentException(String message) {
			return new IllegalArgumentException(message);
		}
	}
	
	TreeException createTreeException(String message) {
		return new TreeException(message);
	}
}
