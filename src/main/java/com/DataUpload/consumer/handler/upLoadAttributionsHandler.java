package com.DataUpload.consumer.handler;


import com.DataUpload.platform.ThingBoardProxy;
import com.DataUpload.utils.Device;
import org.apache.log4j.Logger;

/**
 * Created by tangjialiang on 2017/10/19.
 *
 * 对从Kafka拉取到的数据处理  -- type : attributions
 */
public class upLoadAttributionsHandler extends upLoadDataHandler {

    private static Logger logger = Logger.getLogger(upLoadAttributionsHandler.class) ;

    private ThingBoardProxy tp ;
    // 以下是由Kafka消费者解析到的字段
    private String uId ;
    private String dataType ;
    private String info ;
    private String deviceName ;


    public upLoadAttributionsHandler(ThingBoardProxy tp, String uId, String dataType, String info, String deviceName) {
        this.tp = tp ;
        this.uId = uId ;
        this.dataType = dataType ;
        this.info = info ;
        this.deviceName = deviceName ;
    }

    @Override
    public void process() {
        // 向thingsboard发送数据 attributions
        Device device = new Device(uId, deviceName) ;

        try {
            tp.sendAttributions(device, info);
        } catch (Exception e) {
            logger.error("can't send info: " + info) ;
        }
    }
}
