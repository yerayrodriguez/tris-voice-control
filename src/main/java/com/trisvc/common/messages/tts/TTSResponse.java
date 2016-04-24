package com.trisvc.common.messages.tts;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlRootElement;

import com.trisvc.common.messages.MessageUtil;
import com.trisvc.common.messages.Response;

@XmlRootElement(name = "response")
public class TTSResponse extends Response {

	public TTSResponse() {
		super();
	}

	public TTSResponse(String callerID, String messageID) {
		super(callerID, messageID);
	}
	
	public TTSResponse(String callerID, String messageID, boolean success, String msg) {
		super(callerID, messageID, success, msg);
	}	

	@Override
	public JAXBContext getJAXBContext() {
		return TTSResponse.context;
	}

	static final JAXBContext context = MessageUtil.initContext(TTSResponse.class);


}
