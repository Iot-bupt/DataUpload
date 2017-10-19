package com.KafkaUpload.upload.handler;

/**
 * Created by tangjialiang on 2017/10/19.
 *
 * 对从Kafka拉取到的数据处理  -- type : attributions
 */
public class upLoadAttributionsHandler extends upLoadDataHandler {
    private String deviceId ;
    private String type ;
    private String info ;

    public upLoadAttributionsHandler(String deviceId, String type, String info) {
        this.deviceId = deviceId ;
        this.type = type ;
        this.info = info ;
    }

    @Override
    public void process() {
        // 进行数据合法性对齐 from redis

        // 向thingsboard发送数据 telemetry
    }
}
