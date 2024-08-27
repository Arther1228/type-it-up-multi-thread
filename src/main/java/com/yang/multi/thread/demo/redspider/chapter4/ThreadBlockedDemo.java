package com.yang.multi.thread.demo.redspider.chapter4;

import org.junit.Test;

/**
 * @author yangliangchuang
 * @date 2022/3/19 23:19
 * (1)如果是a先抢到锁，那么 a有可能是RUNNABLE，也有可能是TIMED_WAITING;
 * (2)也有可能b先抢到锁;
 */
public class ThreadBlockedDemo {

    @Test
    public void blockedTest() throws InterruptedException {

        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                testMethod();
            }
        }, "a");
        Thread b = new Thread(new Runnable() {
            @Override
            public void run() {
                testMethod();
            }
        }, "b");

        a.start();
        Thread.sleep(1000L); // 需要注意这里main线程休眠了1000毫秒，而testMethod()里休眠了2000毫秒
        b.start();
        System.out.println(a.getName() + ":" + a.getState()); // 输出？
        System.out.println(b.getName() + ":" + b.getState()); // 输出？

        Thread.sleep(1000L);
        Thread.sleep(3000L);
        System.out.println(a.getName() + ":" + a.getState()); // 输出？
        System.out.println(b.getName() + ":" + b.getState()); // 输出？
    }

    // 同步方法争夺锁
    private synchronized void testMethod() {
        try {
            System.out.println("currentThread: " + Thread.currentThread());
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
