package com.disanbo.component.blockchain.repository;

import com.disanbo.component.blockchain.constant.AlgorithmType;
import com.disanbo.component.blockchain.constant.MethodType;
import com.disanbo.component.blockchain.entity.*;
import com.disanbo.component.blockchain.protobuf.TransactionBuf;
import com.disanbo.component.blockchain.util.BitcoinjUtil;
import com.disanbo.component.blockchain.util.EdDSAUtil;
import com.disanbo.component.blockchain.util.TransactionUtil;
import com.disanbo.component.common.util.HexUtil;
import com.disanbo.component.common.util.HttpUtil;
import com.disanbo.component.common.util.JSON;
import com.disanbo.component.common.util.RandomUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.protobuf.ByteString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author chauncy
 * @date 2018/6/15
 */
@Slf4j
public class BlockChainRepository {

    private static final String TX_NOT_EXIST = "tx not exist";
    private Integer type;
    private BlockProperties blockProperties;

    public BlockChainRepository(Integer type, BlockProperties blockProperties) {
        super();
        this.type = type;
        this.blockProperties = blockProperties;
    }

    /**
     * 上链
     */
    public ChainResponse sendTransaction(TransactionInfo transactionInfo) throws Exception {
        // 构建并设置Transaction
        TransactionBuf.Transaction.Builder transaction = TransactionBuf.Transaction.newBuilder();
        // 合约名
        transaction.setExecer(ByteString.copyFrom(this.blockProperties.getExecer().getBytes()));
        // 合约内容
        transaction.setPayload(transactionInfo.getActionByteStr());
        // 手续费，随便填
        transaction.setFee(1000000);
        // 随机ID，可以防止payload相同的时候，交易重复，不用记录
        transaction.setNonce(RandomUtil.getSecureRandom());
        // 对方地址，如果没有对方地址，可以为空
        transaction.setTo(TransactionUtil.getToAdd(this.blockProperties.getExecer()));
        // 将请求参数签名
        ByteString sign;
        switch (this.type) {
            case AlgorithmType.SECP256K1:
                sign = BitcoinjUtil.sign(transaction.build().toByteArray(), HexUtil.hexToBytes(transactionInfo.getPrivateKey()));
                break;
            case AlgorithmType.ED25519:
                sign = EdDSAUtil.sign(transaction.build().toByteArray(), HexUtil.hexToBytes(transactionInfo.getPrivateKey()));
                break;
            case AlgorithmType.SM2:
                return null;
            case AlgorithmType.ONETIMEED25519:
                return null;
            case AlgorithmType.RINGBASEONED25519:
                return null;
            default:
                throw new RuntimeException("not a valid algorithm type: " + this.type);
        }
        // 构建签名，并设置请求的签名参数
        TransactionBuf.Signature.Builder signature = TransactionBuf.Signature.newBuilder();
        // ty = 1 -> secp256k1
        // ty = 2 -> ed25519
        // ty = 3 -> sm2
        // ty = 4 -> OnetimeED25519
        // ty = 5 -> RingBaseonED25519
        signature.setTy(this.type);
        // 公钥
        signature.setPubkey(ByteString.copyFrom(HexUtil.hexToBytes(transactionInfo.getPublicKey())));
        signature.setSignature(sign);

        transaction.setSignature(signature);

        // 构建交易信息
        String transactionData = HexUtil.bytesToHex(transaction.build().toByteArray());

        ParamsBean paramsBean = new ParamsBean();
        paramsBean.setData(transactionData);
        JsonRpcRequest jsonRpcRequest = new JsonRpcRequest<>(paramsBean, MethodType.SEND_TRANSACTION);

        String requestJson = JSON.toJSONString(jsonRpcRequest);

        // 发送区块链
        String httpPostResult = HttpUtil.httpPost(this.blockProperties.getUrls(), requestJson);
        log.info("发送交易区块链返回结果：\n\r{}", httpPostResult);
        return checkResult(httpPostResult);
    }

