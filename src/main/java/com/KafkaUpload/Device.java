package com.KafkaUpload;

/**
 * Created by tangjialiang on 2017/10/18.
 *
 * 设备相关数据，映射集合
 */
public class Device {

    private String uId ;
    private String deviceAccess ;
    private String deviceId ;

    public Device(String uId, String deviceAccess, String deviceId) {
        this.uId = uId ;
        this.deviceAccess = deviceAccess ;
        this.deviceId = deviceId ;
    }

}
