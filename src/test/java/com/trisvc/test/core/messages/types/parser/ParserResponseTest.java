package com.trisvc.test.core.messages.types.parser;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import com.trisvc.core.messages.Response;
import com.trisvc.core.messages.types.parser.ParserResponse;
import com.trisvc.core.messages.util.MessageUtil;

public class ParserResponseTest {

	@Test
	public void marshalAndUnmarshallShouldBeEquals() {

		Response<ParserResponse> m1 = genTestMessage();

		try {
			String x1 = m1.toString();
			@SuppressWarnings("unchecked")
			Response<ParserResponse> m2 = (Response<ParserResponse>) MessageUtil.unmarshal(x1);
			String x2 = m2.toString();
			assertTrue(x1.equals(x2));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Response<ParserResponse> genTestMessage() {

		ArrayList<String> pl = new ArrayList<String>();
		pl.add("Phrase nº 1");
		pl.add("Phrase nº 2"); 

		ParserResponse p = new ParserResponse(pl);

		Response<ParserResponse> m = new Response<ParserResponse>("CallerID", "MessageID", p, true, "OK");
		m.setTime(MessageUtil.getXMLGregorianCalendar());

		return m;
	}

}
