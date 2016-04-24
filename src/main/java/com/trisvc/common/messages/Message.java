package com.trisvc.common.messages;

import javax.xml.bind.annotation.XmlAnyElement;

public abstract class Message<T extends MessageContent> {

	public Message() {
	}

	public Message(String callerID, String messageID) {
		this.callerID = callerID;
		this.messageID = messageID;
	}

	public void setType(String type) {
	}

	public String getType() {
		return this.getClass().getSimpleName();
	}

	private String callerID;

	public String getCallerID() {
		return callerID;
	}

	public void setCallerID(String callerID) {
		this.callerID = callerID;
	}

	private String messageID;

	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}
	
	@XmlAnyElement
	public abstract T getContent();

	public abstract void setContent(T t);	

	@Override
	public String toString() {
		return MessageUtil.marshal(this);
	}

}
