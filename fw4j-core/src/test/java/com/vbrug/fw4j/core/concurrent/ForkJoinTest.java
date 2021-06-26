package com.vbrug.fw4j.core.concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class ForkJoinTest {
    public static void main(String[] args) throws InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.submit(new MyRecursiveAction());
        forkJoinPool.getActiveThreadCount();
        TimeUnit.SECONDS.sleep(20000);
    }


    public static class MyRecursiveAction extends RecursiveAction {

        @Override
        protected void compute() {
            System.out.println("运行起来就可以，不用返回值");
        }
    }

}
