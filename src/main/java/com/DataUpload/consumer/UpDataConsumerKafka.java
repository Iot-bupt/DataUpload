package com.DataUpload.consumer;

import com.DataUpload.platform.ThingBoardProxy;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;

/**
 * Created by tangjialiang on 2017/10/23.
 */
public class UpDataConsumerKafka extends UpDataConsumer {

    KafkaConsumer<String, String> consumer ;

    public UpDataConsumerKafka(String thingsboardHost, int thingsboardPort, ThingBoardProxy tp) {
        super(thingsboardHost, thingsboardPort, tp) ;
        init() ;
    }

    public void init() {
        Properties props = new Properties() ;
        props.put("bootstrap.servers", "10.108.218.58:9092");
        props.put("group.id", UUID.randomUUID().toString());
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");

        consumer = new KafkaConsumer(props);
        consumer.subscribe(Arrays.asList("test"));

    }

    public void run() {
        try {
            while (!closed.get()) {
                ConsumerRecords<String, String> records = consumer.poll(10000);
                // Handle new records
                for (final ConsumerRecord<String, String> rc : records) {
                    String msg = rc.value() ;
                    System.out.println("msg=" + rc.value());

                    doMessage(msg) ;
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // todo
                    e.printStackTrace();
                }
            }
        } catch (WakeupException e) {
            // Ignore exception if closing
            if (!closed.get())
                throw e;
        } finally {
            consumer.close();
        }
    }

    public void shutdown() {
        closed.set(true);
        consumer.wakeup();
    }
}
