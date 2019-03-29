package com.disanbo.component.blockchain.entity;

import lombok.Data;

import java.util.List;

/**
 * 区块链交易详情类
 *
 * @author chauncy
 * @date 2018/6/15
 */
@Data
public class TransactionResultBean {
    private String[] proofs;
    private Long height;
    private Integer index;
    private Long blockTime;
    private Long amount;
    private String fromAddr;
    private String actionName;
    private Tx tx;
    private Receipt receipt;

    @Data
    public class Tx {
        private String execer;
        private Payload payload;
        private String rawPayload;
        private Signature signature;
        private Long fee;
        private Integer expire;
        private Long nonce;
        private String from;
        private String to;

        @Data
        public class Payload {
            private String rawlog;
        }

        @Data
        public class Signature {
            private Integer ty;
            private String pubkey;
            private String signature;
        }
    }

    @Data
    public class Receipt {
        private Integer ty;
        private String tyName;
        private List<Logs> logs;

        @Data
        public class Logs {
            private Integer ty;
            private String tyName;
            private String log;
            private String rawLog;
        }
    }


}
