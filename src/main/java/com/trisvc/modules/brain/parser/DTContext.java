package com.trisvc.modules.brain.parser;

import java.util.ArrayList;
import java.util.List;

public class DTContext {

	private String module;
	private String instance;
	private List<DataTypeValue> elements;

	public DTContext(String module, String instance) {
		super();
		this.module = module;
		this.instance = instance;
	}

	public DTContext(String module, String instance, List<DataTypeValue> elements) {
		this(module, instance);
		this.elements = elements;
	}

	public String getModule() {
		return module;
	}

	public String getInstance() {
		return instance;
	}

	public List<DataTypeValue> getElements() {
		return elements;
	}

	public void addContextElement(DataTypeValue c) {
		if (elements == null) {
			elements = new ArrayList<DataTypeValue>();
		}
		elements.add(c);
	}

}
