package com.trisvc.modules.echo;

import java.util.ArrayList;
import java.util.List;

import org.freedesktop.dbus.exceptions.DBusException;

import com.trisvc.core.launcher.thread.BaseThread;
import com.trisvc.core.launcher.thread.ThreadUtil;
import com.trisvc.core.messages.types.register.RegisterMessage;
import com.trisvc.core.messages.types.register.structures.DTPatternDefinition;
import com.trisvc.core.messages.types.register.structures.DataTypeDefinition;
import com.trisvc.core.messages.types.register.structures.DataTypeDefinitionList;
import com.trisvc.core.messages.types.register.structures.ModuleCommand;
import com.trisvc.modules.echo.objects.EchoObject;

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

	@Override
	protected RegisterMessage getRegisterMessage() {
		
		RegisterMessage message = new RegisterMessage();
		message.setModuleName("Echo");

		List<ModuleCommand> commandList = new ArrayList<ModuleCommand>();

		List<String> patternList = new ArrayList<String>();
		patternList.add("repite (.*)?");
		List<String> required = new ArrayList<String>();
		ModuleCommand c1 = new ModuleCommand("echo", patternList,required);
		commandList.add(c1);

		/*DataTypeDefinitionList dataTypes = new DataTypeDefinitionList();
		
		List<DataTypeDefinition> listDataTypes = new ArrayList<DataTypeDefinition>();

		DataTypeDefinition tipo1 = new DataTypeDefinition();
		tipo1.setType("Tipo1");
		tipo1.setWeight(200);

		List<DTPatternDefinition> definition1 = new ArrayList<DTPatternDefinition>();
		DTPatternDefinition t1p1 = new DTPatternDefinition("in t1p1", "out t1p1");
		definition1.add(t1p1);
		DTPatternDefinition t1p2 = new DTPatternDefinition("in t1p2", "out t1p2");
		definition1.add(t1p2);

		tipo1.setDefinitions(definition1);
		listDataTypes.add(tipo1);
		
		dataTypes.setDataType(listDataTypes);
		message.setDataTypes(dataTypes);*/
		
		message.setCommands(commandList);
		
		
		return message;
		
	}

}
