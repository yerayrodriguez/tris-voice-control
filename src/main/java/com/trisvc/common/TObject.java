package com.trisvc.common;

import org.freedesktop.dbus.DBusInterface;

public interface TObject extends DBusInterface{
	
	public String send (String msg);

}

