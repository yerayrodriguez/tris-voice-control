package com.trisvc.modules.brain.parser;

import java.util.Map.Entry;

import com.trisvc.core.datatypes.DataTypeHandler;
import com.trisvc.core.datatypes.DataTypeResult;
import com.trisvc.modules.brain.store.DataTypeStore;

public class ParserText {

	public ParserText() {
		// TODO Auto-generated constructor stub
		
	}
	
	public static void process (String text){
		
		text = text.trim().replaceAll("\\s+", " ");
		
		for (Entry<Integer, DataTypeHandler> entry : DataTypeStore.getInstance().getDataTypes().entrySet()) {
			DataTypeHandler d = entry.getValue();
			
			DataTypeResult result = d.eval(text);
			
			if (result != null){
				System.out.println(result.getDetected()+" "+result.getReplaced());
				break;
			}
			
			reemplazar pero volver a chequear el mismo tipo por si acaso
		}		
		
	}
	
	public static void main(String[] args) {
		process("enciden la luz la habitación pequeña y hola");
		process("avísame a las 5 de la tarde");
	}

}
