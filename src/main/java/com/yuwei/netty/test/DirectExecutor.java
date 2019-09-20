package com.yuwei.netty.test;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;

/**
 * Created:  Y.w
 * Date: 2019-09-20
 * Time: 15:21
 * Description:
 */
public class DirectExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        command.run();
    }
}

class ThreadPerTaskExecutor implements Executor{

    @Override
    public void execute(Runnable command) {
        System.out.println("ThreadPerTaskExecutor");
        new Thread(command).start();
    }
}

class SerialExecutor implements Executor{
    final Queue<Runnable> tasks = new ArrayDeque<>();
    final Executor executor;
    Runnable active;

    SerialExecutor(Executor executor){
        this.executor = executor;
    }

    public synchronized void execute(final Runnable r){
        System.out.println("SerialExecutor");
        tasks.offer(() -> {
            try{
                r.run();
            }finally {
                scheduleNext();
            }
        });
        if(active == null){
            scheduleNext();
        }
    }

    protected synchronized void scheduleNext(){
        if((active = tasks.poll()) != null){
            executor.execute(active);
        }
    }

}

class Test2{
    public static void main(String[] args){
//        Executor executor = new DirectExecutor();
//        executor.execute(new TestRunnable());
//        System.out.println("world");

        Executor executor1 = new ThreadPerTaskExecutor();
        Executor executor2 = new SerialExecutor(executor1);
        executor2.execute(new TestRunnable());

    }
}

class TestRunnable implements Runnable{

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
            System.out.println("hello");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
