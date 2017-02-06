package com.csy.live.node.tcp.bean.po;

import io.netty.channel.ChannelHandlerContext;

/**
 * 用户类
 * 
 * @author caishiyu
 */
public class User implements Cloneable {
	private int autoId;
	// 用户id
	private Integer id;

	// 用户对应的tcp连接
	private ChannelHandlerContext ctx;

	public User() {
	}

	@Override
	public User clone() {
		User user = null;
		try {
			user = (User) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ChannelHandlerContext getCtx() {
		return ctx;
	}

	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	public int getAutoId() {
		return autoId;
	}

	public void setAutoId(int autoId) {
		this.autoId = autoId;
	}

}
