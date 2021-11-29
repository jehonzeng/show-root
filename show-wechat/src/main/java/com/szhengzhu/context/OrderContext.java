package com.szhengzhu.context;

import java.util.Map;

import com.szhengzhu.config.BeanAware;
import com.szhengzhu.handler.AbstractOrder;

/**
 * @author Jehon Zeng
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class OrderContext {

    private Map<String, Class> handlerMap;

    public OrderContext(Map<String, Class> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public AbstractOrder getInstance(String type) {
        Class clazz = handlerMap.get(type);
        if (clazz == null) {
            throw new IllegalArgumentException("not found handler for type: " + type);
        }
        return (AbstractOrder) BeanAware.getBean(clazz);
    }
}
