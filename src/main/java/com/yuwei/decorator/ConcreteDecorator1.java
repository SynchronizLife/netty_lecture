package com.yuwei.decorator;

/**
 * Created:  Y.w
 * Date: 2019-09-05
 * Time: 17:49
 * Description:
 */
public class ConcreteDecorator1 extends Decorator {
    public ConcreteDecorator1(Component component) {
        super(component);
    }

    @Override
    public void doSomething() {
        super.doSomething();
        this.doAnotherThing();
    }

    private void doAnotherThing(){
        System.out.println("功能B");
    }
}
