package com.KafkaUpload.upload.handler;

import com.KafkaUpload.Device;
import com.KafkaUpload.ThingBoardProxy;

import java.util.HashMap;

/**
 * Created by tangjialiang on 2017/10/19.
 *
 * 对从Kafka拉取到的数据处理 -- type : telemetry
 */
public class upLoadTelemetryHandler extends upLoadDataHandler {

    private String uId ;
    private String dataType ;
    private String info ;
    private ThingBoardProxy tp ;

    public upLoadTelemetryHandler(ThingBoardProxy tp, String uId, String dataType, String info, HashMap<String, Device> deviceMapper) {
        super(deviceMapper) ;

        this.uId = uId ;
        this.dataType = dataType ;
        this.info = info ;
        this.tp = tp ;
    }

    @Override
    public void process() {
        // 进行设备数据合法性对齐 from redis ==> get accessToken


        // 向thingsboard发送数据 attributions
    }
}
