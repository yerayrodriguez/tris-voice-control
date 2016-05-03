package com.trisvc.modules.brain.thread;

import org.freedesktop.dbus.exceptions.DBusException;

import com.trisvc.core.launcher.thread.BaseThread;
import com.trisvc.core.launcher.thread.ThreadUtil;
import com.trisvc.modules.brain.object.BrainObject;

public class Brain extends BaseThread {	

	private static final long WAIT_CLOSE_MS = 1000;
	
	@Override
	public void execute() {
		try {
			
			exportObject(new Brain());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
		
	@Override
	protected void close() {

			try {
				unExportObject(Brain.class);
			} catch (DBusException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//TODO
			//Think a way to wait for others thread finish
			ThreadUtil.sleep(WAIT_CLOSE_MS);
			getLogger().info("Exit");
			System.exit(0);

	}


}
