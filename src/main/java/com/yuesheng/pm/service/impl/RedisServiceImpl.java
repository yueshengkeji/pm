package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service("redisService")
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean existsKey(String key) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        return redisTemplate.hasKey(key);
    }

    @Override
    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void expireKey(String key, long time, TimeUnit timeUnit) {
        redisTemplate.expire(key, time, timeUnit);
    }

    @Override
    public long getKeyExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }


    @Override
    public void setKey(String key, Object value) {
        ValueOperations<String, Object> redisOperations = redisTemplate.opsForValue();
        redisOperations.set(key, value);
    }

    @Override
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public List<Object> getAllValue(String useKey) {
        ArrayList list = new ArrayList();
        Set<String> key = redisTemplate.keys(useKey + "*");
        key.forEach(k -> {
            try {
                Object value = getValue(k);
                if (!Objects.isNull(value)) {
                    list.add(value);
                }
            } catch (Exception e) {
                LogManager.getLogger("mylog").error("redis getAllValue error:" + e.getMessage() + ",useKey = " + useKey + ",key=" + k);
            }
        });
        /*redisTemplate.execute((RedisCallback<Object>) connection -> {
            try (Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().count(Long.MAX_VALUE).match(useKey).build())) {
                cursor.forEachRemaining((key)->{
                    Object value = getValue(key.toString());
                    if(!Objects.isNull(value)){
                        list.add(value);
                    }
                });
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });*/
        return list;
    }
}
