package com.trisvc.modules.brain.parser;

public class DataTypeFound {
	
	private String dataType;
	private String value;

	public DataTypeFound() {
		super();
	}

	public DataTypeFound(String dataType, String value) {
		super();
		this.dataType = dataType;
		this.value = value;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	

}
