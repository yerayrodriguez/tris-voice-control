package com.trisvc.modules.openhab;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.freedesktop.dbus.exceptions.DBusException;

import com.trisvc.core.launcher.Launcher;
import com.trisvc.core.launcher.thread.BaseThread;
import com.trisvc.core.launcher.thread.ThreadUtil;
import com.trisvc.core.messages.types.register.RegisterMessage;
import com.trisvc.core.messages.types.register.structures.DTPatternDefinition;
import com.trisvc.core.messages.types.register.structures.DataTypeDefinition;
import com.trisvc.core.messages.types.register.structures.DataTypeDefinitionList;
import com.trisvc.core.messages.types.register.structures.ModuleCommand;


public class OpenHab extends BaseThread {

	private static final long WAIT_CLOSE_MS = 1000;
	private static OpenHabItems items = null;
	
	public static final String LOCATION = "LOCATION";
	public static final Integer LOCATION_WEIGHT = 9900;
	
	public static final String OPEN_DEVICE = "OPEN_DEVICE";
	public static final Integer OPEN_DEVICE_WEIGHT = 9850;	
	
	public static final String ON_DEVICE = "ON_DEVICE";
	public static final Integer ON_DEVICE_WEIGHT = 9800;		
	
	public static final String ON = "ON";
	public static final Integer ON_WEIGHT = 9700;	
	
	public static final String OFF = "OFF";
	public static final Integer OFF_WEIGHT = 9600;	
	
	public static final String OPEN = "OPEN";
	public static final Integer OPEN_WEIGHT = 9500;	
	
	public static final String CLOSE = "CLOSE";
	public static final Integer CLOSE_WEIGHT = 9450;		

	@Override
	public void execute() {
		try {
			
			exportObject(new OpenHabObject(), this.getClass().getSimpleName(), this.getInstance());
		} catch (DBusException e) {
			e.printStackTrace();
		}
		statusCheck();
	}

