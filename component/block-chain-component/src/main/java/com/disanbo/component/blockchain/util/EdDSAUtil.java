package com.disanbo.component.blockchain.util;

import com.disanbo.component.blockchain.entity.EncryptKey;
import com.disanbo.component.common.util.HexUtil;
import com.disanbo.component.common.util.Sha256Util;
import com.google.protobuf.ByteString;
import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;
import net.i2p.crypto.eddsa.spec.EdDSAParameterSpec;
import net.i2p.crypto.eddsa.spec.EdDSAPrivateKeySpec;

import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.UUID;

/**
 * ed25519相关方法
 *
 * @author wangtao
 * @date 2018/10/24 16:39
 */
public class EdDSAUtil {

    /**
     * 创建ed25519公私钥
     */
    public static EncryptKey ed25519Key() throws Exception {
        String str = Sha256Util.encryptStr(UUID.randomUUID().toString() + System.currentTimeMillis());
        EdDSAParameterSpec spec = EdDSANamedCurveTable.getByName(EdDSANamedCurveTable.ED_25519);
        EdDSAPrivateKeySpec privateKeySpec = new EdDSAPrivateKeySpec(HexUtil.hexToBytes(str), spec);
        EdDSAPrivateKey edDSAPrivateKey = new EdDSAPrivateKey(privateKeySpec);
        String publicKey = HexUtil.bytesToHex(edDSAPrivateKey.getAbyte());
        String privateKey = HexUtil.bytesToHex(edDSAPrivateKey.geta());
        return new EncryptKey(publicKey, privateKey);
    }

    /**
     * ed25519签名
     */
    public static ByteString sign(byte[] source, byte[] bytePrivateKey) throws Exception {
        EdDSAParameterSpec spec = EdDSANamedCurveTable.getByName(EdDSANamedCurveTable.ED_25519);
        Signature sgr = new EdDSAEngine(MessageDigest.getInstance(spec.getHashAlgorithm()));

        EdDSAPrivateKeySpec privKey = new EdDSAPrivateKeySpec(bytePrivateKey, spec);
        PrivateKey sKey = new EdDSAPrivateKey(privKey);
        sgr.initSign(sKey);

        sgr.update(source);
        byte[] sign = sgr.sign();
        return ByteString.copyFrom(sign, 0, 64);
    }
}
