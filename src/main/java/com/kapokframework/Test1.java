package com.kapokframework;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * TODO
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a>
 * @since 1.0
 */
public class Test1 {

    public static void main(String[] args) {
        ExpressionParser parser = new SpelExpressionParser();
        Boolean value = parser.parseExpression("3 = 2 and (6 > 14 or 1 < 0)").getValue(Boolean.class);

        System.out.println(value);

    }

}
