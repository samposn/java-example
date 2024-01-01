package com.kapokframework.aop.overview.dynamicproxy;

import com.kapokframework.aop.overview.sample.FoolishOldMan;
import com.kapokframework.aop.overview.sample.FoolishOldManDefault;
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

        FoolishOldMan target = FoolishOldManDefault.builder().name("愚公").build();
        JdkDynamicProxy jdkDynamicProxy = new JdkDynamicProxy(target);

        // class $Proxy0 extends java.lang.reflect.Proxy implements FoolishOldMan {}
        final Object object = jdkDynamicProxy.getInstance();
        if (object instanceof FoolishOldMan foolishOldMan) {
            // jdk.proxy1.$Proxy0
            log.info("{}", foolishOldMan.getClass());
            foolishOldMan.moveMountain();
        }

        // class $Proxy1 extends java.lang.reflect.Proxy implements Comparable {}
        Object object2 = Proxy.newProxyInstance(Comparable.class.getClassLoader(), new Class[]{Comparable.class}, (proxy, method, args1) -> {
            if (ReflectionUtils.isEqualsMethod(method)) {
                return proxy == args1[0];
            } else if (ReflectionUtils.isHashCodeMethod(method)) {
                return System.identityHashCode(proxy);
            } else if (ReflectionUtils.isToStringMethod(method)) {
                return ObjectUtils.nullSafeToString((Object) null);
            } else {
                return null;
            }
        });
        // jdk.proxy2.$Proxy1
        log.info("{}", object2.getClass());
    }

    @Slf4j
    @RequiredArgsConstructor
    static class JdkDynamicProxy implements InvocationHandler {

        private final Object target;

        public Object getInstance() {
            return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (ReflectionUtils.isEqualsMethod(method)) {
                return proxy == args[0];
            } else if (ReflectionUtils.isHashCodeMethod(method)) {
                return System.identityHashCode(proxy);
            } else if (ReflectionUtils.isToStringMethod(method)) {
                return ObjectUtils.nullSafeToString(target);
            } else {
                long startTime = System.currentTimeMillis();
                log.info("开挖时间: {}", startTime);
                final Object returnValue = method.invoke(target, args);
                long endTime = System.currentTimeMillis();
                log.info("结束时间: {}", endTime);
                log.info("挖土用时: {}ms", (endTime - startTime));
                return returnValue;
            }
        }
    }

}
