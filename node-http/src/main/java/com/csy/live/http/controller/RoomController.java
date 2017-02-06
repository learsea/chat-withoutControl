package com.csy.live.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csy.live.http.constant.UrlPrefix;
import com.csy.live.http.service.RoomService;

@Controller
@RequestMapping("live/" + UrlPrefix.NO_NEED_LOGIN_PRE)
@ResponseBody
public class RoomController {
	@Autowired
	private RoomService roomService;

	@RequestMapping("hot-rooms")
	public String hotRooms(@RequestParam int page, @RequestParam int size) {
		return roomService.hotRooms(page, size);
	}

	@RequestMapping("room-aud-num")
	public String roomAudNum(@RequestParam int roomId) {
		return roomService.roomAudNum(roomId);
	}

	@RequestMapping("cre-room")
	public String creRoom(@RequestParam int roomId) {
		return roomService.creRoom(roomId);
	}

	@RequestMapping("del-room")
	public String delRoom(@RequestParam int userId) {
		return roomService.delRoom(userId);
	}

	@RequestMapping("join-room")
	public String joinRoom(@RequestParam int roomId, @RequestParam int userId) {
		return roomService.joinRoom(roomId, userId);
	}

	@RequestMapping("leave-room")
	public String leaveRoom(@RequestParam int roomId, @RequestParam int userId) {
		return roomService.leaveRoom(roomId, userId);
	}
}
