package com.csy.live.node.tcp.redis;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.csy.live.common.constant.RedisPrefix;

import redis.clients.jedis.JedisCluster;

@Repository
public class RRoomDao {
	@Autowired
	private JedisCluster cluster;
	private String roomPrefix = RedisPrefix.room_.toString();
	private String userRoomPrefix = RedisPrefix.user_room_.toString();

	public Set<String> getAuds(int roomId) {
		return cluster.smembers(roomPrefix + roomId);
	}

	public void logoutClear(int userId) {
		// 找到用户加入过的所有房间，将该用户从房间中清除（在用户直接退出程序而没有正常退出群时，需要这一流程）
		Set<String> roomIds = cluster.smembers(userRoomPrefix + userId);
		for (String roomId : roomIds) {
			cluster.srem(roomPrefix + roomId, userId + "");
		}
		// 删除记录用户加入房间的缓存
		cluster.del(userRoomPrefix + userId);
	}
}
