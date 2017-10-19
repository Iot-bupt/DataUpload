package com.KafkaUpload.upload.handler;

import com.KafkaUpload.Device;
import com.KafkaUpload.ThingBoardProxy;

import java.util.HashMap;

/**
 * Created by tangjialiang on 2017/10/19.
 *
 * 对从Kafka拉取到的数据处理  -- type : attributions
 */
public class upLoadAttributionsHandler extends upLoadDataHandler {
    private String uId ;
    private String dataType ;
    private String info ;
    private ThingBoardProxy tp ;

    public upLoadAttributionsHandler(ThingBoardProxy tp, String uId, String dataType, String info, HashMap<String, Device> deviceMapper) {
        super(deviceMapper);

        this.tp = tp ;
        this.uId = uId ;
        this.dataType = dataType ;
        this.info = info ;
    }

    @Override
    public void process() {
        // 进行数据合法性对齐 from redis ==> get accessToken
        

        // 向thingsboard发送数据 telemetry


    }
}
