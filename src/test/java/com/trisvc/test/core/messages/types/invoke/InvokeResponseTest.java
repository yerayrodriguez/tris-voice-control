package com.trisvc.test.core.messages.types.invoke;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import com.trisvc.core.messages.Response;
import com.trisvc.core.messages.types.invoke.InvokeResponse;
import com.trisvc.core.messages.util.MessageUtil;
import com.trisvc.modules.brain.parser.DTContext;
import com.trisvc.modules.brain.parser.DataTypeValue;

public class InvokeResponseTest {

	@Test
	public void marshalAndUnmarshallShouldBeEquals() {

		Response m1 = genTestMessage();

		try {
			String x1 = m1.toString();
			System.out.println(x1);
			Response m2 = (Response) MessageUtil.unmarshal(x1);
			String x2 = m2.toString();
			assertTrue(x1.equals(x2));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Response genTestMessage() {
		
		DTContext normalContext = new DTContext();
		normalContext.addContextElement(new DataTypeValue("TYPE1","Phrase nº 1"));
		normalContext.addContextElement(new DataTypeValue("TYPE2","Phrase nº 2"));
		
		InvokeResponse ir = new InvokeResponse();
		ir.setMessage("my msg");
		ir.setInmmediateContext("my inmediate context");
		ir.setNormalContext(normalContext);

		Response m = new Response("CallerID", "MessageID", ir, "ListenerID", true, "OK");
		m.setTime(MessageUtil.getXMLGregorianCalendar());

		return m;
	}

}
