package com.yang.multi.thread.demo.redspider.chapter4;

/**
 * @author yangliangchuang
 * @date 2022/3/20 9:04
 */
public class ThreadNewStateDemo {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {});
        System.out.println(thread.getState()); // 输出 NEW

        thread.start();
        System.out.println(thread.getState()); // 输出 NEW
    }
}
