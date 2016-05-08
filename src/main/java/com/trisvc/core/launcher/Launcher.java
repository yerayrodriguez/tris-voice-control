package com.trisvc.core.launcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trisvc.core.launcher.config.LauncherConfig;
import com.trisvc.core.launcher.config.LauncherConfigHandler;
import com.trisvc.core.launcher.config.ModuleToLoad;
import com.trisvc.core.launcher.thread.BaseThread;
import com.trisvc.core.launcher.thread.ThreadFactory;

public class Launcher {
	
	private static Logger logger = LogManager.getLogger("Launcher");
	public static LauncherConfig config = LauncherConfigHandler.load();

	public static void main(String[] args) {
		
		for (ModuleToLoad m: config.getModules()){
			startThread(m);
		}

	}

	private static void startThread(ModuleToLoad m) {
		logger.debug("Starting "+m.getQualifiedName()+"-"+m.getInstance());
		BaseThread tb = ThreadFactory.getThreadBase(m);
		Thread t = new Thread(tb);
		t.start();
	}

}
