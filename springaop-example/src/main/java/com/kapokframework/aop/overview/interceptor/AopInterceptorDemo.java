package com.kapokframework.aop.overview.interceptor;

import com.kapokframework.aop.overview.sample.FoolishOldMan;
import com.kapokframework.aop.overview.sample.FoolishOldManDefault;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * AOP 拦截器示例
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a> 2023-12-28 17:55:26
 * @since 1.0
 */
@Slf4j
public class AopInterceptorDemo {

    public static void main(String[] args) {
        FoolishOldMan target = FoolishOldManDefault.builder().name("愚公").build();
        JdkDynamicProxy jdkDynamicProxy = new JdkDynamicProxy(target);
        jdkDynamicProxy.setBeforeInterceptor(AopInterceptorDemo::before);
        jdkDynamicProxy.setAfterReturnInterceptor(AopInterceptorDemo::after);

        final Object object = jdkDynamicProxy.getInstance();
        if (object instanceof FoolishOldMan foolishOldMan) {
            foolishOldMan.moveMountain();
        }
    }

    private static Object before(Object proxy, Method method, Object[] args) {
        long startTime = System.currentTimeMillis();
        log.info("开挖时间: {}", startTime);
        return startTime;
    }

    private static Object after(Object proxy, Method method, Object[] args, Object returnValue) {
        long endTime = System.currentTimeMillis();
        log.info("结束时间: {}", endTime);
        return endTime;
    }

    @Slf4j
    @Setter
    @RequiredArgsConstructor
    static class JdkDynamicProxy implements InvocationHandler {

        private final Object target;

        private BeforeInterceptor beforeInterceptor;

        private AfterReturnInterceptor afterReturnInterceptor;

        private ExceptionInterceptor exceptionInterceptor;

        private FinallyInterceptor finallyInterceptor;

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
                long startTime = 0;
                long endTime = 0;
                Object returnValue = null;
                if (beforeInterceptor != null) {
                    startTime = (Long) beforeInterceptor.before(proxy, method, args);
                }
                try {
                    returnValue = method.invoke(target, args);
                    if (afterReturnInterceptor != null) {
                        endTime = (Long) afterReturnInterceptor.after(proxy, method, args, returnValue);
                    }
                } catch (Exception throwable) {
                    if (exceptionInterceptor != null) {
                        exceptionInterceptor.intercept(proxy, method, args, throwable);
                    }
                } finally {
                    if (finallyInterceptor != null) {
                        finallyInterceptor.finalize(proxy, method, args, returnValue);
                    } else {
                        finallyInterceptor = new FinallyTimerInterceptor(startTime, endTime);
                        finallyInterceptor.finalize(proxy, method, args, returnValue);
                    }
                }
                return returnValue;
            }
        }
    }

}

@Slf4j
@RequiredArgsConstructor
class FinallyTimerInterceptor implements FinallyInterceptor {

    private final long startTime;
    private final long endTime;

    @Override
    public Object finalize(Object proxy, Method method, Object[] args, Object returnValue) {
        log.info("挖土用时: {}ms", (endTime - startTime));
        return null;
    }

}
