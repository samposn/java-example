package com.kapokframework.poi;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URL;

/**
 * ReadExcelUsingSaxParserExample
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a>
 * @since 1.0
 */
@Slf4j
public class ReadExcelUsingSaxParserExample {
    public static void main(String[] args) throws Exception {

        URL url = ReadExcelUsingSaxParserExample.class
                .getClassLoader()
                .getResource("MIB_Sales__202308031014.xlsm");
//                .getResource("mibTemplete.xlsx");
//                .getResource("howtodoinjava_demo.xlsx");

        new ExcelReaderHandler().readExcelFile(new File(url.getFile()));
    }
}
