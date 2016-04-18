package com.trisvc.common.messages;


public enum MessageType {

	ModuleRegisterMessage ("ModuleRegisterMessage"), 
	TTSMessage("TTSMessage"),
	EchoMessage ("Echo"),
	MemoryDumpMessage ("MemoryDumpMessage"),
	PruebaTypeMessage ("PruebaTypeMessage");

	private final String type;


	private MessageType (String type) {
		this.type = type;
	}


	public String getType ( ) {
		return type;
	}

	


}
