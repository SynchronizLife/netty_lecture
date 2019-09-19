package com.yuwei.zerocopy2;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author ：y.w
 * @date ：Created in 2019/9/18 21:57
 * @description：
 */
public class NewIOServer {
    public static void main(String[] args)throws Exception{
        InetSocketAddress address = new InetSocketAddress(8899);

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.setReuseAddress(true);
        serverSocket.bind(address);

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

        while (true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(true);

            int readCount = 0;

            while (-1 != readCount){
                try{
                    readCount = socketChannel.read(byteBuffer);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                byteBuffer.rewind();
            }
        }
    }
}
