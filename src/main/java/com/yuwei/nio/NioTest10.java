package com.yuwei.nio;


import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
/**
 * Created:  Y.w
 * Date: 2019-09-06
 * Time: 17:39
 * Description:文件锁
 */
public class NioTest10 {
    public static void main(String[] args)throws Exception{
        RandomAccessFile randomAccessFile = new RandomAccessFile("NioTest10.txt","rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        //true：为共享锁；false：排查锁
        FileLock fileLock = fileChannel.lock(3,6,true);

        System.out.println("valid: " + fileLock.isValid());
        System.out.println("lock type: " + fileLock.isShared());

        fileLock.release();
        randomAccessFile.close();
    }
}