    /**
     * 校验结果
     */
    private ChainResponse checkResult(String jsonResult) {
        TransactionResponse<String> transactionResponse;
        try {
            transactionResponse = JSON.parseObject(jsonResult, new TypeReference<TransactionResponse<String>>() {
            });
        } catch (Exception e) {
            log.info("校验结果出错：{}", e.getMessage());
            transactionResponse = null;
        }
        ChainResponse chainResponse = new ChainResponse();
        if (transactionResponse == null) {
            chainResponse.setSuccess(false);
            chainResponse.setError(null);
            return chainResponse;
        }
        if (transactionResponse.getError() != null || transactionResponse.getResult() == null) {
            chainResponse.setSuccess(false);
            chainResponse.setError(transactionResponse.getError());
            return chainResponse;
        }
        TransactionResponse<TransactionResultBean> queryTransaction;
        try {
            queryTransaction = queryTransaction(transactionResponse.getResult());
        } catch (Exception e) {
            queryTransaction = null;
        }
        if (queryTransaction == null) {
            chainResponse.setSuccess(false);
            chainResponse.setError(null);
            return chainResponse;
        }
        if (StringUtils.isNotBlank(queryTransaction.getError())) {
            chainResponse.setSuccess(false);
            chainResponse.setError(queryTransaction.getError());
            return chainResponse;
        }
        chainResponse.setSuccess(true);
        chainResponse.setHash(transactionResponse.getResult());
        chainResponse.setHeight(queryTransaction.getResult().getHeight());
        return chainResponse;
    }

    /**
     * 根据哈希查询交易信息
     */
    private TransactionResponse<TransactionResultBean> queryTransaction(String hash) throws Exception {
        // 发送交易后，等待一秒后再进行查询
        // 之后每0.5秒再进行一次查询，总共60次
        // 60次，也就是30秒后还没有结果，当失败处理
        Thread.sleep(500);
        for (int i = 0; i < 60; i++) {
            Thread.sleep(500);
            ParamsBean paramsBean = new ParamsBean();
            paramsBean.setHash(hash);
            JsonRpcRequest jsonRpcRequest = new JsonRpcRequest<>(paramsBean, MethodType.QUERY_TRANSACTION);
            String requestBeanStr = JSON.toJSONString(jsonRpcRequest);
            // 发送区块链
            String httpPostResult = HttpUtil.httpPost(this.blockProperties.getUrls(), requestBeanStr);

            TransactionResponse<TransactionResultBean> queryTransaction = JSON.parseObject(httpPostResult, new TypeReference<TransactionResponse<TransactionResultBean>>() {
            });
            if (queryTransaction == null) {
                continue;
            }
            if (queryTransaction.getError() != null || queryTransaction.getResult() == null) {
                if (TX_NOT_EXIST.equals(queryTransaction.getError())) {
                    continue;
                }
            }
            log.info("根据哈希查询区交易信息块链返回结果：\n\r{}", httpPostResult);
            return checkQueryTransaction(queryTransaction);
        }
        log.info("根据哈希查询交易信息失败：null");
        return null;
    }

    private TransactionResponse<TransactionResultBean> checkQueryTransaction(TransactionResponse<TransactionResultBean> queryTransaction) {
        if (queryTransaction == null) {
            log.info("根据哈希查询交易信息失败：null");
            return null;
        }
        if (queryTransaction.getError() != null || queryTransaction.getResult() == null) {
            log.info("根据哈希查询交易信息失败：{}", queryTransaction.getError());
            return queryTransaction;
        }

        if (queryTransaction.getResult().getReceipt().getTy() != 2) {
            log.info("根据哈希查询交易信息失败：{}", queryTransaction.getResult().getReceipt().getLogs().get(0).getLog());
            queryTransaction.setError(queryTransaction.getResult().getReceipt().getLogs().get(0).getLog());
            return queryTransaction;
        }
        log.info("根据哈希查询交易信息成功");
        return queryTransaction;
    }
}
