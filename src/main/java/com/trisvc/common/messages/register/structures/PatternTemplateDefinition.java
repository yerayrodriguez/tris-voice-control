package com.trisvc.common.messages.register.structures;

public class PatternTemplateDefinition {

	private String pattern;
	private String template;

	public PatternTemplateDefinition(String pattern, String template)  {

		this.pattern = pattern;
		this.template = template;
	}

	public PatternTemplateDefinition() {
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String patternIn) {
		this.pattern = patternIn;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String templateOut) {
		this.template = templateOut;
	}

	@Override
	public String toString() {
		return "PatternTemplateDefinition [pattern=" + pattern + ", template=" + template + "]";
	}

}