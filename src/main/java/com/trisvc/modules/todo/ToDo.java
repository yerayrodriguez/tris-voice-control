package com.trisvc.modules.todo;

import org.freedesktop.dbus.exceptions.DBusException;

import com.trisvc.core.launcher.thread.BaseThread;
import com.trisvc.core.launcher.thread.ThreadUtil;
import com.trisvc.modules.todo.object.ToDoObject;

public class ToDo extends BaseThread {	

	private static final long WAIT_CLOSE_MS = 1000;
	
	@Override
	public void execute() {
		try {
			exportObject(new ToDoObject(), this.getClass().getSimpleName());
		} catch (DBusException e) {
			e.printStackTrace();
		}

	}
		
	@Override
	protected void close() {
		try {
			unExportObject(ToDoObject.class);
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
