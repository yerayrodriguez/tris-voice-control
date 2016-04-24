package com.trisvc.modules.brain.thread;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trisvc.common.datatypes.DataType;
import com.trisvc.common.messages.register.structures.DataTypeDefinition;
import com.trisvc.common.messages.register.structures.DataTypeDefinitionList;

public class MemoryStore {

	private static Logger logger = LogManager.getLogger(MemoryStore.class.getName());
	private volatile static MemoryStore instance = null;

	protected MemoryStore() {
		loadDataTypes();
	}

	// Lazy Initialization (If required then only)
	public static MemoryStore getInstance() {
		if (instance == null) {
			// Thread Safe. Might be costly operation in some case
			synchronized (MemoryStore.class) {
				if (instance == null) {
					initializeConfiguration();
				}
			}
		}
		return instance;
	}

	private static void initializeConfiguration() {
		logger.debug("Creating memory store");
		instance = new MemoryStore();
		

	}
	
	private Map<String,DataType> dataTypes = new HashMap<String,DataType>();
	
	//TODO
	//Aditional dataType must be associated with module
	//this way, a common dataType could have differente patterns for different modules
	public void addDataTypeDefinition(DataTypeDefinition d){
		if (dataTypes.containsKey(d.getDataTypeName())){
			dataTypes.get(d.getDataTypeName()).addPatternTemplateDefinitionList(d.getDefinition());
		}else{
			dataTypes.put(d.getDataTypeName(), new DataType(d));
		}
	}
	
	public void addDataTypeDefinitions(List<DataTypeDefinition> l){
	
		for (DataTypeDefinition d: l){
			addDataTypeDefinition(d);
		}
		
	}	
	
	public void addDataTypeDefinitions(DataTypeDefinitionList l){
		
		addDataTypeDefinitions(l.getDatatype());

	}	
	
	public void memoryDump(){
		dumpDataTypes();
	}
	
	public void dumpDataTypes(){
		for (DataType d: dataTypes.values()){
			d.dump();
		}
	}
	
	private void loadDataTypes(){
		logger.debug("Loading default datatypes");
		File file = new File("./config/datatypes.xml");
		DataTypeDefinitionList d = DataTypeDefinitionList.unmarshal(file);
		addDataTypeDefinitions(d);
	}
	
	public static void main(String[] args) {
		MemoryStore.getInstance().memoryDump();
	}
	
}