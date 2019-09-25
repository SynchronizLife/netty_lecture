package com.yuwei.netty.test.classserversocket;


import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created:  Y.w
 * Date: 2019-09-25
 * Time: 9:00
 * Description:
 */
public class Server implements Runnable{
    public static void main(String[] args){
        Thread server = new Thread(new Server());
        server.start();
    }
    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(8899);
            while (true){
                new Thread(new Handler(ss.accept())).start();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    static class Handler implements Runnable{
        final Socket socket;
        Handler(Socket s){
            socket = s;
        }

        @Override
        public void run() {
            try{
                byte[] input = new byte[128];
                socket.getInputStream().read(input);
                byte[] output = process(input);
                socket.getOutputStream().write(output);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        private byte[] process(byte[] cmd){
            String input = new String(cmd);
            System.out.println(input);
            return "world".getBytes();
        }
    }
}
