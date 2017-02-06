package com.csy.live.http.interceptor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.csy.live.common.bean.vo.ErrorResult;
import com.csy.live.common.constant.Status;
import com.csy.live.http.constant.UrlPrefix;
import com.csy.live.http.redis.RUserDao;

public class LoginInterceptor implements HandlerInterceptor {
	@Autowired
	private RUserDao rUserDao;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String url = request.getServletPath();
		if (url.contains(UrlPrefix.NO_NEED_LOGIN_PRE)) {
			return true;
		} else {
			String userId = request.getParameter("userId");
			String token = request.getParameter("token");
			if (rUserDao.check(userId, token)) {
				return true;
			}
			// 只有在返回false的时候才设置response，以保证不影响控制层
			response.setContentType("text/html;charset=UTF-8");
			ServletOutputStream stream = response.getOutputStream();
			stream.write(ErrorResult.getResult(Status._200, "请重新登录").toJSONString().getBytes());
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
