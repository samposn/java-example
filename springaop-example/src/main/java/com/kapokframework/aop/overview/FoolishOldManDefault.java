package com.kapokframework.aop.overview;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * FoolishOldMan
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a> 2023-12-01 08:56:50
 * @since 1.0
 */
@Slf4j
@Getter
@RequiredArgsConstructor
public class FoolishOldManDefault implements FoolishOldMan {

    private final String name;

    @Override
    public void moveMountain() {
        // 模拟在挖土
        for (int i = 1; i <= 10; i++) {
            log.info("{}正在挖土第 {} 次", this.getName(), i);
        }
    }

}
