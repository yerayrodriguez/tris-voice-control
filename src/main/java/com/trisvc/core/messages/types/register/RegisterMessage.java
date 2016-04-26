package com.trisvc.core.messages.types.register;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.trisvc.core.messages.MessageBody;
import com.trisvc.core.messages.types.register.structures.ModuleCommand;
import com.trisvc.core.messages.types.register.structures.PhraseDefinition;

@XmlRootElement
@XmlType(propOrder={"moduleName", "phraseDefinitions", "commands"})
public class RegisterMessage extends MessageBody {

	private String moduleName;
	private List<PhraseDefinition> phraseDefinitions;
	private List<ModuleCommand> commands;

	public RegisterMessage() {
		super();
	}

	public RegisterMessage(String moduleName, List<PhraseDefinition> phraseDefinitions, List<ModuleCommand> commands) {
		super();
		this.moduleName = moduleName;
		this.phraseDefinitions = phraseDefinitions;
		this.commands = commands; 
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	@XmlElementWrapper(name = "phraseDefinitions")
	@XmlElement(name = "definition")
	public List<PhraseDefinition> getPhraseDefinitions() {
		return phraseDefinitions;
	}

	public void setPhraseDefinitions(List<PhraseDefinition> phraseDefinitions) {
		this.phraseDefinitions = phraseDefinitions;
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
