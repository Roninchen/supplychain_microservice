package com.disanbo.component.blockchain.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 区块链请求类
 *
 * @author wangtao
 * @date 2018/6/15
 */
@Data
public class JsonRpcRequest<T> {
    private String jsonrpc;
    private String method;
    private Object id;
    private List<T> params;

    public JsonRpcRequest(T params, String methodType) {
        this.jsonrpc = "2.0";
        this.method = methodType;
        this.id = null;
        this.params = new ArrayList<>();
        this.params.add(params);
    }

}
