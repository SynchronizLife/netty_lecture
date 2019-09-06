package com.yuwei.decorator;

/**
 * Created:  Y.w
 * Date: 2019-09-05
 * Time: 17:46
 * Description:
 */
public class ConcreteComponent implements Component {
    @Override
    public void doSomething() {
        System.out.println("功能A");
    }
}
