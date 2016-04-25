package com.trisvc.core.messages.types.register.structures;

import javax.xml.bind.annotation.XmlAttribute;

public class PhraseConfig {

	@XmlAttribute
	public String type;
	@XmlAttribute
	public boolean optional;
	@XmlAttribute
	public boolean cacheable;

	public PhraseConfig() {
		super();
	}

	public PhraseConfig(String type, boolean optional, boolean cacheable) {
		super();
		this.type = type;
		this.optional = optional;
		this.cacheable = cacheable;
	}

}
