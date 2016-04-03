package com.trisvc.common.messages;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public abstract class Message {

	public abstract MessageType getType();

	public void setType(MessageType type) throws MessageTypeException {
		if (!getType().equals(type))
			throw new MessageTypeException(getType(), type);
	}

	public static String marshal(JAXBContext context, Object object) {
		Marshaller marshaller;
		try {
			marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			StringWriter stringWriter = new StringWriter();
			marshaller.marshal(object, stringWriter);
			return stringWriter.toString();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";

	}

	public static Object unmarshal(JAXBContext context, String stringObject) {
		Unmarshaller unmarshaller;
		try {
			unmarshaller = context.createUnmarshaller();
			StringReader reader = new StringReader(stringObject);
			return unmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
