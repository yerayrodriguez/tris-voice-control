package com.trisvc.core.messages.types.parser;

import javax.xml.bind.annotation.XmlRootElement;

import com.trisvc.core.messages.MessageBody;

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

}
