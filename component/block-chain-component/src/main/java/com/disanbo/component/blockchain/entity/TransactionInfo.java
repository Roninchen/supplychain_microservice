package com.disanbo.component.blockchain.entity;

import com.google.protobuf.ByteString;
import lombok.Data;

/**
 * @author wangtao
 * @date 2018/10/25 10:45
 */
@Data
public class TransactionInfo {
    private ByteString actionByteStr;
    private String publicKey;
    private String privateKey;
}
