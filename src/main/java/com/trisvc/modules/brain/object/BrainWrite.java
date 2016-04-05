package com.trisvc.modules.brain.object;

import com.trisvc.common.BaseObject;

public class BrainWrite implements BaseObject {

	@Override
	public boolean isRemote() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String send(String msg) {
		// TODO Auto-generated method stub
		return "Comando Aceptado";
	}


}
