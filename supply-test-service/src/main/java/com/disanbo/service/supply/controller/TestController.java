package com.disanbo.service.supply.controller;

import com.disanbo.component.common.annotation.MustBeBound;
import com.disanbo.component.common.constant.CommonConst;
import com.disanbo.component.common.entity.ResponseVO;
import com.disanbo.component.common.entity.UserVO;
import com.disanbo.component.common.enums.CommonResponseEnum;
import com.disanbo.component.file.entity.FileVO;
import com.disanbo.component.file.feign.FileUploadFeign;
import com.disanbo.service.supply.entity.properties.TestProperties;
import com.disanbo.service.supply.enums.SupplyResponseEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author wangtao
 * @date 2018/11/6 17:06
 */
@Api(tags = "测试接口", description = "提供多种测试方式")
@Slf4j
@RestController
@RequestMapping(value = "/api/test", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TestController {

    private final TestProperties testProperties;
    private final FileUploadFeign fileUploadFeign;

    public TestController(TestProperties testProperties, FileUploadFeign fileUploadFeign) {
        this.testProperties = testProperties;
        this.fileUploadFeign = fileUploadFeign;
    }


    @ApiOperation(value = "获取用户信息")
    @GetMapping("/user")
    public ResponseVO<UserVO> user(@ApiIgnore @RequestAttribute(CommonConst.USER) UserVO userVO) {
        log.info("获取用户信息：{}", userVO);
        return new ResponseVO<>(CommonResponseEnum.query_success, userVO);
    }

    @ApiOperation(value = "获取配置信息")
    @GetMapping("/properties")
    public ResponseVO<TestProperties> properties() {
        log.info("获取配置信息：{}", testProperties);
        return new ResponseVO<>(CommonResponseEnum.query_success, testProperties);
    }

    @ApiOperation(value = "必须是绑定的token")
    @GetMapping("/bound")
    @MustBeBound
    public ResponseVO bound(@ApiIgnore @RequestAttribute(CommonConst.USER) UserVO userVO) {
        log.info("打印出此条消息说明是绑定的");
        return new ResponseVO<>(CommonResponseEnum.query_success, userVO);
    }

    @ApiOperation(value = "其他语言")
    @ApiImplicitParam(name = "lang", value = "语言：默认中文，英文例如输入：en_US", dataType = "String")
    @GetMapping("/lang")
    public ResponseVO lang(@RequestParam(value = "lang", required = false) String lang) {
        log.info("当前语言为：{}", lang);
        return new ResponseVO<>(SupplyResponseEnum.test_test);
    }

    @ApiOperation(value = "上传文件")
    @PostMapping("/file")
    public ResponseVO<FileVO> file(@RequestPart(required = false) MultipartFile file) {
        log.info("文件上传，单个文件上传");
        if (file == null) {
            log.info("文件为空");
            return new ResponseVO<>(SupplyResponseEnum.test_test);
        }
        log.info("上传文件：{}", file.getOriginalFilename());
        ResponseVO<FileVO> responseVO = fileUploadFeign.upload(file);
        if (responseVO != null && CommonResponseEnum.success.getCode().equals(responseVO.getCode())) {
            log.info("上传成功：{}", responseVO.getData());
            return new ResponseVO<>(CommonResponseEnum.success, responseVO.getData());
        }
        log.info("上传失败：{}", responseVO);
        return new ResponseVO<>(SupplyResponseEnum.test_test);
    }

}
