package com.kapokframework.aop.overview;

import lombok.extern.slf4j.Slf4j;

/**
 * 继承的方式
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a> 2023-12-01 10:43:38
 * @since 1.0
 */
@Slf4j
public class FoolishOldManExtendProxy extends FoolishOldManDefault {

    public FoolishOldManExtendProxy(String name) {
        super(name);
    }

    @Override
    public void moveMountain() {
        long startTime = System.currentTimeMillis();
        log.info("开挖时间: {}", startTime);
        super.moveMountain();
        long endTime = System.currentTimeMillis();
        log.info("结束时间: {}", endTime);
        log.info("挖土用时: {}ms", (endTime - startTime));
    }

}
