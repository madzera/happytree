package com.miuey.happytree.core;

public class TreeConstants {
	
	private TreeConstants() {}

	
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
		static final String DUPLICATED_ID_ERROR = "com.miuey.happytree.error.checked.tree.duplicatedid";
		static final String DIFFERENT_TYPES_ERROR = "com.miuey.happytree.error.checked.tree.mismatchelement";
		static final String DETACHED_ELEMENT = "com.miuey.happytree.error.checked.tree.detached";
		static final String ATTACHED_ELEMENT = "com.miuey.happytree.error.checked.tree.attached";
		static final String CUT_COPY_ROOT = "com.miuey.happytree.error.checked.tree.root";
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
