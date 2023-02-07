package com.kapokframework;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Main
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a>
 * @since 1.0
 */
@Slf4j
public class Main {

    public static void main(String[] args) {

        log.info("测试 SimpleDateFormat {} ", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));

    }

}
