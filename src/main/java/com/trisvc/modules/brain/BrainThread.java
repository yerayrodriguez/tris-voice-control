package com.trisvc.modules.brain;

import org.freedesktop.dbus.exceptions.DBusException;

import com.trisvc.common.BaseThread;
import com.trisvc.common.Channel;
import com.trisvc.common.ThreadUtil;

public class BrainThread extends BaseThread {	

	private static final long WAIT_CLOSE_MS = 1000;
	
	@Override
	public void execute() {
		try {
			getDBusConnection().requestBusName("braincommand.messages.trisvc.com");
			getDBusConnection().exportObject("/com/trisvc/messages/BrainCommand", new Channel());
		} catch (DBusException e) {
			e.printStackTrace();
		}

	}
		
	@Override
	protected void close() {
		try {
			getDBusConnection().unExportObject("/com/trisvc/messages/BrainCommand");
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
