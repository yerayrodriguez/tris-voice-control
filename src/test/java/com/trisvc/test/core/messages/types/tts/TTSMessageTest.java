package com.trisvc.test.core.messages.types.tts;

import static org.junit.Assert.*;

import org.junit.Test;

import com.trisvc.core.messages.Message;
import com.trisvc.core.messages.types.tts.TTSMessage;
import com.trisvc.core.messages.util.MessageUtil;

public class TTSMessageTest {

	@Test
	public void marshalAndUnmarshallShouldBeEquals() {
		TTSMessage t = new TTSMessage("Test of text to speech");
		Message<TTSMessage> m1 = new Message<TTSMessage> ("CallerID", "MessageID",t); 
		m1.time = MessageUtil.getXMLGregorianCalendar(); 
		
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

}
