package com.trisvc.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.DBusSigHandler;
import org.freedesktop.dbus.exceptions.DBusException;

public abstract class BaseThread implements Runnable{
	
	protected static final String THREAD_DEFAULT_ID = "default";
	private Logger logger;
	
	protected String moduleId;
	
	private static DBusConnection connection = initializeDBusConnection();

	
	public void setModuleId(String id){
		//TODO
		//Change for constructor
		//Look at ThreadFactory
		if (id==null)
			id = THREAD_DEFAULT_ID;
		this.moduleId = id;
		//TODO
		//Think how to make a more elegant solution
		logger = LogManager.getLogger(this.getClass().getName()+":"+this.moduleId);
	}	
	
	private static DBusConnection initializeDBusConnection() {
		try {
			return DBusConnection.getConnection(DBusConnection.SESSION);
		} catch (DBusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	protected static DBusConnection getDBusConnection(){
		return connection;
	}
	
	protected void exportObject(BaseObject object) throws DBusException{
		getDBusConnection().requestBusName("com.trisvc.object."+object.getClass().getSimpleName());
		getDBusConnection().exportObject("/com/trisvc/object/"+object.getClass().getSimpleName(), object);			
	}
	
	@SuppressWarnings("rawtypes")
	protected void unExportObject(Class c) throws DBusException{
		getDBusConnection().unExportObject("/com/trisvc/object/"+c.getSimpleName());
	}
	
	protected BaseObject getRemoteObject(Class c){
		BaseObject o;
		try {
			o = (BaseObject) getDBusConnection().getRemoteObject("com.trisvc.object."+c.getSimpleName(),
					"/com/trisvc/object/"+c.getSimpleName(), BaseObject.class);
		} catch (DBusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return o;
	}
	
	@Override
	public void run(){
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
	
	public String getModuleId(){
		return moduleId;
	}
	
	protected Logger getLogger(){
		return logger;
	}

}
