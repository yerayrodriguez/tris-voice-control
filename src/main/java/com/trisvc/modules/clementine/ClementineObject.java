package com.trisvc.modules.clementine;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.UInt32;
import org.freedesktop.dbus.exceptions.DBusException;
import org.mpris.MediaPlayer2.Player;
import org.mpris.MediaPlayer2.Playlists;
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

public class ClementineObject extends BaseObjectWrapper implements BaseObject {

	private Logger logger = LogManager.getLogger(this.getClass().getName());
	private final String CLEMENTINE_LIST = "CLEMENTINE_LIST";
	
	private List<Struct1> channelList;
	private Playlists object;
	private Player player;
	
	public ClementineObject(){
		object = getClementineObject();
		channelList = getChannelList();
	}

	public Response send (Message m) {
		InvokeMessage im = (InvokeMessage)m.getBody();
		
		Response r = MessageUtil.getResponseFromMessage(m);
		InvokeResponse ir = new InvokeResponse();
		r.setBody(ir);	
		r.setSuccess(false);
		
		logger.debug("Command invoked: "+im.getCommand());
		
		switch (im.getCommand()) {
		case "start":
			r.setSuccess(start(im,ir));
			break;
		case "stop":
			r.setSuccess(stop(im,ir));
			break;
		case "list":
			r.setSuccess(list(im,ir));
			break;	
		case "sintonize":
			r.setSuccess(sintonize(im,ir));
			break;			
		default:
			r.setSuccess(false);
			r.setInformation("Command unknown: "+im.getCommand());
			logger.error("Command unknown: "+im.getCommand());
			break;
		}
		
		return r;		
	}
	
	private boolean start(InvokeMessage im, InvokeResponse ir){
		player.Play();
		return true;
	}
	
	private boolean stop(InvokeMessage im, InvokeResponse ir){
		player.Stop();
		return true;
	}	
	
	private boolean list(InvokeMessage im, InvokeResponse ir){

		//TODO
		//Internationalization
		//use markuptags. It must be independent of TTS motor
		//example for picotts http://dafpolo.free.fr/telecharger/svoxpico/SVOX_Pico_Manual.pdf
		String answer = "La lista de canales disponibles son: ";
		for (int i=0; i<channelList.size(); i++){
			Struct1 s = channelList.get(i);
			if (i == channelList.size() -1){
				answer += "y ";
			}
			answer += s.b+". ";
		}
		ir.setMessage(answer);
		return true;
	}	
	
	private boolean sintonize(InvokeMessage im, InvokeResponse ir){
		
		String channel = null;
		for (DataTypeValue dt : im.getParameters()){
			if (dt.getDataType().equals(CLEMENTINE_LIST)){
				channel = dt.getValue();
				break;
			}
		}
		
		if (channel == null){
			logger.error("Channel missing");
			return false;
		}
		
		for (Struct1 s: channelList){
			if (channel.equals(NumeralUtil.convert(s.b))){
				object.ActivatePlaylist(s.a);
			}
		}
	
		return true;
	}	

	
	private Playlists getClementineObject(){		
		
		try {
			DBusConnection conn = DBusConnection.getConnection(DBusConnection.SESSION);
			Playlists object = (Playlists)conn.getRemoteObject("org.mpris.clementine", "/org/mpris/MediaPlayer2");
			player = (Player)conn.getRemoteObject("org.mpris.clementine", "/org/mpris/MediaPlayer2");
			return object;				
			
		} catch (DBusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return null;
		
	}	
	
	private List<Struct1> getChannelList(){
		return object.GetPlaylists(new UInt32(0), new UInt32(100), "", true);
	}

}
