package com.trisvc.core.messages.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.trisvc.core.messages.Message;
import com.trisvc.core.messages.MessageBody;
import com.trisvc.core.messages.Response;
import com.trisvc.core.messages.types.invoke.InvokeMessage;
import com.trisvc.core.messages.types.invoke.InvokeResponse;
import com.trisvc.core.messages.types.parser.ParserMessage;
import com.trisvc.core.messages.types.parser.ParserResponse;
import com.trisvc.core.messages.types.register.RegisterMessage;
import com.trisvc.core.messages.types.register.RegisterResponse;
import com.trisvc.core.messages.types.tts.TTSMessage;
import com.trisvc.core.messages.types.tts.TTSResponse;
import com.trisvc.modules.brain.parser.DTContext;
import com.trisvc.modules.brain.parser.DataTypeValue;

public class MessageUtil {

	static final JAXBContext messageContext = initContext();

	public static JAXBContext initContext() {
		try {
			// @formatter:off
			return JAXBContext.newInstance(
					Message.class, 
					Response.class,
					MessageBody.class, 
					TTSMessage.class,
					TTSResponse.class,
					RegisterMessage.class,
					RegisterResponse.class,
					ParserMessage.class,
					ParserResponse.class,
					InvokeMessage.class,
					InvokeResponse.class,
					DTContext.class,
					DataTypeValue.class
					);
			// @formatter:on
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static String marshal(Message t) {

		// TODO
		// create Exception to check callerID, messageID, time

		try {
			Marshaller marshaller;
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

	//TODO
	//ask for class to check?
	@SuppressWarnings("unchecked")
	public static Message unmarshal(String stringObject) {
		Unmarshaller unmarshaller;
		try {
			unmarshaller = messageContext.createUnmarshaller();
			StringReader reader = new StringReader(stringObject);
			Object o = unmarshaller.unmarshal(reader);
			return (Message) o;
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public static XMLGregorianCalendar getXMLGregorianCalendar() {
		try {
			GregorianCalendar gcal = new GregorianCalendar();
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
		} catch (DatatypeConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
	}
	
	public static Response getResponseFromMessage(Message m){
		Response r = new Response();
		r.setCallerID(m.getCallerID());
		r.setMessageID(m.getMessageID());
		return r;
	}

}
