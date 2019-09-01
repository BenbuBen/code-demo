package com.example.demo;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZookeeperProSync implements Watcher {

	private static ZooKeeper zk = null;

	private static Stat stat = new Stat();

	public static void main(String[] args) throws Exception {
		String path = "/rootnode";

		zk = new ZooKeeper("127.0.0.1:2181", 5000, new ZookeeperProSync());

		System.out.println(new String(zk.getData(path, true, stat)));
	}

	@Override
	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub

	}

}
