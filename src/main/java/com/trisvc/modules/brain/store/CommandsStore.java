package com.trisvc.modules.brain.store;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trisvc.core.datatypes.DataTypeHandler;
import com.trisvc.core.messages.types.register.structures.DataTypeDefinition;
import com.trisvc.core.messages.types.register.structures.DataTypeDefinitionList;

public class CommandsStore {

	private static Logger logger = LogManager.getLogger(CommandsStore.class.getName());
	private volatile static CommandsStore instance = null;

	protected CommandsStore() {
		loadDataTypes();
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
		logger.debug("Creating DataType store");
		instance = new CommandsStore();
	}
	
	private Map<String,DataTypeHandler> dataTypes = new HashMap<String,DataTypeHandler>();
	
	//TODO
	//Aditional dataType must be associated with module
	//this way, a common dataType could have differente patterns for different modules
	public void addDataTypeDefinition(DataTypeDefinition d){
		if (dataTypes.containsKey(d.getType())){
			dataTypes.get(d.getType()).addDTPatternDefinitionList(d.getDefinitions());
		}else{
			dataTypes.put(d.getType(), new DataTypeHandler(d));
		}
	}
	
	public void addDataTypeDefinitions(List<DataTypeDefinition> l){
	
		for (DataTypeDefinition d: l){
			addDataTypeDefinition(d);
		}
		
	}
	
	public void addDataTypeDefinitionList(DataTypeDefinitionList l){
		
		addDataTypeDefinitions(l.getDataType());
		
	}	
	
	public void memoryDump(){
		dumpDataTypes();
	}
	
	public void dumpDataTypes(){
		for (DataTypeHandler d: dataTypes.values()){
			d.dump(); 
		}
	}
	
	private void loadDataTypes(){
		logger.debug("Loading default datatypes");
		File file = new File("./config/datatypes.xml");
		DataTypeDefinitionList d = DataTypeDefinitionList.unmarshal(file);
		addDataTypeDefinitionList(d);
	}
	
	public static void main(String[] args) {
		CommandsStore.getInstance().memoryDump();
	}
	
}