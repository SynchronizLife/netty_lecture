package com.yuwei.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created:  Y.w
 * Date: 2019-09-10
 * Time: 9:39
 * Description:
 */
public class NioServer {

    private static Map<String,SocketChannel> clientMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(8899));

        //创建选择器对象
        Selector selector = Selector.open();
        //将serverSocketChannel注册到选择器,OP_ACCEPT:表示服务器端关注连事件
        serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);

        while(true){
            try{
                selector.select();

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                selectionKeys.forEach(selectionKey -> {
                    final SocketChannel client;
                    try{
                        if(selectionKey.isAcceptable()){
                            ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                            client = server.accept();
                            client.configureBlocking(false);
                            client.register(selector,SelectionKey.OP_READ);

                            String key = "【" + UUID.randomUUID().toString() + "】";
                            clientMap.put(key,client);
                        }else if(selectionKey.isReadable()){
                            client = (SocketChannel)selectionKey.channel();
                            ByteBuffer readBuffer = ByteBuffer.allocate(1024);

                            int count = 0;
                            try {
                                count = client.read(readBuffer);
                            } catch (IOException e) {
                                //客户端断开后，需要手动关闭channel
                                client.close();
                            }

                            if(count > 0){
                                readBuffer.flip();

                                Charset charset = Charset.forName("utf-8");
                                String receivedMessage = String.valueOf(charset.decode(readBuffer).array());

                                System.out.println(client + ": " + receivedMessage);

                                String senderKey = null;

                                for(Map.Entry<String,SocketChannel> entry : clientMap.entrySet()){
                                    if(client == entry.getValue()){
                                        senderKey = entry.getKey();
                                        break;
                                    }
                                }
                                for(Map.Entry<String,SocketChannel> entry : clientMap.entrySet()){
                                    SocketChannel value = entry.getValue();

                                    ByteBuffer writeBuffer = ByteBuffer.allocate(1024);

                                    writeBuffer.put((senderKey + ": " + receivedMessage).getBytes());
                                    writeBuffer.flip();

                                    value.write(writeBuffer);
                                }
                            }
                        }
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                });
                selectionKeys.clear();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }


    }
}
