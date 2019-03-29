package com.disanbo.component.redis.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * springboot 2.0
 *
 * @author chauncy
 * @date 2018/7/31
 */
@Component
public class RedisRepository {

    private final StringRedisTemplate stringRedisTemplate;
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisRepository(StringRedisTemplate stringRedisTemplate, RedisTemplate<String, Object> redisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 设置字符串缓存
     */
    public void setStr(String key, String val, long time, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key, val, time, timeUnit);
    }

    /**
     * 获取字符串
     */
    public String getStr(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 删除字符串
     */
    public void delStr(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 是否存在字符串
     */
    public Boolean existStr(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 设置对象
     */
    public void setObj(String key, Object val, long time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, val, time, timeUnit);
    }

    /**
     * 获取对象
     */
    public Object getObj(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除对象
     */
    public void delObj(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 是否存在对象
     */
    public Boolean existObj(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置集合
     */
    public void setSet(String key, String... val) {
        stringRedisTemplate.opsForSet().add(key, val);
    }

    /**
     * 获取集合
     */
    public Set<String> getSet(String key) {
        return stringRedisTemplate.opsForSet().members(key);
    }

    /**
     * 删除集合
     */
    public void delSet(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 集合中是否存在指定值
     */
    public Boolean existSet(String key, String val) {
        return stringRedisTemplate.opsForSet().isMember(key, val);
    }

}
