package com.trisvc.test.core.messages.types.tts;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.trisvc.core.messages.Response;
import com.trisvc.core.messages.types.tts.TTSResponse;
import com.trisvc.core.messages.util.MessageUtil;

public class TTSResponseTest {

	@Test
	public void marshalAndUnmarshallShouldBeEquals() {

		Response<TTSResponse> m1 = genTestMessage();

		try {
			String x1 = m1.toString();
			System.out.println(x1);
			@SuppressWarnings("unchecked")
			Response<TTSResponse> m2 = (Response<TTSResponse>) MessageUtil.unmarshal(x1);
			String x2 = m2.toString();
			assertTrue(x1.equals(x2));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Response<TTSResponse> genTestMessage() {

		TTSResponse t = new TTSResponse();

		Response<TTSResponse> m = new Response<TTSResponse>("CallerID", "MessageID", t, true, "OK");
		m.setTime(MessageUtil.getXMLGregorianCalendar());

		return m;
	}

}
