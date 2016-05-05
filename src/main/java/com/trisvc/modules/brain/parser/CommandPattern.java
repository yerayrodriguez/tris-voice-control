package com.trisvc.modules.brain.parser;

import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandPattern {
	
	private Logger logger = LogManager.getLogger(this.getClass().getName());

	private String pattern;
	private Pattern compiledPattern;
	
	public CommandPattern(String pattern) {
		logger.trace("Registering pattern '"+pattern+"'");
		this.pattern = pattern;
		compile();
	}

	public String getPattern() {
		return pattern;
	}
	
	public Pattern getCompiledPattern(){
		return compiledPattern;
	}
	
	private void compile()  {
		compiledPattern = Pattern.compile(getPattern(), Pattern.CASE_INSENSITIVE);	
	}

	//TODO
	//Remove
	public void dump(){
		System.out.println("Pattern: "+getPattern());
	}

}