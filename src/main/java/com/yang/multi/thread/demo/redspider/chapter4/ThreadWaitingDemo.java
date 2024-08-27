package com.yang.multi.thread.demo.redspider.chapter4;


/**
 * @author yangliangchuang
 * @date 2022/3/19 23:37
 * (1)调用join()方法，会一直等待这个线程执行完毕（转换为TERMINATED状态）。
 * (2)执行完a.join之后，线程a的状态必然是：TERMINATED，但是b有可能还是RUNNABLE，即还未获取时间分片，还未跑完，这时候的输出结果：
 *   thread a is running...
 *   a:TERMINATED
 *   b:RUNNABLE
 *   thread b is running...
 *
 *  b还有可能是BLOCKED状态，或者TERMINATED状态；
 *
 */
public class ThreadWaitingDemo {

    public static void main(String[] args) throws InterruptedException {

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
//        a.join();
        a.join(1000L);
        b.start();

        System.out.println(a.getName() + ":" + a.getState()); // 输出 TERMINATED
        System.out.println(b.getName() + ":" + b.getState());
    }

    // 同步方法争夺锁
    private static synchronized void testMethod() {
        try {
            System.out.println("currentThread: " + Thread.currentThread());
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
