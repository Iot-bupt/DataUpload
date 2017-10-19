package com.KafkaUpload;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;

/**
 * Created by tangjialiang on 2017/10/18.
 *
 * thingsboard 的操作代理
 */
public class ThingBoardProxy extends ChannelInboundHandlerAdapter {
    private String host ;
    private int port ;

    private ThingsBoardHelper thingsboard = null ;

    /**
     *  todo
     *  two ways to guarantee
     *  1. 在启动时，使用重发机制确保一定能获取到token。（从实际使用场景上，该方法性能较优）
     *  2. 在每条发送到thingsboard的消息前进行请求装饰，优先确保token信息成功。
     */
    private String token = null ;

    public ThingBoardProxy(String host, int port) throws Exception {
        this.host = host ;
        this.port = port ;

        this.thingsboard = new NettyHelper(host, port, this) ;
        this.thingsboard.connect();

        getToken() ;
    }

    // ----------------------- 消息分发 -----------------------

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpResponse)
        {
            HttpResponse response = (HttpResponse) msg;
            System.out.println("CONTENT_TYPE:" + response.headers().get(HttpHeaders.Names.CONTENT_TYPE));
        }
        if(msg instanceof HttpContent)
        {
            HttpContent content = (HttpContent)msg;
            ByteBuf buf = content.content();
            System.out.println(buf.toString(io.netty.util.CharsetUtil.UTF_8));
            buf.release();
        }
    }

    public void process(String response) {
        // according mesg to decide what to do.
        if (response.equals("")) {

        }
    }

    // ----------------------- 相关业务 -----------------------

    // ---> 获取token
    public void getToken() throws Exception{
        ThingsBoardApi api = new ThingsBoardApi(this.host, this.port) ;
        DefaultFullHttpRequest request = api.api_token("tenant@openiot.org", "tenant");

        ((NettyHelper)thingsboard).sendHttp(request);
    }

    // ---> 创建一个设备
    public void createDevice() throws Exception{
        String msg = "{\"name\":\"test_name_tjl\", \"type\":\"default\"}" ;

        ThingsBoardApi api = new ThingsBoardApi(this.host, this.port) ;
        DefaultFullHttpRequest defaultFullHttpRequest = api.api_device(msg);

        ((NettyHelper)thingsboard).sendHttp(defaultFullHttpRequest);
    }


    // ----------------------- test -----------------------

    public static void main(String[] args) {
        /**
         * usage and test
         */
        try {
            String host = "10.108.219.194";
            int port = 8080;

            ThingBoardProxy tp = new ThingBoardProxy(host, port);
//            tp.createDevice();
        } catch (Exception e) {
            System.out.println(e) ;
        }
    }
}
