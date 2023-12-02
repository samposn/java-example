package com.kapokframework.thread.example01;

import lombok.extern.slf4j.Slf4j;

/**
 * 多线程示例一
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a> 2021-09-01 15:27:43
 * @since 1.0
 */
@Slf4j
public class ThreadDemo {

    /**
     * Runnable 运行器，要完成的任务在这里定义，那么我们可以把 Runnable 直接理解为任务
     */
    static class MoveMountain implements Runnable {
        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                log.info("{}正在挖土第 {} 次", Thread.currentThread().getName(), i);
            }
        }
    }

    /**
     * main 方法，运行示例
     * @param args 运行时输入的参数
     */
    public static void main(String[] args) {

        // 创建一项任务，称为 moveMountain
        MoveMountain moveMountain = new MoveMountain();

        // Thread 线程，我们可以理解为劳动力，要完成任务就必须要有劳动力，这里创建了两个分别名为：愚公A、愚公B的劳动力（线程）
        // 分别称为：theFoolishOldManA、theFoolishOldManB
        Thread theFoolishOldManA = new Thread(moveMountain, "愚公A");
        Thread theFoolishOldManB = new Thread(moveMountain, "愚公B");

        // 分别启动两个劳动力（线程），两个劳动力各自运行
        theFoolishOldManA.start();
        theFoolishOldManB.start();

    }

}