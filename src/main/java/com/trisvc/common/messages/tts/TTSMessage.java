package com.trisvc.common.messages.tts;

import javax.xml.bind.annotation.XmlRootElement;

import com.trisvc.common.messages.Message;
import com.trisvc.common.messages.MessageUtil;

@XmlRootElement(name = "message")
public class TTSMessage extends Message<TTSMessageContent> {
	
	private TTSMessageContent content;
	private String text;

	public TTSMessage() {
		super();
	}

	public TTSMessage(String callerID, String messageID) {
		super(callerID, messageID);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	



	public static void main(String[] args) {

		TTSMessage t = new TTSMessage("ejemplo", "sdseee1233");
		TTSMessageContent c = new TTSMessageContent("contenido");
		t.setText("Enciende la luz de la habitación");
		t.setContent(c);

		try {

			String text = t.toString();
			System.out.println(text);
			TTSMessage t2 = (TTSMessage) MessageUtil.unmarshal(TTSMessage.class, text);
			// TTSMessage t2 = new TTSMessage();
			// t2.unmarshal(text);
			System.out.println(t2.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
