package com.trisvc.test.core.messages.types.parser;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import com.trisvc.core.messages.Response;
import com.trisvc.core.messages.types.parser.ParserResponse;
import com.trisvc.core.messages.util.MessageUtil;
import com.trisvc.modules.brain.parser.CommandResult;
import com.trisvc.modules.brain.parser.DataTypeValue;

public class ParserResponseTest {

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

		ArrayList<DataTypeValue> pl = new ArrayList<DataTypeValue>();
		pl.add(new DataTypeValue("TYPE1","Phrase nº 1"));
		pl.add(new DataTypeValue("TYPE","Phrase nº 2"));
		
		CommandResult cr = new CommandResult("mimodule","miinstance","mycommand",pl);

		ParserResponse p = new ParserResponse(cr);

		Response m = new Response("CallerID", "MessageID", p, "ListenerID", true, "OK");
		m.setTime(MessageUtil.getXMLGregorianCalendar());

		return m;
	}

}
