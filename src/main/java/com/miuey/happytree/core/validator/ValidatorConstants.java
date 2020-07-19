package com.miuey.happytree.core.validator;

class ValidatorConstants {
	
	private ValidatorConstants() {}

	
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