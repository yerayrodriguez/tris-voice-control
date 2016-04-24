package com.trisvc.modules.heart.thread;

import org.freedesktop.dbus.exceptions.DBusException;

import com.trisvc.core.BaseThread;
import com.trisvc.core.ThreadUtil;
import com.trisvc.modules.heart.object.Echo;

public class Heart extends BaseThread {	

	private static final long WAIT_CLOSE_MS = 1000;
	
	@Override
	public void execute() {
		try {
			exportObject(new Echo());
		} catch (DBusException e) {
			e.printStackTrace();
		}

	}
		
	@Override
	protected void close() {
		try {
			unExportObject(Echo.class);
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
