package com.disanbo.component.common.util;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * 随机数相关方法
 *
 * @author chauncy
 * @date 2017年11月16日
 */
public final class RandomUtil {

    private static final Random RANDOM = new Random();

    /**
     * 获取指定长度的随机数
     */
    public static String getRandomNumber(final int length) {
        String string;
        for (string = String.valueOf(Math.abs(RANDOM.nextLong())); string.length() < length;
             string = string + String.valueOf(Math.abs(RANDOM.nextLong()))) {
        }
        return string.substring(0, length);
    }

    /**
     * 获取指定长度的字符串
     */
    public static String getRandomString(final int length) {
        String str = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(str.charAt(RANDOM.nextInt(str.length())));
        }
        return sb.toString();
    }

    /**
     * 获取指定长度的十六进制字符串
     */
    public static String getRandomHexString(final int length) {
        String str = "0123456789abcdef";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(str.charAt(RANDOM.nextInt(str.length())));
        }
        return sb.toString();
    }

    /**
     * 获取安全随机数
     */
    public static Long getSecureRandom() {
        return Math.abs(new SecureRandom().nextLong());
    }

    /**
     * 获取UUID，获取失败将获得相同位数的随机字符串
     */
    public static String getUUID() {
        try {
            return UUID.randomUUID().toString().replace("-", "");
        } catch (Exception e) {
            return getRandomString(32);
        }
    }


}
