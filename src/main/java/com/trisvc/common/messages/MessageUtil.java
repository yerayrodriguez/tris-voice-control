package com.trisvc.common.messages;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class MessageUtil {

	public static <T extends Message> String marshal(T t) {
		Marshaller marshaller;
		try {
			marshaller = t.getJAXBContext().createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			StringWriter stringWriter = new StringWriter();
			marshaller.marshal(t, stringWriter);
			return stringWriter.toString();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}


	public static <T> T unmarshal(JAXBContext context, String stringObject) {
		Unmarshaller unmarshaller;
		try {
			unmarshaller = context.createUnmarshaller();
			StringReader reader = new StringReader(stringObject);
			return (T)unmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	public static <T extends Message> T unmarshal(Class<T> c, String xmlText){
		try {
			return (T) MessageUtil.unmarshal(c.newInstance().getJAXBContext(), xmlText);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T extends Message, C extends MessageContent> JAXBContext initContext(Class<T> c, Class<C> d) {
		try {
			return JAXBContext.newInstance(Message.class, c, MessageContent.class, d);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
