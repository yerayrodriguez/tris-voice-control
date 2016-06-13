package com.trisvc.modules.openhab;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mpris.MediaPlayer2.Struct1;

import com.trisvc.core.NumeralUtil;
import com.trisvc.core.messages.Message;
import com.trisvc.core.messages.Response;
import com.trisvc.core.messages.types.invoke.InvokeMessage;
import com.trisvc.core.messages.types.invoke.InvokeResponse;
import com.trisvc.core.messages.util.MessageUtil;
import com.trisvc.modules.BaseObject;
import com.trisvc.modules.BaseObjectWrapper;
import com.trisvc.modules.brain.parser.DataTypeValue;

public class OpenHabObject extends BaseObjectWrapper implements BaseObject {

	private Logger logger = LogManager.getLogger(this.getClass().getName());

	public Response send (Message m) {
		InvokeMessage im = (InvokeMessage) m.getBody();

		Response r = MessageUtil.getResponseFromMessage(m);
		InvokeResponse ir = new InvokeResponse();
		r.setBody(ir);
		r.setSuccess(false);
		
		logger.debug("Command invoked: "+im.getCommand());
		
		switch (im.getCommand()) {
		case "on":
			r.setSuccess(on(im,ir,m,r));
			break;
		case "off":
			r.setSuccess(off(im,ir,m,r));
			break;
		case "open":
			r.setSuccess(open(im,ir,m,r));
			break;
		case "close":
			r.setSuccess(close(im,ir,m,r));
			break;			
		default:
			ir.setMessage("Comando desconocido");
			r.setSuccess(false);
			r.setInformation("Unknown command: " + im.getCommand());
			logger.error(r.getInformation());
			break;
		}
		
		return r;		
	}
	
	private boolean on(InvokeMessage im, InvokeResponse ir, Message m, Response r){
		
		String location = null;
		for (DataTypeValue dt : im.getParameters()){
			if (dt.getDataType().equals(OpenHab.LOCATION)){
				location = dt.getValue();
				break;
			}
		}
		
		if (location == null){
			logger.error("Location missing");
			return false;			
		}
		
		String device = null;
		for (DataTypeValue dt : im.getParameters()){
			if (dt.getDataType().equals(OpenHab.ON_DEVICE)){
				device = dt.getValue();
				break;
			}
		}		
		
		if (device == null){
			logger.error("Device missing");
			return false;
		}
		
		String type = getType(device, location);
		
		if (type == null){
			ir.setMessage("Dispositivo no encontrado");
			r.setSuccess(false);	
			return true;
		}
		
		String status = getOnStatus(type);
		
		OpenHabRest o = new OpenHabRest("http://localhost:8080");
		try {
			o.sendStatus(device+"_"+location, status);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return true;
	}	
	
	private boolean off(InvokeMessage im, InvokeResponse ir, Message m, Response r){
		
		String location = null;
		for (DataTypeValue dt : im.getParameters()){
			if (dt.getDataType().equals(OpenHab.LOCATION)){
				location = dt.getValue();
				break;
			}
		}
		
		if (location == null){
			logger.error("Location missing");
			return false;			
		}
		
		String device = null;
		for (DataTypeValue dt : im.getParameters()){
			if (dt.getDataType().equals(OpenHab.ON_DEVICE)){
				device = dt.getValue();
				break;
			}
		}		
		
		if (device == null){
			logger.error("Device missing");
			return false;
		}
		
		String type = getType(device, location);
		
		if (type == null){
			ir.setMessage("Dispositivo no encontrado");
			r.setSuccess(false);	
			return true;
		}
		
		String status = getOffStatus(type);
		
		OpenHabRest o = new OpenHabRest("http://localhost:8080");
		try {
			o.sendStatus(device+"_"+location, status);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return true;
	}
	
private boolean open(InvokeMessage im, InvokeResponse ir, Message m, Response r){
		
		String location = null;
		for (DataTypeValue dt : im.getParameters()){
			if (dt.getDataType().equals(OpenHab.LOCATION)){
				location = dt.getValue();
				break;
			}
		}
		
		if (location == null){
			logger.error("Location missing");
			return false;			
		}
		
		String device = null;
		for (DataTypeValue dt : im.getParameters()){
			if (dt.getDataType().equals(OpenHab.OPEN_DEVICE)){
				device = dt.getValue();
				break;
			}
		}		
		
		if (device == null){
			logger.error("Device missing");
			return false;
		}
		
		String percentage = null;
		for (DataTypeValue dt : im.getParameters()){
			if (dt.getDataType().equals("PERCENTAGE")){
				percentage = dt.getValue();
				break;
			}
		}		
		
		String type = getType(device, location);
		
		if (type == null){
			ir.setMessage("Dispositivo no encontrado");
			r.setSuccess(false);	
			return true;
		}
		
		String status = getOpenStatus(type);
		if (percentage != null){
			status = percentage.replace("%", "");
		}
		
		OpenHabRest o = new OpenHabRest("http://localhost:8080");
		try {
			o.sendStatus(device+"_"+location, status);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return true;
	}	
	
	private boolean close(InvokeMessage im, InvokeResponse ir, Message m, Response r){
		
		String location = null;
		for (DataTypeValue dt : im.getParameters()){
			if (dt.getDataType().equals(OpenHab.LOCATION)){
				location = dt.getValue();
				break;
			}
		}
		
		if (location == null){
			logger.error("Location missing");
			return false;			
		}
		
		String device = null;
		for (DataTypeValue dt : im.getParameters()){
			if (dt.getDataType().equals(OpenHab.OPEN_DEVICE)){
				device = dt.getValue();
				break;
			}
		}		
		
		if (device == null){
			logger.error("Device missing");
			return false;
		}
		
		String type = getType(device, location);
		
		if (type == null){
			ir.setMessage("Dispositivo no encontrado");
			r.setSuccess(false);	
			return true;
		}
		
		String status = getCloseStatus(type);
		
		OpenHabRest o = new OpenHabRest("http://localhost:8080");
		try {
			o.sendStatus(device+"_"+location, status);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return true;
	}	
	
	private String getType(String device, String location){
		for (OpenHabItem item : OpenHab.getItems().itemList)
		{
			String aux = device+"_"+location;
			if (item.getName().toLowerCase().equals(aux.toLowerCase())){
				return item.getType();
			}
		}
		return null;
	}
	
	private String getOnStatus(String type){

		if ("SwitchItem".equals(type)){
			return "ON";
		}
		
		return null;
	}
	
	private String getOffStatus(String type){

		
		if ("SwitchItem".equals(type)){
			return "OFF";
		}
		
		return null;		
	}

	private String getOpenStatus(String type){
		if ("RollershutterItem".equals(type)){
			return "0";
		}		
		
		return null;
	}
	
	private String getCloseStatus(String type){
		if ("RollershutterItem".equals(type)){
			return "100";
		}
		
		
		return null;		
	}
}
