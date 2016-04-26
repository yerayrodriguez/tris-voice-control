package com.trisvc.modules.brain.thread;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trisvc.core.datatypes.PhraseTypeHandler;
import com.trisvc.core.messages.types.register.structures.PhraseDefinition;
import com.trisvc.core.messages.types.register.structures.PhraseDefinition;

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
	
	private Map<String,PhraseTypeHandler> dataTypes = new HashMap<String,PhraseTypeHandler>();
	
	//TODO
	//Aditional dataType must be associated with module
	//this way, a common dataType could have differente patterns for different modules
	public void addDataTypeDefinition(PhraseDefinition d){
		if (dataTypes.containsKey(d.getName())){
			dataTypes.get(d.getName()).addPatternTemplateDefinitionList(d.getPatterns());
		}else{
			dataTypes.put(d.getName(), new PhraseTypeHandler(d));
		}
	}
	
	public void addDataTypeDefinitions(List<PhraseDefinition> l){
	
		for (PhraseDefinition d: l){
			addDataTypeDefinition(d);
		}
		
	}		
	
	public void memoryDump(){
		dumpDataTypes();
	}
	
	public void dumpDataTypes(){
		for (PhraseTypeHandler d: dataTypes.values()){
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