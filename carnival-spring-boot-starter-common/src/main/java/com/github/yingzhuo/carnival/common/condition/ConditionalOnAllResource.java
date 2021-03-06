/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.condition;

import lombok.val;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.lang.annotation.*;

/**
 * @author 应卓
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Conditional(ConditionalOnAllResource.OnAllResource.class)
public @interface ConditionalOnAllResource {

    public String[] locations();

    static final class OnAllResource implements Condition {

        private ResourceLoader resourceLoader = new DefaultResourceLoader();

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            val locations = getLocations(metadata);

            for (String location : locations) {
                try {
                    val resource = resourceLoader.getResource(location);
                    if (!resource.exists()) {
                        return false;
                    }
                } catch (Exception e) {
                    return false;
                }
            }

            return true;
        }

        private String[] getLocations(AnnotatedTypeMetadata metadata) {
            try {
                val aas = AnnotationAttributes.fromMap(
                        metadata.getAnnotationAttributes(ConditionalOnAllResource.class.getName()));
                return aas.getStringArray("locations");
            } catch (Exception e) {
                return new String[0];
            }
        }
    }

}
