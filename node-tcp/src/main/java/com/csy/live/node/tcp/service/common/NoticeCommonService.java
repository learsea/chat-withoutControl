package com.csy.live.node.tcp.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.csy.live.common.constant.Operation;
import com.csy.live.common.constant.NoticeType;
import com.csy.live.common.utils.JSONUtils;
import com.csy.live.node.tcp.bean.po.ChatMsg;
import com.csy.live.node.tcp.bean.po.Notice;
import com.csy.live.node.tcp.bean.po.User;
import com.csy.live.node.tcp.cache.CtxUser;
import com.csy.live.node.tcp.dao.iface.ChatDao;
import com.csy.live.node.tcp.redis.RUserDao;
import com.csy.live.node.tcp.utils.TranMsgUtil;

import io.netty.channel.ChannelHandlerContext;

@Service
public class NoticeCommonService {
	@Autowired
	private ChatDao chatDao;

	@Autowired
	private RUserDao ruserDao;

	public void send(Notice notice) {
		int uid = notice.getReceiverUid();
		User user = CtxUser.getUserById(uid);
		// 拼接通知
		JSONObject ntc = new JSONObject();
		ntc.put(Operation.operation.toString(), Operation.notice);
		ntc.put(NoticeType.type.toString(), notice.getType());
		ntc.put("detail", notice);

		// 用户在本节点登录，直接发送通知
		if (user != null) {
			user.getCtx().writeAndFlush(JSONUtils.parse(ntc));
		} else {
			String address = ruserDao.getUserAddress(uid);
			if (address != null) {
				// 用户在其他节点登录,转发通知
				TranMsgUtil.tran(address, uid, ntc.toJSONString(), false);
			}
		}
	}

	public boolean send(NoticeType type, ChatMsg chatMsg, boolean needSave) {
		int uid = chatMsg.getListenerId();
		// 发送消息
		ChannelHandlerContext listenerCtx = CtxUser.getCtxById(uid);

		JSONObject ntc = new JSONObject();
		ntc.put(Operation.operation.toString(), Operation.notice);
		ntc.put(NoticeType.type.toString(), type);
		ntc.put("chatMsg", chatMsg);

		if (listenerCtx == null) {
			// 对方不在本节点
			String userAddress = ruserDao.getUserAddress(uid);
			if (userAddress != null) {
				// 对方在其他节点
				TranMsgUtil.tran(userAddress, uid, ntc.toJSONString(), false);
				return true;
			}
			// 用户没登录并且需要保存消息
			if (needSave) {
				chatDao.saveMessage(chatMsg);
			}
			return false;
		} else {
			// 对方在本节点，直接发出消息
			listenerCtx.writeAndFlush(JSONUtils.parse(ntc));
			return true;
		}
	}
}
