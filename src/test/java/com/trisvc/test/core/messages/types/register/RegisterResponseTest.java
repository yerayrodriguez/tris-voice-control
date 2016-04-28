package com.trisvc.test.core.messages.types.register;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.trisvc.core.messages.Response;
import com.trisvc.core.messages.types.register.RegisterResponse;
import com.trisvc.core.messages.util.MessageUtil;

public class RegisterResponseTest {

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

		RegisterResponse t = new RegisterResponse();

		Response m = new Response("CallerID", "MessageID", t, true, "OK");
		m.setTime(MessageUtil.getXMLGregorianCalendar());

		return m;
	}

}
