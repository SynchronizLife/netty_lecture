package com.yuwei.zerocopy2;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author ：y.w
 * @date ：Created in 2019/9/18 21:24
 * @description：传统IO服务器
 */
public class OldIOClient {
    public static void main(String[] args)throws Exception{
        ServerSocket serverSocket = new ServerSocket(8899);

        while(true){
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            try{
                byte[] byteArray = new byte[4096];

                while (true){
                    int readCount = dataInputStream.read(byteArray,0,byteArray.length);

                    if(-1 == readCount){
                        break;
                    }
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
