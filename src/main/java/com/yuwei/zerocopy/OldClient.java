package com.yuwei.zerocopy;

import java.net.Socket;

/**
 * Created:  Y.w
 * Date: 2019-09-18
 * Time: 15:59
 * Description:
 */
public class OldClient {
    public static void main(String[] args)throws Exception{
        Socket socket = new Socket("localhost",8899);
    }
}
