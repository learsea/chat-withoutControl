package com.csy.live.node.tcp.dao.iface;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.csy.live.node.tcp.bean.po.ChatMsg;

/**
 * 聊天持久层类
 * 
 * @author caishiyu
 */
@Repository
public interface ChatDao {

	/**
	 * 保存聊天记录
	 */
	public int saveMessage(ChatMsg message);

	/**
	 * 根据用户id获取未读单聊记录
	 */
	public List<ChatMsg> getMessages(int userId);

	/**
	 * 根据用户id删除所有单聊记录
	 */
	public int delMessages(int userId);
}
