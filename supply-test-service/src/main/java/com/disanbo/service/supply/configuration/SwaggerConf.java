package com.disanbo.service.supply.configuration;

import com.disanbo.component.common.constant.CommonConst;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangtao
 * @date 2018/8/23 20:27
 */
@Configuration
@EnableSwagger2
public class SwaggerConf {

    @Bean
    public Docket restApi() {
        ParameterBuilder contentType = new ParameterBuilder();
        ParameterBuilder token = new ParameterBuilder();
        List<Parameter> listParam = new ArrayList<>();
        contentType.name("Content-Type")
                .description("内容类型")
                .modelRef(new ModelRef("String"))
                .defaultValue(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .parameterType("header")
                .required(true);
        token.name(CommonConst.TOKEN)
                .description("令牌")
                .modelRef(new ModelRef("String"))
                .defaultValue("0")
                .parameterType("header")
                .required(true);
        listParam.add(contentType.build());
        listParam.add(token.build());
        List<ResponseMessage> responseMessageList = new ArrayList<>();
        responseMessageList.add(new ResponseMessageBuilder().code(200200).message("请求成功").build());
        responseMessageList.add(new ResponseMessageBuilder().code(200302).message("需要重新登录").build());
        responseMessageList.add(new ResponseMessageBuilder().code(200303).message("需要绑定账号").build());
        responseMessageList.add(new ResponseMessageBuilder().code(200500).message("请求失败").build());
        responseMessageList.add(new ResponseMessageBuilder().code(400400).message("参数校验错误").build());
        responseMessageList.add(new ResponseMessageBuilder().code(400404).message("请求路径不存在").build());
        responseMessageList.add(new ResponseMessageBuilder().code(400405).message("请求方法不支持").build());
        responseMessageList.add(new ResponseMessageBuilder().code(500500).message("系统内部错误").build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .globalResponseMessage(RequestMethod.POST, responseMessageList)
                .globalResponseMessage(RequestMethod.PUT, responseMessageList)
                .globalResponseMessage(RequestMethod.PATCH, responseMessageList)
                .globalResponseMessage(RequestMethod.DELETE, responseMessageList)
                .globalOperationParameters(listParam)
                .select()
                // 指定提供接口所在的基包
                .apis(RequestHandlerSelectors.basePackage("com.disanbo.service.supply.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("供应链金融示例-API文档")
                .contact(new Contact("wangtao", "null", "wangt@disanbo.com"))
                .description("供应链金融示例——Java版")
                .version("1.0.0")
                .build();
    }

}
