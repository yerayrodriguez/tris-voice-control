package com.trisvc.core.messages;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "response")
@XmlType(propOrder = { "listener", "success", "information" })
public class Response extends Message {

	private String listener;
	private boolean success = true;
	private String information;

	public Response() {
		super();
	}

	public Response(String callerID, String messageID, MessageBody body) {
		super(callerID, messageID, body);
	}

	public Response(String callerID, String messageID, MessageBody body, String listener, boolean success,
			String information) {
		super(callerID, messageID, body);
		this.listener = listener;
		this.success = success;
		this.information = information;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public String getListener() {
		return listener;
	}

	public void setListener(String listener) {
		this.listener = listener;
	}

}
