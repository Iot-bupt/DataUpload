package com.KafkaUpload;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by tangjialiang on 2017/10/18.
 *
 * 从Kafka中拉取数据。并分发数据处理
 */

public class UpDataConsumer implements Runnable {

    // config
    private String host ;
    private int port ;
    private final AtomicBoolean closed = new AtomicBoolean(false);
    private ThingBoardProxy tp = null ;
    KafkaConsumer<String, String> consumer ;
    private UpDataConsumerImpl impl ;


    public UpDataConsumer(String host, int port) {
        this.host = host ;
        this.port = port ;
    }

    public UpDataConsumer(String host, int port, ThingBoardProxy tp) {
        this.host = host ;
        this.port = port ;
        this.tp = tp ;
        this.impl = new UpDataConsumerImpl(host, port, tp) ;
    }

    public void run() {
        try {
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
            while (!closed.get()) {
                ConsumerRecords<String, String> records = consumer.poll(10000);
                // Handle new records
                for (final ConsumerRecord<String, String> rc : records) {
                    String msg = rc.value() ;
                    System.out.println("msg=" + rc.value());

                    UpDataConsumerImpl impl = new UpDataConsumerImpl(host, port, tp) ;
                    impl.process(msg);
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

    // Shutdown hook which can be called from a separate thread
    public void shutdown() {
        closed.set(true);
        consumer.wakeup();
    }

    // --------------- test ---------------
    public static void main(String[] args) {
        String host = "127.0.0.1" ;
        int port = 123 ;

        UpDataConsumer sub1 = new UpDataConsumer(host, port);
        Thread tsub1 = new Thread(sub1);
        tsub1.start();
    }

}