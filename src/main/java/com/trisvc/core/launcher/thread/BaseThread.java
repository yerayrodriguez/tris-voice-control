package com.trisvc.core.launcher.thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.DBusSigHandler;
import org.freedesktop.dbus.exceptions.DBusException;

import com.trisvc.core.Signal;
import com.trisvc.core.launcher.Launcher;
import com.trisvc.modules.BaseObject;
import com.trisvc.modules.RemoteObjectWrapper;

public abstract class BaseThread implements Runnable {

	protected static final String THREAD_DEFAULT_ID = "default";
	private Logger logger;

	protected String instance;

	private static DBusConnection connection = initializeDBusConnection();

	public void setInstance(String instance) {
		// TODO
		// Change for constructor
		// Look at ThreadFactory
		if (instance == null)
			instance = THREAD_DEFAULT_ID;
		this.instance = instance;
		// TODO
		// Think how to make a more elegant solution
		logger = LogManager.getLogger(this.getClass().getName() + ":" + this.instance);
	}

	private static DBusConnection initializeDBusConnection() {
		try {
			if (Launcher.config.getBusAddress().trim().startsWith("tcp")) {
				return DBusConnection.getConnection(Launcher.config.getBusAddress().trim());
			} else if ("SESSION".equals(Launcher.config.getBusAddress().trim())) {
				return DBusConnection.getConnection(DBusConnection.SESSION);
			} else {
				System.out.println("Protocol unknown: " + Launcher.config.getBusAddress());
				return null;
			}

		} catch (DBusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	protected static DBusConnection getDBusConnection() {
		return connection;
	}

	protected void exportObject(BaseObject object, String instance) throws DBusException {
		if (instance == null || instance.trim().length() == 0) {
			instance = "default";
		}
		getDBusConnection().requestBusName("com.trisvc.object." + object.getClass().getSimpleName() + "." + instance);
		getDBusConnection().exportObject("/com/trisvc/object/" + object.getClass().getSimpleName() + "/" + instance,
				object);
	}

	protected void exportObject(BaseObject object) throws DBusException {
		exportObject(object, null);
	}

	@SuppressWarnings("rawtypes")
	protected void unExportObject(Class c) throws DBusException {
		unExportObject(c, null);
	}

	@SuppressWarnings("rawtypes")
	protected void unExportObject(Class c, String instance) throws DBusException {
		if (instance == null || instance.trim().length() == 0) {
			instance = "default";
		}
		getDBusConnection().unExportObject("/com/trisvc/object/" + c.getSimpleName() + "/" + instance);
	}

	protected static RemoteObjectWrapper getRemoteObject(Class c) {
		return getRemoteObject(c, null);
	}

	protected static RemoteObjectWrapper getRemoteObject(Class c, String instance) {
		if (instance == null || instance.trim().length() == 0) {
			instance = "default";
		}

		try {
			BaseObject o;
			o = (BaseObject) getDBusConnection().getRemoteObject(
					"com.trisvc.object." + c.getSimpleName() + "." + instance,
					"/com/trisvc/object/" + c.getSimpleName() + "/" + instance, BaseObject.class);
			RemoteObjectWrapper r = new RemoteObjectWrapper(o);
			return r;
		} catch (DBusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void run() {
		try {
			registerHaltSignal();
			execute();
		} catch (DBusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	abstract protected void execute();

	abstract protected void close();

	private void registerHaltSignal() throws DBusException {
		getDBusConnection().addSigHandler(Signal.HaltSignal.class, new DBusSigHandler<Signal.HaltSignal>() {

			@Override
			public void handle(Signal.HaltSignal sig) {

				getLogger().info("Halt signal received");
				close();

				getDBusConnection().disconnect();

			}

		});

	}

	public String getInstance() {
		return instance;
	}

	protected Logger getLogger() {
		return logger;
	}

}
