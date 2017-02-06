package com.csy.live.http.dao.iface;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.csy.live.http.bean.po.Room;

/**
 * 为规范化dao层，禁止在service层直接调用该层。应该调用daoImpl
 */
@Repository
public interface RoomDao {

	List<Room> getRooms(@Param("offset") int offset, @Param("size") int size);

	int saveRoom(Room room);

	int delRoom(int userId);

}
