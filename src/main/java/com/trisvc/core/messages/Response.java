package com.trisvc.core.messages;

public abstract class Response<T extends MessageBody> extends Message<T> {

	public boolean success = false;
	public String msg;

	public Response() {
	}

	public Response(String callerID, String messageID, T body) {
		super(callerID, messageID, body);
	}

	public Response(String callerID, String messageID, T body, boolean success, String msg) {
		super(callerID, messageID, body);
		this.success = success;
		this.msg = msg;
	}

}
