package com.yuwei.nio;


import java.nio.ByteBuffer;

/**
 * Created:  Y.w
 * Date: 2019-09-06
 * Time: 17:20
 * Description: 只读buffer，我们可以随时将一个普通Buffer调用asReadOnlyBuffer方法返回一个只读Buffer
 *              但不能将一个只读Buffer转换为读写Buffer
 */
public class NioTest7 {
    public static void main(String[] args){
        ByteBuffer buffer = ByteBuffer.allocate(10);

        System.out.println(buffer.getChar());

        for(int i = 0;i < buffer.capacity(); ++i){
            buffer.put((byte) i);
        }

        ByteBuffer readonlyBuffer = buffer.asReadOnlyBuffer();

        System.out.println(readonlyBuffer.getChar());

        readonlyBuffer.position(0);
    }
}
