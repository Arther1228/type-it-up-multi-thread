package com.yang.multi.thread.demo.future;

import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA
 * Description:
 * User: lsr
 * Date: 2020/6/15
 * Time: 18:46
 *【java】获取线程池中的线程返回结果，Future和FutureTask的使用
 * https://blog.csdn.net/lsr40/article/details/106770113
 */
public class FutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        System.out.println("主线程开始工作：");
        long startTime = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        //newThreadDemo(executorService);
        //futureGetDemo(executorService);
        //futureGetAndTimeOutDemo(executorService);
        //futureGetAndCallableDemo(executorService);
        //callbackDemo(executorService);
        futureTaskDemo(executorService);

        long stopTime = System.currentTimeMillis();
        System.out.println("主线程结束，耗时："+(stopTime - startTime)+"ms");
        //关闭线程
        executorService.shutdown();
    }

    /**
     *  第一种，无所谓线程执行是否结束，不阻塞
     *      打印结果：
     *      主线程开始工作：
     *      主线程结束，耗时：3ms
     *      线程中的任务，开始运行！
     *      正在执行！
     *      线程中的任务，结束执行！
     * @param executorService
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void newThreadDemo(ExecutorService executorService) throws ExecutionException, InterruptedException {
        executorService.submit(new Runnable() {
            public void run() {
                System.out.println("线程中的任务，开始运行！");
                doSomeThing();
                System.out.println("线程中的任务，结束执行！");
            }
        });
    }


    /**
     *  第二种，通过future的get方法，会阻塞等到执行完毕！
     *      主线程开始工作：
     *      线程中的任务，开始运行！
     *      正在执行！
     *      线程中的任务，结束执行！
     *      null
     *      主线程结束，耗时：3005ms
     * @param executorService
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void futureGetDemo(ExecutorService executorService) throws ExecutionException, InterruptedException {
        Future<?> future = executorService.submit(new Runnable() {
            public void run() {
                System.out.println("线程中的任务，开始运行！");
                doSomeThing();
                System.out.println("线程中的任务，结束执行！");
            }
        });
        Object o = future.get();
        System.out.println(o);
    }

    /**
     *  第三种，通过future的get方法，并且有超时时间，会阻塞等到执行完毕！
     *
     *      主线程开始工作：
     *      线程中的任务，开始运行！
     *      正在执行！
     *      主线程结束，耗时：1007ms
     *      java.util.concurrent.TimeoutException
     * 	        at java.util.concurrent.FutureTask.get(FutureTask.java:205)
     * 	        at com.study.asyn.FutureTest.futureGetAndTimeOutDemo(FutureTest.java:99)
     * 	        at com.study.asyn.FutureTest.main(FutureTest.java:21)
     *      线程中的任务，结束执行！
     * @param executorService
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void futureGetAndTimeOutDemo(ExecutorService executorService) throws ExecutionException, InterruptedException {
        Future<?> future = executorService.submit(new Runnable() {
            public void run() {
                System.out.println("线程中的任务，开始运行！");
                doSomeThing();
                System.out.println("线程中的任务，结束执行！");
            }
        });
        Object o = null;
        try {
            o = future.get(1,TimeUnit.SECONDS);
            System.out.println(o);
        } catch (TimeoutException e) {
            //true和false的区别大家可以看源码中的注释
            //true就是中断当前执行的线程，false就是让线程执行完
            future.cancel(false);
            e.printStackTrace();
        }

    }


    /**
     *  第四种，通过future的get并且配合Callable方法，获得线程中return结果，会阻塞等到执行完毕！
     *      主线程开始工作：
     *      线程中的任务，开始运行！
     *      正在执行！
     *      线程中的任务，结束执行！
     *      返回值~
     *      主线程结束，耗时：3005ms
     * @param executorService
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void futureGetAndCallableDemo(ExecutorService executorService) throws ExecutionException, InterruptedException {
        Future<?> future = executorService.submit(new Callable<String>() {
            public String call() throws Exception {
                System.out.println("线程中的任务，开始运行！");
                doSomeThing();
                System.out.println("线程中的任务，结束执行！");
                return "返回值~";
            }
        });
        String returnValue = (String)future.get();
        System.out.println(returnValue);
    }

    /**
     *  第五种，想在获得future的get结果之后，再做一些处理，但是又不想阻塞主线程，通过回调，启动另一个线程来做这个事情
     *
     *      主线程开始工作：
     *      线程中的任务，开始运行！
     *      正在执行！
     *      主线程结束，耗时：5ms
     *      线程中的任务，结束执行！
     *      返回值~
     *      成功！
     *
     * @param executorService
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void callbackDemo(ExecutorService executorService) throws ExecutionException, InterruptedException {
        ExecutorService fixExecutorService = Executors.newFixedThreadPool(1);

        final Future<?> future = executorService.submit(new Callable<String>() {
            public String call() throws Exception {
                System.out.println("线程中的任务，开始运行！");
                doSomeThing();
                System.out.println("线程中的任务，结束执行！");
                return "返回值~";
            }
        });

        fixExecutorService.submit(new Runnable() {
            public void run() {
                String returnValue = null;
                try {
                    returnValue = (String)future.get();
                    System.out.println(returnValue);
                    System.out.println("成功！");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        fixExecutorService.shutdown();

    }


    /**
     *  第六种，之前线程都是使用submit提交任务，以此获得返回Future对象
     *  但是使用升级版的FutureTask+线程池的execute方法，也能得到返回值
     *
     *      主线程开始工作：
     *      线程中的任务，开始运行！
     *      正在执行！
     *      线程中的任务，结束执行！
     *      返回值~
     *      主线程结束，耗时：3005ms
     *
     * @param executorService
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void futureTaskDemo(ExecutorService executorService) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
            public String call() throws Exception {
                System.out.println("线程中的任务，开始运行！");
                doSomeThing();
                System.out.println("线程中的任务，结束执行！");
                return "返回值~";
            }
        });
        //execute方法虽然没有返回值，但是因为FutureTask实现类Future+Runnable，
        // 所以也可以直接调用execute方法计算，再调用get获得返回结果
        executorService.execute(futureTask);
        String s = futureTask.get();
        System.out.println(s);

    }


    private static void doSomeThing(){
        try {
            System.out.println("正在执行！");
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
