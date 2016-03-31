package com.trisvc.common.messages.module;

import java.util.List;

import com.trisvc.common.datatypes.PatternTemplate;

public class DataTypeDefinition {

	private String dataTypeName;
	private List<PatternTemplate> definition;

	public DataTypeDefinition(String dataTypeName, List<PatternTemplate> definition) {
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

	public List<PatternTemplate> getDefinition() {
		return definition;
	}

	public void setDefinition(List<PatternTemplate> definition) {
		this.definition = definition;
	}

	@Override
	public String toString() {
		return "DataTypeDefinition [dataTypeName=" + dataTypeName + ", definition=" + definition + "]";
	}

}
