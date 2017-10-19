package com.KafkaUpload;

import com.KafkaUpload.upload.handler.upLoadAttributionsHandler;
import com.KafkaUpload.upload.handler.upLoadDataHandler;
import com.KafkaUpload.upload.handler.upLoadTelemetryHandler;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by tangjialiang on 2017/10/19.
 *
 * 从kafka拉到数据后的消息处理分发
 *
 */
public class UpDataConsumerImpl {
    public void process (String msg) {
        JSONObject pareseedMsg = JSON.parseObject(msg) ;

        try {
            String deviceId = (String)pareseedMsg.get("deviceId") ;
            String type = (String)pareseedMsg.get("infoType") ;
            String info = (String)pareseedMsg.get("msg") ;

            // 分发数据并处理
            if (type.equals("attributions")) {
                getAttributeHandler(deviceId, type, info).process() ;
            } else if (type.equals("telemetry")) {
                getTelemetryHandler(deviceId, type, info).process() ;
            }

        } catch (Exception e) {
            // todo
            System.out.println("Error in parse JSON string") ;
        }
    }

    private upLoadDataHandler getAttributeHandler(String deviceId, String type, String info) {
        return new upLoadAttributionsHandler(deviceId, type, info);
    }

    private upLoadDataHandler getTelemetryHandler(String deviceId, String type, String info) {
        return new upLoadTelemetryHandler(deviceId, type, info);
    }

    // --------------- test ---------------

    public static void main(String[] args) {
        String msg = "{\"deviceId\":\"qwe\", \"infoType\":\"attributions\", \"msg\":\"{}}\"}" ;

        JSONObject parse = JSON.parseObject(msg);
        Object deviceId = parse.get("deviceId");
        Object infoType = parse.get("infoType");
        Object info = parse.get("msg");

        System.out.format("deviceId: %s infoType: %s msg: %s", deviceId, infoType, info) ;
        System.out.println(parse) ;
    }
}
