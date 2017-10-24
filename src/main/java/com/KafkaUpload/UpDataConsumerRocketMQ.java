package com.KafkaUpload;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * Created by tangjialiang on 2017/10/23.
 *
 */

public class UpDataConsumerRocketMQ extends UpDataConsumer {

    DefaultMQPushConsumer consumer ;

    public UpDataConsumerRocketMQ(String thingsboardHost, int thingsboardBort, ThingBoardProxy tp) {
        super(thingsboardHost, thingsboardBort, tp);
        init() ;
    }

    public void init() {
        consumer = new DefaultMQPushConsumer("unique_group_name_quickstart");

        //consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET); // not need

        consumer.setNamesrvAddr("10.108.219.158:9876");
        consumer.setInstanceName("QuickStartConsumer");
        consumer.setConsumeConcurrentlyMaxSpan(3);

        try {
            consumer.subscribe("TopicTest", "*");
        } catch (MQClientException e) {
            System.out.println(e) ;
            e.printStackTrace();
        }

        // 在该线程中转载监听器（之后退出该线程），也可以写在其他位置（只要执行一遍即可）。
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                System.out.println(Thread.currentThread().getName() + " Receive New Messages: " + msgs);
                for (Message msg :msgs){
                    String content = new String(msg.getBody()) ;
                    System.out.println(content) ;
                    doMessage(content);
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        try {
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }


    public void run() {

    }


    public void shutdown() {
        closed.set(true);
        consumer.shutdown() ;
    }
}
