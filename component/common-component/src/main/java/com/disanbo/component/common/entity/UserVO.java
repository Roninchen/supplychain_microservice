package com.disanbo.component.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户基本信息类
 *
 * @author chauncy
 * @date 2018/9/14 21:02
 */
@Data
@ApiModel("用户信息参数")
public class UserVO {
    @ApiModelProperty(value = "用户uid")
    private String uid;
    @ApiModelProperty(value = "用户名称")
    private String name;
    @ApiModelProperty(value = "性别（0：未知，1：男，2：女）")
    private Integer sex;
    @ApiModelProperty(value = "账号")
    private String account;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "密码，无则为空，有则为******")
    private String password;
    @ApiModelProperty(value = "平台标识")
    private String platform;
    @ApiModelProperty(value = "用户在所在平台的uid")
    private String pid;
}
