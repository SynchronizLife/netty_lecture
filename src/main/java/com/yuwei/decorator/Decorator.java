package com.yuwei.decorator;

/**
 * Created:  Y.w
 * Date: 2019-09-05
 * Time: 17:46
 * Description:
 */
public class Decorator implements Component {
    private Component component;

    public Decorator(Component component){
        this.component = component;
    }
    @Override
    public void doSomething() {
        component.doSomething();
    }
}
