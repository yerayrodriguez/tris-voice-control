package com.trisvc.modules.brain.thread;

import org.freedesktop.dbus.exceptions.DBusException;

import com.trisvc.common.BaseThread;
import com.trisvc.common.ThreadUtil;
import com.trisvc.modules.heart.object.Echo;

public class Brain extends BaseThread {	

	private static final long WAIT_CLOSE_MS = 1000;
	
	@Override
	public void execute() {
		try {
			getDBusConnection().requestBusName("braincommand.messages.trisvc.com");
			getDBusConnection().exportObject("/com/trisvc/messages/BrainCommand", new Echo());
		} catch (DBusException e) {
			e.printStackTrace();
		}

	}
		
	@Override
	protected void close() {

			getDBusConnection().unExportObject("/com/trisvc/messages/BrainCommand");
			//TODO
			//Think a way to wait for others thread finish
			ThreadUtil.sleep(WAIT_CLOSE_MS);
			getLogger().info("Exit");
			System.exit(0);

	}

}
