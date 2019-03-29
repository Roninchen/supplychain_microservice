package com.disanbo.component.common.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chauncy
 * @date 2018/10/25 12:24
 */
@Slf4j
public class TokenUtil {
    private final static String MERGE_CODE = "#";
    private final static int TOKEN_LEN = 3;

    /**
     * 合并令牌与uid
     */
    public static String merge(String token, String uid, String loginType) {
        return token + MERGE_CODE + uid + MERGE_CODE + loginType;
    }

    /**
     * 分解令牌与uid
     */
    public static String[] split(String token) {
        try {
            String[] split = token.split(MERGE_CODE);
            if (split.length != TOKEN_LEN) {
                log.info("令牌长度错误，请确认令牌格式或重新登录");
                return null;
            }
            return split;
        } catch (Exception e) {
            log.info("令牌解析错误，请确认令牌格式或重新登录");
            return null;
        }
    }
}
