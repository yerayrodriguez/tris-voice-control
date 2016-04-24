package com.trisvc.core.messages.types.register.structures;

public class DataTypeConfig {

	private String dataType;
	private boolean optional;

	public DataTypeConfig(String dataType, boolean optional) {
		super();
		this.dataType = dataType;
		this.optional = optional;
	}

	public DataTypeConfig() {
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public boolean isOptional() {
		return optional;
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}

}
