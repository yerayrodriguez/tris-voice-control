package com.trisvc.core.messages.types.register.structures;

public class PatternTemplateDefinition {

	private String pattern;
	private String template;

	public PatternTemplateDefinition() {
		super();
	}

	public PatternTemplateDefinition(String pattern, String template) {
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