package com.yuesheng.pm.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

public interface RedisService {
    /**
     * 是否存在key
     *
     * @param key 指定key
     * @return
     */
    boolean existsKey(String key);

    /**
     * 删除key
     *
     * @param key
     */
    void deleteKey(String key);

    /**
     * 设置key过期时间
     *
     * @param key      key
     * @param time     时间
     * @param timeUnit 时间单位
     */
    void expireKey(String key, long time, TimeUnit timeUnit);

    /**
     * 获取key过期时间
     *
     * @param key      指定key
     * @param timeUnit 时间单位
     * @return
     */
    long getKeyExpire(String key, TimeUnit timeUnit);

    /**
     * 设置key
     *
     * @param key
     * @param value
     */
    void setKey(String key, Object value);

    Object getValue(String key);

    /**
     * 获取所有值
     *
     * @param useKey key前缀
     * @return
     */
    List<Object> getAllValue(String useKey);
}
