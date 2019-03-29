package com.disanbo.component.blockchain.client;

import com.disanbo.component.blockchain.builder.BlockChainBuilder;
import com.disanbo.component.blockchain.repository.BlockChainRepository;

/**
 * @author chauncy
 * @date 2018/10/25 10:14
 */

public class BlockChainClients {
    private BlockChainClients() {
        super();
    }

    public static BlockChainRepository secp256k1() {
        return BlockChainBuilder.create().secp256k1().build();
    }

    public static BlockChainRepository ed25519() {
        return BlockChainBuilder.create().ed25519().build();
    }

    public static BlockChainRepository sm2() {
        return BlockChainBuilder.create().sm2().build();
    }

    public static BlockChainRepository onetimeED25519() {
        return BlockChainBuilder.create().onetimeED25519().build();
    }

    public static BlockChainRepository ringBaseonED25519() {
        return BlockChainBuilder.create().ringBaseonED25519().build();
    }

}
