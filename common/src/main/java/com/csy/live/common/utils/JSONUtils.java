package com.csy.live.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.csy.live.common.constant.Status;
import com.csy.live.common.exception.BusinessException;

public class JSONUtils {
	public static String parse(Object o) {
		return JSONObject.toJSONString(o) + "\0";
	}

	public static int getInt(JSONObject in, String param) {
		try {
			int i = in.getIntValue(param);
			return i;
		} catch (Exception e) {
			throw new BusinessException(Status._300, "参数错误," + param);
		}
	}

	public static String getString(JSONObject in, String param) {
		String s = in.getString(param);
		if (s == null) {
			throw new BusinessException(Status._300, "缺少参数," + param);
		}
		return s;
	}
}
