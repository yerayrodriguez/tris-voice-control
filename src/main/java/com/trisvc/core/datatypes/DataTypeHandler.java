package com.trisvc.core.datatypes;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trisvc.core.messages.types.register.structures.DataTypeDefinition;
import com.trisvc.core.messages.types.register.structures.DTPatternDefinition;

import freemarker.template.TemplateException;

public class DataTypeHandler {
	
	private Logger logger; 

	private String type;
	private Integer weight;
	//TODO
	//Change for a hashmap <Pattern, Template>?
	private List<DTPattern> list = new ArrayList<DTPattern>();
	
	public DataTypeHandler(String type, Integer weight, List<DTPattern> list){
		this.type = type;
		this.list = list;
		this.weight = weight;
		logger = LogManager.getLogger(this.getClass().getName()+":"+type);
		logger.debug("Creating DataType "+type);
	}
	
	public DataTypeHandler(DataTypeDefinition d){ 
		
		List<DTPattern> l = new ArrayList<DTPattern>();
		
		for (DTPatternDefinition p: d.getDefinitions()){
			l.add(new DTPattern(p));
		}
		
		this.type = d.getType();
		this.list = l;
		this.weight = d.getWeight();
		logger = LogManager.getLogger(this.getClass().getName()+":"+type);
		logger.debug("Creating DataType "+type);
	}
	
	public void addDTPattern(DTPattern p){
		//TODO
		//Check if is repeated or implement as a HashMap
		//TODO
		//must be a relation between module and datatype patterns to be able 
		//to update all patterns
		//For instance, now there is not way to remove a pattern, think on todo list 
		//module, which need to remove list names.
		this.list.add(p);
	}
	
	public void addDTPatternDefinition(DTPatternDefinition p){
		addDTPattern(new DTPattern(p));
	}
	
	public void addDTPatternList(List<DTPattern> l){
		for (DTPattern p: l){
			addDTPattern(p);
		}
	}
	
	public void addDTPatternDefinitionList(List<DTPatternDefinition> l){
		for (DTPatternDefinition p: l){
			addDTPatternDefinition(p);
		}
	}
	
	public String getType(){
		return type;
	}
	
	public Integer getWeight(){
		return this.weight;
	}
	
	public boolean isValid(String text){
		return eval(text) != null;
	}	 

	public DataTypeResult eval(String text){
		logger.debug("Evaluating '"+text+"'");

		for (DTPattern p: list){
			DataTypeResult r = evalPattern(p,text);
			if (r != null){
				logger.debug("Evaluating '"+text+"' returns '"+r.getReplaced()+"'");
				return r;
			}
		}
		logger.debug("Evaluating '"+text+"' returns -null-");
		return null;
	}
	
	private DataTypeResult evalPattern(DTPattern p, String text){
				
		logger.trace("Checking pattern '"+p.getPattern()+"'");

		Matcher matcher = p.getCompiledPattern().matcher(text);
	
		if (matcher.find()==false){
			logger.trace("Pattern'"+p.getPattern()+"': No matches found");
			return null;
		}
		
		logger.trace("Pattern'"+p.getPattern()+"': "+matcher.groupCount() +" matches found");
		
		Writer out = new StringWriter();
		Map<String,String> groups = new HashMap<String,String>();
		DataTypeResult dtr = new DataTypeResult();
		dtr.setDetected(matcher.group(0));

		for (int i=1; i<=matcher.groupCount(); i++){
			logger.trace("Group: "+matcher.group(i));
			groups.put("_"+(i-1), matcher.group(i));   			
		}
		
		try {
			p.getCompiledTemplate().process(groups, out);
			dtr.setReplaced(out.toString());
			return dtr;
		} catch (TemplateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//TODO
	//Remove	
	public void dump(){
		System.out.println("DataType: "+this.getType());
		for (DTPattern p: list){
			p.dump();
		}
	}
	
	public static void main(String[] args) {				

		LogManager.getLogger("entrada").debug("empezando");
		List<DTPattern> l = new ArrayList<DTPattern>();
		try{
			
			l.add(new DTPattern("(\\d{1,2}) hora(?:s)? y (\\d{1,2})\\s*(?:minutos)?",
					"${(_0?number)*60+(_1?number)}"));
						
			l.add(new DTPattern("(\\d{1,2}) hora(?:s)? y cuarto",
					"${(_0?number)*60+15}"));
			l.add(new DTPattern("(\\d{1,2}) hora(?:s)? y (\\d{1,2}) cuartos\\s*(?:de hora)?",
					"${(_0?number)*60+(_1?number)*15}"));		
			l.add(new DTPattern("(\\d{1,2}) hora(?:s)? y media",
					"${(_0?number)*60+30}"));
			l.add(new DTPattern("(\\d{1,2}) hora(?:s)?",
					"${(_0?number)*60}"));	
			
			

			
			l.add(new DTPattern("(\\d{1,2}) cuarto(?:s)? de hora",
					"${(_0?number)*15}"));	
			l.add(new DTPattern("media hora",
					"30"));
			l.add(new DTPattern("(\\d{1,3}) minuto(?:s)?",
					"${_0}"));
		
		


		}catch (Exception e){
			e.printStackTrace();
		}

		
		DataTypeHandler horas = new DataTypeHandler("TIME", 1,l);

		String prueba = "avísame dentro de 1 hora y 35 minutos";
		DataTypeResult dt = horas.eval(prueba);
		System.out.println(dt.getDetected()+" "+dt.getReplaced());
		//prueba = "avísame dentro de 1 hora y 35 ";
		//horas.eval(prueba);
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
