package com.trisvc.core.datatypes;

public class DataTypeResult {

	private String detected;
	private String replaced;

	public DataTypeResult() {
		super();
	}

	public DataTypeResult(String detected, String replaced) {
		super();
		this.detected = detected;
		this.replaced = replaced;
	}

	public String getDetected() {
		return detected;
	}

	public void setDetected(String detected) {
		this.detected = detected;
	}

	public String getReplaced() {
		return replaced;
	}

	public void setReplaced(String replaced) {
		this.replaced = replaced;
	}

}
