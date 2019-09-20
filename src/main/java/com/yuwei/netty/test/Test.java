package com.yuwei.netty.test;

import io.netty.util.NettyRuntime;
import io.netty.util.internal.SystemPropertyUtil;

/**
 * Created:  Y.w
 * Date: 2019-09-19
 * Time: 15:37
 * Description:
 */
public class Test {
    public static void main(String[] args){
        int result = Math.max(1, SystemPropertyUtil.getInt(
                "io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
        System.out.println(result);
        Test test = new Test();
        String name = test.getClass().getSimpleName();
        System.out.println(name);
    }
}
