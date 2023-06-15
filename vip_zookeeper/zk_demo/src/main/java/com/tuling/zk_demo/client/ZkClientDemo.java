package com.tuling.zk_demo.client;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * @Author Fox
 */
public class ZkClientDemo {

    private static final  String  CONNECT_STR="localhost:2181";
    private final static  String CLUSTER_CONNECT_STR="192.168.1.150:2181,192.168.1.90:2181,192.168.1.91:2181";

    public static void main(String[] args) throws Exception {

        final CountDownLatch countDownLatch=new CountDownLatch(1);

        ZooKeeper zooKeeper = new ZooKeeper(CLUSTER_CONNECT_STR,
                4000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if(Event.KeeperState.SyncConnected==event.getState()
                        && event.getType()== Event.EventType.None){
                    //如果收到了服务端的响应事件，连接成功
                    countDownLatch.countDown();
                    System.out.println("连接建立");
                }
            }
        });
        System.out.printf("连接中");
        countDownLatch.await();
        //CONNECTED
        System.out.println(zooKeeper.getState());

        Stat stat = zooKeeper.exists("/user",false);
        if(null ==stat){
            //创建持久节点
            zooKeeper.create("/user","fox".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }

        //持久监听
        zooKeeper.addWatch("/user",new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println(event);
            }
        },AddWatchMode.PERSISTENT);


        Thread.sleep(Integer.MAX_VALUE);

    }

}
