package com.trisvc.core.messages.types.parser;

import javax.xml.bind.annotation.XmlRootElement;

import com.trisvc.core.messages.MessageBody;
import com.trisvc.modules.brain.parser.DTContext;

@XmlRootElement
public class ParserMessage extends MessageBody {

	private String textToParse;
	private DTContext context;

	public ParserMessage() {
		super();
	}

	public ParserMessage(String textToParse, DTContext context) {
		super();
		this.textToParse = textToParse;
		this.context = context;
	}

	public String getTextToParse() {
		return textToParse;
	}

	public void setTextToParse(String textToParse) {
		this.textToParse = textToParse;
	}

	public DTContext getContext() {
		return context;
	}

	public void setContext(DTContext context) {
		this.context = context;
	}		

}
