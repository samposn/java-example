package com.kapokframework.rsa;

import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import java.security.*;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * RsaUtils
 *
 * @author <a href="mailto:samposn@163.com">Will WM. Zhang</a>
 * @since 1.0
 */
public class RsaUtils {

    private static final String KEY_PAIR_GENERATOR_ALGORITHM = "RSA";

    private static final String KEY_FACTORY_ALGORITHM = "RSA";

    //算法名称
    private static final String TRANSFORMATION = "RSA/ECB/OAEPWithSHA-1AndMGF1Padding";

    //标准签名算法名称
    private static final String RSA_SIGNATURE_ALGORITHM = "SHA1withRSA";
    private static final String RSA2_SIGNATURE_ALGORITHM = "SHA256withRSA";

    /**
     * <p>RSA密钥长度通常以比特（bits）为单位，可以是任何正整数，一 般情况下密钥长度会选择为64的倍数，以便于加密和解密操作的处理。密钥长度越长，加密强度越高，但加密和解密所需的时间也越长。以下是一些常见的RSA密钥长度限制：
     *
     * <ul>
     * <li>在TLS/SSL协议中，RSA密钥长度通常为1024位或2048位。</li>
     * <li>在SSH协议中，RSA密钥长度通常为2048位或4096位。</li>
     * <li>在数字证书中，RSA密钥长度通常为2048位或3072位。</li>
     * <li>在加密通信中，RSA密钥长度通常为2048位或4096位。</li>
     * </ul>
     *
     * <p>需要注意的是，随着计算机计算能力的提高，以及密码分析技术的不断发展，较短的RSA密钥长度可能会变得不够安全。因此，建议在选择RSA密钥长度时，考虑到安全性和性能的平衡，选择适当的密钥长度。
     *
     * <p>RSA算法的密钥长度必须大于等于明文长度，否则加密后的密文会比明文还要短，从而容易被攻击者猜测出来。通常情况下，RSA算法的密钥长度应该比明文长度长至少11位，以确保加密的安全性。例如，如果要加密的明文长度为1024位，那么RSA算法的密钥长度至少应该为1135位。
     */
    private static final int KEY_SIZE = 1035;


    /**
     * 成密钥对
     * @return 返回包含公私钥的 map 对象
     */
    public static Map<String, String> generateKey() {
        return generateKey(KEY_SIZE);
    }


    /**
     * 生成密钥对
     * @param keySize 秘钥长度
     * @return 返回包含公私钥的 Map 对象
     */
    public static Map<String, String> generateKey(int keySize) {
        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(KEY_PAIR_GENERATOR_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("RSA 初始化密钥对出现错误，算法异常");
        }
        //初始化密钥生成器
        keyPairGenerator.initialize(keySize);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        //获取公钥并转成base64编码
        byte[] pub_key = keyPair.getPublic().getEncoded();
        String publicKeyStr = Base64.getEncoder().encodeToString(pub_key);
        //获取私钥并转成base64编码
        byte[] pri_key = keyPair.getPrivate().getEncoded();
        String privateKeyStr = Base64.getEncoder().encodeToString(pri_key);
        //创建一个Map返回结果
        Map<String, String> keyPairMap = new HashMap<>();
        keyPairMap.put("publicKeyStr", publicKeyStr);
        keyPairMap.put("privateKeyStr", privateKeyStr);
        return keyPairMap;
    }


    /**
     * 公钥加密(用于数据加密)
     *
     * @param data         加密前的字符串
     * @param publicKeyStr base64编码后的公钥
     * @return base64编码后的字符串
     * @throws Exception exception
     */
    public static String encryptByPublicKey(String data, String publicKeyStr) throws Exception {
        //Java原生base64解码
        byte[] pubKey = Base64.getDecoder().decode(publicKeyStr);
        //创建X509编码密钥规范
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(pubKey);
        //返回转换指定算法的KeyFactory对象
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_FACTORY_ALGORITHM);
        //根据X509编码密钥规范产生公钥对象
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        //根据转换的名称获取密码对象Cipher（转换的名称：算法/工作模式/填充模式）
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        //用公钥初始化此Cipher对象（加密模式）
        cipher.init(Cipher.ENCRYPT_MODE, publicKey, OAEPParameterSpec.DEFAULT);

