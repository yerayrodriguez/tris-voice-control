package com.trisvc.core.launcher.thread;

public class ThreadUtil {
	
	public static void sleep (long ms){
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void sleep(){
		sleep (1000);
	}
}
