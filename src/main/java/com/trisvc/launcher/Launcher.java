package com.trisvc.launcher;

import com.trisvc.common.BaseThread;
import com.trisvc.common.ThreadUtil;

public class Launcher {

	public static void main(String[] args) {
		startThread("com.trisvc.modules.heart.HeartThread");	
	
		ThreadUtil.sleep();
		startThread("com.trisvc.modules.console.ConsoleThread","prueba");

	}
	
	public static void startThread(String className) {
		startThread(className,null);
	}

	public static void startThread(String className, String id) {
		BaseThread tb = ThreadFactory.getThreadBase(className,id);
		Thread t = new Thread(tb);
		t.start();
	}

}
