package com.yang.multi.thread.demo.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * https://www.cnblogs.com/throwable/p/13369717.html
 */
public class CLHLock implements Lock {

        AtomicReference<QueueNode> tail = new AtomicReference<>(new QueueNode());

    ThreadLocal<QueueNode> pred;
    ThreadLocal<QueueNode> current;

    public CLHLock() {
        current = ThreadLocal.withInitial(QueueNode::new);
        pred = ThreadLocal.withInitial(() -> null);
    }

    @Override
    public void lock() {
        QueueNode node = current.get();
        node.locked = true;
        QueueNode pred = tail.getAndSet(node);
        this.pred.set(pred);
        while (pred.locked) {
        }
    }


    // 忽略其他接口方法的实现

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        QueueNode node = current.get();
        node.locked = false;
        current.set(this.pred.get());
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    static class QueueNode {

        boolean locked;
    }

}	
