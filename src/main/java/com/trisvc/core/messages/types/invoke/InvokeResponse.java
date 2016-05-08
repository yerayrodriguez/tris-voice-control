package com.trisvc.core.messages.types.invoke;

import javax.xml.bind.annotation.XmlRootElement;

import com.trisvc.core.messages.MessageBody;
import com.trisvc.modules.brain.parser.DTContext;

@XmlRootElement
public class InvokeResponse extends MessageBody {

	private String message;
	private DTContext normalContext;
	private String inmmediateContext;

	public InvokeResponse() {
		super();
	}

	public InvokeResponse(String message, DTContext normalContext, String inmmediateContext) {
		super();
		this.message = message;
		this.normalContext = normalContext;
		this.inmmediateContext = inmmediateContext;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public DTContext getNormalContext() {
		return normalContext;
	}

	public void setNormalContext(DTContext normalContext) {
		this.normalContext = normalContext;
	}

	public String getInmmediateContext() {
		return inmmediateContext;
	}

	public void setInmmediateContext(String inmmediateContext) {
		this.inmmediateContext = inmmediateContext;
	}


}
