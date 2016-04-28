package com.trisvc.test.core.messages.types.tts;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.trisvc.core.messages.Message;
import com.trisvc.core.messages.types.tts.TTSMessage;
import com.trisvc.core.messages.util.MessageUtil;

public class TTSMessageTest {

	@Test
	public void marshalAndUnmarshallShouldBeEquals() {	

		Message m1 = genTestMessage();

		try {
			String x1 = m1.toString();
			System.out.println(x1);
			Message m2 = (Message) MessageUtil.unmarshal(x1);
			String x2 = m2.toString();
			assertTrue(x1.equals(x2));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Message genTestMessage() {

		TTSMessage t = new TTSMessage("Test of text to speech");

		Message m = new Message("CallerID", "MessageID", t);
		m.setTime(MessageUtil.getXMLGregorianCalendar());

		return m;
	}

}
