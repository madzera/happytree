package com.madzera.happytree.core;

/*
 * Represents a state machine of the Element object.
 * 
 * Indicates which operation the Element object can run at certain moment of
 * its life cycle. When trying to run a incompatible operation will provoke an
 * Exception threw.
 */
enum ElementState {
	NOT_EXISTED(Operation.PERSIST),
	ATTACHED(Operation.CUT, Operation.COPY, Operation.REMOVE, Operation.UPDATE,
			Operation.CONTAINS),
	DETACHED(Operation.UPDATE);
	
	
	private Operation[] permittedOperations;
	
	
	ElementState(Operation... permittedOperations) {
		this.permittedOperations = permittedOperations;
	}
	
	
	boolean canExecuteOperation(Operation myOperation) {
		boolean no = Boolean.FALSE;
		for (Operation operation : permittedOperations) {
			if (operation.equals(myOperation)) {
				return Boolean.TRUE;
			}
		}
		return no;
	}
}
