package com.kapokframework;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class Test2 {

    public static void main(String[] args) {
        String str = "{\"dimension\":{\"ACCOUNT_ITEM\":\"SALES_PLAN\",\"BUSINESS\":\"DOMESTIC\",\"PAGE\":\"SA_SA\"},\"customization\":{\"aaaaaa\":11111,\"bb\":[1,2,3]}}";

        JSONObject jsonObject = JSON.parseObject(str);

        log.info("jsonObject {}", jsonObject.getJSONObject("customization"));

        log.info("say something: {}", "🥙🥙🌮🍠🥗🧈🍗🥙🥨🥫🍱🦪🦪🍣🍣🍣");


        String str1 = "[{\"bu\":\"MACC\",\"year\":\"2022\",\"monthes\":\"01\",\"scenario\":\"MTD\",\"version\":\"V1_Original\",\"barStatus\":0,\"excelId\":\"8a92a3f865b1e3040165c630e1cc0030\"},{\"bu\":\"MACC\",\"year\":\"2022\",\"monthes\":\"02\",\"scenario\":\"MTD\",\"version\":\"V1_Original\",\"barStatus\":0,\"excelId\":\"8a92a3f865b1e3040165c630e1cc0030\"},{\"bu\":\"MACC\",\"year\":\"2022\",\"monthes\":\"03\",\"scenario\":\"MTD\",\"version\":\"V1_Original\",\"barStatus\":0,\"excelId\":\"8a92a3f865b1e3040165c630e1cc0030\"},{\"bu\":\"MACC\",\"year\":\"2022\",\"monthes\":\"07\",\"scenario\":\"MTD\",\"version\":\"V1_Original\",\"barStatus\":0,\"excelId\":\"8a92a3f865b1e3040165c630e1cc0030\"},{\"bu\":\"MACC\",\"year\":\"2022\",\"monthes\":\"10\",\"scenario\":\"MTD\",\"version\":\"V1_Original\",\"barStatus\":0,\"excelId\":\"8a92a3f865b1e3040165c630e1cc0030\"},{\"bu\":\"MACC\",\"year\":\"2022\",\"monthes\":\"11\",\"scenario\":\"MTD\",\"version\":\"V1_Original\",\"barStatus\":0,\"excelId\":\"8a92a3f865b1e3040165c630e1cc0030\"},{\"bu\":\"MACC\",\"year\":\"2022\",\"monthes\":\"12\",\"scenario\":\"MTD\",\"version\":\"V1_Original\",\"barStatus\":0,\"excelId\":\"8a92a3f865b1e3040165c630e1cc0030\"},{\"bu\":\"MACC\",\"year\":\"2022\",\"monthes\":\"LastMonth\",\"scenario\":\"MTD\",\"version\":\"V1_Original\",\"barStatus\":0,\"excelId\":\"8a92a3f865b1e3040165c630e1cc0030\"}]";
        JSONArray objects = JSON.parseArray(str1);
        log.info("jsonObject1 {}", objects);

        log.info("uuid : {}", UUID.randomUUID().toString());


    }

}
