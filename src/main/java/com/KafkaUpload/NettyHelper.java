package com.KafkaUpload;

import com.Demo.HttpClientInboundHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * Created by tangjialiang on 2017/10/18.
 *
 * 使用该类，向thingsboard发送http请求
 */
public class NettyHelper extends ThingsBoardHelper {
    // 应该与thingsboard建立长链接

    // 与thingsboard的长链接
    Bootstrap b = null ;
    ChannelFuture conn = null ;
    EventLoopGroup workerGroup = null ;

    // thingsboard的代理类
    ChannelInboundHandlerAdapter proxy = null ;

    public NettyHelper(String host, int port, ThingBoardProxy proxy) {
        super(host, port);
        this.proxy = proxy ;
    }

    @Override
    public void connect() {
        workerGroup = new NioEventLoopGroup();

        try {
            b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                    ch.pipeline().addLast(new HttpResponseDecoder());
                    // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
                    ch.pipeline().addLast(new HttpRequestEncoder());
                    ch.pipeline().addLast(new HttpClientInboundHandler());
                }
            });

            // Start the client.
            conn = b.connect(getHost(), getPort()).sync() ;

            // end test
        } catch (Exception e) {
            System.out.println(e) ;
        }
    }



    public void sendHttp(DefaultFullHttpRequest request) throws Exception {
        // 发送http请求

        try {
            conn.channel().write(request);
            conn.channel().flush();
            conn.channel().closeFuture().sync();
        } catch (Exception e) {
            System.out.println(e) ;
        }
    }


}


