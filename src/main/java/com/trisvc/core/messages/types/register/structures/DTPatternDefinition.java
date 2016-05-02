package com.trisvc.core.messages.types.register.structures;

public class DTPatternDefinition {

	private String pattern;
	private String template;

	public DTPatternDefinition() {
		super();
	}

	public DTPatternDefinition(String pattern, String template) {
		super();
		this.pattern = pattern;
		this.template = template;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

}