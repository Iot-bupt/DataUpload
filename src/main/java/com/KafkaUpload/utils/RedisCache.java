package com.KafkaUpload.utils;

import com.KafkaUpload.Device;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tangjialiang on 2017/10/20.
 *
 * Redis Cache
 */
public class RedisCache implements DeviceCacheMapper {
    String host ;
    int port ;
    Jedis jedis;

    public RedisCache(String host, int port) {
        this.host = host ;
        this.port = port ;
    }

    public void initial() {
        jedis = new Jedis(host);

        System.out.println("连接成功");
        //设置 redis 字符串数据
        jedis.set("runoobkey", "www.runoob.com");
        // 获取存储的数据并输出
        System.out.println("redis 存储的字符串为: "+ jedis.get("runoobkey"));
    }

    public Device getDeviceByuId(String uid) {
        List<String> hmget = jedis.hmget(uid, "uId", "deviceAccess", "deviceId", "deviceName");
        if (hmget==null || hmget.size()==0 || hmget.get(0)==null || hmget.get(1)==null || hmget.get(2)==null || hmget.get(3)==null) return null ;

        Device device = new Device(hmget.get(0), hmget.get(1), hmget.get(2), hmget.get(3)) ;
        return device;
    }

    public Boolean addDevice(String uid, Device device) {
        try {
            Map<String, String> deviceMap = new HashMap<String, String>() ;
            deviceMap.put("uId", device.getuId()) ;
            deviceMap.put("deviceAccess", device.getDeviceAccess()) ;
            deviceMap.put("deviceId", device.getDeviceId()) ;
            deviceMap.put("deviceName", device.getDeviceName()) ;
            jedis.hmset(uid, deviceMap) ;
        } catch (Exception e) {
            System.out.println("can't add device to cache") ;
            return false ;
        }
        return true ;
    }

    public static void main(String[] args) {
        RedisCache cache = new RedisCache("10.108.218.64", 6379) ;
        cache.initial();

        Device device = new Device("uid131231", "deviceName231", "deviceId1234", "deviceName31242") ;
        Device uid131231 = cache.getDeviceByuId("uid131231");
        System.out.println("first get: " + uid131231) ;

        if (uid131231 == null) {
            cache.addDevice(device.getuId(), device) ;
        }
        Device deviceByuId = cache.getDeviceByuId(device.getuId());

        System.out.println("get device: " + deviceByuId) ;
    }
}
