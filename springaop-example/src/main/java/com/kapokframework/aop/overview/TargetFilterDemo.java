package com.kapokframework.aop.overview;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * TargetFilterDemo
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a> 2023-12-04 15:40:54
 * @since 1.0
 */
@Slf4j
public class TargetFilterDemo {

    public static void main(String[] args) throws ClassNotFoundException {
        String targetClassName = "com.kapokframework.aop.overview.sample.FoolishOldMan";
        // 获得当前线程的 ClassLoader
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        // 加载目标数
        final Class<?> targetClass = contextClassLoader.loadClass(targetClassName);
        final Method targetMethod = ReflectionUtils.findMethod(targetClass, "moveMountain");
        log.info("targetMethod : {}", targetMethod);

        // 查找方法  throws 类型为 NullPointerException
        ReflectionUtils.doWithMethods(targetClass,
                (method) -> log.info("仅抛出 NullPointerException 方法为：{}", method),
                (method) -> {
                    log.info("method {}", method.getName());
                    final Class<?>[] exceptionTypes = method.getExceptionTypes();
                    return exceptionTypes.length == 1
                            && NullPointerException.class.equals(exceptionTypes[0]);
                }
        );
    }

}
