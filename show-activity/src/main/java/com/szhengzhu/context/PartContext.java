package com.szhengzhu.context;

import com.szhengzhu.config.BeanAware;
import com.szhengzhu.handler.AbstractPart;

import java.util.Map;

/**
 * @author Administrator
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class PartContext {
    
    private Map<String, Class> handlerMap;

    public PartContext(Map<String, Class> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public AbstractPart getInstance(String type) {
        Class clazz = handlerMap.get(type);
        if (clazz == null) {
            throw new IllegalArgumentException("not found handler for type: " + type);
        }
        return (AbstractPart) BeanAware.getBean(clazz);
    }
}
