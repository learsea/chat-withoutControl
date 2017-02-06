package com.csy.live.common.bean.vo;

import com.alibaba.fastjson.JSONObject;
import com.csy.live.common.constant.Status;
import com.csy.live.common.constant.Operation;

public class SuccessResult {
	public static JSONObject getResult() {
		JSONObject result = new JSONObject();
		result.put("status", Status._100.getStatus());
		result.put("msg", "ok");
		return result;
	}

	public static JSONObject getResult(Operation operation) {
		JSONObject result = new JSONObject();
		result.put("operation", operation);
		result.put("status", Status._100.getStatus());
		result.put("msg", "ok");
		return result;
	}
}
