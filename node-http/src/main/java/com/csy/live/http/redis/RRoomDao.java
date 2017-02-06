package com.csy.live.http.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.csy.live.common.constant.ExpireUtil;
import com.csy.live.common.constant.RedisPrefix;

import redis.clients.jedis.JedisCluster;

@Repository
public class RRoomDao {
	@Autowired
	private JedisCluster cluster;
	private String roomPrefix = RedisPrefix.room_.toString();
	private String userRoomPrefix = RedisPrefix.user_room_.toString();

	public int getAudNum(int roomId) {
		Long num = cluster.scard(roomPrefix + roomId);
		return num.intValue();
	}

	public void creRoom(int roomId) {
		cluster.del(roomId + "");
		cluster.sadd(roomPrefix + roomId, roomId + "");
		cluster.expire(roomPrefix + roomId, ExpireUtil.DAY2);
	}

	public void delRoom(int roomId) {
		cluster.del(roomPrefix + roomId);
	}

	public void addAud(int roomId, int userId) {
		cluster.sadd(roomPrefix + roomId, userId + "");
		cluster.expire(roomPrefix + roomId, ExpireUtil.DAY2);
		cluster.sadd(userRoomPrefix + userId, roomId + "");
		cluster.expire(userRoomPrefix + userId, ExpireUtil.DAY2);
	}

	public void minusAud(int roomId, int userId) {
		cluster.srem(roomPrefix + roomId, userId + "");
		cluster.srem(userRoomPrefix + userId, roomId + "");
	}
}
