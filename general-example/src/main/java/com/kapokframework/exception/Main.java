package com.kapokframework.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * Main
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a>
 * @since 1.0
 */
@Slf4j
public class Main {

    public static void main(String[] args) {
        isBiggerThanZero(-4);
    }

    private static void isBiggerThanZero(int i) {
        if (i < 0) {
            throw new RuntimeException("小于0");
        }
    }

}
