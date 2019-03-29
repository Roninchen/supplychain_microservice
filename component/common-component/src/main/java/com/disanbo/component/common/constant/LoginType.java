package com.disanbo.component.common.constant;

/**
 * @author chauncy
 * @date 2018/10/25 14:52
 */

public class LoginType {
    /**
     * 直接在自己平台登录（包含通过其他平台的账号登录）
     */
    public final static String DIRECT = "0;";
    /**
     * 间接登录，且已绑定
     */
    public final static String BINDING = "1;";
    /**
     * 间接登录，且没绑定
     */
    public final static String NOT_BINDING = "2;";
}
