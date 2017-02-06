package com.csy.live.common.constant;

public enum Status {
	// 1开头:成功，2开头:用户错误，3开头:客户端编码造成的参数错误，4开头:服务器错误
	/**
	 * 成功
	 */
	_100,

	/**
	 * 用户输入错误
	 */
	_200,

	/**
	 * 客户端参数错误，该错误只能通过修改客户端代码解决
	 */
	_300,

	/**
	 * 服务端处理错误 ，该错误只能通过修改服务端代码解决
	 */
	_400,

	/**
	 * 数据库错误，该错误一般需要修改服务端代码，或者更改数据库
	 */
	_401;

	private int status;

	Status() {
		this.status = Integer.valueOf(this.toString().substring(1));
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
