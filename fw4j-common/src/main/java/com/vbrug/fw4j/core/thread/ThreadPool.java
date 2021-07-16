package com.vbrug.fw4j.core.thread;

import com.vbrug.fw4j.common.util.function.Supplier;

import java.lang.reflect.Method;
import java.util.concurrent.*;

/**
 * 线程池
 */
public abstract class ThreadPool {

    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 50,
            300L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadPoolExecutor.AbortPolicy());

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Future submit(Object bean, String methodName, Object... args) throws Exception {
        Class[] parameterTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = args[i].getClass();
        }
        Method method = bean.getClass().getMethod(methodName, parameterTypes);
        Callable c = () -> {
            return method.invoke(bean, args);
        };
        return executor.submit(c);
    }

    public static <T> Future<T> submit(Supplier<T> supplier) {
        return executor.submit(supplier::get);
    }

    public static ThreadPoolExecutor getThreadPoolExecutor() {
        return ThreadPool.executor;
    }
}
