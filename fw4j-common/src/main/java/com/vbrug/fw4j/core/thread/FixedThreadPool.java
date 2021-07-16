package com.vbrug.fw4j.core.thread;

import com.vbrug.fw4j.common.util.function.Supplier;

import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 */
public abstract class FixedThreadPool {

    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 50,
            300L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadPoolExecutor.AbortPolicy());

    public static <T> Future<T> submit(Supplier<T> supplier){
        return executor.submit(supplier::get);
    }

    public static ThreadPoolExecutor getThreadPoolExecutor(){
        return FixedThreadPool.executor;
    }
}
