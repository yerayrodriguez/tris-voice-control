package com.trisvc.modules.brain.store;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trisvc.core.datatypes.DataTypeHandler;
import com.trisvc.core.messages.types.register.structures.DataTypeDefinition;
import com.trisvc.core.messages.types.register.structures.DataTypeDefinitionList;

public class DataTypeStore {

	private static Logger logger = LogManager.getLogger(DataTypeStore.class.getName());
	private volatile static DataTypeStore instance = null;

	protected DataTypeStore() {
		loadDataTypes();
	}

	// Lazy Initialization (If required then only)
	public static DataTypeStore getInstance() {
		if (instance == null) {
			// Thread Safe. Might be costly operation in some case
			synchronized (DataTypeStore.class) {
				if (instance == null) {
					initializeConfiguration();
				}
			}
		}
		return instance;
	}

	private static void initializeConfiguration() {
		logger.debug("Creating DataType store");
		instance = new DataTypeStore();
	}

	private Comparator<Integer> comparator = new Comparator<Integer>() {
		@Override
		public int compare(Integer o1, Integer o2) {
			return o2.compareTo(o1);
		}
	};

	//TODO
	//I think is not necessary to use a MAP
	//It is possible to create a comparator to a List
	//Moreover, this way may produce collition
	private SortedMap<Integer, DataTypeHandler> dataTypes = new TreeMap<Integer, DataTypeHandler>(comparator);

	public SortedMap<Integer, DataTypeHandler> getDataTypes() {
		return dataTypes;
	}

	// TODO
	// Aditional dataType must be associated with module
	// this way, a common dataType could have differente patterns for different
	// modules
	public void addDataTypeDefinition(DataTypeDefinition d) {

		for (Entry<Integer, DataTypeHandler> entry : dataTypes.entrySet()) {
			if (entry.getValue().getType().equals(d.getType())) {
				entry.getValue().addDTPatternDefinitionList(d.getDefinitions());
				return;
			}
		}
		dataTypes.put(d.getWeight(), new DataTypeHandler(d));
	}

	public void addDataTypeDefinitions(List<DataTypeDefinition> l) {

		for (DataTypeDefinition d : l) {
			addDataTypeDefinition(d);
		}

	}

	public void addDataTypeDefinitionList(DataTypeDefinitionList l) {

		addDataTypeDefinitions(l.getDataType());

	}

	//TODO
	//Remove
	public void memoryDump() {
		dumpDataTypes();
	}

	//TODO
	//Remove
	public void dumpDataTypes() {
		for (DataTypeHandler d : dataTypes.values()) {
			d.dump();
		}
	}

	private void loadDataTypes() {
		logger.debug("Loading default datatypes");
		File file = new File("./config/datatypes.xml");
		DataTypeDefinitionList d = DataTypeDefinitionList.unmarshal(file);
		addDataTypeDefinitionList(d);
	}

	public static void main(String[] args) {
		DataTypeStore.getInstance().memoryDump();
	}

}