package com.miuey.happytree.core;

import com.miuey.happytree.TreeManager;

class TreeMandatoryValidator extends TreeValidator {

	protected TreeMandatoryValidator(TreeManager manager) {
		super(manager);
	}

	
	void validateMandatoryInput(Object... args) {
		for (Object arg : args) {
			if (arg == null) {
				throw this.throwIllegalArgumentException(TreeRepositoryMessage.
						INVALID_INPUT);
			}
		}
	}
}
