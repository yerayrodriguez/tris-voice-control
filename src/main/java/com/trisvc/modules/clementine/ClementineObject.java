package com.trisvc.modules.clementine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trisvc.core.messages.Message;
import com.trisvc.core.messages.Response;
import com.trisvc.core.messages.types.invoke.InvokeMessage;
import com.trisvc.core.messages.types.invoke.InvokeResponse;
import com.trisvc.core.messages.util.MessageUtil;
import com.trisvc.modules.BaseObject;
import com.trisvc.modules.BaseObjectWrapper;

public class ClementineObject extends BaseObjectWrapper implements BaseObject {

	private Logger logger = LogManager.getLogger(this.getClass().getName());

	public Response send (Message m) {
		InvokeMessage im = (InvokeMessage)m.getBody();
		
		Response r = MessageUtil.getResponseFromMessage(m);
		InvokeResponse ir = new InvokeResponse();
		r.setBody(ir);	
		r.setSuccess(false);
		
		logger.debug("Command invoked: "+im.getCommand());
		
		switch (im.getCommand()) {
		case "start":
			r.setSuccess(start(im,ir));
			break;
		case "stop":
			r.setSuccess(stop(im,ir));
			break;
		case "list":
			r.setSuccess(list(im,ir));
			break;	
		case "sintonize":
			r.setSuccess(sintonize(im,ir));
			break;			
		default:
			r.setSuccess(false);
			r.setInformation("Command unknown: "+im.getCommand());
			logger.error("Command unknown: "+im.getCommand());
			break;
		}
		
		return r;		
	}
	
	private boolean start(InvokeMessage im, InvokeResponse ir){
		ir.setMessage("comienzo");
		return true;
	}
	
	private boolean stop(InvokeMessage im, InvokeResponse ir){
		ir.setMessage("parar");
		return true;
	}	
	
	private boolean list(InvokeMessage im, InvokeResponse ir){
		ir.setMessage("lista");
		return true;
	}	
	
	private boolean sintonize(InvokeMessage im, InvokeResponse ir){
		ir.setMessage("sintoniza");
		return true;
	}	

}
