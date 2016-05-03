package com.trisvc.modules.brain.parser;

import java.util.ArrayList;

public class ParserResult {

	private String originalText;
	private String parsedText;
	private ArrayList<String> dataTypesFound;

	public ParserResult() {
		super();
	}

	public ParserResult(String originalText, String parsedText, ArrayList<String> dataTypesFound) {
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

	public ArrayList<String> getDataTypesFound() {
		return dataTypesFound;
	}

	public void setDataTypesFound(ArrayList<String> dataTypesFound) {
		this.dataTypesFound = dataTypesFound;
	}

}
