package com.trisvc.common.messages.moduleregister;

import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;

public class DataTypeDefinition {

	private String name;
	private List<PatternTemplateDefinition> definition;

	public DataTypeDefinition(String name, List<PatternTemplateDefinition> definition) {
		this.name = name;
		this.definition = definition;
	}

	public DataTypeDefinition() {
	}

	public String getDataTypeName() {
		return name;
	}

	public void setDataTypeName(String name) {
		this.name = name;
	}

	@XmlElementWrapper( name="definitions" )
	public List<PatternTemplateDefinition> getDefinition() {
		return definition;
	}

	public void setDefinition(List<PatternTemplateDefinition> definition) {
		this.definition = definition;
	}

	@Override
	public String toString() {
		return "DataTypeDefinition [name=" + name + ", definition=" + definition + "]";
	}

}
