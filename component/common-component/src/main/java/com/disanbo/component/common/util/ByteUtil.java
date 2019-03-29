package com.disanbo.component.common.util;

/**
 * @author chauncy
 * @date 2018/10/24 19:15
 */
public class ByteUtil {

    /**
     * 合并byte[]
     */
    public static byte[] merge(byte[]... bytes) {
        int len = 0;
        for (byte[] by : bytes) {
            len += by.length;
        }
        byte[] target = new byte[len];
        int i = 0;
        for (byte[] by : bytes) {
            for (byte b : by) {
                target[i] = b;
                i++;
            }
        }
        return target;
    }

    /**
     * byte[]截取
     */
    public static byte[] subByte(byte[] bytes, int start, int end) {
        int len = end - start;
        byte[] target = new byte[len];
        if (len > bytes.length) {
            len = bytes.length;
        }
        System.arraycopy(bytes, 0, target, 0, len);
        return target;
    }
}
