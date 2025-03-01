package com.madzera.happytree.core.atp;

class ATPConstants {
	static final String ERROR_MESSAGE_FILE_LOCATION = "exception.properties";

	
	private ATPConstants() {}

	
	static class Error {
		private Error() {}
		
		//---------------RUNTIME--------------
		static final String INVALID_INPUT_ELEMENTS = "com.madzera.happytree.error.runtime.params";
		
		//----------------TREE----------------
		static final String GENERAL_ERROR = "com.madzera.happytree.error.checked.tree.general";
		static final String NO_TREE_ERROR = "com.madzera.happytree.error.checked.tree.no.tree";
		static final String NO_ID_ERROR = "com.madzera.happytree.error.checked.tree.no.id";
		static final String NO_PARENT_ERROR = "com.madzera.happytree.error.checked.tree.no.parent";
		static final String DIFFERENT_TYPES_ID_ERROR = "com.madzera.happytree.error.checked.tree.mismatch.id";
		static final String DUPLICATE_ID_ERROR = "com.madzera.happytree.error.checked.tree.duplicate.id";
		
		static class Internal {
			private Internal() {}
			//---------------RUNTIME--------------
			static final String GENERAL_ERROR = "com.madzera.happytree.error.runtime.general";
		}
	}
}
