package com.trisvc.common.messages;


public class MessageTypeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7240273386598270882L;
	
	private MessageType actual;
	private MessageType newOne;


	public MessageTypeException (MessageType actual, MessageType newOne) {
		super ( );
		this.actual = actual;
		this.newOne = newOne;
	}


	public MessageType getActual ( ) {
		return actual;
	}


	public MessageType getNewOne ( ) {
		return newOne;
	}

}
