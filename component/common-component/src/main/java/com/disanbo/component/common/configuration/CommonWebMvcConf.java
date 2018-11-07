package com.disanbo.component.common.configuration;


import com.disanbo.component.common.configuration.interceptor.CommonBoundInterceptor;
import com.disanbo.component.common.configuration.interceptor.CommonErrorInterceptor;
import com.disanbo.component.common.configuration.interceptor.CommonOptionsInterceptor;
import com.disanbo.component.common.configuration.interceptor.CommonUserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 请求拦截器
 *
 * @author wangtao
 * @date 2018/5/29
 */
@Configuration
public class CommonWebMvcConf implements WebMvcConfigurer {

    private final CommonOptionsInterceptor commonOptionsInterceptor;
    private final CommonErrorInterceptor commonErrorInterceptor;
    private final CommonBoundInterceptor commonBoundInterceptor;
    private final CommonUserInterceptor commonUserInterceptor;

    public CommonWebMvcConf(CommonOptionsInterceptor commonOptionsInterceptor, CommonErrorInterceptor commonErrorInterceptor, CommonBoundInterceptor commonBoundInterceptor, CommonUserInterceptor commonUserInterceptor) {
        this.commonOptionsInterceptor = commonOptionsInterceptor;
        this.commonErrorInterceptor = commonErrorInterceptor;
        this.commonBoundInterceptor = commonBoundInterceptor;
        this.commonUserInterceptor = commonUserInterceptor;
    }

    /**
     * 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(commonOptionsInterceptor).addPathPatterns("/**");
        registry.addInterceptor(commonErrorInterceptor).addPathPatterns("/error");
        registry.addInterceptor(commonBoundInterceptor).addPathPatterns("/api/**");
        registry.addInterceptor(commonUserInterceptor).addPathPatterns("/api/**");
    }

}
