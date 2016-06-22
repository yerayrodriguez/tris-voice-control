package com.trisvc.modules.voiceif;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freedesktop.dbus.DBusSigHandler;
import org.freedesktop.dbus.exceptions.DBusException;

import com.trisvc.core.PlaySoundFile;
import com.trisvc.core.Signal;
import com.trisvc.core.launcher.Launcher;
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
import com.trisvc.modules.brain.parser.DTContext;
import com.trisvc.modules.brain.parser.DataTypeValue;

public class VoiceIF extends BaseThread {
	
	private final static long INMEDIATE_CONTEXT_PERIOD_MS = 30 * 1000; // 30 seconds
	private final static long NORMAL_CONTEXT_PERIOD_MS = 2 * 60 * 1000; // 2 minutes
	
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	private volatile boolean stop = false;
	
	private RemoteObjectWrapper tts = null;
	private RemoteObjectWrapper brain  = null;
	private String inmediateContext = null;
	private DTContext normalContext = null;
	private String location = Launcher.config.getLocation();
	private String idName = "demo";
	
	@Override
	public void execute() {
		try {
			registerSTTSignal();
			
		} catch (DBusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void handleSTT(String line){
		try {
			
			if (tts == null)
				tts = getRemoteObject("TTS");
			
			if (brain == null)
				brain = getRemoteObject("Brain");

			Date contextTime = new GregorianCalendar().getTime();

				
				if (line.equalsIgnoreCase("finalizar") ) {
					sendHaltSignal();
					return;
				}				
				
				Date actualTime =  new GregorianCalendar().getTime();
				Boolean inmediateContextValid = true;
				Boolean normalContextValid = true;
				
				if (actualTime.getTime() - contextTime.getTime() > INMEDIATE_CONTEXT_PERIOD_MS){
					inmediateContextValid = false;
					logger.debug("Inmediate context expired");
				}
				if (actualTime.getTime() - contextTime.getTime() > NORMAL_CONTEXT_PERIOD_MS){
					normalContextValid = false;
					logger.debug("Normal context expired");
				}				
				
				Response response = null;
				Message m = null;
				//Parse
				m = new Message();
				ParserMessage pm = new ParserMessage();
				
				//TODO
				//hay qeu tomar time para el control del contexto
				//por ahora se controla aqui, tal vez habría que permitir que 
				//cada módulo lo controle
				//y que cada modulo defina el tiempo que quiera?	
				
				if (location != null && location.trim().length()>0){
					if (normalContext == null){
						normalContext = new DTContext("*","");
						normalContextValid = true;
					}
					
					if (normalContext.getElements()==null){
						normalContext.setElements(new ArrayList<DataTypeValue>());
					}
					
					boolean locationFound = false;
					for (DataTypeValue o: normalContext.getElements()){
						if (o.getDataType().equals("LOCATION")){
							locationFound = true;
							break;
						}
					}
					if (locationFound == false){
						DataTypeValue dtv = new DataTypeValue("LOCATION",location);
						if (normalContextValid == false){
							normalContext = new DTContext("*","");
							normalContextValid = true;							
						}
						normalContext.addContextElement(dtv);
					}
				}
				


				
				ParserResponse pr = null;
				if (line.trim().toLowerCase().startsWith(idName)){
					pm.setTextToParse(line);
					if (normalContextValid){
						pm.setContext(normalContext);
					}
					m.setBody(pm);
					response = brain.send(m);
					pr = (ParserResponse)response.getBody();
				} else if (inmediateContextValid && inmediateContext != null){
						//TODO
						//hacer un método para esto?
						line = inmediateContext.replace("[INPUT]", line);
						Message m2 = new Message();
						ParserMessage pm2 = new ParserMessage();	
						pm2.setTextToParse(line);
						//pm.setContext(normalContext);
						m2.setBody(pm2);
						response = brain.send(m2);
						pr = (ParserResponse)response.getBody();					
				}else{
					return;
				}
					
				if (response.isSuccess()==false){
					logger.debug(response.getInformation());
					TTS("Lo lamento, no he entendido el comando.");
					return;
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
					return;
				}
				
				inmediateContext = ir.getInmmediateContext();
				normalContext = ir.getNormalContext();
				contextTime = new GregorianCalendar().getTime();
	
				TTS(ir.getMessage());
				
				System.out.flush();


				if (line.equals("memoryDump")){
					//memory.send(MessageType.MemoryDumpMessage.getType(), "");
				}
				System.out.print("Comando: ");
			
			System.out.println("fuera");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void registerSTTSignal() throws DBusException {
		getDBusConnection().addSigHandler(Signal.STTSignal.class, new DBusSigHandler<Signal.STTSignal>() {

			@Override
			public void handle(Signal.STTSignal sig) {
				System.out.println("received:"+sig.text);
				handleSTT(sig.text);


			}

		});

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
