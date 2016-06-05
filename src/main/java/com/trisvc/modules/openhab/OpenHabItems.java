package com.trisvc.modules.openhab;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "items")
public class OpenHabItems {
	
	List<OpenHabItem> itemList;

	public OpenHabItems() {
		// TODO Auto-generated constructor stub
	}

	public OpenHabItems(List<OpenHabItem> itemList) {
		super();
		this.itemList = itemList;
	}

	@XmlElement(name = "item")
	public List<OpenHabItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<OpenHabItem> itemList) {
		this.itemList = itemList;
	}

	public static void main(String[] args) {
		OpenHabItem uno = new OpenHabItem("a","a", "a", "a");
		OpenHabItem dos = new OpenHabItem("b","b", "b", "b");
		List<OpenHabItem> a = new ArrayList<OpenHabItem>();
		a.add(uno);
		a.add(dos);
		OpenHabItems l = new OpenHabItems(a);
		String jor = (OpenHabUtil.marshalItems(l));
		OpenHabItems x = OpenHabUtil.unmarshalItems(jor);
		System.out.println(OpenHabUtil.marshalItems(x));
		
		
	}
}
