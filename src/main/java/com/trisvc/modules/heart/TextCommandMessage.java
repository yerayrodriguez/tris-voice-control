package com.trisvc.modules.heart;

import com.trisvc.common.Message;

public class TextCommandMessage implements Message {

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
