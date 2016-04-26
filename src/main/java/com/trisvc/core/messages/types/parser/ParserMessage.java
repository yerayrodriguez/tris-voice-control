package com.trisvc.core.messages.types.parser;

import javax.xml.bind.annotation.XmlRootElement;

import com.trisvc.core.messages.MessageBody;

@XmlRootElement
public class ParserMessage extends MessageBody {

	private String textToParse;

	public ParserMessage() {
		super();
	}

	public ParserMessage(String textToParse) {
		super();
		this.textToParse = textToParse;
	}

	public String getTextToParse() {
		return textToParse;
	}

	public void setTextToParse(String textToParse) {
		this.textToParse = textToParse;
	}

}
