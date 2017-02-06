package com.csy.live.node.tcp.controller.tcp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.csy.live.common.constant.Operation;
import com.csy.live.node.tcp.service.UserService;

import io.netty.channel.ChannelHandlerContext;

/**
 * 用户控制层。
 * 
 * @author caishiyu
 */
@Component
public class UserController {
	@Autowired
	private UserService userService;

	/**
	 * 登录
	 * 
	 * @param loginIV
	 *            传入参数
	 * @param ctx
	 *            tcp连接上下文
	 * @return 返回消息
	 */
	public String login(Operation op, JSONObject in, ChannelHandlerContext ctx) {
		return userService.login(op, in, ctx);
	}

	/**
	 * 注销
	 * 
	 * @param logoutIV
	 *            传入参数
	 * @param ctx
	 *            tcp连接上下文
	 * @return 返回消息
	 */
	public String logout(Operation op, JSONObject in, ChannelHandlerContext ctx) {
		return userService.logOut(op, in, ctx);
	}

}
