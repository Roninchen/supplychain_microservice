package com.disanbo.component.common.entity;

import com.disanbo.component.common.util.JSON;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author wangtao
 * @date 2018/11/1 11:37
 */

public class JSONObject implements Map<String, Object> {

    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    private final Map<String, Object> map;

    public JSONObject() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public JSONObject(Map<String, Object> map) {
        this.map = map;
    }

    public JSONObject(int initialCapacity) {
        map = new HashMap<String, Object>(initialCapacity);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        Object val = map.get(key);

        if (val == null && key instanceof Number) {
            val = map.get(key.toString());
        }

        return val;
    }

    public String getString(String key) {
        Object value = get(key);

        if (value == null) {
            return null;
        }

        return value.toString();
    }

    public JSONObject getJSONObject(String key) throws Exception {
        Object value = map.get(key);

        if (value instanceof JSONObject) {
            return (JSONObject) value;
        }

        if (value instanceof String) {
            return JSON.parseObject((String) value);
        }

        return JSON.parseObject(JSON.toJSONString(value));
    }

    public JSONArray getJSONArray(String key) throws Exception {
        Object value = map.get(key);

        if (value instanceof JSONArray) {
            return (JSONArray) value;
        }

        if (value instanceof String) {
            return JSON.parseArray((String) value);
        }

        return JSON.parseArray(JSON.toJSONString(value));
    }

    @Override
    public Object put(String key, Object value) {
        map.put(key, value);
        return this;
    }

    @Override
    public Object remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<Object> values() {
        return map.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return map.entrySet();
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
