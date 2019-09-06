package com.yuwei.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created:  Y.w
 * Date: 2019-09-06
 * Time: 15:12
 * Description:
 */
public class NioTest4 {
    public static void main(String[] args)throws Exception{
        FileInputStream inputStream = new FileInputStream("input.txt");
        FileOutputStream outputStream = new FileOutputStream("output.txt");

        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(512);

        while(true){
            buffer.clear();

            int read = inputChannel.read(buffer);

            System.out.println("read: " + read);

            if(-1 == read){
                break;
            }

            buffer.flip();

            outputChannel.write(buffer);
        }
        inputChannel.close();
        outputChannel.close();
    }
}
