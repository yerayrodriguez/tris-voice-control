package com.trisvc.core.messages;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import com.trisvc.core.messages.util.MessageUtil;

@XmlRootElement(name = "message")
@XmlType(propOrder={"type", "time", "callerID", "messageID", "body"})
public class Message {

	private String messageID;
	private String callerID;
	private XMLGregorianCalendar time;
	private MessageBody body;

	public Message() {
		super();
	}

	public Message(String callerID, String messageID, MessageBody body) {
		super();
		this.callerID = callerID;
		this.messageID = messageID;
		this.body = body;
	}

	public String getType() {                
		return body.getClass().getSimpleName();
	}
	
	public void setType(String type) {
		//TODO
		//throw exception if type != expected type
	}

	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

	public String getCallerID() {
		return callerID;
	}

	public void setCallerID(String callerID) {
		this.callerID = callerID;
	}

	public XMLGregorianCalendar getTime() {
		return time;
	}

	public void setTime(XMLGregorianCalendar time) {
		this.time = time;
	}

	@XmlAnyElement(lax = true)
	public MessageBody getBody() {
		return body;
	}

	public void setBody(MessageBody body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return MessageUtil.marshal(this);
	}
	
	public String packToSend(){
		this.setTime(MessageUtil.getXMLGregorianCalendar());
		return toString();
	}

}
