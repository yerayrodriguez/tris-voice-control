package com.trisvc.common.messages.register.structures;

import java.util.List;

public class ModuleCommand {

	private String name;
	private List<DataTypeConfig> dataTypes;

	public ModuleCommand(String name, List<DataTypeConfig> dataTypes) {
		super();
		this.name = name;
		this.dataTypes = dataTypes;
	}

	public ModuleCommand() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DataTypeConfig> getDataTypes() {
		return dataTypes;
	}

	public void setDataTypes(List<DataTypeConfig> dataTypes) {
		this.dataTypes = dataTypes;
	}

}
