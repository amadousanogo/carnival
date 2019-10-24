/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.autoconfig;

import com.github.yingzhuo.carnival.common.autoconfig.support.AnnotationAttributesHolder;
import com.github.yingzhuo.carnival.restful.security.AuthenticationStrategy;
import com.github.yingzhuo.carnival.restful.security.EnableRestfulSecurity;
import com.github.yingzhuo.carnival.restful.security.blacklist.TokenBlacklistManager;
import com.github.yingzhuo.carnival.restful.security.cache.CacheManager;
import com.github.yingzhuo.carnival.restful.security.core.RestfulSecurityInterceptor;
import com.github.yingzhuo.carnival.restful.security.hook.AfterHook;
import com.github.yingzhuo.carnival.restful.security.hook.BeforeHook;
import com.github.yingzhuo.carnival.restful.security.mvc.UserDetailsPropertyHandlerMethodArgumentResolver;
import com.github.yingzhuo.carnival.restful.security.parser.TokenParser;
import com.github.yingzhuo.carnival.restful.security.realm.UserDetailsRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.OrderComparator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Optional;

/**
 * @author 应卓
 */
@ConditionalOnWebApplication
@AutoConfigureAfter(RestfulSecurityAutoConfig.class)
public class RestfulSecurityInterceptorAutoConfig implements WebMvcConfigurer {

    @Autowired
    private List<TokenParser> tokenParsers;

    @Autowired
    private List<UserDetailsRealm> userDetailsRealms;

    @Autowired
    private List<BeforeHook> beforeHooks;

    @Autowired
    private List<AfterHook> afterHooks;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private TokenBlacklistManager tokenBlackListManager;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        final TokenParser tp;
        final UserDetailsRealm udr;
        final BeforeHook bh;
        final AfterHook ah;

        if (tokenParsers.size() == 1) {
            tp = tokenParsers.get(0);
        } else {
            OrderComparator.sort(tokenParsers);
            tp = tokenParsers.stream().reduce(request -> Optional.empty(), TokenParser::or);
        }

        if (userDetailsRealms.size() == 1) {
            udr = userDetailsRealms.get(0);
        } else {
            OrderComparator.sort(userDetailsRealms);
            udr = userDetailsRealms.stream().reduce(token -> Optional.empty(), UserDetailsRealm::or);
        }

        if (beforeHooks.size() == 1) {
            bh = beforeHooks.get(0);
        } else {
            OrderComparator.sort(beforeHooks);
            bh = beforeHooks.stream().reduce(request -> {
            }, BeforeHook::link);
        }

        if (afterHooks.size() == 1) {
            ah = afterHooks.get(0);
        } else {
            OrderComparator.sort(afterHooks);
            ah = afterHooks.stream().reduce((request, token, userDetails) -> {
            }, AfterHook::link);
        }

        Integer interceptorOrder = AnnotationAttributesHolder.getValue(EnableRestfulSecurity.class, "interceptorOrder");
        if (interceptorOrder == null) {
            interceptorOrder = 0;
        }

        final AuthenticationStrategy authenticationStrategy = AnnotationAttributesHolder.getValue(EnableRestfulSecurity.class, "authenticationStrategy");

        final RestfulSecurityInterceptor interceptor = new RestfulSecurityInterceptor();
        interceptor.setTokenBlacklistManager(tokenBlackListManager);
        interceptor.setTokenParser(tp);
        interceptor.setUserDetailsRealm(udr);
        interceptor.setCacheManager(cacheManager);
        interceptor.setAuthenticationStrategy(authenticationStrategy);
        interceptor.setBeforeHook(bh);
        interceptor.setAfterHook(ah);
        registry.addInterceptor(interceptor).addPathPatterns("/", "/**").order(interceptorOrder);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new UserDetailsPropertyHandlerMethodArgumentResolver());
    }

}
