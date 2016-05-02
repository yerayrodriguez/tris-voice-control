package com.trisvc.core.datatypes;

import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trisvc.core.messages.types.register.structures.DTPatternDefinition;

import freemarker.template.Template;

public class DTPattern {
	
	private Logger logger = LogManager.getLogger(this.getClass().getName());

	private String pattern;
	private String template;
	private Pattern compiledPattern;
	private Template compiledTemplate;
	
	public DTPattern(String pattern, String template) {
		logger.trace("Registering pattern '"+pattern+"' and template '"+template+"'");
		this.pattern = pattern;
		this.template = template;
		compile();
	}
	
	public DTPattern(DTPatternDefinition p) {
		this(p.getPattern(), p.getTemplate());
	}

	public String getPattern() {
		return pattern;
	}

	public String getTemplate() {
		return template;
	}
	
	public Pattern getCompiledPattern(){
		return compiledPattern;
	}

	public Template getCompiledTemplate() {
		return compiledTemplate;
	}
	
	private void compile()  {
		compiledPattern = Pattern.compile(getPattern(), Pattern.CASE_INSENSITIVE);
		try {
			compiledTemplate = new Template(getTemplate(), new StringReader(getTemplate()),
			           FreemarkerConfiguration.getInstance());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public void dump(){
		System.out.println("Pattern: "+getPattern());
		System.out.println("Template: "+getTemplate());
	}

}