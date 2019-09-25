package com.yuwei.netty.test.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created:  Y.w
 * Date: 2019-09-25
 * Time: 11:58
 * Description:
 */
public class Reactor implements Runnable {
    final Selector selector;
    final ServerSocketChannel serverSocket;
    Reactor(int port) throws IOException {
//        selector = Selector.open();
//        serverSocket = ServerSocketChannel.open();
        SelectorProvider p = SelectorProvider.provider();
        selector = p.openSelector();
        serverSocket = p.openServerSocketChannel();
        serverSocket.socket().bind(new InetSocketAddress(port));
        serverSocket.configureBlocking(false);
        SelectionKey sk = serverSocket.register(selector,SelectionKey.OP_ACCEPT);
        sk.attach(new Acceptor());
    }
    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){
                selector.select();
                Set selected = selector.selectedKeys();
                Iterator it = selected.iterator();
                while (it.hasNext()){
                    dispatch((SelectionKey)(it.next()));
                    selected.clear();
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    void dispatch(SelectionKey k){
        Runnable r = (Runnable)(k.attachment());
        if(r != null){
            r.run();
        }
    }

    class Acceptor implements Runnable{

        @Override
        public void run() {
            try {
                SocketChannel c = serverSocket.accept();
                if(c != null){
                    new Handler(selector,c);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    final class Handler implements Runnable{
        ExecutorService pool = Executors.newFixedThreadPool(3);
        final int PROCESSING = 3;
        final SocketChannel socket;
        final SelectionKey sk;
        ByteBuffer input = ByteBuffer.allocate(4096);
        ByteBuffer output = ByteBuffer.allocate(4096);
        static final int READING = 0,SENDING = 1;
        int state = READING;

        Handler(Selector sel,SocketChannel c)throws IOException{
            socket = c;
            c.configureBlocking(false);
            sk = socket.register(sel,0);
            sk.attach(this);
            sk.interestOps(SelectionKey.OP_READ);
            sel.wakeup();
        }
        boolean inputIsComplete(){
            return true;
        }
        boolean outputIsComplete(){
            return true;
        }
        void process(){

        }

        @Override
        public void run() {
            try{
                if(state == READING) read();
                else if(state == SENDING) send();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        void read()throws IOException{
//            socket.read(input);
//            if(inputIsComplete()){
//                process();
//                state = SENDING;
//                sk.interestOps(SelectionKey.OP_WRITE);
//            }
            socket.read(input);
            if(inputIsComplete()){
                state = PROCESSING;
                pool.execute(new Processer());
            }
        }

        void send()throws IOException{
            socket.write(output);
            if(outputIsComplete())sk.cancel();
        }

        synchronized void processAndHandOff(){
            process();
            state = SENDING;
            sk.interestOps(SelectionKey.OP_WRITE);
        }
        class Processer implements Runnable{

            @Override
            public void run() {
                processAndHandOff();
            }
        }
    }
}
