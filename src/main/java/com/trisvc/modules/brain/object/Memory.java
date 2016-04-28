package com.trisvc.modules.brain.object;

import com.trisvc.modules.BaseObject;
import com.trisvc.modules.brain.thread.MemoryStore;

public class Memory implements BaseObject {

	@Override
	public boolean isRemote() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String send(String t, String msg) {
		switch (t){
		case "ModuleRegister": return moduleRegister(msg);
		case "MemoryDump": return memoryDump();
		default: return "";
		}
	}
	
	private String moduleRegister(String msg){
		return "";
	}
	
	private String memoryDump(){
		MemoryStore.getInstance().memoryDump();
		return "";
	}



}
