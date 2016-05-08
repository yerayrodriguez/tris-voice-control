package com.trisvc.core.messages.types.parser;

import javax.xml.bind.annotation.XmlRootElement;

import com.trisvc.core.messages.MessageBody;
import com.trisvc.modules.brain.parser.CommandResult;

@XmlRootElement
public class ParserResponse extends MessageBody {

	// TODO
	// think a way to reuse this object and avoid use ParserResult
	private CommandResult commandResult;

	public ParserResponse() {
		super();
	}

	public ParserResponse(CommandResult commandResult) {
		super();
		this.commandResult = commandResult;
	}

	public CommandResult getCommandResult() {
		return commandResult;
	}

	public void setCommandResult(CommandResult commandResult) {
		this.commandResult = commandResult;
	}

}
