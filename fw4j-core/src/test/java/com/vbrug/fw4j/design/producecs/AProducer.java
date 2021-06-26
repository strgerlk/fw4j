package com.vbrug.fw4j.design.producecs;

import com.vbrug.fw4j.common.util.Assert;
import com.vbrug.fw4j.common.util.CollectionUtils;
import com.vbrug.fw4j.common.util.NumberUtils;
import com.vbrug.fw4j.core.design.producecs.Producer;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class AProducer implements Producer<Long> {

    private final Long          startNumber;
    private final Long          endNumber;
    private final AtomicLong    counter;
    private       long          produceData;
    private final TreeSet<Long> treeSet;

    public AProducer(Long startNumber, Long endNumber, AtomicLong counter, TreeSet<Long> treeSet) {
        this.startNumber = startNumber;
        this.endNumber = endNumber;
        this.counter = counter;
        this.produceData = this.startNumber;
        this.treeSet = treeSet;
    }


    @Override
    public Producer<Long>[] split(int number) throws Exception {
        List<AProducer> splitProducers = new ArrayList<>();
        Assert.isTrue(number > 0, "number 必须大于0");
        if (this.endNumber - this.startNumber < number) {
            System.out.println("数据范围间距小于任务数量, 重置任务数量为间距");
            number = (int) (this.endNumber - this.startNumber);
        }
        if (number == 1) {
            return new AProducer[]{this};
        }
        List<Long> splitNumbers = NumberUtils.avgSplitNumber(this.startNumber, this.endNumber, number);
        for (int i = 1; i < splitNumbers.size(); i++) {
            if (i == splitNumbers.size() - 1)
                splitProducers.add(new AProducer(splitNumbers.get(i - 1), splitNumbers.get(i), this.counter, this.treeSet));
            else
                splitProducers.add(new AProducer(splitNumbers.get(i - 1), splitNumbers.get(i) - 1L, this.counter, this.treeSet));

        }
        if (!CollectionUtils.isEmpty(splitProducers)) {
            return splitProducers.toArray(new AProducer[0]);
        }
        return null;
    }

    @Override
    public Long produce() throws Exception {
        Long produceData1 = produceData++;
        if (produceData1 <= endNumber) {
            synchronized (this.counter) {
                treeSet.add(produceData1);
            }
            System.out.println(Thread.currentThread().getName() + "生产了 " + produceData1 + ", 共计生产" + counter.addAndGet(1L));
/*
            if (produceData == 50L)
                throw new Exception("异常测试");
*/
            return produceData1;
        }
        return null;
    }

    @Override
    public void beforeProduce() {
        System.out.println(Thread.currentThread().getName() + "前置处理");
    }

    @Override
    public void afterProduce() {
        System.out.println(Thread.currentThread().getName() + "后置处理");
    }

    @Override
    public void exceptionHandle(Exception e) throws Exception {
        System.out.println(e.getMessage());
        System.out.println(Thread.currentThread().getName() + "异常处理");
    }

    @Override
    public void finallyHandle() throws Exception {
        System.out.println(Thread.currentThread().getName() + "FINALLY_处理 ");
    }
}

