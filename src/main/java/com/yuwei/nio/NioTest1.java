package com.yuwei.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * Created:  Y.w
 * Date: 2019-09-06
 * Time: 9:04
 * Description:
 */
public class NioTest1 {
    public static void main(String[] args){
        IntBuffer buffer = IntBuffer.allocate(10);

        for(int i = 0;i < 5;i ++){
            //相比于Random，推荐使用SecureRandom，产生的随机数更加随机
            int randomNumber = new SecureRandom().nextInt(20);
            buffer.put(randomNumber);
        }

        //limit:10
        System.out.println("before flip limit: " + buffer.limit());

        buffer.flip();

        //limit:5
        System.out.println("after flip limit: " + buffer.limit());

        System.out.println("enter while loop");

        while (buffer.hasRemaining()){
            //position:0-1-2-3-4
            System.out.println("position: " + buffer.position());
            //limit:5
            System.out.println("limit: " + buffer.limit());
            //capacity:10
            System.out.println("capacity: "+ buffer.capacity());
            System.out.println(buffer.get());
        }
    }
}
