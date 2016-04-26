package com.trisvc.core.messages.types.register.structures;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class ModuleCommand {

	private String name;
	private List<PhraseConfig> dataTypes;

	public ModuleCommand() {
		super();
	}

	public ModuleCommand(String name, List<PhraseConfig> dataTypes) {
		super();
		this.name = name;
		this.dataTypes = dataTypes;
	}

	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElementWrapper(name = "phrases")
	@XmlElement(name = "phrase")
	public List<PhraseConfig> getDataTypes() {
		return dataTypes;
	}

	public void setDataTypes(List<PhraseConfig> dataTypes) {
		this.dataTypes = dataTypes;
	}

}
