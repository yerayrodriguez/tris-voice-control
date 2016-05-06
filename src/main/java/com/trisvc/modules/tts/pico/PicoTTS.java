package com.trisvc.modules.tts.pico;

import org.freedesktop.dbus.exceptions.DBusException;

import com.trisvc.core.launcher.thread.BaseThread;
import com.trisvc.core.launcher.thread.ThreadUtil;
import com.trisvc.modules.tts.pico.objects.TTS;

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