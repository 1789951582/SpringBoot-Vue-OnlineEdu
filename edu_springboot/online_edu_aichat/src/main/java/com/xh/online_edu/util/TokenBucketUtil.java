package com.xh.online_edu.util;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TokenBucketUtil {
    private final int capacity; // 桶的容量
    private final int refillRate; // 每秒补充令牌的数量
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private int tokens; // 当前令牌数量
    private long lastRefillTime; // 上次补充令牌的时间戳

    public TokenBucketUtil(int capacity, int refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate;
        this.tokens = capacity;
        this.lastRefillTime = System.nanoTime();
    }

    public void consume() throws InterruptedException {
        lock.lock();
        try {
            while (tokens <= 0) {
                // 如果没有令牌，阻塞当前线程
                condition.await(1, TimeUnit.SECONDS); // 等待1秒后重新检查
            }
            tokens--;
            condition.signalAll(); // 通知其他等待的线程
        } finally {
            lock.unlock();
        }
    }

    private void refill() {
        long now = System.nanoTime();
        long timeSinceLastRefill = now - lastRefillTime;
        int tokensToAdd = (int) (timeSinceLastRefill * refillRate / TimeUnit.SECONDS.toNanos(1));
        if (tokensToAdd > 0) {
            tokens = Math.min(tokens + tokensToAdd, capacity);
            lastRefillTime = now;
            condition.signalAll(); // 通知所有等待的线程
        }
    }
}