package com.madzera.happytree.core.atp;

import java.util.Map;

import com.madzera.happytree.core.atp.ATPConstants.Error;
import com.madzera.happytree.core.atp.ATPConstants.Message;

enum ATPRepositoryMessage {
	GENERAL(Error.GENERAL_ERROR),
	INVALID_INPUT(Error.INVALID_INPUT_ELEMENTS),
	NO_TREE(Error.NO_TREE_ERROR),
	NO_ID(Error.NO_ID_ERROR),
	NO_PARENT(Error.NO_PARENT_ERROR),
	MISMATCH_TYPE_ID(Error.DIFFERENT_TYPES_ID_ERROR),
	DUPLICATE_ID(Error.DUPLICATE_ID_ERROR),
	NOT_SERIALIZED_NODE(Error.NOT_SERIALIZED_NODE);
	
	
	private String error;
	private static Map<String, String> messages = ATPFactory.mapFactory().
			createHashMap();
	
	static {
		/*
	 	* Invalid null input.
	 	*/
		messages.put(Error.INVALID_INPUT_ELEMENTS, Message.INVALID_INPUT_ELEMENTS);
		
		/*
	 	* Invalid tree element state.
	 	*/
		messages.put(Error.GENERAL_ERROR, Message.GENERAL_ERROR);
		messages.put(Error.NO_TREE_ERROR, Message.NO_TREE_ERROR);
		messages.put(Error.NO_ID_ERROR, Message.NO_ID_ERROR);
		messages.put(Error.NO_PARENT_ERROR, Message.NO_PARENT_ERROR);
		messages.put(Error.DIFFERENT_TYPES_ID_ERROR, Message.DIFFERENT_TYPES_ID_ERROR);
		messages.put(Error.DUPLICATE_ID_ERROR, Message.DUPLICATE_ID_ERROR);
		messages.put(Error.NOT_SERIALIZED_NODE, Message.NOT_SERIALIZED_NODE);
	}
	

	ATPRepositoryMessage(String error) {
		this.error = error;
	}
	
	
	String getMessageError() {
		return messages.get(error);
	}
}
