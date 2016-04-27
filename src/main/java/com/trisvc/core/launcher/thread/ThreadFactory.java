package com.trisvc.core.launcher.thread;

import com.trisvc.core.launcher.config.ModuleToLoad;

public class ThreadFactory {
	
	public static BaseThread getThreadBase(ModuleToLoad m) {

		

		Class<?> c = null;
		try {
			c = Class.forName(m.getModule());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
		
		BaseThread t = null;
		try {		
			t = (BaseThread) c.newInstance();
			//TODO
			//Change for constructor
			//Look at BaseThread
			t.setInstance(m.getInstance());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return t;



}

}
