/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.spring;

import lombok.val;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Spring通用工具
 *
 * @author 应卓
 */
@SuppressWarnings("unchecked")
public final class SpringUtils {

    static ApplicationContext AC = null;
    static Environment ENV = null;
    static ApplicationArguments APP_ARGS = null;
    static List<String> CMD_ARGS = null;

    private SpringUtils() {
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    public static ApplicationContext getApplicationContext() {
        return AC;
    }

    public static Environment getEnvironment() {
        return ENV;
    }

    public static ResourceLoader getResourceLoader() {
        return AC;
    }

    public static ResourcePatternResolver getResourcePatternResolver() {
        return AC;
    }

    public static MessageSource getMessageSource() {
        return AC;
    }

    public static ApplicationEventPublisher getApplicationEventPublisher() {
        return AC;
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    public static String getSpringId() {
        return getEnvironment().getProperty(SpringIdFactory.SPRING_ID);
    }

    public static String getDisplayName() {
        return AC.getDisplayName();
    }

    public static Date getSpringStartupDate() {
        final Date date = new Date();
        date.setTime(AC.getStartupDate());
        return date;
    }

    public static String getSpringStartupDateAsString(String pattern) {
        return DateFormatUtils.format(getSpringStartupDate(), pattern);
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    public static <B> B getBean(Class<B> beanType) {
        return getApplicationContext().getBean(beanType);
    }

    public static <B> B getBean(Class<B> beanType, B defaultIfNotFound) {
        try {
            return getBean(beanType);
        } catch (NoSuchBeanDefinitionException e) {
            return defaultIfNotFound;
        }
    }

    public static <B> B getBean(String beanName) {
        return (B) getApplicationContext().getBean(beanName);
    }

    public static <B> B getBean(String beanName, B defaultIfNotFound) {
        try {
            return getBean(beanName);
        } catch (NoSuchBeanDefinitionException e) {
            return defaultIfNotFound;
        }
    }

    public static <B> B getBean(String beanName, Class<B> beanType) {
        return getApplicationContext().getBean(beanName, beanType);
    }

    public static <B> B getBean(String beanName, Class<B> beanType, B defaultIfNotFound) {
        try {
            return getBean(beanName, beanType);
        } catch (NoSuchBeanDefinitionException e) {
            return defaultIfNotFound;
        }
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    public static <B> boolean containsBean(Class<B> beanType) {
        try {
            AC.getBean(beanType);
            return true;
        } catch (NoUniqueBeanDefinitionException e) {
            return true;
        } catch (BeansException e) {
            return false;
        }
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    public static BeanDefinitionRegistry getBeanDefinitionRegistry() {
        return (BeanDefinitionRegistry) getApplicationContext().getAutowireCapableBeanFactory();
    }

    /* -------------------------------------------------------------------------------------------------------------- */

    public static ConversionService getConversionService() {
        return getBean(ConversionService.class);
    }

    public static Locale getLocale() {
        val request = ServletUtils.getRequest();
        return RequestContextUtils.getLocale(request);
    }

    public static TimeZone getTimeZone() {
        val request = ServletUtils.getRequest();
        return RequestContextUtils.getTimeZone(request);
    }

}
