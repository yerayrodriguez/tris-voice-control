package com.trisvc.modules.brain.store;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trisvc.core.datatypes.DataTypeHandler;
import com.trisvc.core.messages.types.register.structures.DataTypeDefinition;
import com.trisvc.core.messages.types.register.structures.DataTypeDefinitionList;
import com.trisvc.core.messages.types.register.structures.ModuleCommand;

public class CommandsStore {

	private static Logger logger = LogManager.getLogger(CommandsStore.class.getName());
	private volatile static CommandsStore instance = null;

	protected CommandsStore() {
		loadTest();
	}

	// Lazy Initialization (If required then only)
	public static CommandsStore getInstance() {
		if (instance == null) {
			// Thread Safe. Might be costly operation in some case
			synchronized (CommandsStore.class) {
				if (instance == null) {
					initializeConfiguration();
				}
			}
		}
		return instance;
	}

	private static void initializeConfiguration() {
		logger.debug("Creating Command store");
		instance = new CommandsStore();
	}
	
	//TODO
	//Think priority of commands on different modules
	private ArrayList<CommandElement> commands = new ArrayList<CommandElement>();
	
	//TODO
	//Aditional dataType must be associated with module
	//this way, a common dataType could have differente patterns for different modules
	//TODO
	//Instance
	public void addModulecommand(String module, String instance, ModuleCommand m){
		for (String s: m.getCommandPattern()){
			CommandElement c = new CommandElement(module,instance,m.getName(),s);
			commands.add(c);
		}
	}
		
	//TODO
	//Remove	
	public void memoryDump(){
		dumpModuleCommands();
	}
	
	//TODO
	//Remove	
	public void dumpModuleCommands(){
		for (CommandElement m: commands){
			m.dump(); 
		}
	}
	
	//TODO
	//Remove
	private void loadTest(){
		
		List<String> l = null;
		ModuleCommand m = null;
		
		l = new ArrayList<String>();
		l.add("crea/añade *lista (nueva)");
		l.add("Crea/añade *lista (nueva/de) [STRING]");				
		m = new ModuleCommand("create_list",l);
		addModulecommand("LIST","unique",m);
		
		l = new ArrayList<String>();
		l.add("*que contiene/tiene/elementos *[LIST_LIST]");
		l.add("comando mostrar contenido [LIST_LIST] [YES_NO] *");				
		m = new ModuleCommand("list_elements",l);		
		addModulecommand("LIST","unique",m);
		
		l = new ArrayList<String>();
		l.add("Añade *(lista) *[LIST_LIST] [STRING]");
		l.add("Añade [STRING] a la lista [LIST_LIST] *");				
		m = new ModuleCommand("list_elements",l);
		addModulecommand("LIST","unique",m);
	}
	
	public static void main(String[] args) {
		CommandsStore.getInstance().memoryDump();
	}
	
}