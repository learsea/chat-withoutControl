package com.csy.live.node.tcp.websocket.bootstrap.handler;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class MsgCodec extends MessageToMessageCodec<TextWebSocketFrame, String> {

	@Override
	protected void encode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
		out.add(new TextWebSocketFrame(msg));
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, TextWebSocketFrame msg, List<Object> out) throws Exception {
		out.add(msg.text());
	}

}
