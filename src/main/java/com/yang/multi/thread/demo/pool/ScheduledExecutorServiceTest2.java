package com.yang.multi.thread.demo.pool;

import cn.hutool.core.thread.ThreadFactoryBuilder;

import java.util.concurrent.*;

public class ScheduledExecutorServiceTest2 {

    private static ScheduledExecutorService pool;

    public static void main(String[] args) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNamePrefix("ftp-thread-%d").build();
        pool = new ScheduledThreadPoolExecutor(1, namedThreadFactory);
        // 1s 延迟后开始执行任务，每 2s 重复执行一次
        pool.scheduleAtFixedRate(() -> System.out.println("Hello World"), 1000, 2000, TimeUnit.MILLISECONDS);
    }
}
