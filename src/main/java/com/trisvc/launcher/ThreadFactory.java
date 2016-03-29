package com.trisvc.launcher;

import com.trisvc.common.BaseThread;

public class ThreadFactory {
	
	public static BaseThread getThreadBase(String className, String id) {

		

		Class<?> c = null;
		try {
			c = Class.forName(className);
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
			t.setModuleId(id);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return t;



}

}
