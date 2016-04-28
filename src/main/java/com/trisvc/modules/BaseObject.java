package com.trisvc.modules;

import org.freedesktop.dbus.DBusInterface;

public interface BaseObject extends DBusInterface{
	
	public String send (String xmlMessage);			

}

