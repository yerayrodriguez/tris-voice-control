package com.trisvc.core.messages.types.parser;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.trisvc.core.messages.Message;
import com.trisvc.core.messages.MessageBody;
import com.trisvc.core.messages.util.MessageUtil;

@XmlRootElement
public class ParserResponse extends MessageBody{
	
	@XmlElementWrapper( name="groups")
	public List<String> group;

	public ParserResponse() {
		super();
	}
	
	public ParserResponse(List<String> group) {
		super();
		this.group = group;
	}


	public static void main(String[] args) {
		ArrayList<String> l = new ArrayList<String>();
		l.add("hola");
		l.add("que tal estas");
		ParserResponse c = new ParserResponse(l);
		Message<ParserResponse> t = new Message<ParserResponse> ("CallerID", "MessageID",c); 
		t.time = MessageUtil.getXMLGregorianCalendar(); 
		
		try {
			String text = t.toString();
			System.out.println(text);
			@SuppressWarnings("unchecked")
			Message<ParserResponse> t2 = (Message<ParserResponse>) MessageUtil.unmarshal( text);
			// TTSMessage t2 = new TTSMessage();
			// t2.unmarshal(text);
			System.out.println(t2.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}		

}
