package com.jerry.demo.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: Jerry
 * @create: 2020-09-07 14:30
 * @update: 2020-09-07
 * @description: 线程池
 */
public class ThreadPoolUtil {

    /**
     * 线程缓冲队列
     */
    private static final BlockingQueue<Runnable> BQUEUE = new ArrayBlockingQueue<>(100);

    /**
     * 核心线程数，会一直存活，即使没有任务，线程池也会维护线程的最少数量
     */
    private static final int SIZE_CORE_POOL = 5;

    /**
     * 线程池维护线程的最大数量
     */
    private static final int SIZE_MAX_POOL = 10;

    /**
     * 当前线程数超过核心线程数时，空闲线程存活时间
     */
    private static final long ALIVE_TIME = 2000;

    private static final ThreadPoolExecutor POOL = new ThreadPoolExecutor(SIZE_CORE_POOL, SIZE_MAX_POOL, ALIVE_TIME, TimeUnit.MILLISECONDS,
            BQUEUE, new ThreadPoolExecutor.CallerRunsPolicy());

    static {

        POOL.prestartAllCoreThreads();
    }

    public static ThreadPoolExecutor getPool() {
        return POOL;
    }

    public static void main(String[] args) {
        //查看线程数
        System.out.println(POOL.getPoolSize());
        ////调用线程 使用方式：
        ThreadPoolUtil.getPool().execute(new TestThread("JT"));
    }


    /**
     * 测试方法
     */
    private static class TestThread implements Runnable {
        private String name;

        public TestThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(name);
        }
    }
}
