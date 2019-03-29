package com.disanbo.component.sms.feignfallback;

import com.disanbo.component.common.entity.ResponseVO;
import com.disanbo.component.common.enums.CommonResponseEnum;
import com.disanbo.component.sms.entity.Message;
import com.disanbo.component.sms.feign.SmsFeign;
import org.springframework.stereotype.Component;

/**
 * @author chauncy
 * @date 2018/9/19 11:53
 */
@Component
public class SmsFallBack implements SmsFeign {
    /**
     * 发送短信
     */
    @Override
    public ResponseVO send(Message message) {
        return new ResponseVO(CommonResponseEnum.exception_feign);
    }
}
