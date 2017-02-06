package com.csy.live.node.tcp.controller.tcp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.csy.live.common.constant.Operation;
import com.csy.live.node.tcp.service.ChatService;

import io.netty.channel.ChannelHandlerContext;

/**
 * 聊天控制层。
 * 
 * @author caishiyu
 */
@Component
public class ChatController {
	@Autowired
	private ChatService chatService;

	/**
	 * 聊天
	 * 
	 * @param chatIV
	 *            传入参数
	 * @param ctx
	 *            tcp连接上下文
	 * @return 返回消息
	 */
	public String chat(Operation op, JSONObject in, ChannelHandlerContext ctx) {
		return chatService.chat(op, in, ctx);
	}

	/**
	 * 群聊
	 * 
	 * @param groupChatIV
	 *            传入参数
	 * @param ctx
	 *            tcp上下文
	 * @return 返回消息
	 */
	public String groupChat(Operation op, JSONObject in, ChannelHandlerContext ctx) {
		return chatService.groupChat(op, in, ctx);
	}

	/**
	 * 获取未读消息
	 * 
	 * @param getOfflineMsgIV
	 *            入参
	 * @param ctx
	 *            tcp上下文
	 * @return 返回消息
	 */
	public String getOfflineMsg(Operation op, JSONObject in, ChannelHandlerContext ctx) {
		return chatService.getOfflineMsg(op, in, ctx);
	}
}
