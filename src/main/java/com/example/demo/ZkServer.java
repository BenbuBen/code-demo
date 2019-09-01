package com.example.demo;

import org.I0Itec.zkclient.ZkClient;

public class ZkServer {

	private final static String CONNECTSTRING = "127.0.0.1:2181";

	public static void main(String[] args) {
		ZkClient client = getInstance();
		// client.create("/fjh", 999, mode);
	}

	private static ZkClient getInstance() {
		return new ZkClient(CONNECTSTRING, 10000);
	}

}
