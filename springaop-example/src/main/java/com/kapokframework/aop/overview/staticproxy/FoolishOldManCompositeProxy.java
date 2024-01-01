package com.kapokframework.aop.overview.staticproxy;

import com.kapokframework.aop.overview.sample.FoolishOldMan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 组合的方式
 */
@Slf4j
@RequiredArgsConstructor
public class FoolishOldManCompositeProxy {

    private final FoolishOldMan foolishOldMan;

    public void moveMountain() {
        long startTime = System.currentTimeMillis();
        log.info("开挖时间: {}", startTime);
        foolishOldMan.moveMountain();
        long endTime = System.currentTimeMillis();
        log.info("结束时间: {}", endTime);
        log.info("挖土用时: {}ms", (endTime - startTime));
    }

}
