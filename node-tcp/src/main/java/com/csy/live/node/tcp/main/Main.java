package com.csy.live.node.tcp.main;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.csy.live.common.utils.ThreadPool;
import com.csy.live.node.tcp.bootstrap.ServerInit;
import com.csy.live.node.tcp.properties.Conf;
import com.csy.live.node.tcp.utils.HttpClientUtil;
import com.csy.live.node.tcp.websocket.bootstrap.WebsocketInit;
import com.dbay.apns4j.utils.APNSUtils;

/**
 * 服务启动类
 */
@Component
public class Main implements ApplicationContextAware {
	private static Logger logger = Logger.getLogger(Main.class);
	public static ApplicationContext context = null;
	public static Conf conf = null;

	@Value("${socket.on}")
	private boolean socketOn;

	@Value("${websocket.on}")
	private boolean websocketOn;

	@Value("${ios.on}")
	private boolean iosOn;

	@PreDestroy
	public void destroy() {
		// 关闭线程池
		ThreadPool.shutdownNow();
		// 销毁httpclient
		try {
			HttpClientUtil.CLIENT.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@PostConstruct
	/** 服务初始化 */
	public void doInit() {
		logger.info("信息：starting server");
		if (iosOn) {
			logger.info("正在开启ios推送服务");
			APNSUtils.initAPNS();
		}
		if (socketOn) {
			logger.info("正在开启socket服务");
			// 开启tcp聊天服务
			ThreadPool.getExecutorService().execute(new ServerInit());
		}
		if (websocketOn) {
			logger.info("正在开启websocket服务");
			// 开启websocket聊天服务
			ThreadPool.getExecutorService().execute(new WebsocketInit());
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
		conf = context.getBean(Conf.class);
	}
}