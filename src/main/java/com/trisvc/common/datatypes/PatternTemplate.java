package com.trisvc.common.datatypes;

import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import freemarker.template.Template;

public class PatternTemplate {
	
	private Logger logger = LogManager.getLogger(this.getClass().getName());

	private String pattern;
	private String template;
	private Pattern compiledPattern;
	private Template compiledTemplate;
	
	public PatternTemplate(String pattern, String template) throws IOException{
		logger.trace("Registering pattern '"+pattern+"' and template '"+template+"'");
		this.pattern = pattern;
		this.template = template;
		compile();
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
	
	private void compile() throws IOException {
		compiledPattern = Pattern.compile(getPattern(), Pattern.CASE_INSENSITIVE);
		compiledTemplate = new Template(getTemplate(), new StringReader(getTemplate()),
	               TemplateConfigFactory.getInstance());	
	}


}