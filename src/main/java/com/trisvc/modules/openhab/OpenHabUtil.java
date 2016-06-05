package com.trisvc.modules.openhab;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class OpenHabUtil {

	static final JAXBContext messageContext = initContext();

	public static JAXBContext initContext() {
		try {
			// @formatter:off
			return JAXBContext.newInstance(
					OpenHabItems.class,
					OpenHabItem.class
					);
			// @formatter:on
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static String marshalItems(OpenHabItems t) {

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
	
	public static String marshalItem(OpenHabItem t) {

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


	@SuppressWarnings("unchecked")
	public static OpenHabItems unmarshalItems(String stringObject) {
		Unmarshaller unmarshaller;
		try {
			unmarshaller = messageContext.createUnmarshaller();
			StringReader reader = new StringReader(stringObject);
			Object o = unmarshaller.unmarshal(reader);
			return (OpenHabItems) o;
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public static OpenHabItem unmarshalItem(String stringObject) {
		Unmarshaller unmarshaller;
		try {
			unmarshaller = messageContext.createUnmarshaller();
			StringReader reader = new StringReader(stringObject);
			Object o = unmarshaller.unmarshal(reader);
			return (OpenHabItem) o;
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}	
}
