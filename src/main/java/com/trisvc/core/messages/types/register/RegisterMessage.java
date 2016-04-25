package com.trisvc.core.messages.types.register;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.trisvc.core.messages.MessageBody;
import com.trisvc.core.messages.types.register.structures.PhraseDefinition;
import com.trisvc.core.messages.types.register.structures.ModuleCommand;

@XmlRootElement
public class RegisterMessage extends MessageBody{

	public String moduleName;
	@XmlElementWrapper(name = "declaredDatatypes")
	@XmlElement(name="datatype")	
	public List<PhraseDefinition> datatypes;
	@XmlElementWrapper(name = "acceptedCommands")
	@XmlElement(name="command")
	public List<ModuleCommand> commands;
	
	public RegisterMessage() {
		super();
	}	

	public RegisterMessage(String moduleName, List<PhraseDefinition> datatypes, List<ModuleCommand> commands) {
		super();
		this.moduleName = moduleName;
		this.datatypes = datatypes;
		this.commands = commands;
	}

}
