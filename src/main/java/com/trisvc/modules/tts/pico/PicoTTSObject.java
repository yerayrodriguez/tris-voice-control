package com.trisvc.modules.tts.pico;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trisvc.core.ExecuteShellCommand;
import com.trisvc.core.messages.Message;
import com.trisvc.core.messages.Response;
import com.trisvc.core.messages.types.tts.TTSMessage;
import com.trisvc.core.messages.types.tts.TTSResponse;
import com.trisvc.core.messages.util.MessageUtil;
import com.trisvc.modules.BaseObject;
import com.trisvc.modules.BaseObjectWrapper;

public class PicoTTSObject extends BaseObjectWrapper implements BaseObject{

	private Logger logger = LogManager.getLogger(this.getClass().getName());
	static final String ttsTmpPath = "/tmp/trisvc/tts/";
	
	public Response send (Message m) {
		
		TTSMessage b = (TTSMessage)m.getBody();
		Response r = MessageUtil.getResponseFromMessage(m);
		TTSResponse tr = new TTSResponse();
		r.setBody(tr);
		
		String text = b.getTextToSpeech();
		String tmpFileId = UUID.randomUUID().toString();
		
		logger.debug("Texto to tts: "+text);
		
		File f = new File(ttsTmpPath);
		if (f.exists()==false){
			if (f.mkdirs()==false){
				r.setSuccess(false);
				r.setInformation("Dir "+ttsTmpPath+" can not be created");
				logger.error(r.getInformation());
				return r;
			}
		}
		if(f.canWrite()==false) {
			r.setSuccess(false);
			r.setInformation("No write permission to dir "+ttsTmpPath);
			logger.error(r.getInformation());
			return r;
		} 	
		
		String file = "tts_"+tmpFileId+".wav";
		String[] ttsCommand = {"pico2wave","-l", "es-ES", "-w",ttsTmpPath+file,text};
		ExecuteShellCommand.execute(ttsCommand);
		
		File o = new File(ttsTmpPath+file);
		if (o.exists()==false){
			r.setSuccess(false);
			r.setInformation("TTS out file doesn't exist: "+ttsTmpPath+file);
			logger.error(r.getInformation());
			return r;			
		}

		logger.debug("TTS success, file generated: "+ttsTmpPath+file);
		tr.setCodedSound(encode(o));
		//new PlaySoundFile(ttsTmpPath+file).start();
		r.setSuccess(true);
		return r;
	}
	
	private String encode(File file){
        String encodedBase64 = null;
        try {
        	FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            encodedBase64 = Base64.getEncoder().encodeToString(bytes);
            fileInputStreamReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 
        
        return encodedBase64;
	}

}
