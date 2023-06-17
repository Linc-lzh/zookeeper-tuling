package com.tuling.zk_demo.client;

public class ClusterBase extends StandaloneBase {

    private final static  String CLUSTER_CONNECT_STR="192.168.1.150:2181,192.168.1.90:2181,192.168.1.91:2181";


    private static final  int CLUSTER_SESSION_TIMEOUT=60 * 1000;


    @Override
    protected String getConnectStr() {
        return CLUSTER_CONNECT_STR;
    }

    @Override
    protected int getSessionTimeout() {
        return CLUSTER_SESSION_TIMEOUT;
    }
}
