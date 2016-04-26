package com.trisvc.test.core.messages.types.tts;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.trisvc.core.messages.Message;
import com.trisvc.core.messages.types.tts.TTSMessage;
import com.trisvc.core.messages.util.MessageUtil;

public class TTSMessageTest {

	@Test
	public void marshalAndUnmarshallShouldBeEquals() {
		
	    System.out.println("OS Architecture : " + System.getProperty("os.arch"));

	    System.out.println("OS Name : " + System.getProperty("os.name"));

	    System.out.println("OS Version : " + System.getProperty("os.version"));

	    System.out.println("Data Model : " + System.getProperty("sun.arch.data.model"));		

		Message<TTSMessage> m1 = genTestMessage();

		try {
			String x1 = m1.toString();
			@SuppressWarnings("unchecked")
			Message<TTSMessage> m2 = (Message<TTSMessage>) MessageUtil.unmarshal(x1);
			String x2 = m2.toString();
			assertTrue(x1.equals(x2));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Message<TTSMessage> genTestMessage() {

		TTSMessage t = new TTSMessage("Test of text to speech");

		Message<TTSMessage> m = new Message<TTSMessage>("CallerID", "MessageID", t);
		m.setTime(MessageUtil.getXMLGregorianCalendar());

		return m;
	}

}
