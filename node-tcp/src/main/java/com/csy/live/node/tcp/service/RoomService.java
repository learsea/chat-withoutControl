package com.csy.live.node.tcp.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.csy.live.common.bean.vo.SuccessResult;
import com.csy.live.common.constant.NoticeType;
import com.csy.live.common.constant.Operation;
import com.csy.live.common.utils.DateUtils;
import com.csy.live.common.utils.JSONUtils;
import com.csy.live.node.tcp.bean.po.Notice;
import com.csy.live.node.tcp.bean.po.User;
import com.csy.live.node.tcp.service.common.NoticeCommonService;

import io.netty.channel.ChannelHandlerContext;

/**
 * 群组服务层。
 * 
 * @author caishiyu
 *
 */
@Service
public class RoomService {
	private Logger logger = Logger.getLogger(RoomService.class);

	@Autowired
	private NoticeCommonService noticeCommonService;

	public String kickout(Operation op, JSONObject in, ChannelHandlerContext ctx) {
		User loginUser = UserService.checkUser(ctx);

		int kickedId = JSONUtils.getInt(in, "userId");
		int roomId = JSONUtils.getInt(in, "roomId");

		Notice notice = new Notice();
		notice.setReceiverUid(kickedId);
		notice.setCreatetime(DateUtils.getTimeStamp());
		notice.setType(NoticeType.beTG.toString());
		notice.setRelatedRid(roomId);
		notice.setOperatorUid(loginUser.getId());
		notice.setRelatedUid(kickedId);

		noticeCommonService.send(notice);
		logger.info("用户" + kickedId + "被踢出房间" + roomId);
		// 拼接返回结果
		JSONObject result = SuccessResult.getResult(op);
		result.put("groupId", roomId);
		result.put("userId", kickedId);
		return JSONUtils.parse(result);
	}

}
