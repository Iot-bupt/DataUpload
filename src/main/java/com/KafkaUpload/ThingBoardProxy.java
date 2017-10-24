package com.KafkaUpload;

import com.KafkaUpload.utils.DeviceCacheMapper;
import com.KafkaUpload.utils.RedisCache;

/**
 * Created by tangjialiang on 2017/10/18.
 *
 * thingsboard 的操作代理
 */
public class ThingBoardProxy {

    private String host ;
    private int port ;
    private String username  ;
    private String password  ;

    private ThingsBoardApi api ;
    private DeviceCacheMapper cacheMapper = new RedisCache("10.108.218.64", 6379); // redis 地址


    /**
     *  todo
     *  two ways to guarantee
     *  1. 在启动时，使用重发机制确保一定能获取到token。（从实际使用场景上，该方法性能较优）
     *  2. 在每条发送到thingsboard的消息前进行请求装饰，优先确保token信息成功。
     */
    private String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZW5hbnRAdGhpbmdzYm9hcmQub3JnIiwic2NvcGVzIjpbIlRFTkFOVF9BRE1JTiJdLCJ1c2VySWQiOiI4ODdiMmVhMC1hMWJiLTExZTctOTBjNS0zYmVkYjlkZTMyZWEiLCJlbmFibGVkIjp0cnVlLCJpc1B1YmxpYyI6ZmFsc2UsInRlbmFudElkIjoiODg3OWY2MjAtYTFiYi0xMWU3LTkwYzUtM2JlZGI5ZGUzMmVhIiwiY3VzdG9tZXJJZCI6IjEzODE0MDAwLTFkZDItMTFiMi04MDgwLTgwODA4MDgwODA4MCIsImlzcyI6InRoaW5nc2JvYXJkLmlvIiwiaWF0IjoxNTA4NDIyNjU4LCJleHAiOjE1MTc0MjI2NTh9.n9_VBeoQqzUknxbtZVqNwNWl-NbF-3PUsja0DEQ-jLH47DmzvkllO17g4b9vK5vbPxFGldD1Tik8_ECQPpyGIQ" ; // todo token失效时间

    public ThingBoardProxy(String host, int port) throws Exception {
        this.host = host ;
        this.port = port ;
        this.api = new ThingsBoardApi(this.host, this.port) ;
    }

    public ThingBoardProxy(String host, int port, String username, String password) throws Exception {
        this.host = host ;
        this.port = port ;
        this.api = new ThingsBoardApi(this.host, this.port) ;
        this.username = username ;
        this.password = password ;
        getToken(username, password);
    }

    // ----------------------- 相关业务 -----------------------
    // ---> 获取token
    public void getToken(String name, String password) throws Exception{
        // {"name": "tjl", "password":"pass"}
        String token = api.api_token(name, password);
        this.token = token ;
    }

    // ---> 创建一个设备
    public void createDevice(String deviceName, String deviceType) throws Exception{
        // "{\"name\":\"test_name_tjl\", \"type\":\"default\"}" ;
        ThingsBoardApi api = new ThingsBoardApi(this.host, this.port) ;
        String deviceId = api.api_device(token, deviceName, deviceType);

    }

    // --> 发送设备的attributions
    public void sendAttributions(Device device, String msg) throws Exception{
        //ThingsBoardApi api = new ThingsBoardApi(this.host, this.port) ;
        //api.api_attributes(token, deviceToken, msg);

        // doc: 向thingsboard中拥有accessToken的设备发送attribute数据
        // 1、传入的是kafka中的uId，在缓存中查找是否有这个uId对应的设备
        // 2、有   得到accessToken,并发送attribute
        // 2、否   (1)依据设备名创建一个设备（返回deviceId）(2)查找设备的accessToken(在缓存中记录) （3）发送attributes

        String uid = device.getuId() ;
        String deviceName = device.getDeviceName() ;

        Device cacheDevice = cacheMapper.getDeviceByuId(uid);
        if (cacheDevice == null) { // 没有命中
            // 创建设备
            String deviceId = this.api.api_device(token, deviceName, "default");
            String accessToken = this.api.api_accessToken(token, deviceId);

            // 更新缓存
            device = new Device(uid, accessToken, deviceId, deviceName) ;
            cacheMapper.addDevice(uid, device) ;
        }

        // 发送attributes数据
        this.api.api_attributes(token, cacheDevice.getDeviceAccess(), msg);

    }

    // --> 发送设备的telemetry
    public void sendTelelmetry(Device device, String msg) throws Exception{
        //ThingsBoardApi api = new ThingsBoardApi(this.host, this.port) ;
        //api.api_telemetry(token, deviceToken, msg);

        // doc: 向thingsboard中拥有accessToken的设备发送attribute数据
        // 1、传入的是kafka中的uId，在缓存中查找是否有这个uId对应的设备
        // 2、有   得到accessToken,并发送attribute
        // 2、否   (1)依据设备名创建一个设备（返回deviceId）(2)查找设备的accessToken(在缓存中记录) （3）发送attributes

        String uid = device.getuId() ;
        String deviceName = device.getDeviceName() ;

        Device cacheDevice = cacheMapper.getDeviceByuId(uid);
        if (cacheDevice == null) { // 没有命中
            // 创建设备
            String deviceId = this.api.api_device(token, deviceName, "default");
            String accessToken = this.api.api_accessToken(token, deviceId);

            // 更新缓存
            device = new Device(uid, accessToken, deviceId, deviceName) ;
            cacheMapper.addDevice(uid, device) ;
        }

        // 发送attributes数据
        this.api.api_telemetry(token, cacheDevice.getDeviceAccess(), msg);
    }

    public String get_accessToken(String deviceId) {
        ThingsBoardApi api = new ThingsBoardApi(this.host, this.port) ;
        String access_token = api.api_accessToken(token, deviceId);

        return access_token ;
    }

    // ----------------------- test -----------------------

    public static void main(String[] args) {
        /**
         * usage and test
         */
        try {
            String host = "10.108.218.58";
            int port = 8080;
//            String host = "localhost" ;
//            int port = 1234 ;
            String username = "tenant@thingsboard.org" ;
            String password = "tenant" ;

            ThingBoardProxy tp = new ThingBoardProxy(host, port, username, password);
            tp.get_accessToken("2bebd5d0-b4d1-11e7-b5a9-39a0b348caf5");
            tp.createDevice("hello world", "default");

            // telemetry
            String msg = "{\"hallo\":\"tenant@thingsboard.org\", \"hello\":\"tenant\"}" ;
            String deviecToken = "3HLZRun4LjT8PLSOGhvf" ;
            Device device = new Device("uid123123", "myDevice") ;

//            tp.sendTelelmetry(device, msg);

        } catch (Exception e) {
            System.out.println(e) ;
        }
    }
}
