package com.trisvc.core.messages.types.register;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.trisvc.core.messages.MessageBody;
import com.trisvc.core.messages.types.register.structures.DataTypeDefinitionList;
import com.trisvc.core.messages.types.register.structures.ModuleCommand;

@XmlRootElement
@XmlType(propOrder = { "moduleName", "dataTypes", "commands" })
public class RegisterMessage extends MessageBody {

	private String moduleName;
	//TODO
	//instance
	private DataTypeDefinitionList dataTypes;
	private List<ModuleCommand> commands;

	public RegisterMessage() {
		super();
	}

	public RegisterMessage(String moduleName, DataTypeDefinitionList dataTypes, List<ModuleCommand> commands) {
		super();
		this.moduleName = moduleName;
		this.dataTypes = dataTypes;
		this.commands = commands;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public DataTypeDefinitionList getDataTypes() {
		return dataTypes;
	}

	public void setDataTypes(DataTypeDefinitionList dataTypes) {
		this.dataTypes = dataTypes;
	}

	@XmlElementWrapper(name = "acceptedCommands")
	@XmlElement(name = "command")
	public List<ModuleCommand> getCommands() {
		return commands;
	}

	public void setCommands(List<ModuleCommand> commands) {
		this.commands = commands;
	}

}
