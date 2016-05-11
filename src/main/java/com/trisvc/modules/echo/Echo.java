package com.trisvc.modules.echo;

import java.util.ArrayList;
import java.util.List;

import org.freedesktop.dbus.exceptions.DBusException;

import com.trisvc.core.launcher.thread.BaseThread;
import com.trisvc.core.launcher.thread.ThreadUtil;
import com.trisvc.core.messages.types.register.RegisterMessage;
import com.trisvc.core.messages.types.register.structures.ModuleCommand;

public class Echo extends BaseThread {

	private static final long WAIT_CLOSE_MS = 1000;

	@Override
	public void execute() {
		try {
			exportObject(new EchoObject(), this.getClass().getSimpleName());
		} catch (DBusException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void close() {
		try {
			unExportObject(EchoObject.class);
			// TODO
			// Think a way to wait for others thread finish
			ThreadUtil.sleep(WAIT_CLOSE_MS);
			getLogger().info("Exit");
			System.exit(0);
		} catch (DBusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected RegisterMessage genRegisterMessage() {

		List<String> patternList = new ArrayList<String>();
		patternList.add("repite (.*)?");

		List<String> required = new ArrayList<String>();

		ModuleCommand c1 = new ModuleCommand("echo", patternList, required);

		List<ModuleCommand> commandList = new ArrayList<ModuleCommand>();
		commandList.add(c1);

		RegisterMessage message = new RegisterMessage();
		message.setModuleName("Echo");
		message.setCommands(commandList);

		return message;
	}

}
