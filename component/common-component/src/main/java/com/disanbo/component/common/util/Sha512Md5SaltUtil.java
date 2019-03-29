package com.disanbo.component.common.util;

/**
 * @author chauncy
 * @date 2018/8/25 18:02
 */

public final class Sha512Md5SaltUtil {

    private final static String SALT = "encryptbywangtao";

    public static String encrypt(final String str) throws Exception {
        return Sha512Util.encryptStr(Md5Util.encryptStr(str, SALT));
    }

}
