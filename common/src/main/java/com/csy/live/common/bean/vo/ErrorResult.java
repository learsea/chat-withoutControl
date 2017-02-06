package com.csy.live.common.bean.vo;

import com.alibaba.fastjson.JSONObject;
import com.csy.live.common.constant.Status;
import com.csy.live.common.constant.Operation;

public class ErrorResult {
	public static JSONObject getResult(Status code, String errMsg) {
		JSONObject result = new JSONObject();
		result.put("status", code.getStatus());
		result.put("msg", errMsg);
		return result;
	}

	public static JSONObject getResult(Operation operation, Status code, String errMsg) {
		JSONObject result = new JSONObject();
		result.put("operation", operation);
		result.put("status", code.getStatus());
		result.put("msg", errMsg);
		return result;
	}
}
