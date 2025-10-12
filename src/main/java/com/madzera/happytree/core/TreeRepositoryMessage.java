package com.madzera.happytree.core;

import java.util.Map;

import com.madzera.happytree.core.TreeConstants.Error;
import com.madzera.happytree.core.TreeConstants.Message;

enum TreeRepositoryMessage {
	/*
	 * Invalid null input.
	 */
	INVALID_INPUT(Error.INVALID_INPUT_ELEMENTS),
	
	/*
	 * Invalid session state.
	 */
	DUPLICATE_SESSION(Error.DUPLICATE_SESSION_ID_ERROR),
	NO_DEFINED_SESSION(Error.NO_DEFINED_SESSION),
	NO_ACTIVE_SESSION(Error.NO_ACTIVE_SESSION),
	
	/*
	 * Invalid element state.
	 */
	DETACHED_ELEMENT(Error.CUT_COPY_DETACHED_ELEMENT),
	NOTFOUND_ELEMENT(Error.CUT_COPY_NOTFOUND_ELEMENT),
	ATTACHED_ELEMENT(Error.PERSIST_ATTACHED_ELEMENT),
	NOT_EXISTED_ELEMENT(Error.UPDATE_NOT_EXISTED_ELEMENT),
	DUPLICATE_ELEMENT(Error.DUPLICATE_ID_ERROR),
	MISMATCH_TYPE_ELEMENT(Error.DIFFERENT_TYPES_ERROR),
	NOT_BELONG_SESSION(Error.INCORRECT_SESSION),
	IMPOSSIBLE_HANDLE_ROOT(Error.HANDLE_ROOT),
	NOT_SERIALIZED_NODE(Error.NOT_SERIALIZED_NODE);
	
	private String error;
	private static Map<String, String> messages = TreeFactory.mapFactory()
			.createHashMap();
	
	static {
		/*
	 	* Invalid null input.
	 	*/
		messages.put(Error.INVALID_INPUT_ELEMENTS, Message.INVALID_INPUT_ELEMENTS);
		
		/*
	 	* Invalid session state.
	 	*/
		messages.put(Error.DUPLICATE_SESSION_ID_ERROR, Message.DUPLICATE_SESSION_ID);
		messages.put(Error.NO_DEFINED_SESSION, Message.NO_DEFINED_SESSION);
		messages.put(Error.NO_ACTIVE_SESSION, Message.NO_ACTIVE_SESSION);

		/*
	 	* Invalid element state.
	 	*/
		messages.put(Error.CUT_COPY_DETACHED_ELEMENT, Message.CUT_COPY_DETACHED_ELEMENT);
		messages.put(Error.CUT_COPY_NOTFOUND_ELEMENT, Message.CUT_COPY_NOTFOUND_ELEMENT);
		messages.put(Error.PERSIST_ATTACHED_ELEMENT, Message.PERSIST_ATTACHED_ELEMENT);
		messages.put(Error.UPDATE_NOT_EXISTED_ELEMENT, Message.UPDATE_NOT_EXISTED_ELEMENT);
		messages.put(Error.DUPLICATE_ID_ERROR, Message.DUPLICATE_ID_ERROR);
		messages.put(Error.DIFFERENT_TYPES_ERROR, Message.DIFFERENT_TYPES);
		messages.put(Error.INCORRECT_SESSION, Message.INCORRECT_SESSION);
		messages.put(Error.HANDLE_ROOT, Message.HANDLE_ROOT);
		messages.put(Error.NOT_SERIALIZED_NODE, Message.NOT_SERIALIZED_NODE);
	}
	
	
	TreeRepositoryMessage(String error) {
		this.error = error;
	}
	
	
	String getMessageError() {
		return messages.get(error);
	}
}
