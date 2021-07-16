package com.vbrug.fw4j.core.design.producecs;

import com.vbrug.fw4j.common.util.Assert;
import com.vbrug.fw4j.common.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class AConsumerTask implements ConsumerTask<Long> {

    private final AtomicLong    counter;
    private final TreeSet<Long> treeSet;

    public AConsumerTask(AtomicLong counter, TreeSet<Long> treeSet) {
        this.counter = counter;
        this.treeSet = treeSet;
    }

    @Override
    public ConsumerTask<Long>[] split(int number) throws Exception {
        Assert.isTrue(number > 0, "number 必须大于0");
        if (number == 1) {
            return new AConsumerTask[]{this};
        }
        List<AConsumerTask> splitConsumers = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            splitConsumers.add(new AConsumerTask(this.counter, this.treeSet));
        }
        if (!CollectionUtils.isEmpty(splitConsumers)) {
            return splitConsumers.toArray(new AConsumerTask[0]);
        }
        return null;
    }

    @Override
    public void consume(Long aLong) throws Exception {
        synchronized (this.counter) {
            treeSet.add(aLong);
        }
        System.out.println(Thread.currentThread().getName() + "消费了 " + aLong + ", 共计消费 " + this.counter.addAndGet(1L));
    }

    @Override
    public void beforeConsume() throws Exception {
        System.out.println(Thread.currentThread().getName() + "前置处理");
    }

    @Override
    public void afterConsume() throws Exception {
        System.out.println(Thread.currentThread().getName() + "后之处理");
    }

    @Override
    public void exceptionHandle(Exception e) throws Exception {
        System.out.println(e.getMessage());
        System.out.println(Thread.currentThread().getName() + "异常处理 ");
    }

    @Override
    public void finallyHandle() throws Exception {
        System.out.println(Thread.currentThread().getName() + "FINALLY_处理 ");
    }
}
