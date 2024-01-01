package com.kapokframework.aop.overview.interceptor;

import java.lang.reflect.Method;

/**
 * ExceptionInterceptor
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a> 2023-12-28 18:35:31
 * @since 1.0
 */
public interface ExceptionInterceptor {

    void intercept(Object proxy, Method method, Object[] args, Throwable throwable);

}
