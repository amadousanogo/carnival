/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.secret;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.lang.annotation.*;
import java.util.Collections;
import java.util.Set;

/**
 * @author 应卓
 */
public interface MD5 {

    @Documented
    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    public @interface Encrypt {

        public static final class FormatterFactory implements AnnotationFormatterFactory<Encrypt> {
            @Override
            public Set<Class<?>> getFieldTypes() {
                return Collections.singleton(String.class);
            }

            @Override
            public Printer<?> getPrinter(Encrypt annotation, Class<?> fieldType) {
                return (Printer<String>) (o, locale) -> o;
            }

            @Override
            public Parser<?> getParser(Encrypt annotation, Class<?> fieldType) {
                return (Parser<String>) (s, locale) -> DigestUtils.md5Hex(s);
            }
        }
    }

}
