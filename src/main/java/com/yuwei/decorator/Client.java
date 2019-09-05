package com.yuwei.decorator;

/**
 * Created:  Y.w
 * Date: 2019-09-05
 * Time: 17:55
 * Description:
 */
public class Client {
    public static void main(String[] args){
        Component component = new ConcreteDecorator2(new ConcreteDecorator1(new ConcreteComponent()));

        component.doSomething();
    }
}
