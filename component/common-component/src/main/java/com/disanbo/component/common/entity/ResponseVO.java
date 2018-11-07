package com.disanbo.component.common.entity;


import com.disanbo.component.common.enums.ResponseEnum;
import com.disanbo.component.common.repository.I18nRepository;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 响应类
 *
 * @author wangtao
 * @date 2018/3/21
 */
@Data
@ApiModel("响应参数")
public class ResponseVO<T> {
    @ApiModelProperty(value = "编码")
    private Integer code;
    @ApiModelProperty(value = "提示信息")
    private String message;
    @ApiModelProperty("参数")
    private T data;

    /**
     * 返回结果不包含数据，返回信息不包含变量
     */
    public ResponseVO(ResponseEnum responseEnum) {
        this.message = I18nRepository.getMessage(responseEnum.getMessage());
        this.code = responseEnum.getCode();
    }

    /**
     * 返回结果不包含数据，返回信息不包含变量
     */
    public ResponseVO(ResponseEnum responseEnum, String message) {
        this.message = I18nRepository.getMessage(message);
        this.code = responseEnum.getCode();
    }

    /**
     * 返回结果包含数据，返回信息不包含变量
     */
    public ResponseVO(ResponseEnum responseEnum, T data) {
        this.message = I18nRepository.getMessage(responseEnum.getMessage());
        this.code = responseEnum.getCode();
        this.data = data;
    }

}
