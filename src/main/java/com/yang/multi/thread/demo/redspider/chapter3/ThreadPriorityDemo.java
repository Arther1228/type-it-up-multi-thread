package com.yang.multi.thread.demo.redspider.chapter3;

import org.junit.Test;

/**
 * @author admin 2022/3/19
 */
public class ThreadPriorityDemo {

    @Test
    public void setThreadPriority(){
        Thread a = new Thread();
        System.out.println("我是默认线程优先级："+a.getPriority());
        Thread b = new Thread();
        b.setPriority(10);
        System.out.println("我是设置过的线程优先级："+b.getPriority());
    }


    @Test
    public void conflictThreadWithGroupPriority(){
        ThreadGroup threadGroup = new ThreadGroup("t1");
        threadGroup.setMaxPriority(6);
        Thread thread = new Thread(threadGroup,"thread");
        thread.setPriority(9);
        System.out.println("我是线程组的优先级"+threadGroup.getMaxPriority());
        System.out.println("我是线程的优先级"+thread.getPriority());
    }
}
