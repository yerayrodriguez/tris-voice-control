package com.trisvc.modules.console.thread;

import java.awt.TrayIcon.MessageType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.freedesktop.dbus.exceptions.DBusException;

import com.trisvc.core.BaseObject;
import com.trisvc.core.Signal;
import com.trisvc.core.launcher.thread.BaseThread;
import com.trisvc.core.messages.Message;
import com.trisvc.core.messages.types.tts.TTSMessage;
import com.trisvc.modules.tts.pico.object.TTS;

public class Console extends BaseThread {
	
	private volatile boolean stop = false;
	


	@Override
	public void execute() {
		try {

			//BaseObject textCommandMessage = getRemoteObject(Echo.class);
			//BaseObject memory = getRemoteObject(Memory.class);
			BaseObject tts = getRemoteObject(TTS.class);

			String line;
			System.out.print("Comando: ");
			while (!stop && (line = readLine()) != null) {
				Message<TTSMessage> m = new Message<TTSMessage>();
				String returnValue = tts.send(type, message);

				System.out.println(returnValue);
	
				System.out.flush();

				if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
					sendHaltSignal();
					break;
				}
				if (line.equals("memoryDump")){
					memory.send(MessageType.MemoryDumpMessage.getType(), "");
				}
				System.out.print("Comando: ");
			}
			System.out.println("fuera");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void sendHaltSignal() {
		try {
			Signal.HaltSignal s = new Signal.HaltSignal("/com/trisvc/modules/console");
			getDBusConnection().sendSignal(s);
		} catch (DBusException e) {
			e.printStackTrace();
		}
	}

	private String readLine() throws IOException {
		if (System.console() != null) {
			return System.console().readLine();
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		return reader.readLine();
	}

	@Override
	protected void close() {}

}
