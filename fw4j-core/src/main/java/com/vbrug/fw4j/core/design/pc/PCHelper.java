package com.vbrug.fw4j.core.design.pc;

import com.vbrug.fw4j.common.util.Assert;
import com.vbrug.fw4j.core.thread.ThreadState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class PCHelper {

    private final List<Producer> producerList;
    private final List<Consumer> consumerList;

    public PCHelper(Producer... producers) {
        this(Arrays.asList(producers), null);
    }

    public PCHelper(Consumer... consumers) {
        this(null, Arrays.asList(consumers));
    }

    public PCHelper(List<Producer> producerList, List<Consumer> consumerList) {
        this.producerList = producerList;
        this.consumerList = consumerList;
    }

    /**
     * 停止生产者
     */
    @SuppressWarnings("rawtypes")
    public void stopProducer() {
        for (Producer producer : producerList) {
            if (!producer.isStop())
                producer.stop();
        }
    }

    @SuppressWarnings("rawtypes")
    public void stopConsumer() {
        for (Consumer consumer : consumerList) {
            if (!consumer.isStop())
                consumer.stop();
        }
    }

    @SuppressWarnings("rawtypes")
    public void finishProducer() {
        for (Producer producer : producerList) {
            if (!producer.isStop())
                producer.finish();
        }
    }

    @SuppressWarnings("rawtypes")
    public void finishConsumer() {
        for (Consumer consumer : consumerList) {
            if (!consumer.isStop())
                consumer.finish();
        }
    }

    public Producer[] getAliveProducer() {
        List<Producer> aliveProducerList = new ArrayList<>();
        for (Producer producer : producerList) {
            if (!producer.isStop())
                aliveProducerList.add(producer);
        }
        return aliveProducerList.toArray(new Producer[aliveProducerList.size()]);
    }

    public Consumer[] getAliveConsumer() {
        List<Consumer> aliveConsumerList = new ArrayList<>();
        for (Consumer consumer : consumerList) {
            if (!consumer.isStop())
                aliveConsumerList.add(consumer);
        }
        return aliveConsumerList.toArray(new Consumer[aliveConsumerList.size()]);
    }

    public boolean isSuccess() {
        Assert.isTrue(isFinish(), "生成消费器未执行完毕！！！");
        for (Consumer consumer : this.consumerList) {
            if (consumer.state != ThreadState.STOP)
                return false;
        }
        for (Producer producer : this.producerList) {
            if (producer.state != ThreadState.STOP)
                return false;
        }
        return true;
    }

    public boolean isFinish() {
        return this.getAliveConsumer().length == 0 && this.getAliveProducer().length == 0 ? true : false;
    }

    public static void main(String[] args) {
        double atan = Math.atan(-1 / 1);
        System.out.println(atan / Math.PI * 2);
        System.out.println(Math.tan(Math.toRadians(45)));
        System.out.println(Math.toDegrees(atan));
    }

}
