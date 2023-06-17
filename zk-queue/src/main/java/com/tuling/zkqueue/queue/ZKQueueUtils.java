package com.tuling.zkqueue.queue;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.queue.DistributedQueue;
import org.apache.curator.framework.recipes.queue.QueueBuilder;
import org.apache.curator.framework.recipes.queue.QueueSerializer;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZKQueueUtils {
    final  static CuratorFramework client;
    private static DistributedQueue<String> queue;
    private static  String queueName = "/order";
 

    static{
        // 初始化连接
        client = CuratorFrameworkFactory.builder().connectString("192.168.65.204:2181")
                .retryPolicy(new ExponentialBackoffRetry(100,1)).build();
        client.start();
        //创建队列
        createQueue();
 
    }
 
 
    public static CuratorFramework getClient(){
        return ZKQueueUtils.client;
    }
 

 
    public static void closeClient(){
        client.close();
    }
 
    /**
     * 创建队列
     * @param
     */
    public static void createQueue(){
        QueueBuilder<String> builder = QueueBuilder.builder(client, null, createQueueSerializer(), queueName);
        queue = builder.buildQueue();
        try {
            queue.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 创建队列
     * @param
     */
    public static DistributedQueue getQueue(){
        return queue;
    }
 
    public static void setQueueData(String data){
        try {
            queue.put(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void closeQueue(){
        try {
            queue.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
 
    public static QueueSerializer<String> createQueueSerializer() {
        return new QueueSerializer<String>(){
            @Override
            public byte[] serialize(String item) {
                return item.getBytes();
            }
            @Override
            public String deserialize(byte[] bytes) {
                return new String(bytes);
            }
 
        };
    }
}
