package com.trisvc.core.launcher;

import com.trisvc.core.launcher.config.LauncherConfig;
import com.trisvc.core.launcher.config.LauncherConfigHandler;
import com.trisvc.core.launcher.config.ModuleToLoad;
import com.trisvc.core.launcher.thread.BaseThread;
import com.trisvc.core.launcher.thread.ThreadFactory;

public class Launcher {
	
	public static LauncherConfig l = LauncherConfigHandler.load();

	public static void main(String[] args) {
		
		for (ModuleToLoad m: l.getModules()){
			startThread(m);
		}
		
		//TODO
		//create bus first?, sleep tiene sentido?;
		
		//startThread("com.trisvc.modules.heart.thread.Heart");	
	
		//ThreadUtil.sleep();
		//startThread("com.trisvc.modules.console.thread.Console","prueba2");
		//startThread("com.trisvc.modules.brain.thread.Brain");
		//startThread("com.trisvc.modules.tts.pico.thread.PicoTTS");

	}

	private static void startThread(ModuleToLoad m) {
		BaseThread tb = ThreadFactory.getThreadBase(className,id);
		Thread t = new Thread(tb);
		t.start();
	}

}
