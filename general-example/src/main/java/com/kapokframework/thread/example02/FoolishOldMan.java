package com.kapokframework.thread.example02;

import lombok.extern.slf4j.Slf4j;

/**
 * 多线程示例二
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a> 2021-09-01 17:19:03
 * @since 1.0
 */
@Slf4j
public class FoolishOldMan extends Thread {

    public FoolishOldMan(String name) {
        super(name);
    }

    /**
     * Thread 是 Runnable 的实现类，也就是说 Thread 也是一个运行器，那么可以通过继承 Thread，重写 run 方法来定义【要完成的任务】
     * 即 TheFoolishOldMan 是一个自带任务的（劳动力）
     */
    @Override
    public void run() {
        // 模拟在挖土
        for (int i = 1; i <= 10; i++) {
            log.info("{}正在挖土第 {} 次", this.getName(), i);
        }
    }

    /**
     * main 方法，运行示例
     * @param args 运行时输入的参数
     */
    public static void main(String[] args) {

        // 创建两个分别名为：愚公A、愚公B的自带任务的线程（劳动力）
        // 分别称为：theFoolishOldManA、theFoolishOldManB
        FoolishOldMan foolishOldManA = new FoolishOldMan("愚公A");
        FoolishOldMan foolishOldManB = new FoolishOldMan("愚公B");

        // 分别启动两个线程（劳动力），两个线程（劳动力）各自运行
        foolishOldManA.start();
        foolishOldManB.start();

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