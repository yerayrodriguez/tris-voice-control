package com.trisvc.modules;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import com.trisvc.core.messages.Message;
import com.trisvc.core.messages.Response;
import com.trisvc.core.messages.util.MessageUtil;

public class RemoteObjectWrapper {
	
	private BaseObject o;
	
	public RemoteObjectWrapper(BaseObject o){
		this.o = o;
	}
	
	public Response send (Message m){
		
		m.setTime(MessageUtil.getXMLGregorianCalendar());
		try {
			//TODO
			//use static variable
			m.setCallerID(InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m.setMessageID(UUID.randomUUID().toString());
		String xmlMessage = m.toString();
		//System.out.println(xmlMessage);
		String xmlResponse = o.send(xmlMessage);
		//System.out.println(xmlResponse);
		Response r = (Response) MessageUtil.unmarshal(xmlResponse);
		return r;
	}

}

