package com.kapokframework.aop.overview;

import lombok.extern.slf4j.Slf4j;

/**
 * 类加载示例
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a>
 * @since 1.0
 */
@Slf4j
public class ClassLoaderDemo {

    public static void main(String[] args) {
        ClassLoader contextClassLoader = ClassLoaderDemo.class.getClassLoader();
        while (contextClassLoader != null) {
            log.info("{}", contextClassLoader);
            contextClassLoader = contextClassLoader.getParent();
        }
    }

}
