package com.trisvc.modules.brain.object;

import com.trisvc.core.messages.Message;
import com.trisvc.core.messages.Response;
import com.trisvc.core.messages.types.register.RegisterMessage;
import com.trisvc.core.messages.types.register.RegisterResponse;
import com.trisvc.core.messages.util.MessageUtil;
import com.trisvc.modules.BaseObject;
import com.trisvc.modules.BaseObjectWrapper;
import com.trisvc.modules.brain.store.DataTypeStore;

public class BrainObject extends BaseObjectWrapper implements BaseObject {

	public Response send(Message m) {
		switch (m.getType()) {
		case "ModuleRegister":
			return moduleRegister(m);
		case "MemoryDump":
			return memoryDump();
		default:
			return null;
		}
	}

	private Response moduleRegister(Message m) {
		RegisterMessage rm = (RegisterMessage) m.getBody();
		
		DataTypeStore.getInstance().addDataTypeDefinitionList(rm.getDataTypes());
		CommandStore.getInstance().addCommmands();
		
		Response r = MessageUtil.getResponseFromMessage(m);
		RegisterResponse tr = new RegisterResponse();
		r.setBody(tr);

	}

	private Response memoryDump() {
		DataTypeStore.getInstance().memoryDump();
		CommandStore.getInstance().memoryDump();
		return null;
	}

}
