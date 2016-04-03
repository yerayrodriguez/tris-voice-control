package com.trisvc.common.messages.modulefunctionality;

public class PatternTemplateDefinition {

	private String patternIn;
	private String templateOut;

	public PatternTemplateDefinition(String patternIn, String templateOut)  {

		this.patternIn = patternIn;
		this.templateOut = templateOut;
	}

	public PatternTemplateDefinition() {
	}

	public String getPatternIn() {
		return patternIn;
	}

	public void setPatternIn(String patternIn) {
		this.patternIn = patternIn;
	}

	public String getTemplateOut() {
		return templateOut;
	}

	public void setTemplateOut(String templateOut) {
		this.templateOut = templateOut;
	}

	@Override
	public String toString() {
		return "PatternTemplateDefinition [patternIn=" + patternIn + ", templateOut=" + templateOut + "]";
	}

}