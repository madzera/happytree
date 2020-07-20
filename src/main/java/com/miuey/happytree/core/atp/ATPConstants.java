package com.miuey.happytree.core.atp;

class ATPConstants {
	
	private ATPConstants() {}

	
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
		static final String NO_TREE_ERROR = "com.miuey.happytree.error.checked.tree.notree";
		static final String NO_ID_ERROR = "com.miuey.happytree.error.checked.tree.noid";
		static final String NO_PARENT_ERROR = "com.miuey.happytree.error.checked.tree.noparent";
		static final String DIFFERENT_TYPES_ID_ERROR = "com.miuey.happytree.error.checked.tree.mismatchid";
		static final String DUPLICATED_ID_ERROR = "com.miuey.happytree.error.checked.tree.duplicatedid";
		static final String INCONSISTENCY_POST_VALID = "com.miuey.happytree.error.checked.tree.inconsistency";
		//---------------SESSION--------------
		class Internal {
			private Internal() {}
			//---------------RUNTIME--------------
			static final String GENERAL_ERROR = "com.miuey.happytree.error.runtime.general";
		}
	}
}
