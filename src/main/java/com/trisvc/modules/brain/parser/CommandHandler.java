package com.trisvc.modules.brain.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trisvc.core.datatypes.DataTypeHandler;
import com.trisvc.core.messages.types.register.structures.ModuleCommand;

public class CommandHandler {
	
	private Logger logger = null; 

	private String command;
	private String module;
	private String instance;

	private List<CommandPattern> patternList = new ArrayList<CommandPattern>();
	private List<String> dataTypesRequired = new ArrayList<String>();
	
	public CommandHandler(String module, String instance, ModuleCommand m){ 		
		super();
		this.module = module;
		this.instance = instance;
		this.command = m.getName();
		
		this.dataTypesRequired = m.getDataTypesRequired();
		
		List<CommandPattern> list = new ArrayList<CommandPattern>();
		
		for (String pattern: m.getCommandPattern()){
			list.add(new CommandPattern(pattern));
		}

		this.patternList = list;
		
		logger = LogManager.getLogger(this.getClass().getName()+":"+command);
		logger.debug("Creating Command handler: "+command);			
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

	public CommandResult eval(DTContext context, ParserResult parserResult){
		logger.debug("Evaluating command: "+module+"/"+instance+"/"+command);

		CommandResult d = null;
		
		for (CommandPattern p: this.getPatternList()){
			String r = evalPattern(p,parserResult.getParsedText());
			if (r == null){
				logger.debug("Evaluating '"+parserResult.getParsedText()+"' returns -null-");
				continue;
			}
			logger.debug("Evaluating '"+parserResult.getParsedText()+"' returns not null value: "+r);

			d = evalDataTypes(context, parserResult, r);
			if (d != null){
				return d;
			}			
		}
		
		return null;

	}
	
	private CommandResult evalDataTypes(DTContext context, ParserResult parserResult, String inputText){
		this.da
	}
	
	private String evalPattern(CommandPattern p, String text){
				
		logger.trace("Checking pattern '"+p.getPattern()+"'");

		Matcher matcher = p.getCompiledPattern().matcher(text);
	
		if (matcher.find()==false){
			logger.trace("Pattern'"+p.getPattern()+"': No matches found");
			return null;
		}
		
		logger.trace("Pattern'"+p.getPattern()+"': "+matcher.groupCount() +" matches found");
		
		String result = "";
		
		if (matcher.groupCount()>1){
			return matcher.group(1);   			
		}
		
		return ""; //Matchets but there is not group to return
	}
	
	//TODO
	//Remove	
	public void dump(){
		System.out.println("Command: "+this.getCommand());
		for (CommandPattern p: this.getPatternList()){
			p.dump();
		}
	}
	
	public static void main(String[] args) {				

		LogManager.getLogger("entrada").debug("empezando");
		List<CommandPattern> l = new ArrayList<CommandPattern>();
		try{
			
			l.add(new CommandPattern("(\\d{1,2}) hora(?:s)? y (\\d{1,2})\\s*(?:minutos)?",
					"${(_0?number)*60+(_1?number)}"));
						
			l.add(new CommandPattern("(\\d{1,2}) hora(?:s)? y cuarto",
					"${(_0?number)*60+15}"));
			l.add(new CommandPattern("(\\d{1,2}) hora(?:s)? y (\\d{1,2}) cuartos\\s*(?:de hora)?",
					"${(_0?number)*60+(_1?number)*15}"));		
			l.add(new CommandPattern("(\\d{1,2}) hora(?:s)? y media",
					"${(_0?number)*60+30}"));
			l.add(new CommandPattern("(\\d{1,2}) hora(?:s)?",
					"${(_0?number)*60}"));	
			
			

			
			l.add(new CommandPattern("(\\d{1,2}) cuarto(?:s)? de hora",
					"${(_0?number)*15}"));	
			l.add(new CommandPattern("media hora",
					"30"));
			l.add(new CommandPattern("(\\d{1,3}) minuto(?:s)?",
					"${_0}"));
		
		


		}catch (Exception e){
			e.printStackTrace();
		}

		
		DataTypeHandler horas = new DataTypeHandler("TIME", 1,l);

		String prueba = "avísame dentro de 1 hora y 35 minutos";
		horas.eval(prueba);
		prueba = "avísame dentro de 1 hora y 35 ";
		horas.eval(prueba);
	/*	   try {

			Writer out = new OutputStreamWriter(System.out);
			   Map root = new HashMap();
		        root.put("a", "1");	
		        root.put("b", "2");			
			t.process(root, out);


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}


}
