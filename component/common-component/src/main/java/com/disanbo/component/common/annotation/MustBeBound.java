package com.disanbo.component.common.annotation;

import java.lang.annotation.*;

/**
 * 使用此注解，必须绑定的用户才能访问
 *
 * @author wangtao
 * @date 2018/10/26 11:22
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MustBeBound {
}
