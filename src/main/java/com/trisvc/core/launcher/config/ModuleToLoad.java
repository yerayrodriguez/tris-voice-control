package com.trisvc.core.launcher.config;

import javax.xml.bind.annotation.XmlAttribute;

public class ModuleToLoad {

	private String qualifiedName;
	private String instance;

	public ModuleToLoad() {
		super();
	}

	public ModuleToLoad(String qualifiedName, String instance) {
		super();
		this.qualifiedName = qualifiedName;
		this.instance = instance;
	}

	public String getQualifiedName() {
		if (qualifiedName == null)
			return "";
		return qualifiedName.trim();
	}

	public void setQualifiedName(String qualifiedName) {
		this.qualifiedName = qualifiedName;
	}

	@XmlAttribute
	public String getInstance() {
		if (instance == null || instance.trim().length() == 0)
			return "default";
		return instance.trim();
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

}