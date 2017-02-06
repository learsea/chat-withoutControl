package com.csy.live.http.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.csy.live.http.bean.po.Room;
import com.csy.live.http.dao.iface.RoomDao;
import com.csy.live.http.redis.RRoomDao;

@Repository
public class RoomDaoImpl {
	@Autowired
	private RoomDao roomDao;
	@Autowired
	private RRoomDao rRoomDao;

	public List<Room> getRooms(int page, int size) {
		List<Room> rooms = roomDao.getRooms(page * size, size);
		for (Room room : rooms) {
			room.setRoomId(room.getUserId());
			room.setAudNum(rRoomDao.getAudNum(room.getUserId()));
		}
		return rooms;
	}

	public int saveRoom(Room room) {
		return roomDao.saveRoom(room);
	}

	public int delRoom(int userId) {
		return roomDao.delRoom(userId);
	}

}
