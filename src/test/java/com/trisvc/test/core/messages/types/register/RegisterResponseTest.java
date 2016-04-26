package com.trisvc.test.core.messages.types.register;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.trisvc.core.messages.Response;
import com.trisvc.core.messages.types.register.RegisterResponse;
import com.trisvc.core.messages.util.MessageUtil;

public class RegisterResponseTest {

	@Test
	public void marshalAndUnmarshallShouldBeEquals() {

		Response<RegisterResponse> m1 = genTestMessage();

		try {
			String x1 = m1.toString();
			System.out.println(x1);
			@SuppressWarnings("unchecked")
			Response<RegisterResponse> m2 = (Response<RegisterResponse>) MessageUtil.unmarshal(x1);
			String x2 = m2.toString();
			assertTrue(x1.equals(x2));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Response<RegisterResponse> genTestMessage() {

		RegisterResponse t = new RegisterResponse();

		Response<RegisterResponse> m = new Response<RegisterResponse>("CallerID", "MessageID", t, true, "OK");
		m.setTime(MessageUtil.getXMLGregorianCalendar());

		return m;
	}

}
