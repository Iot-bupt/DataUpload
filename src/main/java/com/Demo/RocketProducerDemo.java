package com.Demo;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;

import java.util.Random;

/**
 * Created by tangjialiang on 2017/10/23.
 */
public class RocketProducerDemo {

    public static void main(String[] args) throws MQClientException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("unique_group_name_quickstart");
        producer.setNamesrvAddr("10.108.219.158:9876");
        producer.setInstanceName("QuickStartProducer");
        producer.start();

        for (int i = 0; i < 100; i++) {
            try {

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 温度随机数
                int max=20;
                int min=10;
                Random random = new Random();
                int temperature = random.nextInt(max)%(max-min+1) + min;
                int type = (random.nextInt(max)%(max-min+1) + min)%2;
                String dataType = (type==0) ? ("telemetry") : ("attributions") ;
                String info = "{\"uId\":\"uid1231231231\", \"dataType\":\""+dataType+"\", \"info\":{\"temperature\":\""+temperature+"\"}, \"deviceName\":\"tjl's Demo VDevice\"}" ; // "":""

                Message msg = new Message("hello",// topic
                        "device",
                        info.getBytes()// body
                );
                SendResult sendResult = producer.send(msg);

                System.out.println(sendResult);
            } catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(1000);
            }
        }

//        producer.shutdown();
    }
}
