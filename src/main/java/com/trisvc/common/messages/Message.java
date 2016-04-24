package com.trisvc.common.messages;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "message")
public class Message<T extends MessageContent> {

	private String messageID;
	private String callerID;
	private T content;

	public Message() {
	}

	public Message(String callerID, String messageID, T content) {
		this.callerID = callerID;
		this.messageID = messageID;
		this.content = content;
	}	

	public void setType(String type) {
	}

	public String getType() {
		return this.getClass().getSimpleName();
	}

	public String getCallerID() {
		return callerID;
	}

	public void setCallerID(String callerID) {
		this.callerID = callerID;
	}

	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

	@XmlAnyElement(lax=true)
	public T getContent(){
		return content;
	}

	public void setContent(T content){
		this.content = content;
	}

	@Override
	public String toString() {
		return MessageUtil.marshal(this);
	}

}
