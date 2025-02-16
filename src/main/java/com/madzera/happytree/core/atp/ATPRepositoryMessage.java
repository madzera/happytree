package com.madzera.happytree.core.atp;

import java.io.IOException;
import java.util.Properties;

enum ATPRepositoryMessage {
	GENERAL(ATPConstants.Error.GENERAL_ERROR),
	INVALID_INPUT(ATPConstants.Error.INVALID_INPUT_ELEMENTS),
	NO_TREE(ATPConstants.Error.NO_TREE_ERROR),
	NO_ID(ATPConstants.Error.NO_ID_ERROR),
	NO_PARENT(ATPConstants.Error.NO_PARENT_ERROR),
	MISMATCH_TYPE_ID(ATPConstants.Error.DIFFERENT_TYPES_ID_ERROR),
	DUPLICATED_ID(ATPConstants.Error.DUPLICATED_ID_ERROR);
	
	private String errorDesc;
	private static Properties properties = new Properties();
	
	
	ATPRepositoryMessage(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	
	
	String getMessageError() {
		String error = null;
		try {
			loadProperties();
			error = properties.getProperty(errorDesc);
		} catch (IOException e) {
			error = ATPConstants.Error.Internal.GENERAL_ERROR;
		}
		return error;
	}
	
	private static void loadProperties() throws IOException {
		properties.load(ClassLoader.getSystemResourceAsStream(ATPConstants.
				ERROR_MESSAGE_FILE_LOCATION));
	}
}
