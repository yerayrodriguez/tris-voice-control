package com.trisvc.modules.tts.pico.object;

import java.io.File;
import java.io.IOException;

import com.trisvc.core.BaseObject;
import com.trisvc.core.ExecuteShellCommand;
import com.trisvc.core.PlaySoundFile;

public class TTS implements BaseObject {

	@Override
	public boolean isRemote() {
		return false;
	}

	@Override
	public String send(String type, String msg) {
		try {
			tts("",msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}


	static final String ttsTmpPath = "/tmp/trisvc/tts/";
	static final 	
	
	private String tts (String callerID, String text) throws IOException{
		
		System.out.println(text);
		File f = new File(ttsTmpPath);
		if (f.exists()==false){
			if (f.mkdirs()==false){
				throw new IOException("Dir "+ttsTmpPath+" can not be created");
			}
		}
		if(f.canWrite()==false) {
		  throw new IOException("No write permission to dir "+ttsTmpPath);
		} 	
		
		String file = callerID+"_out.wav";
		String[] ttsCommand = {"pico2wave","-l", "es-ES", "-w",ttsTmpPath+file,text};
		ExecuteShellCommand.execute(ttsCommand);

		
		File o = new File(ttsTmpPath+file);
		if (o.exists()==false){
			throw new IOException("TTS out file doesn't exist");
		}
		System.out.println(ttsTmpPath+file);
		
		new PlaySoundFile(ttsTmpPath+file).start();
		return ttsTmpPath+file;
	}

}
