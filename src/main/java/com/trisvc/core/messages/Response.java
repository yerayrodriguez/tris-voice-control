package com.trisvc.core.messages;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "response")
@XmlType(propOrder={"success", "information"})
public class Response<T extends MessageBody> extends Message<T> {

	private boolean success = false;
	private String information;

	public Response() {
		super();
	}

	public Response(String callerID, String messageID, T body) {
		super(callerID, messageID, body);
	}

	public Response(String callerID, String messageID, T body, boolean success, String information) {
		super(callerID, messageID, body);
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
	
	

}
