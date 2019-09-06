package com.yuwei.decorator;

/**
 * Created:  Y.w
 * Date: 2019-09-05
 * Time: 17:54
 * Description:
 */
public class ConcreteDecorator2 extends Decorator{

    public ConcreteDecorator2(Component component) {
        super(component);
    }

    @Override
    public void doSomething() {
        super.doSomething();
        this.doAnotherThing();
    }

    private void doAnotherThing(){
        System.out.println("功能C");
    }
}
