package com.yuwei.netty.fourthexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created:  Y.w
 * Date: 2019-08-29
 * Time: 9:57
 * Description:
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel > {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //设置心跳：读：写：读写分别为5,7,3秒
        pipeline.addLast(new IdleStateHandler(5,7,3,TimeUnit.SECONDS));
        pipeline.addLast(new MyServerHandler());
    }
}
