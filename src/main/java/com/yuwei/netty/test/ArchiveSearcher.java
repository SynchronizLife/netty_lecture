package com.yuwei.netty.test;

import java.util.concurrent.*;

/**
 * Created:  Y.w
 * Date: 2019-09-23
 * Time: 14:30
 * Description:
 */
public interface ArchiveSearcher {
    String search(String target);
}

class ArchiveSearcherImpl implements ArchiveSearcher{

    @Override
    public String search(String target) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello" + target;
    }
}

class App{
    ExecutorService executor = Executors.newFixedThreadPool(1);
    ArchiveSearcher searcher = new ArchiveSearcherImpl();

    void showSearch(final String target)throws InterruptedException{
        FutureTask<String> future = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return searcher.search(target);
            }
        });
        executor.execute(future);
        displayOtherThings();
        try{
            displayText(future.get());
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        App app = new App();
        app.showSearch("world");
    }

    private void displayOtherThings() {
        System.out.println("执行其他的事情");
    }
    private void displayText(String result){
        System.out.println("获取到结果：" + result);
    }
}
