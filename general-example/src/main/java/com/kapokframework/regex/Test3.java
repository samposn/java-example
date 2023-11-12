package com.kapokframework.regex;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Test3
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a>
 * @since 1.0
 */
public class Test3 {

    public static void main(String[] args) throws Exception {
        ExpressRunner runner = new ExpressRunner();
        runner.setIgnoreConstChar(true);
        /**
         * 表达式计算的数据注入接口
         */
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        context.put("a","dqdwq'ss1");
        context.put("b","dqdwq'ss1");
        String express = " '${a}' == b ";//===> 定义规则，计算表达式等等
        express = replaceExpress(express, context, "");
        Object r = runner.execute(express, context, null, true, true);// 解析规则+执行规则
        System.out.println(r);

    }

    public static String replaceExpress(String str, Map<String,Object> params, String defaultVal){
        Pattern p = Pattern.compile("\\$\\{(.*)\\}");
        Matcher m = p.matcher(str);
        while(m.find()){
            String paramValue = m.group(1);
            Object o = params.get(paramValue);
            if(o != null) {
                String value = String.valueOf(o);
                if(StringUtils.isBlank(value) || "null".equalsIgnoreCase(value)){
                    value = defaultVal;
                }
                if(value.contains("'") ){
                    value = value.replaceAll("'","\\\\\\'");
                }else if(value.contains("\"")){
                    value = value.replaceAll("\"","\\\\\\\"");
                }
                str = str.replace(m.group(), value);
            }else{
                str = str.replace(m.group(), defaultVal);
            }

        }
        return str;
    }

}
