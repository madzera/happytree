package com.miuey.happytree.core.validator;

import java.io.IOException;
import java.util.Properties;

enum RepositoryMessage {
	INVALID_INPUT(Constants.Error.INVALID_INPUT_ELEMENTS),
	DUPLICATED_SESSION(Constants.Error.DUPLICATED_SESSION_ID_ERROR);
	
	private String errorDesc;
	private static Properties properties = new Properties();
	
	
	RepositoryMessage(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	
	
	String getMessageError() {
		String error = null;
		try {
			loadProperties();
			error = properties.getProperty(errorDesc);
		} catch (IOException e) {
			error = Constants.Error.GENERAL_ERROR;
		}
		return error;
	}
	
	private static void loadProperties() throws IOException {
		properties.load(ClassLoader.getSystemResourceAsStream(
				Constants.Config.ERROR_MESSAGE_FILE_LOCATION));
	}
}
