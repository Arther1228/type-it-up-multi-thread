package com.yang.multi.thread.demo.pool;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

@Slf4j
public class MultipleFutureTaskDemo {

    public static void main(String[] args) {
        // 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // 创建多个带返回值的任务

        Instant startTime = Instant.now();
        List<Future<String>> futureList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Future<String> future = executor.submit(() -> {
                // 模拟耗时操作
                Random random = new Random();
                int randomNumber = random.nextInt(4001);
                Thread.sleep(randomNumber);
                log.info("{} 耗时：{}", Thread.currentThread().getName(), randomNumber);
                return "Hello, from the Callable task!";
            });
            futureList.add(future);
        }

        // 关闭线程池
        executor.shutdown();

        // 获取任务的返回值
        for (Future<String> future : futureList) {
            try {
                String result = future.get();
                System.out.println("任务返回值: " + result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        Instant endTime = Instant.now();
        long seconds = ChronoUnit.MILLIS.between(startTime, endTime);
        log.warn("本批次处理结束。耗时：{}毫秒", seconds);

    }
}
