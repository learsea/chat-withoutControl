package com.csy.live.node.tcp.controller.tcp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.csy.live.common.constant.Operation;
import com.csy.live.node.tcp.service.RoomService;

import io.netty.channel.ChannelHandlerContext;

/**
 * 群组控制层。
 * 
 * @author caishiyu
 */
@Component
public class RoomController {
	@Autowired
	private RoomService roomService;

	/**
	 * 用户被T出群
	 * 
	 * @param kickoutIV
	 *            传入参数
	 * @param ctx
	 *            tcp上下文
	 * @return 返回消息
	 */
	public String kickout(Operation op, JSONObject in, ChannelHandlerContext ctx) {
		return roomService.kickout(op, in, ctx);
	}

}
