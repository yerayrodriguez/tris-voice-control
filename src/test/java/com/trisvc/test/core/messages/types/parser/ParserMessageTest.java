package com.trisvc.test.core.messages.types.parser;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.trisvc.core.messages.Message;
import com.trisvc.core.messages.types.parser.ParserMessage;
import com.trisvc.core.messages.util.MessageUtil;

public class ParserMessageTest {

	@Test
	public void marshalAndUnmarshallShouldBeEquals() {

		Message<ParserMessage> m1 = genTestMessage();
		
		try {
			String x1 = m1.toString();
			@SuppressWarnings("unchecked")
			Message<ParserMessage> m2 = (Message<ParserMessage>) MessageUtil.unmarshal(x1);
			String x2 = m2.toString();
			assertTrue(x1.equals(x2));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Message<ParserMessage> genTestMessage(){
		
		ParserMessage p = new ParserMessage("Test of text to parse");
		
		Message<ParserMessage> m = new Message<ParserMessage> ("CallerID", "MessageID",p); 
		m.setTime(MessageUtil.getXMLGregorianCalendar()); 
		
		return m;
	}

}
