package com.taobao.autopersistence.context;

import java.util.HashMap;
import java.util.Map;

/**
 * 类PersistenceContext.java的实现描述：
 * 上下文
 * @author zhanzui.ldh 2012-9-27 下午1:31:51
 */
public class PersistenceContext {

    private Map<String, Object> contextMap = new HashMap<String, Object>();

    public void put(String key, Object value) {
        contextMap.put(key, value);
    }
    
    public String getString(String key) {
        return (String)contextMap.get(key);
    }
    
    public Object get(String key) {
        return contextMap.get(key);
    }
}
