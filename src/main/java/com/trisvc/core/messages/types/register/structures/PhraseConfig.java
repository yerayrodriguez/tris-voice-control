package com.trisvc.core.messages.types.register.structures;

import javax.xml.bind.annotation.XmlAttribute;

public class PhraseConfig {

	private String type;
	private boolean optional;
	private boolean cacheable;

	public PhraseConfig() {
		super();
	}

	public PhraseConfig(String type, boolean optional, boolean cacheable) {
		super();
		this.type = type;
		this.optional = optional;
		this.cacheable = cacheable;
	}

	@XmlAttribute
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@XmlAttribute
	public boolean isOptional() {
		return optional;
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}

	@XmlAttribute
	public boolean isCacheable() {
		return cacheable;
	}

	public void setCacheable(boolean cacheable) {
		this.cacheable = cacheable;
	}

}
