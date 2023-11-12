package com.kapokframework;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * TODO
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a>
 * @since 1.0
 */
@Slf4j
public class Test1 {

    public static void main(String[] args) {
        ExpressionParser parser = new SpelExpressionParser();
        Boolean value = parser.parseExpression("!{'20450200021','20450200022'}.contains('20450200021')").getValue(Boolean.class);

        log.info("{}", value);

        log.info("{}", StringUtils.substring("!contains", 1));

        LocalDateTime beginTime = LocalDateTime.parse("2023-08-06 09:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endTime = LocalDateTime.parse("2023-08-07 01:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        LocalDateTime beginTime = LocalDateTime.parse("2023-08-11 21:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        LocalDateTime endTime = LocalDateTime.parse("2023-08-14 09:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime nowTime= LocalDateTime.now();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMM d, hh:mm a", Locale.ENGLISH);
        String dateBegin = dateTimeFormatter.format(beginTime);
        String dateEnd = dateTimeFormatter.format(endTime);

        if (nowTime.isAfter(beginTime) && nowTime.isBefore(endTime)) {
            log.info("{} ~ {} (China time)", dateBegin, dateEnd);
        } else {
            log.info("Nothing!");
        }





    }

}
