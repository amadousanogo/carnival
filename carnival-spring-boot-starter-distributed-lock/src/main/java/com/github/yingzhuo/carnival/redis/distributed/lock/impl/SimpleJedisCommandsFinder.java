/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.redis.distributed.lock.impl;

import com.github.yingzhuo.carnival.redis.distributed.lock.JedisCommandsFinder;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

/**
 * @author 应卓
 */
public class SimpleJedisCommandsFinder implements JedisCommandsFinder {

    private final JedisPool jedisPool;
    private final JedisSentinelPool jedisSentinelPool;
    private final JedisCluster jedisCluster;

    public SimpleJedisCommandsFinder(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
        this.jedisCluster = null;
        this.jedisSentinelPool = null;
    }

    public SimpleJedisCommandsFinder(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
        this.jedisPool = null;
        this.jedisSentinelPool = null;
    }

    public SimpleJedisCommandsFinder(JedisSentinelPool jedisSentinelPool) {
        this.jedisPool = null;
        this.jedisSentinelPool = jedisSentinelPool;
        this.jedisCluster = null;
    }

    @Override
    public JedisCommands find() {
        if (jedisPool != null) {
            return jedisPool.getResource();
        } else if (jedisSentinelPool != null) {
            return jedisSentinelPool.getResource();
        } else {
            return jedisCluster;
        }
    }

}
