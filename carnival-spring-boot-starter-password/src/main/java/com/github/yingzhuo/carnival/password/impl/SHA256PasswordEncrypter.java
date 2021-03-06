/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.password.impl;

import com.github.yingzhuo.carnival.password.PasswordEncrypter;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Objects;

/**
 * @author 应卓
 */
public class SHA256PasswordEncrypter implements PasswordEncrypter {

    @Override
    public String encrypt(String rawPassword, String leftSalt, String rightSalt) {
        Objects.requireNonNull(rawPassword);

        if (leftSalt == null) {
            leftSalt = EMPTY_SALT;
        }

        if (rightSalt == null) {
            rightSalt = EMPTY_SALT;
        }

        return DigestUtils.sha256Hex(leftSalt + rawPassword + rightSalt);
    }

}
