package com.trisvc.common.messages;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.trisvc.common.messages.tts.TTSMessage;
import com.trisvc.common.messages.tts.TTSMessageContent;

public class MessageUtil {

	public static <T extends Message<?>> String marshal(T t) {
		Marshaller marshaller;
		try {
			marshaller = messageContext.createMarshaller();
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

	public static <T> T unmarshal(String stringObject) {
		Unmarshaller unmarshaller;
		try {
			unmarshaller = messageContext.createUnmarshaller();
			StringReader reader = new StringReader(stringObject);
			return (T) unmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public static <T extends Message> T unmarshal(Class<T> c, String xmlText) {
	
			return (T) MessageUtil.unmarshal(xmlText);

	}

	public static <T> JAXBContext initContext() {
		try {
			return JAXBContext.newInstance(
					Message.class, 
					TTSMessage.class, 
					TTSMessageContent.class);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	static final JAXBContext messageContext = initContext();

}
