package com.trisvc.modules.heart;

import org.freedesktop.dbus.exceptions.DBusException;

import com.trisvc.common.BaseThread;
import com.trisvc.common.ThreadUtil;

public class HeartThread extends BaseThread {	

	private static final long WAIT_CLOSE_MS = 1000;
	
	@Override
	public void execute() {
		try {
			getDBusConnection().requestBusName("textcommand.messages.trisvc.com");
			getDBusConnection().exportObject("/com/trisvc/messages/TextCommand", new TextCommandMessage());
		} catch (DBusException e) {
			e.printStackTrace();
		}

	}
		
	@Override
	protected void close() {
		try {
			getDBusConnection().unExportObject("/com/trisvc/messages/TextCommand");
			//TODO
			//Think a way to wait for others thread finish
			ThreadUtil.sleep(WAIT_CLOSE_MS);
			getLogger().info("Exit");
			System.exit(0);
		} catch (DBusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
