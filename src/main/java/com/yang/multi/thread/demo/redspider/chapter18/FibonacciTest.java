package com.yang.multi.thread.demo.redspider.chapter18;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class FibonacciTest {

    class Fibonacci extends RecursiveTask<Integer> {

        int n;

        public Fibonacci(int n) {
            this.n = n;
        }

        // 主要的实现逻辑都在compute()里
        @Override
        protected Integer compute() {
            // 这里先假设 n >= 0
            if (n <= 1) {
                return n;
            } else {
                // f(n-1)
                Fibonacci f1 = new Fibonacci(n - 1);
                f1.fork();
                // f(n-2)
                Fibonacci f2 = new Fibonacci(n - 2);
                f2.fork();
                // f(n) = f(n-1) + f(n-2)
                return f1.join() + f2.join();
            }
        }
    }

    @Test
    public void testFib() throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        System.out.println("CPU核数：" + Runtime.getRuntime().availableProcessors());
        long start = System.currentTimeMillis();
        Fibonacci fibonacci = new Fibonacci(40);
        Future<Integer> future = forkJoinPool.submit(fibonacci);
        System.out.println(future.get());
        long end = System.currentTimeMillis();
        System.out.println(String.format("耗时：%d millis", end - start));
    }


    // 普通递归，复杂度为O(2^n)
    public int plainRecursion(int n) {
        if (n == 1 || n == 2) {
            return 1;
        } else {
            return plainRecursion(n -1) + plainRecursion(n - 2);
        }
    }

    @Test
    public void testPlain() {
        long start = System.currentTimeMillis();
        int result = plainRecursion(40);
        long end = System.currentTimeMillis();
        System.out.println("计算结果:" + result);
        System.out.println(String.format("耗时：%d millis",  end -start));
    }

    // 通过循环来计算，复杂度为O(n)
    private int computeFibonacci(int n) {
        // 假设n >= 0
        if (n <= 1) {
            return n;
        } else {
            int first = 1;
            int second = 1;
            int third = 0;
            for (int i = 3; i <= n; i ++) {
                // 第三个数是前两个数之和
                third = first + second;
                // 前两个数右移
                first = second;
                second = third;
            }
            return third;
        }
    }

    @Test
    public void testComputeFibonacci() {
        long start = System.currentTimeMillis();
        int result = computeFibonacci(40);
        long end = System.currentTimeMillis();
        System.out.println("计算结果:" + result);
        System.out.println(String.format("耗时：%d millis",  end -start));
    }


}