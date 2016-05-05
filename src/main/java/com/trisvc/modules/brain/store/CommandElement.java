package com.trisvc.modules.brain.store;

public class CommandElement {

	private String module;
	private String instance;
	private String command;
	private String pattern;

	public CommandElement() {
		super();
	}

	public CommandElement(String module, String instance, String command, String pattern) {
		super();
		this.module = module;
		this.instance = instance;
		this.command = command;
		this.pattern = pattern;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public void dump(){
		System.out.println(module+" "+instance+" "+command+" "+pattern);
	}
}
