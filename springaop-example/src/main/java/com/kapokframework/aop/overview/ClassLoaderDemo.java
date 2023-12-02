package com.kapokframework.aop.overview;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassLoaderDemo
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a>
 * @since 1.0
 */
@Slf4j
public class ClassLoaderDemo {

    public static void main(String[] args) {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        while (contextClassLoader != null) {
            log.info("{}", contextClassLoader);
            contextClassLoader = contextClassLoader.getParent();
        }
    }

}
