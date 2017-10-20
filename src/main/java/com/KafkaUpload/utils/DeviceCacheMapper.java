package com.KafkaUpload.utils;

import com.KafkaUpload.Device;

/**
 * Created by tangjialiang on 2017/10/19.
 *
 * 设备缓存接口
 */
public interface DeviceCacheMapper {

    public Device getDeviceByuId(String uid) ;

    public Boolean addDevice(String uid, Device device) ;
}
