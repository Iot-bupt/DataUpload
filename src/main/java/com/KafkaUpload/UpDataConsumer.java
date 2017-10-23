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

public abstract class UpDataConsumer implements Runnable {

    // config
    protected String host ;
    protected int port ;
    protected ThingBoardProxy tp = null ;
    protected UpDataConsumerImpl impl ;
    protected final AtomicBoolean closed = new AtomicBoolean(false);

    public UpDataConsumer(String host, int port, ThingBoardProxy tp) {
        this.host = host ;
        this.port = port ;
        this.tp = tp ;
        this.impl = new UpDataConsumerImpl(host, port, tp) ;
    }

    // Shutdown hook which can be called from a separate thread
    public abstract void shutdown() ;

    // --------------- test ---------------
    public static void main(String[] args) {
        String host = "127.0.0.1" ;
        int port = 123 ;

        UpDataConsumer sub1 = new UpDataConsumerKafka(host, port, null);
        Thread tsub1 = new Thread(sub1);
        tsub1.start();
    }

}