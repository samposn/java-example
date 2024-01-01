package com.kapokframework.aop.overview.interceptor;

import java.lang.reflect.Method;

/**
 * 前置拦截器
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a> 2023-12-28 18:05:06
 * @since 1.0
 */
public interface BeforeInterceptor {

    Object before(Object proxy, Method method, Object[] args);

}
