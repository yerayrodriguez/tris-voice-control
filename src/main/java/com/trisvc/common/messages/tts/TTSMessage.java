package com.trisvc.common.messages.tts;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import com.trisvc.common.messages.Message;
import com.trisvc.common.messages.MessageType;
import com.trisvc.common.messages.prueba.MessagePrueba;

@XmlRootElement(name = "message")
public class TTSMessage extends Message{

	private String text;		

	public TTSMessage(String text) {
		super();
		this.text = text;
	}
	
	public TTSMessage(){		
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "TTSMessage";
	}

	@Override
	public MessageType getType() {
		// TODO Auto-generated method stub
		return MessageType.TTSMessage;
	}
	
	// TODO
	// Think other way to implement
	static final JAXBContext context = initContext();

	private static JAXBContext initContext() {
		try {
			return JAXBContext.newInstance(Message.class, TTSMessage.class);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}	
	
	public static void main(String[] args) {
		
		TTSMessage t = new TTSMessage("holitas, que tal estas");
		
		try {

			String text = Message.marshal(TTSMessage.context, t);
			System.out.println(text);
			//MessagePrueba m2 = (MessagePrueba) Message.unmarshal(MessagePrueba.context, text);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}
}
