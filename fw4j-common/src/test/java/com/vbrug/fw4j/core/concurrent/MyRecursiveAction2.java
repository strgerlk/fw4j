package com.vbrug.fw4j.core.concurrent;

import java.util.concurrent.RecursiveAction;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class MyRecursiveAction2 extends RecursiveAction {

    private int beginValue;
    private int endValue;

    public MyRecursiveAction2(int beginValue, int endValue) {
        super();
        this.beginValue = beginValue;
        this.endValue = endValue;
    }

    @Override
    protected void compute() {
        System.out.println(Thread.currentThread().getId() + "---------------");
        if (endValue - beginValue > 2) {
            int                middleValue = (beginValue + endValue) / 2;
            MyRecursiveAction2 leftAction  = new MyRecursiveAction2(beginValue, middleValue);
            MyRecursiveAction2 rightAction = new MyRecursiveAction2(middleValue, endValue);
            leftAction.fork();
            rightAction.fork();
            leftAction.join();
            rightAction.join();
        }

    }
}
