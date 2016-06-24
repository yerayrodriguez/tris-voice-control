package com.trisvc.modules.brain.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trisvc.core.NumeralUtil;
import com.trisvc.core.messages.types.register.structures.ModuleCommand;

public class CommandHandler {

	private static Logger logger = LogManager.getLogger(CommandHandler.class.getName());;

	private String command;
	private String module;
	private String instance;

	private List<CommandPattern> patternList = new ArrayList<CommandPattern>();
	private List<String> dataTypesRequired = new ArrayList<String>();

	public CommandHandler(String module, String instance, ModuleCommand m) {
		super();
		this.module = module;
		this.instance = instance;
		this.command = m.getName();

		this.dataTypesRequired = m.getDataTypesRequired();

		List<CommandPattern> list = new ArrayList<CommandPattern>();

		for (String pattern : m.getCommandPattern()) {
			list.add(new CommandPattern(pattern));
		}

		this.patternList = list;

		
		logger.debug("Creating Command handler: " + command);
	}

	public String getCommand() {
		return command;
	}

	public String getModule() {
		return module;
	}

	public String getInstance() {
		return instance;
	}

	public List<CommandPattern> getPatternList() {
		return patternList;
	}

	public List<String> getDataTypesRequired() {
		return dataTypesRequired;
	}

	public CommandResult eval(DTContext context, ParserResult parserResult) {
		logger.debug("Evaluating command: " + module + "/" + instance + "/" + command);

		CommandResult d = null;

		for (CommandPattern p : this.getPatternList()) {
			String r = evalPattern(p, parserResult.getParsedText());
			if (r == null) {
				logger.debug("Evaluating '" + parserResult.getParsedText() + "' returns -null-");
				continue;
			}
			logger.debug("Evaluating '" + parserResult.getParsedText() + "' returns not null value: " + r);

			d = evalDataTypes(context, parserResult, r);
			if (d != null) {
				return d;
			}
		}

		return null;

	}

	private CommandResult evalDataTypes(DTContext context, ParserResult parserResult, String inputText) {

		List<DataTypeValue> commandValues = new ArrayList<DataTypeValue>();
		for (String dataTypeRequired : this.getDataTypesRequired()) {
			commandValues.add(new DataTypeValue(dataTypeRequired, null));
		}

		fillDataTypeList(commandValues, parserResult.getDataTypesFound());

		if (context != null) {
			//TODO
			//context must admit fixed context from anywhere
			//remove "*"
			//maybe any context field must have associated the module and instance
			if (context.getModule().equals("*") || (context.getModule().equals(this.getModule()) && context.getInstance().equals(this.getInstance()))) {
				fillDataTypeList(commandValues, context.getElements());
			}
		}

		for (DataTypeValue resultDTV : commandValues) {
			if (resultDTV.getValue() == null) {
				return null;
			}
		}

		CommandResult d = new CommandResult();

		d.setCommand(this.getCommand());
		d.setInstance(this.getInstance());
		d.setModule(this.getModule());
		d.setValues(commandValues);
		
		inputText = correctInputText(inputText, parserResult);

		commandValues.add(new DataTypeValue("STRING", inputText));

		return d;

	}
	
	private String correctInputText(String text, ParserResult parserResult){
	
		for (DataTypeValue dtf: parserResult.getDataTypesFound()){
			text = text.replace("["+dtf.getDataType()+"]", dtf.getValue());
		}
		
		return text;
		
	}
	
	

	private void fillDataTypeList(List<DataTypeValue> result, List<DataTypeValue> source) {
		if (source == null)
			return;

		for (DataTypeValue sourceDTV : source) {
			String dataType = sourceDTV.getDataType();
			String value = sourceDTV.getValue();

			for (DataTypeValue resultDTV : result) {
				if (resultDTV.getValue() == null && resultDTV.getDataType().equals(dataType)) {
					resultDTV.setValue(value);
					break;
				}
			}
		}
	}

	private String evalPattern(CommandPattern p, String text) {

		logger.trace("Checking pattern '" + p.getPattern() + "'");

		Matcher matcher = p.getCompiledPattern().matcher(text);

		if (matcher.find() == false) {
			logger.trace("Pattern'" + p.getPattern() + "': No matches found");
			return null;
		}

		logger.trace("Pattern'" + p.getPattern() + "': " + matcher.groupCount() + " matches found");

		if (matcher.groupCount() > 0) {
			return matcher.group(1).trim();
		}

		return ""; // Matchets but there is not group to return
	}

	// TODO
	// Remove
	public void dump() {
		
		for (CommandPattern p : this.getPatternList()) {
			p.dump();
		}
	}

	public static void main(String[] args) {


		List<String> commandPattern = new ArrayList<String>();
		commandPattern.add("añade(?:.*lista)?(?:.*\\[LIST_LIST\\])? (.*)");
		List<String> dataTypesRequired = new ArrayList<String>();
		dataTypesRequired.add("LIST_LIST");
		ModuleCommand m = new ModuleCommand("create_list", commandPattern, dataTypesRequired);
		
		CommandHandler c = new CommandHandler("example","default",m);
		
		ParserResult p = ParserText.process(NumeralUtil.convert("añade  cuatro papas"));
		
		DTContext dtc = new DTContext("example","default");
		List<DataTypeValue> ldt = new ArrayList<DataTypeValue>();
		ldt.add(new DataTypeValue("LIST_LIST","compra"));
		dtc.addContextElement(new DataTypeValue("LIST_LIST","compra"));
		
		CommandResult r=c.eval(dtc, p);


	}

}
