package com.trisvc.common;

import org.freedesktop.dbus.DBusInterface;

public interface BaseObject extends DBusInterface{
	
	public String send (String message);	
	
	//TODO
	//Think how to implement getName()
	//to allow create a object with any name and 
	//pass another name
}

