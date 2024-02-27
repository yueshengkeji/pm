package com.yuesheng.pm.util;

import org.springframework.stereotype.Component;

import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component("threadManager")
public class ThreadManager {
    /**
     * 任务执行延迟毫秒
     */
    private final int OPERATE_DELAY_TIME = 100;

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    /**
     * 执行任务
     *
     * @param task 任务
     */
    public void execute(TimerTask task) {
        executorService.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * 执行任务
     *
     * @param task      任务
     * @param delayTime 延时时间（毫秒）
     */
    public void execute(TimerTask task, int delayTime) {
        executorService.schedule(task, delayTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 停止任务线程池
     */
    public void shutdown() {

        shutdownAndAwaitTermination(executorService);
    }

    public static void shutdownAndAwaitTermination(ExecutorService pool) {
        if (pool != null && !pool.isShutdown()) {
            pool.shutdown();
            try {
                if (!pool.awaitTermination(120, TimeUnit.SECONDS)) {
                    pool.shutdownNow();
                }
            } catch (InterruptedException ie) {
                pool.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}
