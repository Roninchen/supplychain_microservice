package com.disanbo.component.common.controller;


import com.disanbo.component.common.enums.CommonResponseEnum;
import com.disanbo.component.common.entity.ResponseVO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 错误处理
 *
 * @author chauncy
 * @date 2018/8/23 10:27
 */
@RestController
@RequestMapping(value = "/exception", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CommonExceptionController {

    @RequestMapping("/url/not-fount")
    public ResponseVO urlNotFount() {
        return new ResponseVO(CommonResponseEnum.exception_url_not_found);
    }

}
