package com.disanbo.component.common.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * @author wangtao
 * @date 2017/12/15
 */
public final class AesUtil {

    private static final String AES = "AES";

    /**
     * 加密
     */
    public static String encrypt(final String data, final String key) throws Exception {
        return doAES(data, key, Cipher.ENCRYPT_MODE);
    }

    /**
     * 解密
     */
    public static String decrypt(final String data, final String key) throws Exception {
        return doAES(data, key, Cipher.DECRYPT_MODE);
    }

    /**
     * 加解密
     */
    private static String doAES(final String data, final String key, final int mode) throws Exception {
        //判断是加密还是解密
        boolean encrypt = mode == Cipher.ENCRYPT_MODE;
        byte[] content;
        //true 加密内容 false 解密内容
        if (encrypt) {
            content = data.getBytes(StandardCharsets.UTF_8);
        } else {
            content = HexUtil.hexToBytes(data);
        }
        // 1.构造密钥生成器，指定为AES算法,不区分大小写
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
        // 2.根据ecnodeRules规则初始化密钥生成器
        // 生成一个128位的随机源,根据传入的字节数组
        keyGenerator.init(128, new SecureRandom(key.getBytes()));
        // 3.产生原始对称密钥
        SecretKey secretKey = keyGenerator.generateKey();
        // 4.获得原始对称密钥的字节数组
        byte[] encodedKey = secretKey.getEncoded();
        // 5.根据字节数组生成AES密钥
        SecretKeySpec keySpec = new SecretKeySpec(encodedKey, AES);
        // 6.根据指定算法AES自成密码器
        // 创建密码器
        Cipher cipher = Cipher.getInstance(AES);
        // 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
        // 初始化
        cipher.init(mode, keySpec);
        byte[] result = cipher.doFinal(content);
        if (encrypt) {
            // 将二进制转换成16进制
            return HexUtil.bytesToHex(result);
        } else {
            return new String(result, StandardCharsets.UTF_8);
        }
    }
}
