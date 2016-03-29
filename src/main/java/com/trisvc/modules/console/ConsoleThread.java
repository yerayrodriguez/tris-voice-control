package com.trisvc.modules.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;

import com.trisvc.common.BaseThread;
import com.trisvc.common.Message;
import com.trisvc.common.Signal;

public class ConsoleThread extends BaseThread {
	
	private volatile boolean stop = false;
	


	@Override
	public void execute() {
		try {

			DBusConnection conn = DBusConnection.getConnection(DBusConnection.SESSION);
			Message textCommandMessage = (Message) conn.getRemoteObject("textcommand.messages.trisvc.com",
					"/com/trisvc/messages/TextCommand", Message.class);

			String line;
			System.out.print("Comando: ");
			while (!stop && (line = readLine()) != null) {
				String returnValue = textCommandMessage.send(line);
				System.out.println(returnValue);
				System.out.flush();

				if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
					sendHaltSignal();
					break;
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
