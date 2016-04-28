package com.trisvc.modules.tts.pico.object;

import java.io.File;

import com.trisvc.core.ExecuteShellCommand;
import com.trisvc.core.PlaySoundFile;
import com.trisvc.core.messages.Message;
import com.trisvc.core.messages.Response;
import com.trisvc.core.messages.types.tts.TTSMessage;
import com.trisvc.core.messages.types.tts.TTSResponse;
import com.trisvc.core.messages.util.MessageUtil;
import com.trisvc.modules.BaseObject;
import com.trisvc.modules.BaseObjectWrapper;

public class TTS extends BaseObjectWrapper implements BaseObject{


	/*@Override
	public String send(String xmlMessage) {
		Message m = (Message) MessageUtil.unmarshal(xmlMessage);
		Response r = send(m);
		return r.toString();
	}
	
	@Override
	public boolean isRemote() {
		//TODO
		//WTF??
		return false;
	}*/

	/*@Override
	public String send(String xmlMessage) {
		Message m = (Message) MessageUtil.unmarshal(xmlMessage);	
			
		Response r = tts(m);

		return r.toString();
	}*/


	static final String ttsTmpPath = "/tmp/trisvc/tts/";
	
	
	public Response send (Message m) {
		
		TTSMessage b = (TTSMessage)m.getBody();
		Response r = MessageUtil.getResponseFromMessage(m);
		TTSResponse tr = new TTSResponse();
		r.setBody(tr);
		
		String text = b.getTextToSpeech();
		String callerID = m.getCallerID();
		
		System.out.println(text);
		File f = new File(ttsTmpPath);
		if (f.exists()==false){
			if (f.mkdirs()==false){
				r.setSuccess(false);
				r.setInformation("Dir "+ttsTmpPath+" can not be created");
				return r;
			}
		}
		if(f.canWrite()==false) {
			r.setSuccess(false);
			r.setInformation("No write permission to dir "+ttsTmpPath);
			return r;
		} 	
		
		String file = callerID+"_out.wav";
		String[] ttsCommand = {"pico2wave","-l", "es-ES", "-w",ttsTmpPath+file,text};
		ExecuteShellCommand.execute(ttsCommand);

		
		File o = new File(ttsTmpPath+file);
		if (o.exists()==false){
			r.setSuccess(false);
			r.setInformation("TTS out file doesn't exist");
			return r;			
		}
		
		System.out.println(ttsTmpPath+file);
		
		new PlaySoundFile(ttsTmpPath+file).start();
		r.setSuccess(true);
		return r;
	}

}
