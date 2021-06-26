package com.vbrug.fw4j.core.concurrent;

import java.util.Date;
import java.util.concurrent.RecursiveTask;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class MyForkJoinTask extends RecursiveTask<Date> {
    @Override
    protected Date compute() {
        return null;
    }
}
