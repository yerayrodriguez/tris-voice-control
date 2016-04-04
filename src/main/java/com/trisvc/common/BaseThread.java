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
	
	protected DBusConnection getDBusConnection() throws DBusException{
		return DBusConnection.getConnection(DBusConnection.SESSION);
	}
	
	protected void exportObject(TObject object) throws DBusException{
		String objectName = object.getClass().getName();
		getDBusConnection().requestBusName(objectName+".object.trisvc.com");
		getDBusConnection().exportObject("/com/trisvc/messages/"+objectName, object);			
	}
	
	protected void unExportObject(Class c) throws DBusException{
		getDBusConnection().unExportObject("/com/trisvc/messages/"+c.getName());
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
				try {
					getDBusConnection().disconnect();
				} catch (DBusException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

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
