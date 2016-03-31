package com.trisvc.common.messages;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;


public class Prueba implements Message {
	
	private MessageType type;
	private MessageBody body;
	
	@Override
	public MessageType getType() {
		return MessageType.PruebaType;
	}
	
	@Override
	public void setType(MessageType type){
		this.type = type;
	}

	@Override
	public MessageBody getBody() {
		return new PruebaBody();
	}


	
	public static void main(String[] args) {
		Prueba prueba = new Prueba();
		Message message = new Prueba();
		
		try{
		JAXBContext context = JAXBContext.newInstance(Message.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal(message,new File("jora.xml"));
		}catch (Exception e){
			e.printStackTrace();
		}
	}





}
