package com.disanbo.component.blockchain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公私钥
 *
 * @author chauncy
 * @date 2018/10/24 18:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EncryptKey {
    private String publicKey;
    private String privateKey;
}
