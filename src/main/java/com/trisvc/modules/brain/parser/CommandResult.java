package com.trisvc.modules.brain.parser;

import java.util.List;

public class CommandResult {
	private String module;
	private String instance;
	private String command;
	private List<DataTypeValue> values;

	public CommandResult() {
		super();
	}

	public CommandResult(String module, String instance, String command, List<DataTypeValue> values) {
		super();
		this.module = module;
		this.instance = instance;
		this.command = command;
		this.values = values;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public List<DataTypeValue> getValues() {
		return values;
	}

	public void setValues(List<DataTypeValue> values) {
		this.values = values;
	}

	public String toString() {
		String s = "Module: " + module + System.lineSeparator() + "Instance: " + instance + System.lineSeparator()
				+ "Command: " + command + System.lineSeparator() + "Values: " + System.lineSeparator();
		for (DataTypeValue value : values) {
			s = s + "   - " + value.getDataType() + ": " + value.getValue() + System.lineSeparator();
		}
		return s;
	}

}
