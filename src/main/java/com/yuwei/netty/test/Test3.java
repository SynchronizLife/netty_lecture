package com.yuwei.netty.test;

/**
 * Created:  Y.w
 * Date: 2019-09-30
 * Time: 15:10
 * Description:
 */
public class Test3 {
    public static void main(String[] args){
        //2的指数判断，true表示value是2的指数
        int value = 4;
        boolean result = (value & -value) == value;
        System.out.println(result);
    }
}