	@Override
	protected void close() {
		try {
			unExportObject(OpenHabObject.class);
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
		
		OpenHabRest rest= new OpenHabRest("http://"+Launcher.config.getOpenHAB());

		try {
			items = OpenHabUtil.unmarshalItems(rest.getItems());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<String> patternList0 = new ArrayList<String>();
		patternList0.add("(?:\\[ON\\]).*(\\[ON_DEVICE\\]).*(\\[LOCATION\\])");
		patternList0.add("(?:\\[ON\\]).*(\\[ON_DEVICE\\])");
		List<String> required0 = new ArrayList<String>();
		required0.add("LOCATION");
		required0.add("ON_DEVICE");
		ModuleCommand c0 = new ModuleCommand("on", patternList0, required0);	
		
		List<String> patternList1 = new ArrayList<String>();
		patternList1.add("(?:\\[OFF\\]).*(\\[ON_DEVICE\\]).*(\\[LOCATION\\])");
		patternList1.add("(?:\\[OFF\\]).*(\\[ON_DEVICE\\])");
		List<String> required1 = new ArrayList<String>();
		required1.add("LOCATION");
		required1.add("ON_DEVICE");
		ModuleCommand c1 = new ModuleCommand("off", patternList1, required1);	
		
		List<String> patternList2 = new ArrayList<String>();
		patternList2.add("(?:\\[OPEN\\]).*(\\[OPEN_DEVICE\\]).*(\\[LOCATION\\]).*(\\[PERCENTAGE\\])");
		patternList2.add("(?:\\[OPEN\\]).*(\\[OPEN_DEVICE\\]).*(\\[PERCENTAGE\\])");
		List<String> required2 = new ArrayList<String>();
		required2.add("LOCATION");
		required2.add("OPEN_DEVICE");
		required2.add("PERCENTAGE");
		ModuleCommand c2 = new ModuleCommand("open", patternList2, required2);	
		
		List<String> patternList3 = new ArrayList<String>();
		patternList3.add("(?:\\[CLOSE\\]).*(\\[OPEN_DEVICE\\]).*(\\[LOCATION\\])");
		patternList3.add("(?:\\[CLOSE\\]).*(\\[OPEN_DEVICE\\])");
		List<String> required3 = new ArrayList<String>();
		required3.add("LOCATION");
		required3.add("OPEN_DEVICE");
		ModuleCommand c3 = new ModuleCommand("close", patternList3, required3);		
		
		List<String> patternList4 = new ArrayList<String>();
		patternList4.add("(?:\\[OPEN\\]).*(\\[OPEN_DEVICE\\]).*(\\[LOCATION\\])");
		patternList4.add("(?:\\[OPEN\\]).*(\\[OPEN_DEVICE\\])");
		List<String> required4 = new ArrayList<String>();
		required4.add("LOCATION");
		required4.add("OPEN_DEVICE");
		ModuleCommand c4 = new ModuleCommand("open", patternList4, required4);	
		
		List<String> patternList5 = new ArrayList<String>();
		patternList5.add("cómo está .*(\\[OPEN_DEVICE\\]).*(\\[LOCATION\\])");
		patternList5.add("cómo está .*(\\[OPEN_DEVICE\\])");
		List<String> required5 = new ArrayList<String>();
		required5.add("LOCATION");
		required5.add("OPEN_DEVICE");
		ModuleCommand c5 = new ModuleCommand("status_open", patternList5, required5);			
				
		List<ModuleCommand> commandList = new ArrayList<ModuleCommand>();
		commandList.add(c0);
		commandList.add(c1);
		commandList.add(c2);
		commandList.add(c3);
		commandList.add(c4);
		commandList.add(c5);
		
				
		List<DTPatternDefinition> definitions = new ArrayList<DTPatternDefinition>();
		List<String> locations = getListOfLocations(items);
		for (String location: locations){
			DTPatternDefinition d = new DTPatternDefinition();
			d.setPattern(location);
			d.setTemplate(location);
			definitions.add(d);
		}
		DataTypeDefinition locationsList = new DataTypeDefinition(LOCATION,LOCATION_WEIGHT,definitions);
		
		definitions = new ArrayList<DTPatternDefinition>();
		List<String> devices = getListOfOnDevices(items);
		for (String device: devices){
			DTPatternDefinition d = new DTPatternDefinition();
			d.setPattern(device);
			d.setTemplate(device);
			definitions.add(d);
		}
		DataTypeDefinition devicesList = new DataTypeDefinition(ON_DEVICE,ON_DEVICE_WEIGHT,definitions);	
		
		definitions = new ArrayList<DTPatternDefinition>();
		List<String> openDevices = getListOfOpenDevices(items);
		for (String device: openDevices){
			DTPatternDefinition d = new DTPatternDefinition();
			d.setPattern(device);
			d.setTemplate(device);
			definitions.add(d);
		}
		DataTypeDefinition openDevicesList = new DataTypeDefinition(OPEN_DEVICE,OPEN_DEVICE_WEIGHT,definitions);	
				
		
		definitions = new ArrayList<DTPatternDefinition>();
		for (String on: getListOn()){
			DTPatternDefinition d = new DTPatternDefinition();
			d.setPattern(on);
			d.setTemplate(on) ;
			definitions.add(d);
		}
		DataTypeDefinition onList = new DataTypeDefinition(ON,ON_WEIGHT,definitions);	
		
		definitions = new ArrayList<DTPatternDefinition>();
		for (String off: getListOff()){
			DTPatternDefinition d = new DTPatternDefinition();
			d.setPattern(off);
			d.setTemplate(off) ;
			definitions.add(d);
		}
		DataTypeDefinition offList = new DataTypeDefinition(OFF,OFF_WEIGHT,definitions);			
		
		definitions = new ArrayList<DTPatternDefinition>();
		for (String open: getListOpen()){
			DTPatternDefinition d = new DTPatternDefinition();
			d.setPattern(open);
			d.setTemplate(open) ;
			definitions.add(d);
		}
		DataTypeDefinition openList = new DataTypeDefinition(OPEN,OPEN_WEIGHT,definitions);	
		
		definitions = new ArrayList<DTPatternDefinition>();
		for (String close: getListClose()){
			DTPatternDefinition d = new DTPatternDefinition();
			d.setPattern(close);
			d.setTemplate(close) ;
			definitions.add(d);
		}
		DataTypeDefinition closeList = new DataTypeDefinition(CLOSE,CLOSE_WEIGHT,definitions);			
				
		
		List<DataTypeDefinition> dataTypeList = new ArrayList<DataTypeDefinition>();
		dataTypeList.add(locationsList);
		dataTypeList.add(devicesList);
		dataTypeList.add(openDevicesList);
		dataTypeList.add(onList);
		dataTypeList.add(offList);
		dataTypeList.add(openList);
		dataTypeList.add(closeList);		
		
		DataTypeDefinitionList dataTypes = new DataTypeDefinitionList(dataTypeList);

		RegisterMessage message = new RegisterMessage();
		message.setModuleName("OpenHab");
		message.setCommands(commandList);
		message.setDataTypes(dataTypes);		

		return message;
	}
	
	public static OpenHabItems getItems(){
		return items;
	}

	private List<String> getListOfOnDevices(OpenHabItems items){
		
		List<String> devices = new ArrayList<String>();
		
		for (OpenHabItem item : items.getItemList()){
			if (item.getType().equals("SwitchItem") ){
				devices.add(item.getName().toLowerCase().split("_")[0]);
			}
		}
		
		return devices;
		
	}
	
	private List<String> getListOfOpenDevices(OpenHabItems items){
		
		List<String> devices = new ArrayList<String>();
		
		for (OpenHabItem item : items.getItemList()){
			if (item.getType().equals("RollershutterItem")){
				devices.add(item.getName().toLowerCase().split("_")[0]);
			}
		}
		
		return devices;
		
	}	
	
	private List<String> getListOfLocations(OpenHabItems items){
		
		List<String> locations = new ArrayList<String>();
		
		for (OpenHabItem item : items.getItemList()){
			if (item.getType().equals("GroupItem")){
				locations.add(item.getName().toLowerCase());
			}
		}
		
		return locations;
		
	}
	
	private List<String> getListOn(){
		List<String> on = new ArrayList<String>();
		on.add("encender");
		on.add("enciende");
		return on;
	}
	
	private List<String> getListOff(){
		List<String> off = new ArrayList<String>();
		off.add("apagar");
		off.add("apaga");		
		return off;
	}
	
	private List<String> getListOpen(){
		List<String> on = new ArrayList<String>();
		on.add("abrir");
		on.add("abre");
		on.add("subir");
		on.add("sube");
		return on;
	}
	
	private List<String> getListClose(){
		List<String> off = new ArrayList<String>();
		off.add("cerrar");
		off.add("cierra");
		off.add("bajar");
		off.add("baja");
		return off;
	}	
	
    
	public void statusCheck() {
		WebSocketContainer container = null;//
		Session session = null;
		try {
			// Tyrus is plugged via ServiceLoader API. See notes above
			container = ContainerProvider.getWebSocketContainer();
			// WS1 is the context-root of my web.app
			// ratesrv is the path given in the ServerEndPoint annotation on
			// server implementation

			ClientEndpointConfig customCec = ClientEndpointConfig.Builder.create()

					.configurator(new MyClientConfigurator()).build();

			session = container.connectToServer(OpenHabStatusCheck.class, customCec,
					URI.create("ws://"+Launcher.config.getOpenHAB()+"/rest/items?Accept=application/xml"));
			wait4TerminateSignal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private  Object waitLock = new Object();
	
	private  void wait4TerminateSignal() {
		synchronized (waitLock) {
			try {
				waitLock.wait();
			} catch (InterruptedException e) {
			}
		}
	}	
	
	public static void updateState(OpenHabItem o){

		for (OpenHabItem item: items.getItemList()){
			if (item.getName().equals(o.getName())){

				item.setState(o.getState());
			}
		}
	}
}
