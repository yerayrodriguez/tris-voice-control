package com.trisvc.modules.openhab;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trisvc.core.launcher.Launcher;

public class OpenHabRest {

	private final String USER_AGENT = "Mozilla/5.0";
	private static Logger logger = LogManager.getLogger(OpenHabRest.class.getName());
	
	private String url; 
	
	public OpenHabRest(String url){
		this.url = url;
	}

	public static void main(String[] args) throws Exception {

		OpenHabRest http = new OpenHabRest("http://"+Launcher.config.getOpenHAB());

		
		System.out.println("\nTesting 2 - Send Http POST request");
		//100 close
		//0 open
		http.sendStatus("persiana_dormitorio","0");
		OpenHabItems o = OpenHabUtil.unmarshalItems(http.getItems());
		System.out.println(OpenHabUtil.marshalItems(o));

	}

	public boolean sendStatus(String device, String status) throws Exception {

		String deviceRestUrl = url+"/rest/items/"+device;
		URL obj = new URL(deviceRestUrl);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		//con.setRequestProperty("User-Agent", USER_AGENT);
		//con.setRequestProperty("Accept-Language", "es-ES,en;q=0.5");
		con.setRequestProperty("Content-type", "text/plain");
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(status);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		logger.debug("Sending 'POST' request to URL : " + deviceRestUrl);
		logger.debug("Post parameters : " + status);
		logger.debug("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		logger.debug(response.toString());
		
		if (responseCode == 201){
			return true;
		}
		
		return false;

	}
	
	public String getItems() throws Exception {

		String deviceRestUrl = url+"/rest/items?Accept=application/xml";
		URL obj = new URL(deviceRestUrl);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("GET");
		//con.setRequestProperty("User-Agent", USER_AGENT);
		//con.setRequestProperty("Accept-Language", "es-ES,en;q=0.5");
		con.setRequestProperty("Content-type", "text/plain");

		int responseCode = con.getResponseCode();
		logger.debug("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		logger.debug(response.toString());
		
		return response.toString();

	}	

}