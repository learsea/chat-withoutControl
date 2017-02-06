package com.csy.live.node.tcp.properties;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Conf {
	private Logger logger = Logger.getLogger(Conf.class);
	private String host;

	@Value("${http.port}")
	private int httpPort;

	@Value("${socket.port}")
	private int socketPort;

	@Value("${websocket.port}")
	private int websocketPort;

	public Conf() throws UnknownHostException {
		host = getLocalIP();
		logger.info("localhost ip:" + host);
	}

	public int getHttpPort() {
		return httpPort;
	}

	public String getHost() {
		return host;
	}

	public int getSocketPort() {
		return socketPort;
	}

	public int getWebsocketPort() {
		return websocketPort;
	}

	/**
	* 判断操作系统类型
	*/
	private boolean isWindowsOS() {
		boolean isWindowsOS = false;
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("windows") > -1) {
			isWindowsOS = true;
		}
		return isWindowsOS;
	}

	/**
	  * 获取本机ip地址，并自动区分Windows还是linux操作系统
	  * @return String
	  */
	private String getLocalIP() {
		String sIP = "";
		InetAddress ip = null;
		try {
			// 如果是Windows操作系统
			if (isWindowsOS()) {
				ip = InetAddress.getLocalHost();
			}
			// 如果是Linux操作系统
			else {
				boolean bFindIP = false;
				Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface
						.getNetworkInterfaces();
				while (netInterfaces.hasMoreElements()) {
					if (bFindIP) {
						break;
					}
					NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
					if (ni.isLoopback() || ni.isVirtual() || !ni.isUp()) {
						continue;
					}
					// 特定情况，可以考虑用ni.getName判断
					// 遍历所有ip
					Enumeration<InetAddress> ips = ni.getInetAddresses();
					while (ips.hasMoreElements()) {
						ip = (InetAddress) ips.nextElement();
						if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() // 127.开头的都是lookback地址
								&& ip.getHostAddress().indexOf(":") == -1) {
							bFindIP = true;
							break;
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (null != ip) {
			sIP = ip.getHostAddress();
		}
		return sIP;
	}

}
