package com.madzera.happytree.core.atp;

import com.madzera.happytree.core.TreeConstants;

class ATPConstants extends TreeConstants {
	static final String ERROR_MESSAGE_FILE_LOCATION = "exception.properties";

	
	private ATPConstants() {}

	
	class Error {
		private Error() {}
		
		//---------------RUNTIME--------------
		static final String INVALID_INPUT_ELEMENTS = "com.madzera.happytree.error.runtime.params";
		
		//----------------TREE----------------
		static final String GENERAL_ERROR = "com.madzera.happytree.error.checked.tree.general";
		static final String NO_TREE_ERROR = "com.madzera.happytree.error.checked.tree.notree";
		static final String NO_ID_ERROR = "com.madzera.happytree.error.checked.tree.noid";
		static final String NO_PARENT_ERROR = "com.madzera.happytree.error.checked.tree.noparent";
		static final String DIFFERENT_TYPES_ID_ERROR = "com.madzera.happytree.error.checked.tree.mismatchid";
		static final String DUPLICATED_ID_ERROR = "com.madzera.happytree.error.checked.tree.duplicatedid";
		
		class Internal {
			private Internal() {}
			//---------------RUNTIME--------------
			static final String GENERAL_ERROR = "com.madzera.happytree.error.runtime.general";
		}
	}
}
