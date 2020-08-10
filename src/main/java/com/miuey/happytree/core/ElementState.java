package com.miuey.happytree.core;

enum ElementState {
	NOT_EXISTED(Operation.PERSIST),
	ATTACHED(Operation.CUT, Operation.COPY, Operation.REMOVE, Operation.UPDATE),
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
