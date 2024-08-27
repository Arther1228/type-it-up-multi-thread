package com.yang.multi.thread.demo.aqs;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.LockSupport;

/**
 * https://www.cnblogs.com/throwable/p/13369717.html
 */

public class LockSupportMain implements Runnable {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private Thread thread;

    private void setThread(Thread thread) {
        this.thread = thread;
    }

    public static void main(String[] args) throws Exception {
        LockSupportMain main = new LockSupportMain();
        Thread thread = new Thread(main, "LockSupportMain");
        main.setThread(thread);
        thread.start();
        Thread.sleep(2000);
        main.unpark();
        Thread.sleep(2000);
    }

    @Override
    public void run() {
        System.out.println(String.format("%s-步入run方法,线程名称:%s", FORMATTER.format(LocalDateTime.now()),
                Thread.currentThread().getName()));
        LockSupport.park();
        System.out.println(String.format("%s-解除阻塞,线程继续执行,线程名称:%s", FORMATTER.format(LocalDateTime.now()),
                Thread.currentThread().getName()));
    }

    private void unpark() {
        LockSupport.unpark(thread);
    }
}

/**
 * // 某个时刻的执行结果如下：
 * 2019-02-25 00:39:57.780-步入run方法,线程名称:LockSupportMain
 * 2019-02-25 00:39:59.767-解除阻塞,线程继续执行,线程名称:LockSupportMain
 */
