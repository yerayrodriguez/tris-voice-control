package com.trisvc.modules.stt.google;

import org.freedesktop.dbus.exceptions.DBusException;

import com.trisvc.core.Signal;
import com.trisvc.core.launcher.thread.BaseThread;
import com.trisvc.core.messages.types.register.RegisterMessage;

public class STTGoogle extends BaseThread {

	private static final long WAIT_CLOSE_MS = 1000;

	@Override
	public void execute() {
		STTGoogleRecognizer recognizer = new STTGoogleRecognizer();
		while (true){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sendSTTSignal(recognizer.recognize());		
		}		
	}
	
	private void sendSTTSignal(String text) {
		try {
			Signal.STTSignal s = new Signal.STTSignal("/com/trisvc/modules/stt",text);
			getDBusConnection().sendSignal(s);
		} catch (DBusException e) {
			e.printStackTrace();
		}
	}	

	@Override
	protected void close() {

	}

	
	@Override
	protected RegisterMessage genRegisterMessage() {
		return null;
	}	

}
