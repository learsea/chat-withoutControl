package com.csy.live.http.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.csy.live.common.bean.vo.SuccessResult;
import com.csy.live.common.constant.Status;
import com.csy.live.common.exception.BusinessException;
import com.csy.live.http.bean.po.Room;
import com.csy.live.http.dao.RoomDaoImpl;
import com.csy.live.http.redis.RRoomDao;

@Service
public class RoomService {

	@Autowired
	private RoomDaoImpl roomDaoImpl;
	@Autowired
	private RRoomDao rRoomDao;

	@Value("{serverUrl}")
	private String serverUrl;

	public String hotRooms(int page, int size) {
		List<Room> roomList = roomDaoImpl.getRooms(page, size);
		JSONObject result = SuccessResult.getResult();
		result.put("rooms", roomList);
		return result.toJSONString();
	}

	public String creRoom(int roomId) {
		if (serverUrl == null) {
			throw new BusinessException(Status._400, "没有可选服务器");
		}
		rRoomDao.creRoom(roomId);
		JSONObject result = SuccessResult.getResult();
		return result.toJSONString();
	}

	public String delRoom(int userId) {
		rRoomDao.delRoom(userId);
		return SuccessResult.getResult().toJSONString();
	}

	public String joinRoom(int roomId, int userId) {
		rRoomDao.addAud(roomId, userId);
		JSONObject result = SuccessResult.getResult();
		return result.toJSONString();
	}

	public String leaveRoom(int roomId, int userId) {
		rRoomDao.minusAud(roomId, userId);
		return SuccessResult.getResult().toJSONString();
	}

	public String roomAudNum(int roomId) {
		JSONObject result = SuccessResult.getResult();
		result.put("num", rRoomDao.getAudNum(roomId));
		return result.toJSONString();
	}
}
