package com.csy.live.node.tcp.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.csy.live.node.tcp.cache.CtxUser;
import com.csy.live.node.tcp.properties.Conf;

import io.netty.channel.ChannelHandlerContext;
import redis.clients.jedis.JedisCluster;

@Repository
public class RUserDao {
	@Autowired
	private JedisCluster cluster;
	@Autowired
	private Conf conf;

	// 校验用户是否登录
	public boolean checkLogin(int uid) {
		ChannelHandlerContext listenerCtx = CtxUser.getCtxById(uid);
		if (listenerCtx == null) {
			// 对方不在本节点
			String userAddress = getUserAddress(uid);
			if (userAddress != null) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	/**
	 * 用户登录，设置用户对应的http地址（用于转发请求）
	 */
	public void addServerUser(int uid) {
		cluster.set(uid + "", conf.getHost() + ":" + conf.getHttpPort());
	}

	/**
	 * 用户注销
	 */
	public void delServerUser(int uid) {
		cluster.del(uid + "");
	}

	/**
	 * 根据用户id获取其对应的服务器http address
	 */
	public String getUserAddress(int uid) {
		return cluster.get(uid + "");
	}
}
