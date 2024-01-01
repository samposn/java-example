package com.kapokframework.aop.overview.interceptor;

import java.lang.reflect.Method;

/**
 * （方法返回）后置拦截器
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a> 2023-12-28 18:07:12
 * @since 1.0
 */
public interface AfterReturnInterceptor {

    Object after(Object proxy, Method method, Object[] args, Object returnValue);

}
