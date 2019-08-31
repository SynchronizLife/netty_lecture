package com.yuwei.netty.sixthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

public class TestClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        MyDataInfo.MyMessage myMessage = null;
        //每隔2秒向服务端发送随机产生的数据
        while (true){
            int randomInt = new Random().nextInt(3);
            Thread.sleep(2000);
            if(0 == randomInt){
                myMessage = MyDataInfo.MyMessage.newBuilder()
                        .setDataType(MyDataInfo.MyMessage.DataType.PersonType)
                        .setPerson(MyDataInfo.Person.newBuilder()
                                .setName("张三")
                                .setAge(20)
                                .setAddress("杭州").build())
                        .build();
            }else if(1 == randomInt) {
                myMessage = MyDataInfo.MyMessage.newBuilder()
                        .setDataType(MyDataInfo.MyMessage.DataType.DogType)
                        .setDog(MyDataInfo.Dog.newBuilder()
                                .setName("小狗")
                                .setAge(2).build())
                        .build();
            }else {
                myMessage = MyDataInfo.MyMessage.newBuilder()
                        .setDataType(MyDataInfo.MyMessage.DataType.CatType)
                        .setCat(MyDataInfo.Cat.newBuilder()
                                .setName("小猫")
                                .setAge(1).build()).build();

            }

            ctx.channel().writeAndFlush(myMessage);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

    }
}
