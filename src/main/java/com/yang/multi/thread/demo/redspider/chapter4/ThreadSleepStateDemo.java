package com.yang.multi.thread.demo.redspider.chapter4;

import lombok.SneakyThrows;

/**
 * @author yangliangchuang
 * @date 2022/3/19 23:53
 * (1)Thread.sleep代表的是当前线程sleep;
 * (2)Thread sleep的时候状态是TIMED_WAITING;
 * (3)这个例子是自己想的，有可能不准确;
 * (4)sleep方法是不会释放当前的锁的，而wait方法会。
 */
public class ThreadSleepStateDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread a = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                threadSleep();
            }
        }, "a");

        a.start();

        Thread.sleep(10);
        System.out.println(a.getName() + ":" + a.getState());  //a:TIMED_WAITING
        System.out.println(Thread.currentThread().getName() + ":" + a.getState());
    }

    private static void threadSleep() throws InterruptedException {
        Thread.sleep(10000);
    }
}
