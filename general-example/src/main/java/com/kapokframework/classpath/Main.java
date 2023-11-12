package com.kapokframework.classpath;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Main
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a>
 * @since 1.0
 */
@Slf4j
public class Main {

    public static void main(String[] args) throws IOException {
        File file = new File("abc");
        log.info("          path: {}", file.getPath());
        log.info(" absolute path: {}", file.getAbsolutePath());
        log.info("canonical path: {}", file.getCanonicalPath());

        String path = Objects.requireNonNull(Main.class.getResource("/")).getPath();
        log.info("    class path: {}", path);
    }

}
