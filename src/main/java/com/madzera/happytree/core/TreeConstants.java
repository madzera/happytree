package com.madzera.happytree.core;

class TreeConstants {
	
	private TreeConstants() {}
	

	static class Message {
		private Message() {}
		//---------------RUNTIME EXCEPTION--------------
		static final String INVALID_INPUT_ELEMENTS = "Invalid null/empty argument(s).";
		//---------------------SESSION------------------
		static final String DUPLICATE_SESSION_ID = "Duplicate session identifier.";
		static final String NO_DEFINED_SESSION = "No defined session.";
		static final String NO_ACTIVE_SESSION = "No active session.";
		//---------------------TREE---------------------
		static final String CUT_COPY_DETACHED_ELEMENT = "It is not possible to copy/cut/remove elements. Invalid lifecycle state.";
		static final String CUT_COPY_NOTFOUND_ELEMENT = "It is not possible to cut the element. Source element not found.";
		static final String PERSIST_ATTACHED_ELEMENT = "It is not possible to persist the element. Invalid lifecycle state.";
		static final String UPDATE_NOT_EXISTED_ELEMENT = "It is not possible to update the element. Invalid lifecycle state.";
		static final String DUPLICATE_ID_ERROR = "Duplicate ID.";
		static final String DIFFERENT_TYPES = "Type mismatch error: incompatible parameterized tree type.";
		static final String INCORRECT_SESSION = "Element not defined in this session.";
		static final String HANDLE_ROOT = "The root of the tree cannot be handled for this operation.";
		static final String NOT_SERIALIZED_NODE = "The wrapped node must implement Serializable interface.";

	}
	static class Error {
		private Error() {}
		//---------------RUNTIME EXCEPTION--------------
		static final String INVALID_INPUT_ELEMENTS = "com.madzera.happytree.error.runtime.params";
		//---------------------TREE---------------------
		static final String DUPLICATE_ID_ERROR = "com.madzera.happytree.error.checked.tree.duplicate.id";
		static final String INCORRECT_SESSION = "com.madzera.happytree.error.checked.tree.not.belong.to.session";
		static final String DIFFERENT_TYPES_ERROR = "com.madzera.happytree.error.checked.tree.mismatch.element";
		static final String CUT_COPY_DETACHED_ELEMENT = "com.madzera.happytree.error.checked.tree.copy.cut.remote.detached";
		static final String CUT_COPY_NOTFOUND_ELEMENT = "com.madzera.happytree.error.checked.tree.copy.cut.remote.notfound";
		static final String PERSIST_ATTACHED_ELEMENT = "com.madzera.happytree.error.checked.tree.persist.attached";
		static final String UPDATE_NOT_EXISTED_ELEMENT = "com.madzera.happytree.error.checked.tree.update.not.existed";
		static final String HANDLE_ROOT = "com.madzera.happytree.error.checked.tree.root";
        static final String NOT_SERIALIZED_NODE = "com.madzera.happytree.error.checked.element.not.serialized.node";
		//---------------------SESSION------------------
		static final String DUPLICATE_SESSION_ID_ERROR = "com.madzera.happytree.error.checked.session.duplicate.id";
		static final String NO_DEFINED_SESSION = "com.madzera.happytree.error.checked.session.no.defined.session";
		static final String NO_ACTIVE_SESSION = "com.madzera.happytree.error.checked.session.no.active.session";
		
		static class Internal {
			private Internal() {}
			//---------------RUNTIME--------------
			static final String GENERAL_ERROR = "com.madzera.happytree.error.runtime.general";
		}
	}
}
