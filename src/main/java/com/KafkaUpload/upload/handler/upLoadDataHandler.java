package com.KafkaUpload.upload.handler;

import com.KafkaUpload.Device;

import java.util.HashMap;

/**
 * Created by tangjialiang on 2017/10/19.
 */
public class upLoadDataHandler {

    private HashMap<String, Device> deviceMapper ;

    public upLoadDataHandler(HashMap<String, Device> deviceMapper) {
        this.deviceMapper = deviceMapper ;
    }

    /**
     * 工作接口
     * 子类实现具体方法
     */
    public void process() {}

    /**
     *
     * @return deviecId
     */
    public String toBeValidate() {

        // 向thingsbaoard查询该deviceId是否存在。
        // 否： 在thingsboard中创建该device，再返回该设备的access-token
        // 是： 返回该设备的access-token

        return "" ; //deviceId
    }
}
