package com.trisvc.modules.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freedesktop.dbus.exceptions.DBusException;

import com.trisvc.core.PlaySoundFile;
import com.trisvc.core.Signal;
import com.trisvc.core.launcher.thread.BaseThread;
import com.trisvc.core.messages.Message;
import com.trisvc.core.messages.Response;
import com.trisvc.core.messages.types.invoke.InvokeMessage;
import com.trisvc.core.messages.types.invoke.InvokeResponse;
import com.trisvc.core.messages.types.parser.ParserMessage;
import com.trisvc.core.messages.types.parser.ParserResponse;
import com.trisvc.core.messages.types.register.RegisterMessage;
import com.trisvc.core.messages.types.tts.TTSMessage;
import com.trisvc.core.messages.types.tts.TTSResponse;
import com.trisvc.modules.RemoteObjectWrapper;

public class Console extends BaseThread {
	
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	private volatile boolean stop = false;
	
	private RemoteObjectWrapper tts = null;

	@Override
	public void execute() {
		try {

			tts = getRemoteObject("TTS");
			RemoteObjectWrapper brain = getRemoteObject("Brain");

			String line;
			System.out.print("Comando: ");
			while (!stop && (line = readLine()) != null) {
				
				if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
					sendHaltSignal();
					break;
				}				
				
				Response response = null;
				Message m = null;
				//Parse
				m = new Message();
				ParserMessage pm = new ParserMessage();
				pm.setTextToParse(line);
				m.setBody(pm);
				response = brain.send(m);
				ParserResponse pr = (ParserResponse)response.getBody();
				
				if (response.isSuccess()==false){
					logger.debug(response.getInformation());
					TTS("Lo lamento, no he entendido el comando.");
					continue;
				}
				
				
				RemoteObjectWrapper module = getRemoteObject(pr.getCommandResult().getModule());
				
				m = new Message();
				InvokeMessage im = new InvokeMessage();
				im.setCommand(pr.getCommandResult().getCommand());
				im.setParameters(pr.getCommandResult().getValues());
				m.setBody(im);
				response = module.send(m);
				InvokeResponse ir = (InvokeResponse)response.getBody();
				
				if (response.isSuccess() == false){
					logger.debug(response.getInformation()+" "+ir.getMessage());
					TTS("Lo lamento, ha ocurrido un error al ejecutar la orden.");
					continue;
				}
	
				TTS(ir.getMessage());
				
				System.out.flush();


				if (line.equals("memoryDump")){
					//memory.send(MessageType.MemoryDumpMessage.getType(), "");
				}
				System.out.print("Comando: ");
			}
			System.out.println("fuera");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void TTS(String text){
		
		if (text == null || text.trim().length() == 0){
			//Play a beep?
			return;
		}
		
		Message m = new Message();
		TTSMessage tm = new TTSMessage();
		tm.setTextToSpeech(text);
		m.setBody(tm);
		Response response = tts.send(m);
		TTSResponse tr = (TTSResponse)response.getBody();

		if (response.isSuccess()==false){
			logger.debug(response.getInformation());
			return;					
		}
		
		new PlaySoundFile(tr.getCodedSound()).start();		
	}

	private void sendHaltSignal() {
		try {
			Signal.HaltSignal s = new Signal.HaltSignal("/com/trisvc/modules/console");
			getDBusConnection().sendSignal(s);
		} catch (DBusException e) {
			e.printStackTrace();
		}
	}

	private String readLine() throws IOException {
		if (System.console() != null) {
			return System.console().readLine();
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		return reader.readLine();
	}

	@Override
	protected void close() {}
	
	@Override
	protected RegisterMessage genRegisterMessage() {
		return null;
	}	

}
