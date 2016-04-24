package com.trisvc.core.messages.types.parser;

import javax.xml.bind.annotation.XmlRootElement;

import com.trisvc.core.messages.Message;
import com.trisvc.core.messages.MessageBody;
import com.trisvc.core.messages.util.MessageUtil;

@XmlRootElement
public class ParserMessage extends MessageBody{
	
	public String textToParse;

	public ParserMessage() {
		super();
	}
	
	public ParserMessage(String textToParse){
		super();
		this.textToParse = textToParse;
	}
	
	public static void main(String[] args) {
		ParserMessage c = new ParserMessage("Test of text to parse");
		Message<ParserMessage> t = new Message<ParserMessage> ("CallerID", "MessageID",c); 
		t.time = MessageUtil.getXMLGregorianCalendar(); 
		
		try {
			String text = t.toString();
			System.out.println(text);
			@SuppressWarnings("unchecked")
			Message<ParserMessage> t2 = (Message<ParserMessage>) MessageUtil.unmarshal( text);
			// TTSMessage t2 = new TTSMessage();
			// t2.unmarshal(text);
			System.out.println(t2.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

}
