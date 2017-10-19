package com.KafkaUpload;

/**
 * Created by tangjialiang on 2017/10/19.
 */
public class ThingsBoardHelper {

    // configuration
    private String host ;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    private int port ;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public ThingsBoardHelper(String host, int port) {
        this.host = host ;
        this.port = port ;
    }

    public void connect() {

    }

    public void sendHttp(String msg) {

    }

    public void sendPost(String msg) {

    }

    public void sendGet(String msg) {

    }
}
