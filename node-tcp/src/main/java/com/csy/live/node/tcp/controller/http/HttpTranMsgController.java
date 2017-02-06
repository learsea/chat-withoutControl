package com.csy.live.node.tcp.controller.http;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.csy.live.common.bean.vo.SuccessResult;
import com.csy.live.node.tcp.cache.CtxUser;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

@Controller
@ResponseBody
public class HttpTranMsgController {
	/**
	 * 节点间转发消息
	 * 
	 * @param userId
	 *            用户id
	 * @param notice
	 *            json格式消息
	 * @param needClose
	 *            是否需要关闭连接 true 需要 false 不需要
	 */
	@RequestMapping("/tran")
	public String transport(@RequestParam("userId") int userId, @RequestParam("notice") String notice,
			@RequestParam("needClose") boolean needClose) {
		ChannelHandlerContext ctx = CtxUser.getCtxById(userId);
		ChannelFuture future = ctx.writeAndFlush(notice + "\0");
		if (needClose) {
			System.err.println("notice 时关闭连接");
			future.addListener(ChannelFutureListener.CLOSE);
		}
		JSONObject result = SuccessResult.getResult();
		return result.toJSONString();
	}
}
