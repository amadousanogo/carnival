/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.curator.util;

import lombok.val;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

import java.nio.charset.StandardCharsets;

/**
 * @author 应卓
 * @since 1.3.3
 */
public final class NameUtils extends AbstractUtils {

    public static String getName(String path) {
        return getName(path, true);
    }

    public static String getName(String path, boolean removePath) {
        try {

            if (!path.startsWith("/")) {
                path = "/" + path;
            }

            CuratorFramework cli = getClient();

            val result = cli.create()
                    .creatingParentContainersIfNeeded()
                    .withMode(CreateMode.PERSISTENT_SEQUENTIAL)
                    .forPath(path);

            String name = new String(result.getBytes(StandardCharsets.UTF_8));
            return removePath ? name.substring(path.length()) : name;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    // ------------------------------------------------------------------------------------------------------------

    private NameUtils() {
    }

}
