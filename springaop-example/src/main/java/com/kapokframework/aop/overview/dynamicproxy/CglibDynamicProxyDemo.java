package com.kapokframework.aop.overview.dynamicproxy;

import com.kapokframework.aop.overview.sample.FoolishOldMan;
import com.kapokframework.aop.overview.sample.FoolishOldManDefault;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGLIB 动态代理示例
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a> 2023-12-28 09:42:10
 * @since 1.0
 */
@Slf4j
public class CglibDynamicProxyDemo {

    public static void main(String[] args) {
        // 创建增强器
        Enhancer enhancer = new Enhancer();

        // 指定需要增强的类型
        enhancer.setSuperclass(FoolishOldManDefault.class);

        // 指定拦截的接口
        enhancer.setInterfaces(new Class[]{FoolishOldMan.class});

        // 指定回调
        enhancer.setCallback((MethodInterceptor) CglibDynamicProxyDemo::intercept);

        // 创建实例并调用方法
        // enhancer.create() 使用无参数构造器创建实例
        final Object object = enhancer.create(new Class[]{String.class}, new String[]{"愚公A"}); // 使用带参数构造器创建实例
        if (object instanceof FoolishOldMan foolishOldMan) {
            foolishOldMan.moveMountain();
            log.info("foolishOldMan {}", foolishOldMan);
        }
    }

    private static Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        // MethodInterceptor 方法拦截器会拦截所有方法的调用，这里需要做调用哪些方法的分发
        if ("moveMountain".equals(method.getName())) {
            long startTime = System.currentTimeMillis();
            log.info("开挖时间: {}", startTime);
            final Object returnValue = proxy.invokeSuper(obj, args);
            long endTime = System.currentTimeMillis();
            log.info("结束时间: {}", endTime);
            log.info("挖土用时: {}ms", (endTime - startTime));
            return returnValue;
        } else {
            return proxy.invokeSuper(obj, args);
        }
    }
}
