package com.trisvc.core.messages.types.register.structures;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class ModuleCommand {

	private String name;
	private List<DataTypeConfig> dataTypes;

	public ModuleCommand() {
		super();
	}

	public ModuleCommand(String name, List<DataTypeConfig> dataTypes) {
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
	public List<DataTypeConfig> getDataTypes() {
		return dataTypes;
	}

	public void setDataTypes(List<DataTypeConfig> dataTypes) {
		this.dataTypes = dataTypes;
	}

}
