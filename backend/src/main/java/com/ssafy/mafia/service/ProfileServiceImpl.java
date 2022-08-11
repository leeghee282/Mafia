package com.ssafy.mafia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.mafia.entity.RoomUser;
import com.ssafy.mafia.repository.RoomUserRepository;

@Service
public class ProfileServiceImpl implements ProfileService{
	
	@Autowired
	RoomUserService roomUserService;
	
	@Override
	public void enterRoom(RoomUser roomUser) throws Exception {
		
		roomUserService.createRoomUser(roomUser);
		
	}

	@Override
	public void exitRoom(RoomUser roomUser) throws Exception {
		// TODO Auto-generated method stub
		
	}

	

	
	
}