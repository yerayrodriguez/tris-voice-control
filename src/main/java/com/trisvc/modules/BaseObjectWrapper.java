package com.trisvc.modules;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.trisvc.core.launcher.thread.BaseThread;
import com.trisvc.core.messages.Message;
import com.trisvc.core.messages.Response;
import com.trisvc.core.messages.types.register.RegisterMessage;
import com.trisvc.core.messages.util.MessageUtil;

public class BaseObjectWrapper {


	public String send(String xmlMessage) {
		Message m = (Message) MessageUtil.unmarshal(xmlMessage);
		Response r = send(m);
		try {
			//TODO
			//use static variable
			String host = InetAddress.getLocalHost().getHostName();
			r.setListener(host);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		r.setTime(MessageUtil.getXMLGregorianCalendar());
		return r.toString();
	}
	

	public boolean isRemote() {
		//TODO
		//WTF??
		return false;
	}
	
	public Response send(Message m){
		return null;
	}
	
	//TODO
	//refacto, get rid off from here	
	protected boolean updateModule (RegisterMessage rm){
		if (rm == null)
			return true;
		
		RemoteObjectWrapper brain = BaseThread.getRemoteObject("Brain");
		Message m = new Message();
		m.setBody(rm);
		Response response = brain.send(m);	
		if (!response.isSuccess()){
			//logger.error("Could not register module");
			return false;
		}
		return true;
	}	

}
