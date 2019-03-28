/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.json;

import java.util.HashMap;

/**
 * @author 应卓
 */
public class Payload extends HashMap<String, Object> {

    public static Payload newInstance() {
        return new Payload();
    }

    private static final long serialVersionUID = -4761981717718761013L;

    public Payload() {
        super();
    }

}
