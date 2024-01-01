package com.kapokframework.aop.overview.sample;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * FoolishOldMan
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a> 2023-12-01 08:56:50
 * @since 1.0
 */
@Slf4j
@Getter
@ToString
@SuperBuilder
public class FoolishOldManDefault implements FoolishOldMan {

    protected String name;

    @Override
    public void moveMountain() {
        // 模拟在挖土
        for (int i = 1; i <= 10; i++) {
            log.info("{}第 {} 次挖土", this.getName(), i);
        }
    }

}
