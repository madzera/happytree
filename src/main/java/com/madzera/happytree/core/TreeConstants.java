package com.madzera.happytree.core;

public class TreeConstants {
	
	private TreeConstants() {}

	
	/*
	 * Configuration constants to be accessed within the ATP package.
	 */
	static class Config {
		private Config() {}
		
		static final String ERROR_MESSAGE_FILE_LOCATION = "exception.properties";
	}
	
	static class Error {
		private Error() {}
		//---------------RUNTIME--------------
		static final String INVALID_INPUT_ELEMENTS = "com.madzera.happytree.error.runtime.params";
		//----------------TREE----------------
		static final String DUPLICATE_ID_ERROR = "com.madzera.happytree.error.checked.tree.duplicate.id";
		static final String INCORRECT_SESSION = "com.madzera.happytree.error.checked.tree.not.belong.to.session";
		static final String DIFFERENT_TYPES_ERROR = "com.madzera.happytree.error.checked.tree.mismatch.element";
		static final String CUT_COPY_DETACHED_ELEMENT = "com.madzera.happytree.error.checked.tree.copy.cut.remote.detached";
		static final String CUT_COPY_NOTFOUND_ELEMENT = "com.madzera.happytree.error.checked.tree.copy.cut.remote.notfound";
		static final String PERSIST_ATTACHED_ELEMENT = "com.madzera.happytree.error.checked.tree.persist.attached";
		static final String UPDATE_NOT_EXISTED_ELEMENT = "com.madzera.happytree.error.checked.tree.update.not.existed";
		static final String HANDLE_ROOT = "com.madzera.happytree.error.checked.tree.root";
		//---------------SESSION--------------
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