        //对数据加密
        byte[] encrypt = cipher.doFinal(data.getBytes());
        //返回base64编码后的字符串
        return Base64.getEncoder().encodeToString(encrypt);
    }


    /**
     * 私钥解密(用于数据解密)
     *
     * @param data          解密前的字符串
     * @param privateKeyStr 私钥
     * @return 解密后的字符串
     * @throws Exception exception
     */
    public static String decryptByPrivateKey(String data, String privateKeyStr) throws Exception {
        //Java原生base64解码
        byte[] priKey = Base64.getDecoder().decode(privateKeyStr);
        //创建PKCS8编码密钥规范
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(priKey);
        //返回转换指定算法的KeyFactory对象
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_FACTORY_ALGORITHM);
        //根据PKCS8编码密钥规范产生私钥对象
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //根据转换的名称获取密码对象Cipher（转换的名称：算法/工作模式/填充模式）
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        //用私钥初始化此Cipher对象（解密模式）
        cipher.init(Cipher.DECRYPT_MODE, privateKey, OAEPParameterSpec.DEFAULT);
        //对数据解密
        byte[] decrypt = cipher.doFinal(Base64.getDecoder().decode(data));
        //返回字符串
        return new String(decrypt);
    }


    /**
     * RSA签名
     *
     * @param data     待签名数据
     * @param priKey   私钥
     * @param signType RSA或RSA2
     * @return 签名
     * @throws Exception exception
     */
    public static String sign(byte[] data, byte[] priKey, String signType) throws Exception {
        //创建PKCS8编码密钥规范
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(priKey);
        //返回转换指定算法的KeyFactory对象
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_FACTORY_ALGORITHM);
        //根据PKCS8编码密钥规范产生私钥对象
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        //标准签名算法名称(RSA还是RSA2)
        String algorithm = KEY_FACTORY_ALGORITHM.equals(signType) ? RSA_SIGNATURE_ALGORITHM : RSA2_SIGNATURE_ALGORITHM;
        //用指定算法产生签名对象Signature
        Signature signature = Signature.getInstance(algorithm);
        //用私钥初始化签名对象Signature
        signature.initSign(privateKey);
        //将待签名的数据传送给签名对象(须在初始化之后)
        signature.update(data);
        //返回签名结果字节数组
        byte[] sign = signature.sign();
        //返回Base64编码后的字符串
        return Base64.getEncoder().encodeToString(sign);
    }

    /**
     * RSA校验数字签名
     *
     * @param data     待校验数据
     * @param sign     数字签名
     * @param pubKey   公钥
     * @param signType RSA或RSA2
     * @return boolean 校验成功返回true，失败返回false
     */
    public static boolean verify(byte[] data, byte[] sign, byte[] pubKey, String signType) throws Exception {
        //返回转换指定算法的KeyFactory对象
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_FACTORY_ALGORITHM);
        //创建X509编码密钥规范
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pubKey);
        //根据X509编码密钥规范产生公钥对象
        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
        //标准签名算法名称(RSA还是RSA2)
        String algorithm = KEY_FACTORY_ALGORITHM.equals(signType) ? RSA_SIGNATURE_ALGORITHM : RSA2_SIGNATURE_ALGORITHM;
        //用指定算法产生签名对象Signature
        Signature signature = Signature.getInstance(algorithm);
        //用公钥初始化签名对象,用于验证签名
        signature.initVerify(publicKey);
        //更新签名内容
        signature.update(data);
        //得到验证结果
        return signature.verify(sign);
    }

}
