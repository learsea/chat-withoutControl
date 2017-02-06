package com.csy.live.http.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class SpringStartupListener implements ApplicationListener<ContextRefreshedEvent> {
	private boolean isSet = false;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (!isSet) {
			// 插入逻辑
			isSet = true;
		}
	}
}
