/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jsr349;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 应卓
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {

    public Complexity complexity() default Complexity.ANY;

    public String specialChars() default "\"',./<>?;:'{}[]+=-_!@#$%^&*()`~";

    public int minLength() default Integer.MIN_VALUE;

    public int maxLength() default Integer.MAX_VALUE;

    public String message() default "Invalid password.";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    // ----------------------------------------------------------------------------------------------------------------

    /**
     * 密码的复杂度
     */
    public enum Complexity {

        /**
         * 无要求
         */
        ANY,

        /**
         * 数字 (0-9)
         */
        NUMERIC,

        /**
         * 字母 (a-z, A-Z)
         */
        ALPHABETIC,

        /**
         * 字母 + 数字 (a-z, A-Z, 0-9)
         */
        ALPHABETIC_AND_NUMERIC,

        /**
         * 字母 + 数字 + 特殊字符
         */
        ALPHABETIC_AND_NUMERIC_AND_SPECIAL_CHARS,

        /**
         * 小写字母 + 大写字母 + 数字
         */
        LOWER_AND_UPPER_AND_NUMERIC,

        /**
         * 小写字母 + 大写字母 + 数字 + 特殊字符
         */
        LOWER_AND_UPPER_AND_NUMERIC_AND_SPECIAL_CHARS,

        /**
         * 字母 数字 特殊字符 中至少两种
         */
        AT_LEAST_TWO_KIND_OF_ALPHABETIC_AND_NUMERIC_AND_SPECIAL_CHARS
    }

}
