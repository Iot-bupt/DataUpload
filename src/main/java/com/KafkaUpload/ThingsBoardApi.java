package com.KafkaUpload;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;

import java.net.URI;

/**
 * Created by tangjialiang on 2017/10/18.
 *
 * thingsboard的相关api接口
 */
public class ThingsBoardApi {

    private String host = "127.0.0.1" ;
    private int port = 123 ;

    public ThingsBoardApi(String host, int port) {
        this.host = host ;
        this.port = port ;
    }


    //  --------------- thingsboard提供的相关接口 ---------------

    /**
     * POST /api/v1/{deviceToken}/attributes
     * 发送设备的属性数据
     * @param deviceToken
     * @param attributes
     * @param msg
     * @return
     * @throws Exception
     */
    public DefaultFullHttpRequest postDeviceAttributes(String deviceToken, String attributes, String msg) throws Exception{
        // todo
        return null ;
    }

    /**
     * POST /api/v1/{deviceToken}/telemetry
     * 发送设备的实时数据
     * @param deviceToken
     * @param msg
     * @return
     * @throws Exception
     */
    public DefaultFullHttpRequest api_telemetry(String deviceToken, String msg) throws Exception {
        // todo
        String address = "http://" + host + "/api/auth/login" ;
        URI uri = new URI(address);
        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST,
                uri.toASCIIString(), Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));

        // 构建http请求
        request.headers().set(HttpHeaders.Names.HOST, host);
        request.headers().set(HttpHeaders.Names.CONTENT_TYPE, HttpHeaders.Values.APPLICATION_JSON) ;
        request.headers().set(HttpHeaders.Names.ACCEPT, HttpHeaders.Values.APPLICATION_JSON) ;
        return null ;
    }

    /**
     * POST /api/device
     * 获取
     * @param msg
     * @return
     * @throws Exception
     */
    public DefaultFullHttpRequest api_device(String msg) throws Exception{
        String address = "http://" + host + ":8080/api/device" ;
        URI uri = new URI(address);

        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST,
                uri.toASCIIString(), Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));

        // 构建http请求
        request.headers().set(HttpHeaders.Names.HOST, host);
        request.headers().set(HttpHeaders.Names.CONTENT_TYPE, HttpHeaders.Values.APPLICATION_JSON) ;
        request.headers().set(HttpHeaders.Names.ACCEPT, HttpHeaders.Values.APPLICATION_JSON) ;
        request.headers().set("X-Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZW5hbnRAb3BlbmlvdC5vcmciLCJzY29wZXMiOlsiVEVOQU5UX0FETUlOIl0sInVzZXJJZCI6ImNjM2NlMTAwLTlmY2UtMTFlNi04MDgwLTgwODA4MDgwODA4MCIsImVuYWJsZWQiOnRydWUsInRlbmFudElkIjoiY2JhNDRhODAtOWZjZS0xMWU2LTgwODAtODA4MDgwODA4MDgwIiwiY3VzdG9tZXJJZCI6IjEzODE0MDAwLTFkZDItMTFiMi04MDgwLTgwODA4MDgwODA4MCIsImlzcyI6ImJ1cHQub3BlbmlvdCIsImlhdCI6MTUwODMxMTEzOCwiZXhwIjoxNTA4MzEyMDM4fQ.jfoKM-WrQ-vWpFtpMUc4nrP5OHz5bJXD1lMd_CSUpuzbitaVWtdkmFQ30xThDapayhnLMd5mSt6i-D0bCHl5XA") ; // 上行数据不带

        return request ;
    }

    /**
     * POST /api/auth/login
     * 获取账号的token
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    public DefaultFullHttpRequest api_token(String username, String password) throws Exception {
        String msg = "{\"username\":\""+username+"\", \"password\":\""+password+"\"}" ;

        String address = "http://" + host + ":8080/api/auth/login" ;
        URI uri = new URI(address);

        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST,
                uri.toASCIIString(), Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));

        // 构建http请求
        request.headers().set(HttpHeaders.Names.HOST, host);
        request.headers().set(HttpHeaders.Names.CONTENT_TYPE, HttpHeaders.Values.APPLICATION_JSON) ;
        request.headers().set(HttpHeaders.Names.ACCEPT, HttpHeaders.Values.APPLICATION_JSON) ;

        return request ;
    }

    /**
     * GET /api/device/{deviceId}/credentials
     * 获取设备的access-token
     * @param deviceId
     * @param msg
     * @return
     */
    public DefaultFullHttpRequest api_accessToken(String deviceId, String msg) {
        // todo
        return null ;
    }
}
