package com.miuey.happytree.core.validator;

class Constants {
	
	private Constants() {}

	
	class Config {
		static final String ERROR_MESSAGE_FILE_LOCATION = "exception.properties";
		
		private Config() {}
	}
	
	class Error {
		private Error() {}
		//---------------RUNTIME--------------
		static final String INVALID_INPUT_ELEMENTS = "com.miuey.happytree.error.runtime.params";
		//----------------TREE----------------
		static final String GENERAL_ERROR = "com.miuey.happytree.error.checked.tree.general";
		static final String DIFFERENT_ID_TYPES_ERROR = "com.miuey.happytree.error.checked.tree.mismatchid";
		static final String NO_ID_ERROR = "com.miuey.happytree.error.checked.tree.noid";
		static final String NO_PARENT_ID_ERROR = "com.miuey.happytree.error.checked.tree.noparent";
		static final String NO_TREE_ERROR = "com.miuey.happytree.error.checked.tree.isnttree";
		static final String DUPLICATED_ID_ERROR = "com.miuey.happytree.error.checked.tree.duplicatedid";
		static final String FATAL_INCONSISTENCY_ERROR = "com.miuey.happytree.error.checked.tree.inconsistency";
		//---------------SESSION--------------
		static final String DUPLICATED_SESSION_ID_ERROR = "com.miuey.happytree.error.checked.session.duplicatedid";
		static final String NO_DEFINED_SESSION = "com.miuey.happytree.error.checked.session.nodefinedsession";
		static final String NO_ACTIVE_SESSION = "com.miuey.happytree.error.checked.session.noactivesession";
		
		class Internal {
			private Internal() {}
			//---------------RUNTIME--------------
			static final String GENERAL_ERROR = "com.miuey.happytree.error.runtime.general";
		}
	}
}
