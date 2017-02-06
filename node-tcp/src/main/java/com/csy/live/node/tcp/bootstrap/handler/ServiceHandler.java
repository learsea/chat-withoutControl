package com.csy.live.node.tcp.bootstrap.handler;

import java.net.InetSocketAddress;

import org.apache.log4j.Logger;

import com.csy.live.common.bean.vo.ErrorResult;
import com.csy.live.common.constant.Status;
import com.csy.live.common.constant.Operation;
import com.csy.live.common.constant.Ping;
import com.csy.live.common.utils.JSONUtils;
import com.csy.live.node.tcp.controller.tcp.Control;
import com.csy.live.node.tcp.main.Main;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

/**
 * 总控制类
 * 
 * @author caishiyu
 *
 */
public class ServiceHandler extends ChannelInboundHandlerAdapter {
	private Logger logger = Logger.getLogger(ServiceHandler.class);
	private Control control = Main.context.getBean(Control.class);;

	/**
	 * 读消息
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg.equals(Ping.PING)) {
			return;
		}
		try {
			// 调用control方法解析消息
			String response = control.control((String) msg, ctx);
			if (response != null) {
				// response为null说明已经处理过输出（比如logout中，关闭了连接）
				ctx.writeAndFlush(response);
			}
			// 如果是空暂时不处理,由client来处理
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// 如果收到内容太多无法解析，则通知客户端
		if (cause instanceof TooLongFrameException) {
			ctx.writeAndFlush(
					JSONUtils.parse(ErrorResult.getResult(Operation.unknown, Status._400, "客户端错误。原因：内容过多，无法解析")));
		} else if (cause.getMessage().equals("远程主机强迫关闭了一个现有的连接。")
				|| cause.getMessage().equals("Connection reset by peer")) {
			// 不输出异常信息
		} else {
			ctx.writeAndFlush(JSONUtils
					.parse(ErrorResult.getResult(Operation.unknown, Status._400, "服务器异常。原因：" + cause.getMessage())));
			super.exceptionCaught(ctx, cause);
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("有新的连接：" + ((InetSocketAddress) ctx.channel().remoteAddress()).getHostName());
		}
		super.channelActive(ctx);
	}

	/** 实例释放, 当Socket连接被用户主动关闭后调用 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(((InetSocketAddress) ctx.channel().remoteAddress()).getHostName() + "：断开连接");
		}
		control.exit(ctx);
		super.channelInactive(ctx);
	}

	/* 心跳检测 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			if (evt instanceof IdleStateEvent) {
				IdleStateEvent e = (IdleStateEvent) evt;
				if (e.state().equals(IdleState.ALL_IDLE)) {
					ctx.writeAndFlush(Ping.PING0);
				} else if (e.state().equals(IdleState.READER_IDLE)) {
					control.exit(ctx);
				}
			}
		}
	}
}