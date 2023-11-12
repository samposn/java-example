package com.kapokframework;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a>
 * @since 1.0
 */
public class Main {

    public static void main(String[] args) {

        Logger log = LoggerFactory.getLogger(Main.class);

        log.debug("输出DEBUG级别日志");
        log.info("输出INFO级别日志");
        log.warn("输出WARN级别日志");
        log.error("输出ERROR级别日志");

        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.print(lc);

    }

}
