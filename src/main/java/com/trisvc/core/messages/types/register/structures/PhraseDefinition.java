package com.trisvc.core.messages.types.register.structures;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;

public class PhraseDefinition {

	private String name;
	private List<PatternTemplateDefinition> patterns;

	public PhraseDefinition() {
		super();
	}

	public PhraseDefinition(String name, List<PatternTemplateDefinition> patterns) {
		super();
		this.name = name;
		this.patterns = patterns;
	}

	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElementWrapper(name = "patterns")
	public List<PatternTemplateDefinition> getPatterns() {
		return patterns;
	}

	public void setPatterns(List<PatternTemplateDefinition> patterns) {
		this.patterns = patterns;
	}

}
