package com.disanbo.component.common.repository;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * 国际化仓库
 *
 * @author qbanxiaoli
 * @date 2018/5/31 10:12
 */
@Component
public class I18nRepository implements MessageSourceAware {

    private static MessageSource messageSource;

    @Override
    public void setMessageSource(@NonNull MessageSource messageSource) {
        I18nRepository.messageSource = messageSource;
    }

    /**
     * 从配置文件中找到message对应语言的消息
     */
    public static String getMessage(String message) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(message, null, message, locale);
    }

}
