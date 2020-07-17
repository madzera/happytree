package com.miuey.happytree.core.validator;

import java.io.IOException;
import java.util.Properties;

enum ValidatorRepositoryMessage {
	INVALID_INPUT(ValidatorConstants.Error.INVALID_INPUT_ELEMENTS),
	DUPLICATED_SESSION(ValidatorConstants.Error.DUPLICATED_SESSION_ID_ERROR),
	NO_DEFINED_SESSION(ValidatorConstants.Error.NO_DEFINED_SESSION),
	NO_ACTIVE_SESSION(ValidatorConstants.Error.NO_ACTIVE_SESSION);
	
	private String errorDesc;
	private static Properties properties = new Properties();
	
	
	ValidatorRepositoryMessage(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	
	
	String getMessageError() {
		String error = null;
		try {
			loadProperties();
			error = properties.getProperty(errorDesc);
		} catch (IOException e) {
			error = ValidatorConstants.Error.Internal.GENERAL_ERROR;
		}
		return error;
	}
	
	private static void loadProperties() throws IOException {
		properties.load(ClassLoader.getSystemResourceAsStream(
				ValidatorConstants.Config.ERROR_MESSAGE_FILE_LOCATION));
	}
}
