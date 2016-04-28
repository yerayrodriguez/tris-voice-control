package com.trisvc.test.core.messages.types.parser;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.trisvc.core.messages.Message;
import com.trisvc.core.messages.types.parser.ParserMessage;
import com.trisvc.core.messages.util.MessageUtil;

public class ParserMessageTest {

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
	
	private Message genTestMessage(){
		
		ParserMessage p = new ParserMessage("Test of text to parse");
		
		Message m = new Message ("CallerID", "MessageID",p); 
		m.setTime(MessageUtil.getXMLGregorianCalendar()); 
		
		return m;
	}

}
