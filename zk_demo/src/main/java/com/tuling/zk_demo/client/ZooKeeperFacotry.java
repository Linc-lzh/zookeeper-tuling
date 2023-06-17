package com.tuling.zk_demo.client;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

public class ZooKeeperFacotry {
    private static final int SESSION_TIMEOUT = 5000;

    public static ZooKeeper create(String connectionString) throws Exception {
        final CountDownLatch connectionLatch = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper(connectionString, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getType()== Event.EventType.None
                        && event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                    connectionLatch.countDown();
                    System.out.println("连接建立");
                }
            }
        });

        System.out.println("等待连接建立...");
        connectionLatch.await();

        return zooKeeper;
    }

}
