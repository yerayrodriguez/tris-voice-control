package com.trisvc.common;

import org.freedesktop.dbus.DBusInterface;

public interface BaseObject extends DBusInterface{
	
	//TODO
	//get rid off type
	//now i'm using it to deserialize message
	//but this information is also inside message
	public String send (String type, String message);	
	
	//TODO
	//Think how to implement getName()
	//to allow create a object with any name and 
	//pass another name
}

