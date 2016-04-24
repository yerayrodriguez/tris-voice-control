package com.trisvc.common.messages.tts;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;

import com.trisvc.common.messages.Message;
import com.trisvc.common.messages.MessageContent;
import com.trisvc.common.messages.MessageUtil;

@XmlRootElement(name="content")
public class TTSMessageContent extends MessageContent {
	
	private String prueba;

	public TTSMessageContent() {
		// TODO Auto-generated constructor stub
	}

	public String getPrueba() {
		return prueba;
	}

	public void setPrueba(String prueba) {
		this.prueba = prueba;
	}

	public TTSMessageContent(String prueba) {
		super();
		this.prueba = prueba;
	}
	
	static final JAXBContext context = initContext( TTSMessageContent.class);

	
	public static <T extends MessageContent> JAXBContext initContext(Class<T> c) {
		try {
			return JAXBContext.newInstance(MessageContent.class, c);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public String toString() {
		return marshal(this);
	}
	
	public static <T extends MessageContent> String marshal(T t) {
		Marshaller marshaller;
		try {
			marshaller = context.createMarshaller();
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
	public static void main(String[] args) {


		TTSMessageContent t = new TTSMessageContent("contenido");


		try {

			String text = t.toString();
			System.out.println(text);
			TTSMessageContent t2 = (TTSMessageContent) MessageUtil.unmarshal(context, text);
			// TTSMessage t2 = new TTSMessage();
			// t2.unmarshal(text);
			System.out.println(t2.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
