package com.madzera.happytree.core.atp;

class ATPConstants {
	
	private ATPConstants() {}


	static class Message {
		private Message() {}
		//---------------RUNTIME EXCEPTION--------------
		static final String INVALID_INPUT_ELEMENTS = "Invalid null/empty argument(s).";
		//---------------------TREE---------------------
		static final String GENERAL_ERROR = "Unable to transform input objects. Ensure the presence of a default constructor, getters, and setters.";
		static final String NO_TREE_ERROR = "No @Tree annotation found.";
		static final String NO_ID_ERROR = "No @Id annotation found.";
		static final String NO_PARENT_ERROR = "No @Parent annotation found.";
		static final String DIFFERENT_TYPES_ID_ERROR = "ID type mismatch error.";
		static final String DUPLICATE_ID_ERROR = "Duplicate ID.";
		static final String NOT_SERIALIZED_NODE = "The wrapped object must implement Serializable.";
	}
	
	static class Error {
		private Error() {}
		//---------------RUNTIME EXCEPTION--------------
		static final String INVALID_INPUT_ELEMENTS = "com.madzera.happytree.error.runtime.params";
		//---------------------TREE---------------------
		static final String GENERAL_ERROR = "com.madzera.happytree.error.checked.tree.general";
		static final String NO_TREE_ERROR = "com.madzera.happytree.error.checked.tree.no.tree";
		static final String NO_ID_ERROR = "com.madzera.happytree.error.checked.tree.no.id";
		static final String NO_PARENT_ERROR = "com.madzera.happytree.error.checked.tree.no.parent";
		static final String DIFFERENT_TYPES_ID_ERROR = "com.madzera.happytree.error.checked.tree.mismatch.id";
		static final String DUPLICATE_ID_ERROR = "com.madzera.happytree.error.checked.tree.duplicate.id";
		static final String NOT_SERIALIZED_NODE = "com.madzera.happytree.error.checked.element.not.serialized.node";
		
		static class Internal {
			private Internal() {}
			//---------------RUNTIME--------------
			static final String GENERAL_ERROR = "com.madzera.happytree.error.runtime.general";
		}
	}
}
