package com.Demo;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * Created by tangjialiang on 2017/10/18.
 */


public class ProducerDemo1 {
    /**
     * @param args
     */
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "10.108.218.64:9092");
        props.put("metadata.broker.list","10.108.218.64:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer(props);
        // 发送业务消息
        // 读取文件 读取内存数据库 读socket端口
        for (int i = 1; i <= 100; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ProducerRecord<String, String> mesg = new ProducerRecord<String, String>("test",
                    i+" said "+ i + " love you baby for " + i + " times,will you have a nice day with me tomorrow") ;

            System.out.format("topic: %s info: %s\n", "test", mesg.value()) ;
            producer.send(mesg);

        }
    }
}

// fastjson
//


// bin/zookeeper-server-start.sh config/zookeeper.properties &
// bin/kafka-server-start.sh config/server.properties &

// ./bin/kafka-topics  --delete --zookeeper 10.108.218.64:2181  --topic wordcount
// bin/kafka-console-consumer.sh --zookeeper 10.108.218.64:2181 --topic test --from-beginning
// bin/kafka-console-producer.sh --broker-list 10.108.218.64:9092 --topic test
// bin/kafka-topics.sh --list --zookeeper 10.108.218.64:2181