package com.csy.live.node.tcp.controller.tcp;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.csy.live.common.bean.vo.ErrorResult;
import com.csy.live.common.constant.Status;
import com.csy.live.common.constant.NoticeType;
import com.csy.live.common.constant.Operation;
import com.csy.live.common.utils.FlashCrossUtils;
import com.csy.live.common.utils.JSONUtils;
import com.csy.live.node.tcp.bean.po.User;
import com.csy.live.node.tcp.cache.CtxUser;

import io.netty.channel.ChannelHandlerContext;

@Component
public class Control {
	@Autowired
	private UserController userController;
	@Autowired
	private ChatController chatController;
	@Autowired
	private RoomController groupController;
	private Logger logger = Logger.getLogger(Control.class);

	/**
	 * 接收参数，分发消息
	 * 
	 * @param msg
	 *            接收到的原始信息
	 * @param user
	 *            tcp连接中保存的用户bean
	 * @param ctx
	 *            tcp连接上下文
	 * @return 返回消息
	 */
	public String control(String msg, ChannelHandlerContext ctx) {

		if (FlashCrossUtils.FLASHREQUEST.equals(msg)) {
			return FlashCrossUtils.FLASH_RESPONSE + "\0";
		}
		JSONObject in = null;
		try {
			in = JSONObject.parseObject(msg);
			if (in == null) {
				throw new RuntimeException();
			}
		} catch (Exception e) {
			return JSONUtils.parse(ErrorResult.getResult(Operation.unknown, Status._200, "客户端错误。原因：参数错误"));
		}
		Operation op = null;
		try {
			op = Operation.valueOf(in.getString(Operation.operation.toString()));
		} catch (Exception e) {
			return JSONUtils
					.parse(ErrorResult.getResult(Operation.unknown, Status._200, "客户端错误。原因：没有传入操作类型,字段operation"));
		}
		switch (op) {
		case login:
			return userController.login(op, in, ctx);
		case logout:
			return userController.logout(op, in, ctx);
		case chat:
			return chatController.chat(op, in, ctx);
		case groupChat:
			return chatController.groupChat(op, in, ctx);
		case getOfflineMsg:
			return chatController.getOfflineMsg(op, in, ctx);
		case kickout:
			return groupController.kickout(op, in, ctx);
		default:
			return JSONUtils.parse(ErrorResult.getResult(op, Status._200, "客户端错误。原因：未知操作类型"));
		}
	}

	/**
	 * 客户端关闭连接
	 */
	public void exit(ChannelHandlerContext ctx) {
		User loginUser = CtxUser.getUserByCtx(ctx);
		if (loginUser != null) {
			CtxUser.clearCache(loginUser.getId());
			if (logger.isDebugEnabled()) {
				logger.debug("用户" + loginUser.getId() + "的连接关闭\n");
			}
			loginUser = null;
			JSONObject ntc = new JSONObject();
			ntc.put(Operation.operation.toString(), Operation.notice);
			ntc.put(NoticeType.type.toString(), NoticeType.timeoutDisconnect);
			ctx.writeAndFlush(JSONUtils.parse(ntc));
		}
		ctx.close();
	}
}
