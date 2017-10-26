package com.DataUpload.consumer;

import com.DataUpload.consumer.handler.upLoadAttributionsHandler;
import com.DataUpload.consumer.handler.upLoadDataHandler;
import com.DataUpload.consumer.handler.upLoadTelemetryHandler;
import com.DataUpload.platform.ThingBoardProxy;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by tangjialiang on 2017/10/19.
 *
 * 从kafka拉到数据后的消息处理分发
 *
 */
public class UpDataConsumerImpl {

    private String host ;
    private int port ;
    ThingBoardProxy tp ;

    public UpDataConsumerImpl(String host, int port, ThingBoardProxy tp) {
        this.host = host ;
        this.port = port ;
        this.tp = tp ;
    }


    public void process (String msg) {
        JSONObject pareseedMsg = JSON.parseObject(msg) ;

        try {
            String uId = (String)pareseedMsg.get("uId") ;
            String dataType = (String)pareseedMsg.get("dataType") ;
            String info = pareseedMsg.get("info").toString() ;
            String deviceName = (String)pareseedMsg.get("deviceName") ;

            // 分发数据并处理
            if (dataType.equals("attributions")) {
                getAttributeHandler(uId, dataType, info, deviceName).process() ;
            } else if (dataType.equals("telemetry")) {
                getTelemetryHandler(uId, dataType, info, deviceName).process() ;
            }

        } catch (Exception e) {
            // todo
            System.out.println("Error in parse JSON string") ;
        }
    }

    private upLoadDataHandler getAttributeHandler(String deviceId, String type, String info, String deviceName) {
        return new upLoadAttributionsHandler(tp, deviceId, type, info, deviceName);
    }

    private upLoadDataHandler getTelemetryHandler(String deviceId, String type, String info, String deviceName) {
        return new upLoadTelemetryHandler(tp, deviceId, type, info, deviceName);
    }

    // --------------- test ---------------

    public static void main(String[] args) {
        String mesg = "{\"uId\":\"uid1231231231\", \"dataType\":\"telemetry\", \"info\":{\"uId\":\"uid1231231231\"}, \"deviceName\":\"tjl's Demo VDevice\"}" ;//"temperature""16"

        JSONObject parse = JSON.parseObject(mesg);
        Object deviceId = parse.get("deviceId");
        Object infoType = parse.get("infoType");
        Object info = parse.get("msg");

        System.out.format("deviceId: %s infoType: %s msg: %s", deviceId, infoType, info) ;
        System.out.println(parse) ;
    }
}
