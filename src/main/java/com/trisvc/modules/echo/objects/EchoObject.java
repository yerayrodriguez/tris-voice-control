package com.trisvc.modules.echo.objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trisvc.core.messages.Message;
import com.trisvc.core.messages.Response;
import com.trisvc.core.messages.types.invoke.InvokeMessage;
import com.trisvc.core.messages.types.invoke.InvokeResponse;
import com.trisvc.core.messages.util.MessageUtil;
import com.trisvc.modules.BaseObject;
import com.trisvc.modules.BaseObjectWrapper;

public class EchoObject extends BaseObjectWrapper implements BaseObject {

	private Logger logger = LogManager.getLogger(this.getClass().getName());

	public Response send (Message m) {
		InvokeMessage im = (InvokeMessage)m.getBody();
		
		Response r = MessageUtil.getResponseFromMessage(m);
		InvokeResponse ir = new InvokeResponse();
		r.setBody(ir);		

		String text = "";
		if (im.getParameters().size()>0)
			text = im.getParameters().get(0).getValue();
		
		logger.debug("Echoed text: "+text);
		
		ir.setMessage(text);
		
		r.setSuccess(true);
		return r;		
	}

}
