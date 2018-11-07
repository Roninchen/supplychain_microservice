package com.disanbo.component.blockchain.entity;

import lombok.Data;

/**
 * 区块链响应封装类
 *
 * @author wangtao
 * @date 2018/8/25 14:58
 */
@Data
public class ChainResponse {
    private boolean success;
    private String error;
    private String hash;
    private Long height;
}
