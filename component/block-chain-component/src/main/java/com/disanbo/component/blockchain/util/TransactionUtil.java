package com.disanbo.component.blockchain.util;

import com.disanbo.component.common.util.Base58Util;
import com.disanbo.component.common.util.ByteUtil;
import com.disanbo.component.common.util.Ripemd160Util;
import com.disanbo.component.common.util.Sha256Util;
import org.apache.commons.lang3.StringUtils;

/**
 * @author wangtao
 * @date 2018/10/24 19:09
 */

public class TransactionUtil {

    private static final String SEED = "address seed bytes for public key";
    private static String TO = "";

    public static String getToAdd(final String execer) throws Exception {
        if (StringUtils.isBlank(TO)) {
            byte[] merge = (SEED + execer).getBytes();
            byte[] digest = Sha256Util.encryptByte(merge);
            for (int i = 0; i < digest.length; i++) {
                digest[i] = (byte) (digest[i] & 0xff);
            }
            digest = Sha256Util.encryptByte(Sha256Util.encryptByte(digest));
            byte[] hash = Ripemd160Util.getHash(digest);
            byte[] byte25 = new byte[25];
            System.arraycopy(hash, 0, byte25, 1, 20);
            byte[] subByte = ByteUtil.subByte(byte25, 0, 21);
            subByte = Sha256Util.encryptByte(Sha256Util.encryptByte(subByte));
            for (int i = 21, j = 0; i < 25; i++, j++) {
                byte25[i] = subByte[j];
            }
            TO = Base58Util.encode(byte25);
        }
        return TO;
    }
}
