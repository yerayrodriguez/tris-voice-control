package com.trisvc.common.messages;


public enum MessageType {

	ModuleRegister ("ModuleRegister"), 
	Echo ("Echo"),
	PruebaType ("PruebaType");

	private final String type;


	private MessageType (String type) {
		this.type = type;
	}


	public String getType ( ) {
		return type;
	}

	


}
