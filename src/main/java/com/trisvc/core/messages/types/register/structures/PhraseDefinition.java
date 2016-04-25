package com.trisvc.core.messages.types.register.structures;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;

public class PhraseDefinition {

	@XmlAttribute
	public String name;

	@XmlElementWrapper(name = "patterns")
	public List<PatternTemplateDefinition> patterns;

	public PhraseDefinition() {
		super();
	}

	public PhraseDefinition(String name, List<PatternTemplateDefinition> patterns) {
		super();
		this.name = name;
		this.patterns = patterns;
	}

}
