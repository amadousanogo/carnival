/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.flow.parser;

import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

/**
 * @author 应卓
 * @since 1.3.6
 */
public class HttpParameterStepTokenParser implements StepTokenParser {

    private final String parameterName;

    public HttpParameterStepTokenParser() {
        this("step_token");
    }

    public HttpParameterStepTokenParser(String parameterName) {
        this.parameterName = parameterName;
    }

    @Override
    public Optional<String> parse(NativeWebRequest request) {
        return Optional.ofNullable(request.getParameter(parameterName));
    }

}
