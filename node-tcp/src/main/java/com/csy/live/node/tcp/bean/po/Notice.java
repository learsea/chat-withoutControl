package com.csy.live.node.tcp.bean.po;

public class Notice {
	// 通知类型
	private String type;
	// 通知接收人id
	private Integer receiverUid;
	// 操作人id
	private Integer operatorUid;
	// 被操作人id
	private Integer relatedUid;
	// 被操作房间id
	private Integer relatedRid;
	// 操作时间
	private long createtime;
	// 消息id
	private String msgId;
	// 附加内容
	private String content;

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public Integer getRelatedRid() {
		return relatedRid;
	}

	public void setRelatedRid(Integer relatedRid) {
		this.relatedRid = relatedRid;
	}

	public Integer getRelatedUid() {
		return relatedUid;
	}

	public void setRelatedUid(Integer relatedUid) {
		this.relatedUid = relatedUid;
	}

	public long getCreatetime() {
		return createtime;
	}

	public void setCreatetime(long createtime) {
		this.createtime = createtime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getReceiverUid() {
		return receiverUid;
	}

	public void setReceiverUid(Integer receiverUid) {
		this.receiverUid = receiverUid;
	}

	public Integer getOperatorUid() {
		return operatorUid;
	}

	public void setOperatorUid(Integer operatorUid) {
		this.operatorUid = operatorUid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
