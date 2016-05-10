package com.trisvc.modules.clementine;

import java.util.List;

import org.freedesktop.dbus.DBusInterface;
import org.freedesktop.dbus.UInt32;

public interface PlayListInterface extends DBusInterface{

	public List<PlayListsStruct> GetPlaylists(UInt32 index, UInt32 maxCount, String order, Boolean reverseOrder);

}
