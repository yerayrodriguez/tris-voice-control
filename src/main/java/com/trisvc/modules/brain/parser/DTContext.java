package com.trisvc.modules.brain.parser;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DTContext {

	private String module;
	private String instance;
	private List<DataTypeValue> elements;

	public DTContext() {
		super();
	}

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

	public void setModule(String module) {
		this.module = module;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public List<DataTypeValue> getElements() {
		return elements;
	}

	public void setElements(List<DataTypeValue> elements) {
		this.elements = elements;
	}

	public void addContextElement(DataTypeValue c) {
		if (elements == null) {
			elements = new ArrayList<DataTypeValue>();
		}
		elements.add(c);
	}

}
