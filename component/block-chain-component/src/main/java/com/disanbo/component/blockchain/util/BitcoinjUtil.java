package com.disanbo.component.blockchain.util;

import com.disanbo.component.blockchain.entity.EncryptKey;
import com.disanbo.component.common.util.Sha256Util;
import com.google.protobuf.ByteString;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Sha256Hash;
import org.spongycastle.asn1.sec.SECNamedCurves;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.generators.ECKeyPairGenerator;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECKeyGenerationParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;

import java.security.SecureRandom;

/**
 * @author chauncy
 * @date 2018/10/24 20:42
 */

public class BitcoinjUtil {
    /**
     * 创建secp256k1公私钥
     */
    public static EncryptKey secp256k1Key() throws Exception {
        ECKeyPairGenerator gen = new ECKeyPairGenerator();
        X9ECParameters parameters = SECNamedCurves.getByName("secp256k1");
        ECDomainParameters ecParams = new ECDomainParameters(parameters.getCurve(), parameters.getG(),
                parameters.getN(), parameters.getH());
        ECKeyGenerationParameters keyGenParam = new ECKeyGenerationParameters(ecParams, new SecureRandom());
        gen.init(keyGenParam);
        AsymmetricCipherKeyPair generateKeyPair = gen.generateKeyPair();
        ECPrivateKeyParameters ecPvt = (ECPrivateKeyParameters) generateKeyPair.getPrivate();
        ECKey ecKey = ECKey.fromPrivate(ecPvt.getD());
        String publicKey = ecKey.getPublicKeyAsHex();
        String privateKey = ecKey.getPrivateKeyAsHex();
        return new EncryptKey(publicKey, privateKey);
    }

    /**
     * secp256k1签名
     */
    public static ByteString sign(byte[] source, byte[] bytePrivateKey) throws Exception {
        byte[] sha256 = Sha256Util.encryptByte(source);
        Sha256Hash sha256Hash = Sha256Hash.wrap(sha256);
        ECKey ecKey = ECKey.fromPrivate(bytePrivateKey);
        ECKey.ECDSASignature ecdsaSignature = ecKey.sign(sha256Hash);
        byte[] sign = ecdsaSignature.encodeToDER();
        return ByteString.copyFrom(sign, 0, 64);
    }

}
