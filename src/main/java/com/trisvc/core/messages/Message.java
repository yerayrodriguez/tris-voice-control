package com.trisvc.core.messages;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import com.trisvc.core.messages.util.MessageUtil;

@XmlRootElement(name = "message")
@XmlType(propOrder={"type", "time", "callerID", "messageID", "body"})
public class Message<T extends MessageBody> {

	public String messageID;
	public String callerID;
	public XMLGregorianCalendar time;

	@XmlAnyElement(lax = true)
	public T body;

	public Message() {
	}

	public Message(String callerID, String messageID, T body) {
		this.callerID = callerID;
		this.messageID = messageID;
		this.body = body;
	}

	public void setType(String type) {
	}

	public String getType() {                
		return body.getClass().getSimpleName();
	}

	@Override
	public String toString() {
		return MessageUtil.marshal(this);
	}

}
