package com.trisvc.core.messages.types.tts;

import javax.xml.bind.annotation.XmlRootElement;

import com.trisvc.core.messages.Message;
import com.trisvc.core.messages.MessageBody;
import com.trisvc.core.messages.util.MessageUtil;

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

	public static void main(String[] args) {
		TTSMessage c = new TTSMessage("Test of text to speech");
		Message<TTSMessage> t = new Message<TTSMessage> ("CallerID", "MessageID",c); 
		t.time = MessageUtil.getXMLGregorianCalendar(); 
		
		try {
			String text = t.toString();
			System.out.println(text);
			@SuppressWarnings("unchecked")
			Message<TTSMessage> t2 = (Message<TTSMessage>) MessageUtil.unmarshal( text);
			// TTSMessage t2 = new TTSMessage();
			// t2.unmarshal(text);
			System.out.println(t2.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
