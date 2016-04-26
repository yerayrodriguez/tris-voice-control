package com.trisvc.core.messages.types.parser;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.trisvc.core.messages.MessageBody;

@XmlRootElement
public class ParserResponse extends MessageBody {

	private List<String> phrases;

	public ParserResponse() {
		super();
	}

	public ParserResponse(List<String> phrases) {
		super();
		this.phrases = phrases;
	}

	@XmlElementWrapper(name = "phrases")
	@XmlElement(name = "phrase")
	public List<String> getPhrases() {
		return phrases;
	}

	public void setPhrases(List<String> phrases) {
		this.phrases = phrases;
	}

}
