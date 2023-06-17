package com.tuling.zk_demo.curator;


public  class CuratorClusterBase extends CuratorStandaloneBase {

    private final static  String CLUSTER_CONNECT_STR="192.168.1.150:2181,192.168.1.90:2181,192.168.1.91:2181";

    public   String getConnectStr() {
        return CLUSTER_CONNECT_STR;
    }
}
