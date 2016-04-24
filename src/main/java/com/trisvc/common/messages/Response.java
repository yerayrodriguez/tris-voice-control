package com.trisvc.common.messages;

public abstract class Response extends Message{
	
	private boolean success = false;
	private String  msg;
	
	public Response() {
	}

	public Response(String callerID, String messageID) {
		super(callerID,messageID);
	}	
	
	public Response(String callerID, String messageID, boolean success, String msg) {
		super(callerID,messageID);
		this.success = success;
		this.msg = msg;
	}		

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}


}
