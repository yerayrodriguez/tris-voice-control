package com.trisvc.launcher;

import com.trisvc.common.BaseThread;
import com.trisvc.common.ThreadUtil;

public class Launcher {

	public static void main(String[] args) {
		startThread("com.trisvc.modules.heart.thread.Heart");	
	
		ThreadUtil.sleep();
		startThread("com.trisvc.modules.console.thread.Console","prueba");
		startThread("com.trisvc.modules.brain.thread.Brain");


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
