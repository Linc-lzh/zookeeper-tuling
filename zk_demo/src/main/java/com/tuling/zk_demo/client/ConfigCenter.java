package com.tuling.zk_demo.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ConfigCenter {

    private final static  String CONNECT_STR="192.168.1.150:2181";


    public static void main(String[] args) throws Exception {

        ZooKeeper zooKeeper= ZooKeeperFacotry.create(CONNECT_STR);


        MyConfig myConfig = new MyConfig();
        myConfig.setKey("anykey");
        myConfig.setName("anyName");

        ObjectMapper objectMapper=new ObjectMapper();

        byte[] bytes = objectMapper.writeValueAsBytes(myConfig);
        //创建持久节点  create /myconfig
        zooKeeper.create("/myconfig", bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        Watcher watcher = new Watcher() {
            @SneakyThrows
            @Override
            public void process(WatchedEvent event) {
                if (event.getType()== Event.EventType.NodeDataChanged
                        && event.getPath()!=null && event.getPath().equals("/myconfig")){
                    log.info(" PATH:{}  发生了数据变化" ,event.getPath());
                    //获取配置信息
                    byte[] data = zooKeeper.getData("/myconfig", this, null);
                    MyConfig newConfig = objectMapper.readValue(new String(data), MyConfig.class);
                    log.info("数据发生变化: {}",newConfig);

                }


            }
        };

        byte[] data = zooKeeper.getData("/myconfig", watcher, null);
        MyConfig originalMyConfig = objectMapper.readValue(new String(data), MyConfig.class);
        log.info("原始数据: {}", originalMyConfig);


        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

}
