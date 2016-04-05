package com.trisvc.common.messages.moduleregister;

import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;

public class DataTypeDefinition {

	private String dataTypeName;
	private List<PatternTemplateDefinition> definition;

	public DataTypeDefinition(String dataTypeName, List<PatternTemplateDefinition> definition) {
		this.dataTypeName = dataTypeName;
		this.definition = definition;
	}

	public DataTypeDefinition() {
	}

	public String getDataTypeName() {
		return dataTypeName;
	}

	public void setDataTypeName(String dataTypeName) {
		this.dataTypeName = dataTypeName;
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
		return "DataTypeDefinition [dataTypeName=" + dataTypeName + ", definition=" + definition + "]";
	}

}
