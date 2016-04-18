package com.trisvc.common.messages.prueba;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import com.trisvc.common.messages.Message;
import com.trisvc.common.messages.MessageType;

@XmlRootElement(name = "message")
public class MessagePrueba extends Message {

	private MessagePruebaContent content;

	// TODO
	// Think other way to implement
	static final JAXBContext context = initContext();

	private static JAXBContext initContext() {
		try {
			return JAXBContext.newInstance(Message.class, MessagePrueba.class);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public MessageType getType() {
		return MessageType.PruebaTypeMessage;
	}

	public MessagePruebaContent getContent() {
		return content;
	}

	public void setContent(MessagePruebaContent content) {
		this.content = content;
	}





	public static void main(String[] args) {
		// MessagePrueba prueba = new MessagePrueba();
		MessagePrueba message = new MessagePrueba();
		message.setContent(new MessagePruebaContent("jojor"));

		try {

			String text = Message.marshal(MessagePrueba.context, message);
			System.out.println(text);
			//MessagePrueba m2 = (MessagePrueba) Message.unmarshal(MessagePrueba.context, text);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


}
