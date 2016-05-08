package com.trisvc.modules.todo.object;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trisvc.core.messages.Message;
import com.trisvc.core.messages.Response;
import com.trisvc.core.messages.types.invoke.InvokeMessage;
import com.trisvc.core.messages.types.invoke.InvokeResponse;
import com.trisvc.core.messages.util.MessageUtil;
import com.trisvc.modules.BaseObject;
import com.trisvc.modules.BaseObjectWrapper;

public class ToDoObject extends BaseObjectWrapper implements BaseObject {

	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	private Map<String, List<String>> memory = new HashMap<String, List<String>>();

	public Response send (Message m) {
		InvokeMessage im = (InvokeMessage)m.getBody();
		
		Response r = MessageUtil.getResponseFromMessage(m);
		InvokeResponse ir = new InvokeResponse();
		r.setBody(ir);		
		
		switch (im.getCommand()) {
		case "list_lists":
			listLists(im, ir, r);
			break;
		case "create_list":
			createList(im, ir, r);
			break;
		case "open_list":
			openList(im, ir, r);
			break;
		case "list_elements":
			listElements(im, ir, r);
			break;
		case "add_element":
			addElement(im, ir, r);
			break;
		case "remove_list":
			removeList(im, ir, r);
			break;
		case "remove_element":
			removeElement(im, ir, r);
			break;
		default:
			ir.setMessage("Comando desconocido");
			r.setSuccess(false);
			r.setInformation("Unknown command: "+im.getCommand());
			logger.error(r.getInformation());			
			break;
		}
		
		r.setSuccess(true);
		return r;		
	}

}
