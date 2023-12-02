package com.kapokframework.thread.example03;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 多线程示例三
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a> 2023-11-30 16:54:11
 * @since 1.0
 */
@Slf4j
public class FoolishOldMan implements Callable<Void> {

    @Override
    public Void call() {
        // 模拟在挖土
        for (int i = 1; i <= 10; i++) {
            log.info("{}正在挖土第 {} 次", Thread.currentThread().getName(), i);
        }
        return null;
    }

    public static void main(String[] args) {
        FoolishOldMan foolishOldManA = new FoolishOldMan();
        FoolishOldMan foolishOldManB = new FoolishOldMan();
        FutureTask<Void> futureTaskA = new FutureTask<>(foolishOldManA);
        FutureTask<Void> futureTaskB = new FutureTask<>(foolishOldManB);
        Thread threadA = new Thread(futureTaskA, "愚公A");
        Thread threadB = new Thread(futureTaskB, "愚公B");
        threadA.start();
        threadB.start();
    }

}
