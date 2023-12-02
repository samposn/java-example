package com.kapokframework.aop.overview;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Java AOP 代理模式，静态代理示例
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a>
 * @since 1.0
 */
public class StaticProxyDemo {

    public static void main(String[] args) {
        // 继承的方式
        FoolishOldMan foolishOldManA = new FoolishOldManExtendProxy("愚公A");
        foolishOldManA.moveMountain();

        // 组合的方式
        FoolishOldMan foolishOldMan = new FoolishOldManDefault("愚公B");
        FoolishOldManCompositeProxy foolishOldManCompositeProxy = new FoolishOldManCompositeProxy(foolishOldMan);
        foolishOldManCompositeProxy.moveMountain();
    }

}

