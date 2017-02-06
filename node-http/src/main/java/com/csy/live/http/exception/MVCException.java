package com.csy.live.http.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.csy.live.common.bean.vo.ErrorResult;
import com.csy.live.common.constant.Status;

@ControllerAdvice
public class MVCException {
	@ResponseBody
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public String fileTooBig() {
		return ErrorResult.getResult(Status._200, "文件过大").toJSONString();
	}
}
