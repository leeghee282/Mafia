package com.ssafy.mafia.service;

import java.util.List;

import com.ssafy.mafia.entity.Room;
import com.ssafy.mafia.entity.RoomUser;

public interface ProfileService {
	
	public void enterRoom(RoomUser roomUser) throws Exception;
	public void exitRoom(RoomUser roomUser) throws Exception;
	
}
