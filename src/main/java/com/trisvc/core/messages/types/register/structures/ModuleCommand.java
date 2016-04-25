package com.trisvc.core.messages.types.register.structures;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class ModuleCommand {

	@XmlAttribute
	public String name;
	@XmlElementWrapper(name = "phrases")
	@XmlElement(name="phrase")	
	public List<PhraseConfig> dataTypes;
	
	public ModuleCommand() {
		super();
	}	

	public ModuleCommand(String name, List<PhraseConfig> dataTypes) {
		super();
		this.name = name;
		this.dataTypes = dataTypes;
	}



}
