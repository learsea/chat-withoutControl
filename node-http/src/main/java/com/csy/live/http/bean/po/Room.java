package com.csy.live.http.bean.po;

public class Room {
	private Integer roomId;
	// 房间创建人id
	private Integer userId;
	// 地址
	private String address;
	// 观众数量
	private Integer audNum = 0;
	// 直播地址
	private String url;
	// 房间显示优先级
	private Long priority;

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getAudNum() {
		return audNum;
	}

	public void setAudNum(Integer audNum) {
		this.audNum = audNum;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getPriority() {
		return priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

}
