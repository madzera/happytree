package com.madzera.happytree.core;

import java.io.IOException;
import java.util.Properties;

enum TreeRepositoryMessage {
	/*
	 * Invalid null input.
	 */
	INVALID_INPUT(TreeConstants.Error.INVALID_INPUT_ELEMENTS),
	
	/*
	 * Invalid session state.
	 */
	DUPLICATE_SESSION(TreeConstants.Error.DUPLICATE_SESSION_ID_ERROR),
	NO_DEFINED_SESSION(TreeConstants.Error.NO_DEFINED_SESSION),
	NO_ACTIVE_SESSION(TreeConstants.Error.NO_ACTIVE_SESSION),
	
	/*
	 * Invalid element state.
	 */
	DETACHED_ELEMENT(TreeConstants.Error.CUT_COPY_DETACHED_ELEMENT),
	ATTACHED_ELEMENT(TreeConstants.Error.PERSIST_ATTACHED_ELEMENT),
	NOT_EXISTED_ELEMENT(TreeConstants.Error.UPDATE_NOT_EXISTED_ELEMENT),
	DUPLICATE_ELEMENT(TreeConstants.Error.DUPLICATE_ID_ERROR),
	MISMATCH_TYPE_ELEMENT(TreeConstants.Error.DIFFERENT_TYPES_ERROR),
	NOT_BELONG_SESSION(TreeConstants.Error.INCORRECT_SESSION),
	IMPOSSIBLE_HANDLE_ROOT(TreeConstants.Error.HANDLE_ROOT);
	
	private String errorDesc;
	
	/*
	 * The only one 'new' operator outside of TreeFactory.
	 */
	private static Properties properties = new Properties();
	
	
	TreeRepositoryMessage(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	
	
	String getMessageError() {
		String error = null;
		try {
			loadProperties();
			error = properties.getProperty(errorDesc);
		} catch (IOException e) {
			/*
			 * Not found error messages properties file.
			 */
			error = TreeConstants.Error.Internal.GENERAL_ERROR;
		}
		return error;
	}
	
	private static void loadProperties() throws IOException {
		properties.load(ClassLoader.getSystemResourceAsStream(TreeConstants.
				Config.ERROR_MESSAGE_FILE_LOCATION));
	}
}
