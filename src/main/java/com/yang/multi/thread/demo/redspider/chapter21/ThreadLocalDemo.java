package com.yang.multi.thread.demo.redspider.chapter21;

public class ThreadLocalDemo {
    public static final ThreadLocal<String> THREAD_LOCAL = ThreadLocal.withInitial(() -> {
        System.out.println("invoke initial value");
        return "default value";
    });

    public static void main(String[] args) throws InterruptedException {
        new Thread(() ->{
            THREAD_LOCAL.set("first thread");
            System.out.println(THREAD_LOCAL.get());
        }).start();

        new Thread(() ->{
            THREAD_LOCAL.set("second thread");
            System.out.println(THREAD_LOCAL.get());
        }).start();

        new Thread(() ->{
            THREAD_LOCAL.set("third thread");
            THREAD_LOCAL.remove();
            System.out.println(THREAD_LOCAL.get());
        }).start();

        new Thread(() ->{
            System.out.println(THREAD_LOCAL.get());
        }).start();

        Thread.sleep(5L);
    }
}