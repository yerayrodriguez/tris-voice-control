package com.trisvc.core.messages.types.register.structures;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class DataTypeDefinition {

	private String type;
	private Integer weight;
	private List<DTPatternDefinition> definitions;

	public DataTypeDefinition() {
		super();
	}

	public DataTypeDefinition(String type, Integer weight, List<DTPatternDefinition> definitions) {
		super();
		this.type = type;
		this.definitions = definitions;
		this.weight = weight;
	}

	@XmlAttribute
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@XmlAttribute
	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	@XmlElementWrapper(name = "definitions")
	@XmlElement(name = "definition")
	public List<DTPatternDefinition> getDefinitions() {
		return definitions;
	}

	public void setDefinitions(List<DTPatternDefinition> definitions) {
		this.definitions = definitions;
	}

}
