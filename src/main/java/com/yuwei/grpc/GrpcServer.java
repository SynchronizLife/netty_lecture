package com.yuwei.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * Created:  Y.w
 * Date: 2019-09-04
 * Time: 11:07
 * Description:
 */
public class GrpcServer {
    private Server server;

    private void start() throws IOException {
        this.server = ServerBuilder.forPort(8899)
                .addService(new StudentServiceImpl())
                .build().start();
        System.out.println("server started");

        Runtime.getRuntime().addShutdownHook(new Thread(() ->{
            System.out.println("关闭jvm");
            GrpcServer.this.stop();
        }));


    }
    private void stop(){
        if(null != this.server){
            this.server.shutdown();
        }
    }
    private void awaitTermination() throws InterruptedException {
        if(null != this.server){
            this.server.awaitTermination();
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        GrpcServer server = new GrpcServer();

        server.start();
        server.awaitTermination();
    }
}
