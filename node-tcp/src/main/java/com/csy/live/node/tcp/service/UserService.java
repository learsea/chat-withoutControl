package com.csy.live.node.tcp.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.csy.live.common.bean.vo.SuccessResult;
import com.csy.live.common.constant.Status;
import com.csy.live.common.constant.NoticeType;
import com.csy.live.common.constant.Operation;
import com.csy.live.common.exception.BusinessException;
import com.csy.live.common.utils.JSONUtils;
import com.csy.live.node.tcp.bean.po.User;
import com.csy.live.node.tcp.cache.CtxUser;
import com.csy.live.node.tcp.redis.RUserDao;
import com.csy.live.node.tcp.utils.TranMsgUtil;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

/**
 * 用户服务层。
 * 
 * @author caishiyu
 *
 */
@Service
public class UserService {
	private Logger logger = Logger.getLogger(UserService.class);

	@Autowired
	private RUserDao ruserDao;

	public String login(Operation op, JSONObject in, ChannelHandlerContext ctx) {
		int loginId = JSONUtils.getInt(in, "userId");

		// 根据本次连接获取user
		User cacheUser = CtxUser.getUserByCtx(ctx);
		// 本次连接没有登录
		if (cacheUser == null) {
			try {

				// 检查登录用户是否在本节点别的连接登录过
				if (CtxUser.hasUser(loginId)) {
					// 用户被挤下线通知
					JSONObject ntc = new JSONObject();
					ntc.put(Operation.operation.toString(), Operation.notice);
					ntc.put(NoticeType.type.toString(), NoticeType.kickDisconnect);
					// 设置vo
					ChannelHandlerContext oldCtx = CtxUser.clearCache(loginId);
					oldCtx.writeAndFlush(JSONUtils.parse(ntc)).addListener(ChannelFutureListener.CLOSE);
				} else {
					// 检查登录用户是否在其他节点登录过
					String address = ruserDao.getUserAddress(loginId);
					if (address != null) {
						// 用户被挤下线通知
						JSONObject ntc = new JSONObject();
						ntc.put(Operation.operation.toString(), Operation.notice);
						ntc.put(NoticeType.type.toString(), NoticeType.kickDisconnect);
						// 用户通过别的连接登录过
						// 设置挤下线通知
						TranMsgUtil.tran(address, loginId, ntc.toJSONString(), true);
					}
				}

				// 在本节点登录
				cacheUser = new User();
				cacheUser.setId(loginId);
				cacheUser.setCtx(ctx);
				// 设置缓存
				CtxUser.addCache(cacheUser);
				logger.info("用户：" + loginId + " 登录");
				// 拼接返回结果
				JSONObject result = SuccessResult.getResult(op);
				result.put("id", loginId);
				return JSONUtils.parse(result);
			} catch (Exception e) {
				ChannelHandlerContext oldCtx = CtxUser.clearCache(loginId);
				if (oldCtx != null) {
					oldCtx.close();
				}
				throw new BusinessException(Status._200, e.getMessage());
			}
		}
		throw new BusinessException(Status._200, "已经登录过");
	}

	public String logOut(Operation op, JSONObject in, ChannelHandlerContext ctx) {

		// 校验是否登录
		User cacheUser = UserService.checkUser(ctx);
		CtxUser.clearCache(cacheUser.getId());
		logger.info("用户：" + cacheUser.getId() + " 注销");

		// 拼接返回结果
		JSONObject result = SuccessResult.getResult(op);
		ctx.writeAndFlush(JSONUtils.parse(result)).addListener(ChannelFutureListener.CLOSE);
		return null;
	}

	/**
	 * 检验用户是否登录
	 * 
	 * @param ctx
	 *            tcp连接
	 * @return 若用户已经登录则返回user,否则抛出异常
	 */
	public static User checkUser(ChannelHandlerContext ctx) {
		// 根据本次连接获取user
		User cacheUser = CtxUser.getUserByCtx(ctx);
		if (cacheUser == null) {
			throw new BusinessException(Status._200, "没有登录");
		}
		return cacheUser;
	}
}
