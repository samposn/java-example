package com.kapokframework;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.sql.Timestamp;
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

        log.info("测试 SimpleDateFormat {} ", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Timestamp(System.currentTimeMillis()))));

        String a = "abdsfasdf";
        log.info("数组 {}", a.split("-")[0]);

        log.info("String.format {}", String.format("%02d", 1));
        log.info("String.format {}", String.format("%02d", 100));

        double d = 45113.1;

        log.info("小数部分: {}", d % 1);

        File file = new File("/Users/zhangweiming/sensors/abc/distinct.txt");

        String fileName = "sql/dm/README.md";
        String[] filePathArr = StringUtils.split(fileName, "/");
        log.info("最后一个 s: {}", filePathArr[filePathArr.length - 1]);

        String filePath = FilenameUtils.getFullPathNoEndSeparator(fileName);


        log.info("文件：{}", filePath);

        String difference = StringUtils.difference("/Users/zhangweiming/test/a/", "/Users/zhangweiming/test/a/b/1.txt");

        log.info("difference：{}", difference);

    }

}
