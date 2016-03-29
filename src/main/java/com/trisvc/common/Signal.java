package com.trisvc.common;

import org.freedesktop.dbus.DBusInterface;
import org.freedesktop.dbus.DBusSignal;
import org.freedesktop.dbus.exceptions.DBusException;

public interface Signal extends DBusInterface {
	
	public class HaltSignal extends DBusSignal{

		public HaltSignal(String path) throws DBusException {
			super(path);
			
		}

	}
	
	public class LogSignal extends DBusSignal{
		
		public final String logMsg;

		public LogSignal(String path, String logMsg) throws DBusException {
			super(path, logMsg);
			this.logMsg = logMsg;
		}

	}	
	
}
