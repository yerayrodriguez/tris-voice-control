package com.trisvc.common.messages.tts;

import javax.xml.bind.annotation.XmlRootElement;

import com.trisvc.common.messages.MessageContent;

@XmlRootElement(name="content")
public class TTSMessageContent extends MessageContent {
	
	private String prueba;

	public TTSMessageContent() {
		super();
	}
	
	public TTSMessageContent(String prueba) {
		super();
		this.prueba = prueba;
	}	

	public String getPrueba() {
		return prueba;
	}

	public void setPrueba(String prueba) {
		this.prueba = prueba;
	}
	

}
