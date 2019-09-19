package com.yuwei.zerocopy2;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * Created:  Y.w
 * Date: 2019-09-19
 * Time: 8:57
 * Description:
 */
public class NewIOClient {
    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",8899));
        socketChannel.configureBlocking(true);

        String fileName = "E:\\setup\\gradle-3.5.1-all.zip";

        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        long startTime = System.currentTimeMillis();

        long transferCount = fileChannel.transferTo(0,fileChannel.size(),socketChannel);

        System.out.println("发送的字节数：" + transferCount + ",耗时：" + (System.currentTimeMillis() - startTime));

        fileChannel.close();
    }
}
