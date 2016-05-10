package com.trisvc.core.launcher.thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.DBusSigHandler;
import org.freedesktop.dbus.exceptions.DBusException;

import com.trisvc.core.Signal;
import com.trisvc.core.launcher.Launcher;
import com.trisvc.core.messages.Message;
import com.trisvc.core.messages.Response;
import com.trisvc.core.messages.types.register.RegisterMessage;
import com.trisvc.core.messages.types.tts.TTSMessage;
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

	protected void exportObject(BaseObject object, String module, String instance) throws DBusException {
		if (instance == null || instance.trim().length() == 0) {
			instance = "default";
		}
		if (module == null || module.trim().length() == 0){
			module = object.getClass().getSimpleName();
		}
		getDBusConnection().requestBusName("com.trisvc.modules." + module + "." + instance);
		getDBusConnection().exportObject("/com/trisvc/modules/" + module + "/" + instance,
				object);
	}
	
	protected void exportObject(BaseObject object, String module) throws DBusException {
		exportObject(object, module ,null);
	}	

	protected void exportObject(BaseObject object) throws DBusException {
		exportObject(object, null,null);
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
		getDBusConnection().unExportObject("/com/trisvc/modules/" + c.getSimpleName() + "/" + instance);
	}

	protected static RemoteObjectWrapper getRemoteObject(String moduleName) {
		return getRemoteObject(moduleName, null);
	}

	protected static RemoteObjectWrapper getRemoteObject(String moduleName, String instance) {
		if (instance == null || instance.trim().length() == 0) {
			instance = "default";
		}

		try {
			BaseObject o;
			o = (BaseObject) getDBusConnection().getRemoteObject(
					"com.trisvc.modules." + moduleName + "." + instance,
					"/com/trisvc/modules/" + moduleName + "/" + instance, BaseObject.class);
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
			if (!registerModule(getRegisterMessage())){
				return;
			}
			execute();
		} catch (DBusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	abstract protected void execute();

	abstract protected void close();
	
	abstract protected RegisterMessage getRegisterMessage();
	
	protected boolean registerModule (RegisterMessage rm){
		if (rm == null)
			return true;
		
		RemoteObjectWrapper brain = getRemoteObject("Brain");
		Message m = new Message();
		m.setBody(rm);
		Response response = brain.send(m);	
		if (!response.isSuccess()){
			logger.error("Could not register module");
			return false;
		}
		return true;
	}

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
