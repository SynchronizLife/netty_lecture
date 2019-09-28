package com.yuwei.netty.secondexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //group是一个事件执行组，是一个事件循环，告诉pipeline在与这个IO线程不同的另外一个线程当中去运行该Handler的事件处理器方法，
        // 这样的话IO线程不会被一个耗时的任务所阻塞。
        EventExecutorGroup group = new DefaultEventExecutorGroup(16);

        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        //将MyServerHandler的事件处理器的回调方法交予事件执行组异步的去执行业务逻辑
        pipeline.addLast(group,"myServerHandler",new MyServerHandler());
    }
}
