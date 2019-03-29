package com.disanbo.component.blockchain.builder;

import com.disanbo.component.blockchain.constant.AlgorithmType;
import com.disanbo.component.blockchain.entity.BlockProperties;
import com.disanbo.component.blockchain.repository.BlockChainRepository;

/**
 * @author chauncy
 * @date 2018/10/25 10:56
 */
public class BlockChainBuilder {
    private static final int DEFAULTTYPE = 1;
    private Integer type;

    public static BlockChainBuilder create() {
        return new BlockChainBuilder();
    }

    public final BlockChainBuilder secp256k1() {
        this.type = AlgorithmType.SECP256K1;
        return this;
    }

    public final BlockChainBuilder ed25519() {
        this.type = AlgorithmType.ED25519;
        return this;
    }

    public final BlockChainBuilder sm2() {
        this.type = AlgorithmType.SM2;
        return this;
    }

    public final BlockChainBuilder onetimeED25519() {
        this.type = AlgorithmType.ONETIMEED25519;
        return this;
    }

    public final BlockChainBuilder ringBaseonED25519() {
        this.type = AlgorithmType.RINGBASEONED25519;
        return this;
    }

    public BlockChainRepository build() {
        if (this.type < 1 || this.type > 5) {
            this.type = DEFAULTTYPE;
        }
        BlockProperties blockProperties = new BlockProperties();
        return new BlockChainRepository(type, blockProperties);
    }
}
