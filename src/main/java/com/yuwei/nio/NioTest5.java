package com.yuwei.nio;

import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;

/**
 * Created:  Y.w
 * Date: 2019-09-06
 * Time: 16:49
 * Description: ByteBuffer类型化的put与get方法
 */
public class NioTest5 {
    public static void main(String[] args){
        ByteBuffer buffer = ByteBuffer.allocate(64);

        buffer.putInt(16);
        buffer.putLong(5000000000L);
        buffer.putDouble(14.28288);
        buffer.putChar('你');
        buffer.putShort((short)2);
        buffer.putChar('我');

        buffer.flip();

        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getDouble());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());
        System.out.println(buffer.getChar());
    }
}
