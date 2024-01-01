package com.kapokframework.aop.overview.interceptor;

import java.lang.reflect.Method;

/**
 * FinallyInterceptor
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a> 2023-12-28 18:21:35
 * @since 1.0
 */
public interface FinallyInterceptor {

    Object finalize(Object proxy, Method method, Object[] args, Object returnValue);

}
