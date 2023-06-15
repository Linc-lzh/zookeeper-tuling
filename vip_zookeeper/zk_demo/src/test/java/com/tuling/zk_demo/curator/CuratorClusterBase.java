package com.tuling.zk_demo.curator;


public  class CuratorClusterBase extends CuratorStandaloneBase {

    private final static  String CLUSTER_CONNECT_STR="192.168.65.156:2181,192.168.65.190:2181,192.168.65.200:2181";

    public   String getConnectStr() {
        return CLUSTER_CONNECT_STR;
    }
}
