package com.csy.live.node.tcp.utils;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpClientUtil {
	public static final CloseableHttpClient CLIENT = HttpClients.createDefault();
}
