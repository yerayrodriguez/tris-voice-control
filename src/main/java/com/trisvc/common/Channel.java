package com.trisvc.common;

import com.trisvc.common.TObject;

public class Channel implements TObject {

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
