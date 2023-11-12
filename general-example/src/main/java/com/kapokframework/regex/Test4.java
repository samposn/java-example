package com.kapokframework.regex;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Test4
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a>
 * @since 1.0
 */
@Slf4j
public class Test4 {
    public static void main(String[] args) {
        // 时间参数
        String timeStr = "2021-07-28 15:16:44";
        // 表达式
        String regex = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d$";
        // 实例化
        Pattern p = Pattern.compile(regex);
        // 创建Matcher对象
        Matcher mach = p.matcher(timeStr);
        // 结果
        boolean flag = mach.matches();

        log.info("flag : {}", flag);


        BigDecimal bd = new BigDecimal("-1.1E-31");
        BigDecimal rounded = bd.setScale(2, RoundingMode.HALF_UP);
        System.out.println(rounded); // 输出：123.5

//        BigDecimal rounded2 = bd.round(2, RoundingMode.DOWN);
//        System.out.println(rounded2); // 输出：123.45

    }
}
