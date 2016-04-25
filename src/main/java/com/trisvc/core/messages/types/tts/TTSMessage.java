package com.trisvc.core.messages.types.tts;

import javax.xml.bind.annotation.XmlRootElement;

import com.trisvc.core.messages.MessageBody;

@XmlRootElement
public class TTSMessage extends MessageBody {
	
	public String textToSpeech;

	public TTSMessage() {
		super();
	}
	
	public TTSMessage(String textToSpeech) {
		super();
		this.textToSpeech = textToSpeech;
	}	

}
