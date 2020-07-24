package com.miuey.happytree.core;

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
	DUPLICATED_SESSION(TreeConstants.Error.DUPLICATED_SESSION_ID_ERROR),
	NO_DEFINED_SESSION(TreeConstants.Error.NO_DEFINED_SESSION),
	NO_ACTIVE_SESSION(TreeConstants.Error.NO_ACTIVE_SESSION),
	
	/*
	 * Invalid element state.
	 */
	DETACHED_ELEMENT(TreeConstants.Error.DETACHED_ELEMENT),
	DUPLICATED_ELEMENT(TreeConstants.Error.DUPLICATED_ID_ERROR),
	MISMATCH_TYPE_ELEMENT(TreeConstants.Error.DIFFERENT_TYPES_ID_ERROR),
	IMPOSSIBLE_COPY_ROOT(TreeConstants.Error.CUT_COPY_ROOT);
	
	private String errorDesc;
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
			error = TreeConstants.Error.Internal.GENERAL_ERROR;
		}
		return error;
	}
	
	private static void loadProperties() throws IOException {
		properties.load(ClassLoader.getSystemResourceAsStream(TreeConstants.
				Config.ERROR_MESSAGE_FILE_LOCATION));
	}
}
