package com.yang.multi.thread.demo.pool;

import org.junit.Test;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yangliangchuang 2022/9/26 16:49
 */
public class ThreadPoolExecutorTest {

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(20, 30,
            0L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(3000));

    @Test
    public void test1() {
        for (int i = 0; i < 1000; i++) {
            final int index = i;
            threadPoolExecutor.execute(
                    () -> {
                        System.out.println(index + " 被执行，线程名： " + Thread.currentThread().getName());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        //return;
                    }
            );
        }
    }

}
