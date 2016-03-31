package com.trisvc.common.messages.module;

import java.util.List;

import com.trisvc.common.messages.Message;

public class ModuleFunctionality {

	private String moduleName;
	private List<DataTypeDefinition> datatypes;
	private List<ModuleCommand> commands;

	public ModuleFunctionality(String moduleName, List<DataTypeDefinition> datatypes) {
		this.moduleName = moduleName;
		this.datatypes = datatypes;
	}

	public ModuleFunctionality() {
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String name) {
		this.moduleName = name;
	}

	public List<DataTypeDefinition> getDatatypes() {
		return datatypes;
	}

	public void setDatatypes(List<DataTypeDefinition> datatypes) {
		this.datatypes = datatypes;
	}

	@Override
	public String toString() {
		return "ModuleFunctionality [moduleName=" + moduleName + ", datatypes=" + datatypes + "]";
	}

}
