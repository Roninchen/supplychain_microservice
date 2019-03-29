package com.disanbo.component.common.util;

import java.security.MessageDigest;

/**
 * sha1
 *
 * @author chauncy
 * @date 2018/9/14
 */
public final class Sha512Util {

    public static String encryptStr(final String source) throws Exception {
        return HexUtil.bytesToHex(encrypt(source.getBytes()));
    }

    public static String encryptStr(final byte[] source) throws Exception {
        return HexUtil.bytesToHex(encrypt(source));
    }

    public static byte[] encryptByte(final String source) throws Exception {
        return encrypt(source.getBytes());
    }

    public static byte[] encryptByte(final byte[] source) throws Exception {
        return encrypt(source);
    }

    private static byte[] encrypt(final byte[] source) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
        messageDigest.update(source);
        return messageDigest.digest();
    }

}
