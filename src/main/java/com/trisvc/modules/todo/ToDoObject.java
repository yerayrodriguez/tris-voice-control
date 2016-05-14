package com.trisvc.modules.todo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trisvc.core.messages.Message;
import com.trisvc.core.messages.Response;
import com.trisvc.core.messages.types.invoke.InvokeMessage;
import com.trisvc.core.messages.types.invoke.InvokeResponse;
import com.trisvc.core.messages.types.register.RegisterMessage;
import com.trisvc.core.messages.types.register.structures.DTPatternDefinition;
import com.trisvc.core.messages.types.register.structures.DataTypeDefinition;
import com.trisvc.core.messages.types.register.structures.DataTypeDefinitionList;
import com.trisvc.core.messages.util.MessageUtil;
import com.trisvc.modules.BaseObject;
import com.trisvc.modules.BaseObjectWrapper;
import com.trisvc.modules.brain.parser.DTContext;
import com.trisvc.modules.brain.parser.DataTypeValue;

public class ToDoObject extends BaseObjectWrapper implements BaseObject {

	private Logger logger = LogManager.getLogger(this.getClass().getName());

	private Map<String, List<String>> memory = new HashMap<String, List<String>>();
	private static final String TODO_LIST = "TODO_LIST";

	public Response send(Message m) {
		InvokeMessage im = (InvokeMessage) m.getBody();

		Response r = MessageUtil.getResponseFromMessage(m);
		InvokeResponse ir = new InvokeResponse();
		r.setBody(ir);

		switch (im.getCommand()) {
		case "list_lists":
			listLists(im, ir, m, r);
			break;
		case "create_list":
			createList(im, ir, m, r);
			break;
		case "open_list":
			openList(im, ir, m, r);
			break;
		case "list_elements":
			listElements(im, ir, m, r);
			break;
		case "add_element_c":
			addElementC(im, ir, m, r);
			break;
		case "add_element":
			addElement(im, ir, m, r);
			break;
		case "remove_list":
			removeList(im, ir, m, r);
			break;
		case "remove_element":
			removeElement(im, ir, m, r);
			break;
		default:
			ir.setMessage("Comando desconocido");
			r.setSuccess(false);
			r.setInformation("Unknown command: " + im.getCommand());
			logger.error(r.getInformation());
			break;
		}

		return r;
	}

	public void createList(InvokeMessage im, InvokeResponse ir, Message m, Response r) {
		String listName = getFreeText(im);
		if (listName == null) {
			ir.setMessage("Diga el nombre que quiere asignar a la lista");
			ir.setInmmediateContext("crea la lista de [INPUT]");
			r.setSuccess(true);
			return;
		}

		if (memory.containsKey(listName)) {
			ir.setMessage("La lista " + listName + " ya existe.");
			DTContext normalContext = new DTContext("ToDo", "default");
			normalContext.addContextElement(new DataTypeValue(TODO_LIST, listName));
			ir.setNormalContext(normalContext);
			r.setSuccess(true);
			return;
		}

		memory.put(listName, new ArrayList<String>());

		// Update message
		updateModule(genUpdateMessage());

		// TODO
		// Instance
		DTContext normalContext = new DTContext("ToDo", "default");
		normalContext.addContextElement(new DataTypeValue(TODO_LIST, listName));
		ir.setNormalContext(normalContext);
		ir.setMessage("Se ha creado la lista " + listName);
		r.setSuccess(true);

	}

	public void listLists(InvokeMessage im, InvokeResponse ir, Message m, Response r) {
		// TODO
		// Internationalization
		// use markuptags. It must be independent of TTS motor
		// example for picotts
		// http://dafpolo.free.fr/telecharger/svoxpico/SVOX_Pico_Manual.pdf

		if (memory.size() == 0) {
			ir.setMessage("No hay listas de tareas creadas");
			r.setSuccess(true);
			return;
		}

		String answer = "Las listas de tareas disponibles son: ";

		Integer count = 0;
		for (String k : memory.keySet()) {
			count++;
			if (count > 1 && count == memory.size()) {
				answer += "y ";
			}
			answer += k + ". ";
		}
		ir.setMessage(answer);
		r.setSuccess(true);
	}

