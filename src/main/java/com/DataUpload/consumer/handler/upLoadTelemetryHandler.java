package com.DataUpload.consumer.handler;


import com.DataUpload.platform.ThingBoardProxy;
import com.DataUpload.utils.Device;

/**
 * Created by tangjialiang on 2017/10/19.
 *
 * 对从Kafka拉取到的数据处理 -- type : telemetry
 */
public class upLoadTelemetryHandler extends upLoadDataHandler {

    private String uId ;
    private String dataType ;
    private String info ;
    private String deviceName ;
    private ThingBoardProxy tp ;

    public upLoadTelemetryHandler(ThingBoardProxy tp, String uId, String dataType, String info, String deviceName) {

        this.uId = uId ;
        this.dataType = dataType ;
        this.info = info ;
        this.tp = tp ;
        this.deviceName = deviceName ;
    }

    @Override
    public void process() {
        // 向thingsboard发送数据 telemetry
        Device device = new Device(uId, deviceName) ;

        try {
            tp.sendTelelmetry(device, info);
        } catch (Exception e) {
            System.out.println(e) ;
            e.printStackTrace() ;
        }
    }
}
