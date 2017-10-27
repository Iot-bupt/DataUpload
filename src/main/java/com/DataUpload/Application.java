package com.DataUpload;


import com.DataUpload.consumer.UpDataConsumer;
import com.DataUpload.consumer.UpDataConsumerRocketMQ;
import com.DataUpload.platform.ThingBoardProxy;

/**
 * Created by tangjialiang on 2017/10/25.
 *
 * get device from mesg queue and send to iot paltom
 *
 */
public class Application {

    public static void main(String[] args) {
        // load the configurations
        String host = "10.108.218.58";
        int port = 8080;

        try {
            String username = "tenant@thingsboard.org" ;
            String password = "tenant" ;

            ThingBoardProxy tp = new ThingBoardProxy(host, port, username, password);

            UpDataConsumer consumer = new UpDataConsumerRocketMQ(host, port, tp);

            Thread thread = new Thread(consumer);
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
