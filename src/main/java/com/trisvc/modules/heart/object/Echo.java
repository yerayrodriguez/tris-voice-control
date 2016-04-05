package com.trisvc.modules.heart.object;

import com.trisvc.common.BaseObject;

public class Echo implements BaseObject {

	@Override
	public boolean isRemote() {
		return false;
	}

	@Override
	public String send(String msg) {
		return msg;
	}

	public static String getObjectName() {
		return Echo.class.getSimpleName();
	}

}