	public void addElementC(InvokeMessage im, InvokeResponse ir, Message m, Response r) {

		String element = getFreeText(im);
		if (element == null) {
			logger.error("Element is null");
			r.setSuccess(false);
			return;
		}

		String listName = getListName(im);

		if (listName == null) {
			ir.setMessage("No ha especificado la lista.");
			r.setSuccess(true);
			return;
		}

		List<String> list = memory.get(listName);
		if (list == null) {
			logger.error("List is null");
			r.setSuccess(false);
			return;
		}

		list.add(element);
		// TODO
		// Instance
		DTContext normalContext = new DTContext("ToDo", "default");
		normalContext.addContextElement(new DataTypeValue(TODO_LIST, listName));
		ir.setNormalContext(normalContext);
		ir.setInmmediateContext("añade a " + listName + " [INPUT]");
		ir.setMessage(element);

		r.setSuccess(true);

	}

	public void addElement(InvokeMessage im, InvokeResponse ir, Message m, Response r) {

		String element = getFreeText(im);
		if (element == null) {
			logger.error("Element is null");
			r.setSuccess(false);
			return;
		}

		String listName = getListName(im);

		if (listName == null) {
			ir.setMessage("¿a qué lista quieres añadirlo?");
			ir.setInmmediateContext("command addElement " + element + " [INPUT]");
			r.setSuccess(true);
			return;
		}

		List<String> list = memory.get(listName);
		if (list == null) {
			logger.error("List is null");
			r.setSuccess(false);
			return;
		}

		list.add(element);
		// TODO
		// Instance
		DTContext normalContext = new DTContext("ToDo", "default");
		normalContext.addContextElement(new DataTypeValue(TODO_LIST, listName));
		ir.setNormalContext(normalContext);
		ir.setInmmediateContext("añade a " + listName + " [INPUT]");
		ir.setMessage(element);

		r.setSuccess(true);

	}

	public void listElements(InvokeMessage im, InvokeResponse ir, Message m, Response r) {
		String listName = getListName(im);
		if (listName == null) {
			logger.error("List name not found");
			r.setSuccess(false);
			return;
		}
		List<String> list = memory.get(listName);
		if (list == null) {
			logger.error("List is null");
			r.setSuccess(false);
			return;
		}
		if (list.isEmpty()) {
			ir.setMessage("La lista está vacía");
		} else {

			String answer = "El contenido de la lista " + listName + " es: ";
			for (int i = 0; i < list.size(); i++) {
				String s = list.get(i);
				if (i > 0 && i == list.size() - 1) {
					answer += "y ";
				}
				answer += s + ". ";
			}
			ir.setMessage(answer);
		}

		// TODO
		// Instance
		DTContext normalContext = new DTContext("ToDo", "default");
		normalContext.addContextElement(new DataTypeValue(TODO_LIST, listName));
		ir.setNormalContext(normalContext);

		r.setSuccess(true);
	}

	public void removeElement(InvokeMessage im, InvokeResponse ir, Message m, Response r) {

	}

	public void openList(InvokeMessage im, InvokeResponse ir, Message m, Response r) {

	}

	public void removeList(InvokeMessage im, InvokeResponse ir, Message m, Response r) {

	}

	private String getElement(InvokeMessage im, String type) {
		String element = null;

		for (DataTypeValue dt : im.getParameters()) {
			if (dt.getDataType().equals(type)) {
				element = dt.getValue();
				if (element != null) {
					element = element.trim().toLowerCase();
				}
				if (element.length() == 0) {
					element = null;
				}
				break;
			}
		}
		return element;
	}

	private String getListName(InvokeMessage im) {
		return getElement(im, TODO_LIST);
	}

	private String getFreeText(InvokeMessage im) {
		return getElement(im, "STRING");
	}

	protected RegisterMessage genUpdateMessage() {
		final String TODO = "TODO_LIST";
		final Integer WEIGHT = 9300;

		List<DTPatternDefinition> definitions = new ArrayList<DTPatternDefinition>();
		for (String k : memory.keySet()) {
			definitions.add(new DTPatternDefinition(k, k));
		}

		DataTypeDefinition channelList = new DataTypeDefinition(TODO, WEIGHT, definitions);

		List<DataTypeDefinition> dataTypeList = new ArrayList<DataTypeDefinition>();
		dataTypeList.add(channelList);

		DataTypeDefinitionList dataTypes = new DataTypeDefinitionList(dataTypeList);

		RegisterMessage message = new RegisterMessage();
		message.setModuleName("ToDo");
		// message.setCommands(commandList);
		message.setDataTypes(dataTypes);

		return message;
	}
}
