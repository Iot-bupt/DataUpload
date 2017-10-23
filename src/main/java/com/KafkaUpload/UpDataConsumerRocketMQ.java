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
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("unique_group_name_quickstart");

        //consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET); // not need

        consumer.setNamesrvAddr("172.30.248.219:9876");
        consumer.setInstanceName("QuickStartConsumer");

        try {
            consumer.subscribe("test", "TagA3");
        } catch (MQClientException e) {
            System.out.println(e) ;
            e.printStackTrace();
        }
    }


    public void run() {
        // 在该线程中转载监听器（之后退出该线程），也可以写在其他位置（只要执行一遍即可）。
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                System.out.println(Thread.currentThread().getName() + " Receive New Messages: " + msgs);
                for (Message msg :msgs){
                    System.out.println(new String(msg.getBody())) ;
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
    }


    public void shutdown() {
        closed.set(true);
        consumer.shutdown() ;
    }
}
