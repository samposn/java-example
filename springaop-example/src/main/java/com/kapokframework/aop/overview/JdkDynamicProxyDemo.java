package com.kapokframework.aop.overview;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Java AOP 代理模式，动态代理示例
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a> 2023-12-01 08:49:06
 * @since 1.0
 */
@Slf4j
public class JdkDynamicProxyDemo {

    public static void main(String[] args) {
        JdkDynamicProxy<FoolishOldMan> jdkDynamicProxy = new JdkDynamicProxy<>(new FoolishOldManDefault("愚公"));
        jdkDynamicProxy.getInstance().moveMountain();
    }

}

@Slf4j
@RequiredArgsConstructor
class JdkDynamicProxy<T> implements InvocationHandler {

    private final T targetObject;

    public T getInstance() {
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        return (T) Proxy.newProxyInstance(contextClassLoader, new Class[]{FoolishOldMan.class}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (ReflectionUtils.isEqualsMethod(method)) {
            return proxy == args[0];
        } else if (ReflectionUtils.isHashCodeMethod(method)) {
            return System.identityHashCode(proxy);
        } else if (ReflectionUtils.isToStringMethod(method)) {
            return ObjectUtils.nullSafeToString(proxy);
        } else {
            log.info("之前");
            final Object invoke = method.invoke(this.targetObject, args);
            log.info("之后");
            return invoke;
        }
    }
}
