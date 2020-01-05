/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.datasource.env;

import com.github.yingzhuo.carnival.common.env.AbstractEnvironmentPostProcessor;
import org.springframework.boot.env.HoconPropertySourceLoader;
import org.springframework.core.Ordered;

/**
 * @author 应卓
 * @since 1.4.1
 */
public class ExtendDataSourceHoconEnvironmentPostProcessor extends AbstractEnvironmentPostProcessor implements Ordered {

    private static final String[] prefix = new String[]{
            "file:config/datasource",
            "file:datasource",
            "classpath:config/datasource",
            "classpath:datasource",
            "classpath:META-INF/datasource",
    };

    private static final String[] suffix = new String[]{".conf"};

    public ExtendDataSourceHoconEnvironmentPostProcessor() {
        super(prefix, suffix, "carnival-extend-datasource", new HoconPropertySourceLoader());
    }

    @Override
    public int getOrder() {
        return 104;
    }

}
