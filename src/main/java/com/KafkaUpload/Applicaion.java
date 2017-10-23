package com.KafkaUpload;

/**
 * Created by tangjialiang on 2017/10/18.
 *
 * 从kafka中获取设备发送的消息数据, 根据设备的uId得到设备的deviceId（异常：新建设备），
 * 并把消息发送（方式http）到thingsboard。
 *
 * 数据范围： attributes，telemetry
 */

public class Applicaion {

    public static void main(String[] args) {
        // load the configurations
        String host = "10.108.218.58";
        int port = 8080;

        try {
            String username = "tenant@thingsboard.org" ;
            String password = "tenant" ;

            ThingBoardProxy tp = new ThingBoardProxy(host, port, username, password);

            UpDataConsumer cusmer = new UpDataConsumer(host, port, tp);

            Thread thread = new Thread(cusmer);
            thread.start();
        } catch (Exception e) {
            // todo
            System.out.println(e) ;
        } finally {
            // todo
            // clean up env.
        }
    }
}
