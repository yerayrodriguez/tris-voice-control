package com.trisvc.modules.console.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.freedesktop.dbus.exceptions.DBusException;

import com.trisvc.common.BaseObject;
import com.trisvc.common.BaseThread;
import com.trisvc.common.Signal;
import com.trisvc.common.messages.MessageType;
import com.trisvc.modules.brain.object.Memory;
import com.trisvc.modules.heart.object.Echo;

public class Console extends BaseThread {
	
	private volatile boolean stop = false;
	


	@Override
	public void execute() {
		try {

			BaseObject textCommandMessage = getRemoteObject(Echo.class);
			BaseObject memory = getRemoteObject(Memory.class);

			String line;
			System.out.print("Comando: ");
			while (!stop && (line = readLine()) != null) {
				String returnValue = textCommandMessage.send(MessageType.Echo.getType(),line);

				System.out.println(returnValue);
	
				System.out.flush();

				if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
					sendHaltSignal();
					break;
				}
				if (line.equals("memoryDump")){
					memory.send(MessageType.MemoryDump.getType(), "");
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