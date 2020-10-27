package com.atguigu.springcloud.controller;


import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HelloZK {

    private static final Logger logger= LoggerFactory.getLogger(HelloZK.class);

    private final static String CONNECTSTRING="192.168.80.133:2181";
    private final static String PATH="/test";
    private final static int SESSION_TIMEOUT=50*1000;



    /*开启ZooKeeper  获取实例对象*/
    public ZooKeeper startZK() throws IOException {
        return new ZooKeeper(CONNECTSTRING, SESSION_TIMEOUT, new Watcher() {
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }
    /*关闭ZooKeeper 关闭实例对象流*/
    public void stopZK(ZooKeeper zk) throws InterruptedException {
        if(zk != null){
            zk.close();
        }
    }
    /*创建一个Znode节点*/
    public void createZnode(ZooKeeper zk,String nodePath,String nodeValue) throws KeeperException, InterruptedException {
        zk.create(nodePath, nodeValue.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    /*获取一个Znode节点*/
    public String getZnode(ZooKeeper zk,String nodePath) throws KeeperException, InterruptedException {
        String result=null;
        byte[] data = zk.getData(nodePath, false, new Stat());
        result=new String(data);

        return result;
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        HelloZK hello = new HelloZK();

        ZooKeeper zk = hello.startZK();

        Thread.sleep(3000);

        //判断当前节点下有没有这个名称的路径
        if(zk.exists(PATH, false) == null){
            System.out.println("**********");
            //没有就创建
            hello.createZnode(zk,PATH,"hello");
            String resultValue = hello.getZnode(zk, PATH);
            System.out.println("*****resultValue:"+resultValue);
            logger.info("*****resultValue:"+resultValue);
        }else {
            //有就获取这个值
            System.out.println("*****i have this node");
            logger.info("*****i have this node");
        }
        hello.stopZK(zk);
    }
}
