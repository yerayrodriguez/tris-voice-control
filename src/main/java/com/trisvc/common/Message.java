package com.trisvc.common;

import org.freedesktop.dbus.DBusInterface;

public interface Message extends DBusInterface{
	
	public String send (String msg);

}

