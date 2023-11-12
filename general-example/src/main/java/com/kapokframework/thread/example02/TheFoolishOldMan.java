package com.kapokframework.thread.example02;

import lombok.extern.slf4j.Slf4j;

/**
 * 多线程示例二
 *
 * @author <a href=" ">Will WM. Zhang</a >
 * @date 2021-09-01 17:19
 * @since 1.0
 */
@Slf4j
public class TheFoolishOldMan extends Thread {

    public TheFoolishOldMan(String name) {
        super(name);
    }

    // Thread 是 Runnable 的实现类，也就是说 Thread 也是一个运行器，那么可以通过继承 Thread，重写 run 方法来定义：要完成的任务
    // 即 TheFoolishOldMan 是一个自带任务的劳动力（线程）
    @Override
    public void run() {
        // 模拟在挖土
        for (int i = 1; i <= 10; i++) {
            log.info("我正在挖土第 {} 次", i);
        }
    }

    // main 方法，运行示例
    public static void main(String[] args) {

        // 创建两个分别名为：愚公A、愚公B的自带任务的劳动力（线程）
        // 分别称为：theFoolishOldManA、theFoolishOldManB
        TheFoolishOldMan theFoolishOldManA = new TheFoolishOldMan("愚公A");
        TheFoolishOldMan theFoolishOldManB = new TheFoolishOldMan("愚公B");

        // 分别启动两个劳动力（线程），两个劳动力各自运行
        theFoolishOldManA.start();
        theFoolishOldManB.start();

    }

    /*
     * 总结：
     * 通过继承 Thread 与实现 Runnable 来处理多线程的区别是：
     * 1. 实现 Runnable 接口，可以避免单继承的局限性，具有较强的健壮性。
     * 2. 实现 Runnable 接口，可以实现资源的共享，同时处理同一资源。
     * 3. 继承 Thread 类，线程间都是独立运行，资源不共享。
     * 4. 继承 Thread 类，不再被其他类继承（Java 不存在多继承）。
     */
}