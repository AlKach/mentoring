package com.kachanov.mentoring.concurrency.customer.executor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PausableExecutor extends ThreadPoolExecutor {
    private boolean isPaused = false;
    private final Lock pauseLock = new ReentrantLock();
    private final Condition pauseCondition = pauseLock.newCondition();

    public PausableExecutor(int poolSize) {
        super(poolSize, poolSize, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        pauseLock.lock();
        try {
            while (isPaused) {
                pauseCondition.await();
            }
        } catch (InterruptedException e) {
            t.interrupt();
        } finally {
            pauseLock.unlock();
        }
    }

    public void pause() {
        pauseLock.lock();
        isPaused = true;
        pauseLock.unlock();
    }

    public void resume() {
        pauseLock.lock();
        isPaused = false;
        pauseLock.unlock();
    }
}
