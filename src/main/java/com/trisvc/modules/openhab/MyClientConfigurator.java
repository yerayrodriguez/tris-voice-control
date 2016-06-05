package com.trisvc.modules.openhab;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.websocket.ClientEndpointConfig.Configurator;

public class MyClientConfigurator extends Configurator {
	
	@Override
	public void beforeRequest(Map<String,List<String>> headers)
	{
	    List<String> values = new ArrayList<String>();
	    values.add("websocket");

	    headers.put("X-Atmosphere-Transport", values);
	}

}