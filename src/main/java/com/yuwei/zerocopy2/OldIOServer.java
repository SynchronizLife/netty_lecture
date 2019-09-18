package com.yuwei.zerocopy2;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author ：y.w
 * @date ：Created in 2019/9/18 21:28
 * @description：
 */
public class OldIOServer {
    public static void main(String[] args)throws Exception{
        Socket socket = new Socket("localhost",8899);

        String fileName = "E:\\soft-dev\\soft-setup\\zip-tar-gz\\gradle-3.5-all.zip";
        InputStream inputStream = new FileInputStream(fileName);

        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        byte[] buffer = new byte[4096];
        long readCout;
        long total = 0;

        long startTime = System.currentTimeMillis();

        while ((readCout = inputStream.read(buffer)) >= 0){
            total += readCout;
            dataOutputStream.write(buffer);
        }
        System.out.println("发送总字节数：" + total + " ,耗时：" + (System.currentTimeMillis() - startTime));

        dataOutputStream.close();
        socket.close();
        inputStream.close();
    }
}
