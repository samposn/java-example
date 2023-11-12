package com.kapokframework.rsa;

import lombok.extern.slf4j.Slf4j;

import java.util.Base64;
import java.util.Map;

/**
 * Main
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a>
 * @since 1.0
 */
@Slf4j
public class Main {

    public static final String PRIVATE_KEY_STR = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDErp/AL9GiFluUdOF9ZV3BNYTuyT6yaMlQPYoNYtWFOIKjbTPwNWot9DEJxq0DrQVAvoagpxkLXiteLetP259xrMtjT/Y+RsqTZik1p2/hHr2y2mb6jfazVMyKONuEP9w1IGr4pAA0ZPsd41slXQnrylmw9DiFxLyMbClp7Zu38AsK1KKdTiyq58fVBr9aRXreAnTwT/96Uo5Lwscw2VHu1yPkJwqQmtcdJLILst1ZzqYPNTiUFFjk8Nccc5WJ47FTCHh7D7U0hM7vdK2s5UETOquBgF5+Vjo+uFOVkWx63zrPIGJQdEMW6ckRqEKOwRMMlG289NJsyYDzihPSpD7tAgMBAAECgf8sM5sIuD7+P3+ImWacUF7St84GIGddKJXzg5zHQzvKX4J0BNKFOoz3pMdgjTTda2ttdtuXg4/H/j/A2aIZtspz48fz8De8T8YmBOC1g4VL8HqPYh+gc+DKbLvPZaJ4a51m/dEMmJu3bvpV8G1HPKGXwp3dFdKNCmWZsehpkKd/amNtV/T8+F5QoEl0DsBbNRYnC+ugr46PtyHKgcEOeCRXOdq4abmuYBFQNSgHPLSL/aKFdKrlV893DJgwODGK647pgtYrU2UAzqyLe3zTJFs9vC3LEtqkYOIrA+g8o5c2rRJ5FonyLVI4V5oynaggixufjRBd+ziip69nUV20pSECgYEA608+rPKvLQISZClKQXI7lS7kLgdb/lXudBdixL6WLa9daiW5RHeH5pi6DNkBCwrmJBadKLF/R6O+8El9qq8zMnHQPp3Klxns3JK1UH3ZeTsfuEXQJ9mX4W5cQW1C5CQa0jcplTl5FmW3054rqYMOrPKUNqOPr38seCeOJCviuAUCgYEA1fni3CmcObf8iZVwO/2UiLe4zF01VXmAdfxvFeQRUBshpTfAVUCsO8Qn41UiPz8OQy2WCPsoBj5Ci5auNhKLj1kM2Rlb0sbu0MIYghVnOrf1MBEI/qgolBVVgcI0mjMrjeZ0OhoGcdJu93+Raroe0CZ+fhBLYXHfPe5hhaWFJ8kCgYEAglS7BznHsush2p5QBZ3KyJ9XPPNQjbd0wpItX4GcVqN53xAT5Is8F4niCrmq7T3VKInp9B8Tu09Ds31RAFfXyInnaLcm/bgbTDRp/rIl4RLRR9RLLbdEe1UP/iERWqFwxZxOCNvzaGdggPJrhpETcNFPLFA9hluu+sIV5Yz1Hy0CgYEAszvNu31fTzm+X8C9coLGmH7MXOL2edJ8uFfq9PtFRUR0umAoy0CRBL8aamI1faTj3YGh24QF0rT0KNjetIx0Om7tRCzprXTVNihfPxeOkLDmwIyEbEuPMfts1HRIe6HBKeuQD9sc6trJ+Kbyt+OPS+vyqMYdXlJ0HPxA1CIBl4ECgYAQPIYmbEQtXF5fS4dCfpIuMgmeHrr9xE8kx/gmJGrgRFeH5XUA14GLoz1JxESw0l4kifzCgTeewKjuc658xP+QpBM7TqB7adKsXWoCVf9fee/JqoHz9SebWORKQoxofOBimv81ABhnVeZV4XirjfvK2DeCclOl55GBMyYe3jm4ZA==";

    public static void main(String[] args) throws Exception {

//        String encrypt = "Yiu552lj2RXomcMN/g2ugg03a2bKHDkqK2097UjsuEiN5zXWfhPnWRlFn3G/BusR8l74tGI6hA7Gkr9JE0BFFiWhwMNvubfzS9TRIJX0w8P6z0b6/69DhK+Y9bjBUUKAIS6RJHYDY0Va88DyRO/kn1XElverRYIVonHt76blMhtf3BveyHnUKKAWoZ9f5uYdF+EBOzWkUlikFTlGTuY+gKrHzH8OuQtEfbHaGeL9FZ/Q2UsRE1ZpOAriwwP/sPS1WcMSzApCJxRE/Sph9h+aOYeLw9Q2vaDl/VgGUwge+I874OzoUCHkQ84R6JVXgszhmnImTLfesZeO1oNBwtorVw==";

//        String decrypt = RsaUtils.decryptByPrivateKey(encrypt, PRIVATE_KEY_STR);
//        log.info("解密后数据：{}", decrypt);

        encryptAndDecrypt();
//        signAndVerify();
    }

    /**
     * RSA数据加密和解密
     *
     * @throws Exception exception
     */
    private static void encryptAndDecrypt() throws Exception {
        Map<String, String> keyMap = RsaUtils.generateKey();
        String publicKeyStr = keyMap.get("publicKeyStr");
        String privateKeyStr = keyMap.get("privateKeyStr");
        log.info("------------------------------ 生成的公钥和私钥 ------------------------------");
        log.info("获取到的公钥：{}", publicKeyStr);
        log.info("获取到的私钥：{}", privateKeyStr);
        // 待加密数据
        String data = "tranSeq=1920542585&amount=100&payType=wechat";
        // 公钥加密
        log.info("------------------------------ 加密和解密 ------------------------------");
        log.info("待加密的数据：{}", data);
        String encrypt = RsaUtils.encryptByPublicKey(data, publicKeyStr);
        log.info("加密后数据：" + encrypt);
        // 私钥解密
        String decrypt = RsaUtils.decryptByPrivateKey(encrypt, privateKeyStr);
        log.info("解密后数据：{}", decrypt);
    }

    /**
     * RSA数据签名和验签
     *
     * @throws Exception exception
     */
    private static void signAndVerify() throws Exception {
        Map<String, String> keyMap = RsaUtils.generateKey();
        String publicKeyStr = keyMap.get("publicKeyStr");
        String privateKeyStr = keyMap.get("privateKeyStr");
        log.info("-----------------生成的公钥和私钥------------------------------");
        log.info("获取到的公钥：{}", publicKeyStr);
        log.info("获取到的私钥：{}", privateKeyStr);
        // 数字签名
        String data = "tranSeq=1920542585&amount=100&payType=wechat";
        log.info("待签名的数据：{}", data);
        String sign = RsaUtils.sign(data.getBytes(), Base64.getDecoder().decode(privateKeyStr), "RSA");
        log.info("数字签名结果：{}", sign);
        boolean verify = RsaUtils.verify(data.getBytes(), Base64.getDecoder().decode(sign), Base64.getDecoder().decode(publicKeyStr), "RSA");
        log.info("数字签名验证结果：{}", verify);
    }

}
