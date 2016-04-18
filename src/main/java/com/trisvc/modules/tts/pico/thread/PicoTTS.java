package com.trisvc.modules.tts.pico.thread;

import org.freedesktop.dbus.exceptions.DBusException;

import com.trisvc.common.BaseThread;
import com.trisvc.common.ThreadUtil;
import com.trisvc.modules.tts.pico.object.TTS;

public class PicoTTS extends BaseThread{
	
	private static final long WAIT_CLOSE_MS = 1000;

	@Override
	protected void execute() {
		try {
			
			exportObject(new TTS());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	protected void close() {
		try {
			unExportObject(TTS.class);
		} catch (DBusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//TODO
		//Think a way to wait for others thread finish
		ThreadUtil.sleep(WAIT_CLOSE_MS);
		getLogger().info("Exit");
		System.exit(0);
		
	}


	
}
