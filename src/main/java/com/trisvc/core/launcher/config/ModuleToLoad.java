package com.trisvc.core.launcher.config;

import javax.xml.bind.annotation.XmlAttribute;

public class ModuleToLoad {

	private String module;
	private String instance;

	public ModuleToLoad() {
		super();
	}

	public ModuleToLoad(String module, String instance) {
		super();
		this.module = module;
		this.instance = instance;
	}

	public String getModule() {
		if (module == null)
			return "";
		return module.trim();
	}

	public void setModule(String module) {
		this.module = module;
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