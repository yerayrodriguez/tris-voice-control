package com.trisvc.modules.brain.parser;

import java.util.ArrayList;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trisvc.core.datatypes.DataTypeHandler;
import com.trisvc.core.datatypes.DataTypeResult;
import com.trisvc.modules.brain.store.DataTypeStore;

public class ParserText {
	
	private static Logger logger = LogManager.getLogger(ParserText.class.getName());

	public ParserText() {
		// TODO Auto-generated constructor stub
		
	}
	
	//TODO
	//I don't know if this implementation could be a problem
	//now the order or datatypes in the parsed text and the list are not the same
	public static void process (String text){
		
		text = text.trim().replaceAll("\\s+", " ");
		ParserResult pr = new ParserResult();
		pr.setOriginalText(text);
		pr.setDataTypesFound(new ArrayList<DataTypeFound>());
		
		DataTypeResult result = null;
		logger.debug("Parcing text: "+text);
		
		for (Entry<Integer, DataTypeHandler> entry : DataTypeStore.getInstance().getDataTypes().entrySet()) {
			DataTypeHandler d = entry.getValue();			
			
			result = d.eval(text);
			
			while (result != null){
				logger.trace("Parcing text. Actual state: ");
				logger.trace(result);				
				text = text.replace(result.getDetected(), "["+d.getType()+"]");
				pr.getDataTypesFound().add(new DataTypeFound(d.getType(),result.getReplaced()));
				
				logger.trace("Parcing text: "+text);
				result = d.eval(text);
			}
		}	
		
		pr.setParsedText(text);
		
		logger.debug("Parcing text result: "+pr);
		
	}
	
	public static void main(String[] args) {
		//process("enciden la luz la habitación pequeña y hola en 5 horas al despacho  feo");
		process("avísame a las 5 de la tarde para la lista de regalos para eli");
		process("avísame a las 5 de la mañana ");
	}

}
