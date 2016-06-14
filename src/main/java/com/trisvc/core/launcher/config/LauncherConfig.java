package com.trisvc.core.launcher.config;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "busAddress", "location", "openHAB", "modules" })
public class LauncherConfig {

	private String busAddress;
	private String location;
	private String openHAB;
	private List<ModuleToLoad> modules;

	public LauncherConfig() {
		super();
	}

	public LauncherConfig(List<ModuleToLoad> modules, String busAddress, String location, String openHAB) {
		super();
		this.modules = modules;
		this.busAddress = busAddress;
		this.location = location;
		this.openHAB = openHAB;
	}

	@XmlElementWrapper(name = "modules")
	@XmlElement(name = "module")
	public List<ModuleToLoad> getModules() {
		return modules;
	}

	public void setModules(List<ModuleToLoad> modules) {
		this.modules = modules;
	}

	public String getBusAddress() {
		if (busAddress == null)
			return "";
		return busAddress.trim();
	}

	public void setBusAddress(String busAddress) {
		this.busAddress = busAddress;
	}

	public String getLocation() {
		if (location == null)
			return "";
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getOpenHAB() {
		return openHAB;
	}

	public void setOpenHAB(String openHAB) {
		this.openHAB = openHAB;
	}

	
}
