package com.trisvc.modules.openhab;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "item")
public class OpenHabItem {
	
	private String type;
	private String name;
	private String state;
	private String link;

	public OpenHabItem() {
		super();
	}

	public OpenHabItem(String type, String name, String state, String link) {
		super();
		this.type = type;
		this.name = name;
		this.state = state;
		this.link = link;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	

}
