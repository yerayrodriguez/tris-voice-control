package com.trisvc.common.datatypes;

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

import com.trisvc.common.messages.moduleregister.DataTypeDefinition;
import com.trisvc.common.messages.moduleregister.PatternTemplateDefinition;

import freemarker.template.TemplateException;

public class DataType {
	
	private Logger logger; 

	private String name;
	//TODO
	//¿Change for a hashmap <Pattern, Template>?
	private List<PatternTemplate> list = new ArrayList<PatternTemplate>();
	
	public DataType(String name, List<PatternTemplate> list){
		this.name = name;
		this.list = list;
		logger = LogManager.getLogger(this.getClass().getName()+":"+name);
		logger.debug("Creating DataType "+name);
	}
	
	public DataType(DataTypeDefinition d){
		
		List<PatternTemplate> l = new ArrayList<PatternTemplate>();
		
		for (PatternTemplateDefinition p: d.getDefinition()){
			l.add(new PatternTemplate(p));
		}
		
		this.name = d.getDataTypeName();
		this.list = l;
		logger = LogManager.getLogger(this.getClass().getName()+":"+name);
		logger.debug("Creating DataType "+name);
	}
	
	public void addPatternTemplate(PatternTemplate p){
		//TODO
		//Check if is repeated or implement as a HashMap
		this.list.add(p);
	}
	
	public void addPatternTemplateDefinition(PatternTemplateDefinition p){
		addPatternTemplate(new PatternTemplate(p));
	}
	
	public void addPatternTemplateList(List<PatternTemplate> l){
		for (PatternTemplate p: l){
			addPatternTemplate(p);
		}
	}
	
	public void addPatternTemplateDefinitionList(List<PatternTemplateDefinition> l){
		for (PatternTemplateDefinition p: l){
			addPatternTemplateDefinition(p);
		}
	}
	
	public String getName(){
		return name;
	}
	
	public boolean isValid(String text){
		return eval(text) != null;
	}	

	public String eval(String text){
		logger.debug("Evaluating '"+text+"'");
		for (PatternTemplate p: list){
			String r = evalPattern(p,text);
			if (r != null){
				logger.debug("Evaluating '"+text+"' returns '"+r+"'");
				return r;
			}
		}
		logger.debug("Evaluating '"+text+"' returns null");
		return null;
	}
	
	private String evalPattern(PatternTemplate p, String text){
				
		logger.trace("Checking pattern '"+p.getPattern()+"'");
		Matcher matcher = p.getCompiledPattern().matcher(text.trim());
	
		if (matcher.matches()==false){
			logger.trace("Pattern'"+p.getPattern()+"': No matches found");
			return null;
		}
		
		logger.trace("Pattern'"+p.getPattern()+"': "+matcher.groupCount() +" matches found");
		
		Writer out = new StringWriter();
		Map<String,String> groups = new HashMap<String,String>();
		for (int i=1; i<=matcher.groupCount(); i++){
			logger.trace("Group: "+matcher.group(i));
			groups.put("_"+(i-1), matcher.group(i));   			
		}
		
		try {
			p.getCompiledTemplate().process(groups, out);
		} catch (TemplateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out.toString();
	}
	
	public static void main(String[] args) {				

		LogManager.getLogger("entrada").debug("empezando");
		List<PatternTemplate> l = new ArrayList<PatternTemplate>();
		try{
		l.add(new PatternTemplate("a las (\\d{1,2}) y (\\d{1,2})","${_0?left_pad(2, '0')}:${_1?left_pad(2, '0')}:00"));
		l.add(new PatternTemplate("a las (\\d{1,2}) y cuarto","${0}:15:00"));
		l.add(new PatternTemplate("a las (\\d{1,2}) y media","${0}:30:00"));
		l.add(new PatternTemplate("a las (\\d{1,2}) en punto","${0}:00:00"));
		l.add(new PatternTemplate("a las (\\d{1,2}) menos (\\d{1,2})","${(_0?number-1)?left_pad(2, '0')}:${(60-_1?number)?left_pad(2, '0')}:00"));
		l.add(new PatternTemplate("a las (\\d) menos cuarto","${0}:00:00"));
		l.add(new PatternTemplate("a las (\\d{1,2})","${0}:00:00"));
		}catch (Exception e){
			
		}

		
		DataType horas = new DataType("TIME", l);

		String prueba = "a las 5 menos 10 ";
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
