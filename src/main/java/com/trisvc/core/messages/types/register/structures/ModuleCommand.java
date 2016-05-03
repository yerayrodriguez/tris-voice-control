package com.trisvc.core.messages.types.register.structures;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class ModuleCommand {

	private String name;
	private List<String> commandPattern;

	public ModuleCommand() {
		super();
	}

	public ModuleCommand(String name, List<String> commandPattern) {
		super();
		this.name = name;
		this.commandPattern = commandPattern;
	}

	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElementWrapper(name = "patterns")
	@XmlElement(name = "pattern")
	public List<String> getCommandPattern() {
		return commandPattern;
	}

	public void setCommandPattern(List<String> commandPattern) {
		this.commandPattern = commandPattern;
	}
	
	

}
