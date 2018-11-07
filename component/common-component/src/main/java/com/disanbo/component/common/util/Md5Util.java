package com.disanbo.component.common.util;

import java.security.MessageDigest;

/**
 * @author wangtao
 * @date 2018/3/26
 */

public final class Md5Util {

    /**
     * md5加密
     */
    public static String encryptStr(final String source) throws Exception {
        return HexUtil.bytesToHex(encrypt(source.getBytes(), null));
    }

    /**
     * md5加密
     */
    public static String encryptStr(final byte[] source) throws Exception {
        return HexUtil.bytesToHex(encrypt(source, null));
    }

    /**
     * md5加密
     */
    public static byte[] encryptByte(final String source) throws Exception {
        return encrypt(source.getBytes(), null);
    }

    /**
     * md5加密
     */
    public static byte[] encryptByte(final byte[] source) throws Exception {
        return encrypt(source, null);
    }

    /**
     * md5加盐加密
     */
    public static String encryptStr(final String source, final String salt) throws Exception {
        return HexUtil.bytesToHex(encrypt(source.getBytes(), salt.getBytes()));
    }

    /**
     * md5加盐加密
     */
    public static String encryptStr(final byte[] source, final byte[] salt) throws Exception {
        return HexUtil.bytesToHex(encrypt(source, salt));
    }

    /**
     * md5加盐加密
     */
    public static byte[] encryptByte(final String source, final String salt) throws Exception {
        return encrypt(source.getBytes(), salt.getBytes());
    }

    /**
     * md5加盐加密
     */
    public static byte[] encryptByte(final byte[] source, final byte[] salt) throws Exception {
        return encrypt(source, salt);
    }

    private static byte[] encrypt(final byte[] source, final byte[] salt) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        if (salt != null && salt.length > 0) {
            messageDigest.update(salt);
        }
        messageDigest.update(source);
        return messageDigest.digest();
    }
}
