package com.trisvc.modules.clementine;

import org.freedesktop.dbus.DBusInterface;
import org.freedesktop.dbus.Position;
import org.freedesktop.dbus.Struct;

public final class PlayListsStruct extends Struct  
{  
	   @Position(0)  
	   public final DBusInterface path;  
	   @Position(1)  
	   public final String listName;  
	   @Position(2)  
	   public final String aux;  
	   
	   public PlayListsStruct(DBusInterface path, String listName, String aux)  
	   {  
	      this.path = path;
	      this.listName = listName;
	      this.aux = aux;
	   }  
	}