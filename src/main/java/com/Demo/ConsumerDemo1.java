package com.Demo;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

/**
 * Created by tangjialiang on 2017/10/18.
 */


public class ConsumerDemo1 implements Runnable {
    private final AtomicBoolean closed = new AtomicBoolean(false);
    KafkaConsumer<String, String> consumer;// = new KafkaConsumer<>(props);

    public void run() {
        try {
            Properties props = new Properties();
            props.put("bootstrap.servers", "10.108.218.58:9092");
            props.put("group.id", "test");
            props.put("enable.auto.commit", "true");
            props.put("auto.commit.interval.ms", "1000");
            props.put("session.timeout.ms", "30000");
            props.put("key.deserializer",
                    "org.apache.kafka.common.serialization.StringDeserializer");
            props.put("value.deserializer",
                    "org.apache.kafka.common.serialization.StringDeserializer");
            consumer = new KafkaConsumer(props);
            consumer.subscribe(Arrays.asList("test"));
            while (!closed.get()) {
                ConsumerRecords<String, String> records = consumer.poll(1000);
                // Handle new records
                for (final ConsumerRecord<String, String> rc : records) {
                    System.out.println("msg=" + rc.value());
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
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

    // Shutdown hook which can be called from a separate thread
    public void shutdown() {
        closed.set(true);
        consumer.wakeup();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ConsumerDemo1 sub1 = new ConsumerDemo1();
        Thread tsub1 = new Thread(sub1);
        tsub1.start();
    }

}