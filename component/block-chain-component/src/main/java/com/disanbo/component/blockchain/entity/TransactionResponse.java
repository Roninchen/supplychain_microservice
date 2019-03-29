package com.disanbo.component.blockchain.entity;

import lombok.Data;

/**
 * 区块链响应
 *
 * @author chauncy
 * @date 2018/6/15
 */
@Data
public class TransactionResponse<T> {
    private Integer id;
    private T result;
    private String error;
}
