package com.csy.live.node.tcp.bean.po;

/**
 * 聊天记录po
 *
 */
public class ChatMsg {
	// 说话人id
	private Integer talkerId;
	// 接收人id
	private Integer listenerId;
	// 群id
	private Integer roomId;
	// 消息体
	private String content;
	// 聊天时间
	private long time;
	// 消息id
	private String msgId;

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getTalkerId() {
		return talkerId;
	}

	public void setTalkerId(Integer talkerId) {
		this.talkerId = talkerId;
	}

	public Integer getListenerId() {
		return listenerId;
	}

	public void setListenerId(Integer listenerId) {
		this.listenerId = listenerId;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

}
