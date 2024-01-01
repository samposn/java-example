package com.kapokframework.aop.overview.staticproxy;

import com.kapokframework.aop.overview.sample.FoolishOldMan;
import com.kapokframework.aop.overview.sample.FoolishOldManDefault;

/**
 * Java AOP 代理模式，静态代理示例
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a>
 * @since 1.0
 */
public class StaticProxyDemo {

    public static void main(String[] args) {
        // 继承的方式
        FoolishOldMan foolishOldManA = FoolishOldManExtendProxy.builder().name("愚公A").build();
        foolishOldManA.moveMountain();

        // 组合的方式
        FoolishOldMan foolishOldMan = FoolishOldManDefault.builder().name("愚公B").build();
        FoolishOldManCompositeProxy foolishOldManCompositeProxy = new FoolishOldManCompositeProxy(foolishOldMan);
        foolishOldManCompositeProxy.moveMountain();
    }

}

