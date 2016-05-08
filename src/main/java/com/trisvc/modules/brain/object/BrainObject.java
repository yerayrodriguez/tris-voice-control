package com.trisvc.modules.brain.object;

import java.util.ArrayList;
import java.util.Map.Entry;

import com.trisvc.core.NumeralUtil;
import com.trisvc.core.datatypes.DataTypeHandler;
import com.trisvc.core.datatypes.DataTypeResult;
import com.trisvc.core.messages.Message;
import com.trisvc.core.messages.Response;
import com.trisvc.core.messages.types.parser.ParserMessage;
import com.trisvc.core.messages.types.parser.ParserResponse;
import com.trisvc.core.messages.types.register.RegisterMessage;
import com.trisvc.core.messages.types.register.RegisterResponse;
import com.trisvc.core.messages.util.MessageUtil;
import com.trisvc.modules.BaseObject;
import com.trisvc.modules.BaseObjectWrapper;
import com.trisvc.modules.brain.parser.CommandHandler;
import com.trisvc.modules.brain.parser.CommandResult;
import com.trisvc.modules.brain.parser.DataTypeValue;
import com.trisvc.modules.brain.parser.ParserResult;
import com.trisvc.modules.brain.store.CommandsStore;
import com.trisvc.modules.brain.store.DataTypeStore;

public class BrainObject extends BaseObjectWrapper implements BaseObject {

	public Response send(Message m) {
		switch (m.getType()) {
		case "RegisterMessage":
			return moduleRegister(m);
		case "MemoryDumpMessage":
			return memoryDump(m);
		case "ParserMessage":
			return parser(m);
		default:
			return null;
		}
	}

	private Response moduleRegister(Message m) {
		RegisterMessage rm = (RegisterMessage) m.getBody();
		
		if (rm.getDataTypes() != null && rm.getDataTypes().getDataType() != null && 
				rm.getDataTypes().getDataType().size()>0){
			DataTypeStore.getInstance().addDataTypeDefinitionList(rm.getDataTypes());
		}
		//TODO
		//instance
		CommandsStore.getInstance().addModuleCommands(rm.getModuleName(),"default",rm.getCommands());
		
		Response r = MessageUtil.getResponseFromMessage(m);
		RegisterResponse tr = new RegisterResponse();
		r.setBody(tr);
		r.setSuccess(true);

		return r;
	}

	//TODO
	//Change dump methods to respond string
	private Response memoryDump(Message m) {
		DataTypeStore.getInstance().memoryDump();
		CommandsStore.getInstance().memoryDump();

		//TODO
		//create dump message
		/*Response r = MessageUtil.getResponseFromMessage(m);
		MemoryDumpResponse tr = new MemoryDumpResponse();
		r.setBody(tr);
		r.setSuccess(true);

		return r;*/
		
		return null;
	}
	
	private Response parser (Message m) {
		
		ParserMessage pm = (ParserMessage) m.getBody();
		ParserResult pr = new ParserResult();
		pr.setOriginalText(pm.getTextToParse());
		
		ArrayList<DataTypeValue> dataTypesFound = new ArrayList<DataTypeValue>();
		
		//Lowcase
		String textToParse = pm.getTextToParse().trim().toLowerCase();
		
		//Numeral
		textToParse = NumeralUtil.convert(textToParse);
		
		//DataTypes
		for(Entry<Integer, DataTypeHandler> entry : DataTypeStore.getInstance().getDataTypes().entrySet()) {
			DataTypeResult dtr = entry.getValue().eval(textToParse);
			if (dtr != null){
				//TODO
				//think other way
				textToParse = textToParse.replace(dtr.getDetected(), dtr.getReplaced());
				dataTypesFound.add(new DataTypeValue(entry.getValue().getType(),dtr.getReplaced()));
			}
		}		
		pr.setParsedText(textToParse);
		pr.setDataTypesFound(dataTypesFound);
		
		//Commands
		CommandResult cr = null;
		for (CommandHandler command: CommandsStore.getInstance().getCommands()){
			cr = command.eval(pm.getContext(), pr);
			if (cr != null)
				break;
		}
		
		Response r = MessageUtil.getResponseFromMessage(m);
		ParserResponse parserResponse = new ParserResponse();
		
		if (cr == null){
			r.setSuccess(false);
			r.setBody(parserResponse);
			r.setInformation("Command not found");
			return r;
		}

		
		parserResponse.setCommandResult(cr);
		r.setBody(parserResponse);
		r.setSuccess(true);
		
		return r;

	}

}
