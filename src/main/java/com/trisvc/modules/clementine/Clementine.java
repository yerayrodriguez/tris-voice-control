package com.trisvc.modules.clementine;

import java.util.ArrayList;
import java.util.List;

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.UInt32;
import org.freedesktop.dbus.exceptions.DBusException;
import org.mpris.MediaPlayer2.Playlists;
import org.mpris.MediaPlayer2.Struct1;

import com.trisvc.core.NumeralUtil;
import com.trisvc.core.launcher.thread.BaseThread;
import com.trisvc.core.launcher.thread.ThreadUtil;
import com.trisvc.core.messages.types.register.RegisterMessage;
import com.trisvc.core.messages.types.register.structures.DTPatternDefinition;
import com.trisvc.core.messages.types.register.structures.DataTypeDefinition;
import com.trisvc.core.messages.types.register.structures.DataTypeDefinitionList;
import com.trisvc.core.messages.types.register.structures.ModuleCommand;

public class Clementine extends BaseThread{
	
	private static final long WAIT_CLOSE_MS = 1000;
	private List<Struct1> channelList = null;

	@Override
	protected void execute() {
		try {			
			exportObject(new ClementineObject(), this.getClass().getSimpleName());
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	@Override
	protected void close() {
		try {
			unExportObject(ClementineObject.class);
			// TODO
			// Think a way to wait for others thread finish
			ThreadUtil.sleep(WAIT_CLOSE_MS);
			getLogger().info("Exit");
			System.exit(0);
		} catch (DBusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected RegisterMessage genRegisterMessage() {
		
		final String CLEMENTINE_LIST = "CLEMENTINE_LIST";
		final Integer WEIGHT = 9400;
		
		List<String> patternList0 = new ArrayList<String>();
		patternList0.add("(?:pon|sintoniza|enciende)(?:.*)(\\["+CLEMENTINE_LIST+"\\])");
		List<String> required0 = new ArrayList<String>();
		required0.add("CLEMENTINE_LIST");
		ModuleCommand c0 = new ModuleCommand("sintonize", patternList0, required0);		
		
		List<String> patternList1 = new ArrayList<String>();
		patternList1.add("(?:pon)(?:.*)(?:radio|música)");
		List<String> required1 = new ArrayList<String>();
		ModuleCommand c1 = new ModuleCommand("start", patternList1, required1);
		
		List<String> patternList2 = new ArrayList<String>();
		patternList2.add("(?:quita)(?:.*)(?:radio|música)");
		patternList2.add("(?:\\[OFF\\])(?:.*)(?:radio|música)");
		List<String> required2 = new ArrayList<String>();
		ModuleCommand c2 = new ModuleCommand("stop", patternList2, required2);
		
		List<String> patternList3 = new ArrayList<String>();
		patternList3.add("(?:dime|que|qué)(?:.*)(?:emisoras|canales de radio)");
		List<String> required3 = new ArrayList<String>();
		ModuleCommand c3 = new ModuleCommand("list", patternList3, required3);			

		List<ModuleCommand> commandList = new ArrayList<ModuleCommand>();
		commandList.add(c0);
		commandList.add(c1);
		commandList.add(c2);
		commandList.add(c3);		
				
		List<DTPatternDefinition> definitions = new ArrayList<DTPatternDefinition>();
		channelList = getListOfChannels();
		for (Struct1 s: channelList){
			DTPatternDefinition d = new DTPatternDefinition();
			d.setPattern(NumeralUtil.convert(s.b));
			d.setTemplate(NumeralUtil.convert(s.b));
			definitions.add(d);
		}

		DataTypeDefinition channelList = new DataTypeDefinition(CLEMENTINE_LIST,WEIGHT,definitions);
		
		List<DataTypeDefinition> dataTypeList = new ArrayList<DataTypeDefinition>();
		dataTypeList.add(channelList);
		
		DataTypeDefinitionList dataTypes = new DataTypeDefinitionList(dataTypeList);

		RegisterMessage message = new RegisterMessage();
		message.setModuleName("Clementine");
		message.setCommands(commandList);
		message.setDataTypes(dataTypes);		

		return message;
	}
	
	private List<Struct1> getListOfChannels(){		
		
		try {
			DBusConnection conn = DBusConnection.getConnection(DBusConnection.SESSION);
			Playlists object = (Playlists)conn.getRemoteObject("org.mpris.clementine", "/org/mpris/MediaPlayer2");
			return object.GetPlaylists(new UInt32(0), new UInt32(100), "", true);					
			
		} catch (DBusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return null;
		
	}
	
}
