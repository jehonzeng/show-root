package com.szhengzhu.context;

import com.szhengzhu.config.BeanAware;
import com.szhengzhu.handler.AbstractActivity;

import java.util.Map;

/**
 * @author Administrator
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class ActivityContext {
    
    private Map<String, Class> handlerMap;

    public ActivityContext(Map<String, Class> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public AbstractActivity getInstance(String type) {
        Class clazz = handlerMap.get(type);
        if (clazz == null) {
            throw new IllegalArgumentException("not found handler for type: " + type);
        }
        return (AbstractActivity) BeanAware.getBean(clazz);
    }
}
