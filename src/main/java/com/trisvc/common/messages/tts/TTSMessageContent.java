package com.trisvc.common.messages.tts;

import javax.xml.bind.annotation.XmlRootElement;

import com.trisvc.common.messages.Message;
import com.trisvc.common.messages.MessageContent;
import com.trisvc.common.messages.MessageUtil;

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
	

	public static void main(String[] args) {
		TTSMessageContent c = new TTSMessageContent("contenido");
		Message<TTSMessageContent> t = new Message<TTSMessageContent> ("ejemplo", "sdseee1233",c); 
		
		

		t.setContent(c);

		try {

			String text = t.toString();
			System.out.println(text);
			Message<TTSMessageContent> t2 = (Message<TTSMessageContent>) MessageUtil.unmarshal( text);
			// TTSMessage t2 = new TTSMessage();
			// t2.unmarshal(text);
			System.out.println(t2.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
