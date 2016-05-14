package com.trisvc.modules.todo;

import java.util.ArrayList;
import java.util.List;

import org.freedesktop.dbus.exceptions.DBusException;
import org.mpris.MediaPlayer2.Struct1;

import com.trisvc.core.NumeralUtil;
import com.trisvc.core.launcher.thread.BaseThread;
import com.trisvc.core.launcher.thread.ThreadUtil;
import com.trisvc.core.messages.types.register.RegisterMessage;
import com.trisvc.core.messages.types.register.structures.DTPatternDefinition;
import com.trisvc.core.messages.types.register.structures.DataTypeDefinition;
import com.trisvc.core.messages.types.register.structures.DataTypeDefinitionList;
import com.trisvc.core.messages.types.register.structures.ModuleCommand;

public class ToDo extends BaseThread {	

	private static final long WAIT_CLOSE_MS = 1000;
	
	@Override
	public void execute() {
		try {
			exportObject(new ToDoObject(), this.getClass().getSimpleName());
		} catch (DBusException e) {
			e.printStackTrace();
		}

	}
		
	@Override
	protected void close() {
		try {
			unExportObject(ToDoObject.class);
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

	//TODO
	//this must be part of object, not thread
	@Override
	protected RegisterMessage genRegisterMessage() {
		final String TODO = "TODO_LIST";
		final Integer WEIGHT = 9300;
		
		List<String> patternList0 = new ArrayList<String>();
		patternList0.add("(?:crea)(?:.*)lista(?: nueva)?(?: de| para)?(.*)");
		List<String> required0 = new ArrayList<String>();
		ModuleCommand c0 = new ModuleCommand("create_list", patternList0, required0);		
			
		List<String> patternList1 = new ArrayList<String>();
		patternList1.add("(?:dime|que|qué|enumera|di|enumerame)(?:.*)listas");
		List<String> required1 = new ArrayList<String>();
		ModuleCommand c1 = new ModuleCommand("list_lists", patternList1, required1);
		
		List<String> patternList2 = new ArrayList<String>();
		patternList2.add("command addElement (.*)? (?:\\[TODO_LIST\\])?");
		List<String> required2 = new ArrayList<String>();
		ModuleCommand c2 = new ModuleCommand("add_element_c", patternList2, required2);
		
		List<String> patternList3 = new ArrayList<String>();
		patternList3.add("añade (?:.*)(?:\\[TODO_LIST\\]) (.*)");
		patternList3.add("añade (.*) (?:a|a la lista)(?: .*) (?:\\[TODO_LIST\\])");
		patternList3.add("añade (.*)");
		List<String> required3 = new ArrayList<String>();
		required3.add("TODO_LIST");
		ModuleCommand c3 = new ModuleCommand("add_element", patternList3, required3);
		
		List<String> patternList4 = new ArrayList<String>();
		patternList4.add("(?:dime|que|qué|enumera|di|enumerame)(?:.*) \\[TODO_LIST\\]");
		List<String> required4 = new ArrayList<String>();
		required4.add("TODO_LIST");
		ModuleCommand c4 = new ModuleCommand("list_elements", patternList4, required4);		

		List<ModuleCommand> commandList = new ArrayList<ModuleCommand>();
		commandList.add(c0);
		commandList.add(c1);
		commandList.add(c2);
		commandList.add(c3);
		commandList.add(c4);
				
		List<DTPatternDefinition> definitions = new ArrayList<DTPatternDefinition>();


		DataTypeDefinition channelList = new DataTypeDefinition(TODO,WEIGHT,definitions);
		
		List<DataTypeDefinition> dataTypeList = new ArrayList<DataTypeDefinition>();
		dataTypeList.add(channelList);
		
		DataTypeDefinitionList dataTypes = new DataTypeDefinitionList(dataTypeList);

		RegisterMessage message = new RegisterMessage();
		message.setModuleName("ToDo");
		message.setCommands(commandList);
		message.setDataTypes(dataTypes);		

		return message;		
	}

}
