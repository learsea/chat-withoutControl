package com.csy.live.node.tcp.service;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.csy.live.common.bean.vo.SuccessResult;
import com.csy.live.common.constant.Status;
import com.csy.live.common.constant.NoticeType;
import com.csy.live.common.constant.Operation;
import com.csy.live.common.exception.BusinessException;
import com.csy.live.common.utils.DateUtils;
import com.csy.live.common.utils.JSONUtils;
import com.csy.live.node.tcp.bean.po.ChatMsg;
import com.csy.live.node.tcp.bean.po.User;
import com.csy.live.node.tcp.dao.iface.ChatDao;
import com.csy.live.node.tcp.redis.RRoomDao;
import com.csy.live.node.tcp.service.common.NoticeCommonService;

import io.netty.channel.ChannelHandlerContext;

/**
 * 聊天服务层。
 * 
 * @author caishiyu
 *
 */
@Service
public class ChatService {
	private Logger logger = Logger.getLogger(ChatService.class);

	@Autowired
	private NoticeCommonService noticeCommonService;

	@Autowired
	private ChatDao chatDao;

	@Autowired
	private RRoomDao rRoomDao;

	public String chat(Operation op, JSONObject in, ChannelHandlerContext ctx) {
		// 校验是否登录
		User cacheUser = UserService.checkUser(ctx);

		int talkerId = cacheUser.getId();
		int listenerId = JSONUtils.getInt(in, "listenerId");
		String msgId = JSONUtils.getString(in, "msgId");
		String content = JSONUtils.getString(in, "content");
		long now = DateUtils.getTimeStamp();

		// 设置消息体
		ChatMsg chatMsg = new ChatMsg();
		chatMsg.setTalkerId(talkerId);
		chatMsg.setListenerId(listenerId);
		chatMsg.setTime(now);
		chatMsg.setContent(content);
		chatMsg.setMsgId(msgId);

		noticeCommonService.send(NoticeType.chat, chatMsg, true);

		logger.info("用户：" + talkerId + " 向 " + "用户：" + listenerId + " 发送消息： " + content);
		// 拼接返回结果
		JSONObject result = SuccessResult.getResult(op);
		result.put("time", now);
		result.put("msgId", in.getString("msgId"));
		return JSONUtils.parse(result);
	}

	public String groupChat(Operation op, JSONObject in, ChannelHandlerContext ctx) {
		// 校验是否登录
		User cacheUser = UserService.checkUser(ctx);

		int talkerId = cacheUser.getId();
		String content = JSONUtils.getString(in, "content");
		int roomId = JSONUtils.getInt(in, "roomId");
		String msgId = JSONUtils.getString(in, "msgId");

		Set<String> roomUsers = rRoomDao.getAuds(roomId);
		if (roomUsers == null || roomUsers.size() < 1) {
			throw new BusinessException(Status._200, "没有该群");
		}
		// 除去自己
		if (!roomUsers.remove(cacheUser.getId() + "")) {
			throw new BusinessException(Status._200, "不是群成员");
		}
		long now = DateUtils.getTimeStamp();
		ChatMsg chatMsg = new ChatMsg();
		chatMsg.setTalkerId(talkerId);
		chatMsg.setRoomId(roomId);
		chatMsg.setTime(now);
		chatMsg.setContent(content);
		chatMsg.setMsgId(msgId);

		for (String listenerId : roomUsers) {
			chatMsg.setListenerId(Integer.parseInt(listenerId));
			noticeCommonService.send(NoticeType.groupChat, chatMsg, false);
		}
		logger.info("用户：" + cacheUser.getId() + " 在房间" + roomId + " 中发送消息：" + content);

		// 拼接返回结果
		JSONObject result = SuccessResult.getResult(op);
		result.put("time", now);
		result.put("msgId", in.getString("msgId"));
		return JSONUtils.parse(result);
	}

	public String getOfflineMsg(Operation op, JSONObject in, ChannelHandlerContext ctx) {
		// 校验是否登录
		User cacheUser = UserService.checkUser(ctx);
		int userId = cacheUser.getId();
		// 获取未读单聊消息列表
		List<ChatMsg> chatMsgList = chatDao.getMessages(userId);
		chatDao.delMessages(userId);
		logger.info("用户：" + userId + "获取" + chatMsgList.size() + "条离线消息");

		// 拼接返回结果
		JSONObject result = SuccessResult.getResult(op);
		result.put("chatMsgList", chatMsgList);
		return JSONUtils.parse(result);
	}

}
