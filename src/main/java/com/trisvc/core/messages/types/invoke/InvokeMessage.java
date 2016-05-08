package com.trisvc.core.messages.types.invoke;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.trisvc.core.messages.MessageBody;
import com.trisvc.modules.brain.parser.DataTypeValue;

@XmlRootElement
public class InvokeMessage extends MessageBody {

	private String command;
	private List<DataTypeValue> parameters;

	public InvokeMessage() {
		super();
	}

	public InvokeMessage(String command, List<DataTypeValue> parameters) {
		super();
		this.command = command;
		this.parameters = parameters;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public List<DataTypeValue> getParameters() {
		return parameters;
	}

	public void setParameters(List<DataTypeValue> parameters) {
		this.parameters = parameters;
	}
	
	


}
