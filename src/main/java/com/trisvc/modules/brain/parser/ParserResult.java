package com.trisvc.modules.brain.parser;

import java.util.ArrayList;

public class ParserResult {

	private String originalText;
	private String parsedText;
	private ArrayList<DataTypeValue> dataTypesFound;

	public ParserResult() {
		super();
	}

	public ParserResult(String originalText, String parsedText, ArrayList<DataTypeValue> dataTypesFound) {
		super();
		this.originalText = originalText;
		this.parsedText = parsedText;
		this.dataTypesFound = dataTypesFound;
	}

	public String getOriginalText() {
		return originalText;
	}

	public void setOriginalText(String originalText) {
		this.originalText = originalText;
	}

	public String getParsedText() {
		return parsedText;
	}

	public void setParsedText(String parsedText) {
		this.parsedText = parsedText;
	}

	public ArrayList<DataTypeValue> getDataTypesFound() {
		return dataTypesFound;
	}

	public void setDataTypesFound(ArrayList<DataTypeValue> dataTypesFound) {
		this.dataTypesFound = dataTypesFound;
	}

	@Override
	public String toString() {
		String aux = System.lineSeparator() +
				"Original: " + getOriginalText() + System.lineSeparator() + 
				"Parsed: " + getParsedText()+ System.lineSeparator() + 
				"List:" + System.lineSeparator();
		for (DataTypeValue s : getDataTypesFound()) {
			aux += "- " +s.getDataType()+" - "+ s.getValue() + System.lineSeparator();
		}
		return aux;
	}

}
