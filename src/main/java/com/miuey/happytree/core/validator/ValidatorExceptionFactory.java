package com.miuey.happytree.core.validator;

import com.miuey.happytree.exception.TreeException;

class ValidatorExceptionFactory {
	private static ValidatorExceptionFactory treeExceptionFactory;
	private static RuntimeExceptionFactory runtimeExceptionFactory;
	
	
	private ValidatorExceptionFactory() {}
	
	
	static RuntimeExceptionFactory runtimeExceptionFactory() {
		if (runtimeExceptionFactory == null) {
			runtimeExceptionFactory = new ValidatorExceptionFactory().new 
					RuntimeExceptionFactory();
		}
		return runtimeExceptionFactory;
	}
	
	static ValidatorExceptionFactory treeExceptionFactory() {
		if (treeExceptionFactory == null) {
			treeExceptionFactory = new ValidatorExceptionFactory();
		}
		return treeExceptionFactory;
	}
	
	class RuntimeExceptionFactory extends ValidatorExceptionFactory {
		RuntimeExceptionFactory() {}
		
		IllegalArgumentException createIllegalArgumentException(String message) {
			return new IllegalArgumentException(message);
		}
	}
	
	TreeException createTreeException(String message) {
		return new TreeException(message);
	}
}
