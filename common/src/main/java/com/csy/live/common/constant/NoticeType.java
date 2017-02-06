package com.csy.live.common.constant;

/**
 * 通知类型
 * 
 * @author pxw
 *
 */
public enum NoticeType {
	type,
	// 挤下线
	kickDisconnect,
	// 超时下线
	timeoutDisconnect,

	// 单聊通知
	chat,
	// 群聊通知
	groupChat,

	// 被T出群通知
	beTG,
}
