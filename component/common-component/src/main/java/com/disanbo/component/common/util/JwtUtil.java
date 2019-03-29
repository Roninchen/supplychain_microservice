package com.disanbo.component.common.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Map;

/**
 * @author chauncy
 * @date 2018/10/25 15:25
 */

public class JwtUtil {
    private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    public static String create(Map<String, Object> claims) throws Exception {
        return Jwts.builder().setClaims(claims).setId(RandomUtil.getUUID()).signWith(signatureAlgorithm, generalKey()).compact();
    }

    public static Map<String, Object> parse(String jws) throws Exception {
        return Jwts.parser().setSigningKey(generalKey()).parseClaimsJws(jws).getBody();
    }

    private static SecretKey generalKey() {
        String stringKey = "7786df7fc3a34e26a61c034d5ec8245d";
        byte[] encodedKey = Base64.getDecoder().decode(stringKey);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }
}
