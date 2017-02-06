package com.csy.live.common.exception;

import com.csy.live.common.constant.Status;

/**
 * 自定义业务层异常
 * 
 * @author caishiyu
 */
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = -1353943450805355235L;
	private Status status;

	/**
	 * 自定义错误信息
	 */
	public BusinessException(Status status, String message) {
		super(message);
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
