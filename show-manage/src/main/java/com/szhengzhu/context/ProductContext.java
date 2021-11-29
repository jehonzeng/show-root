package com.szhengzhu.context;

import com.szhengzhu.config.BeanAware;
import com.szhengzhu.handler.AbstractProduct;

import java.util.Map;

/**
 * @author Administrator
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class ProductContext {

    private Map<String, Class> handlerMap;

    public ProductContext(Map<String, Class> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public AbstractProduct getInstance(String type) {
        Class clazz = handlerMap.get(type);
        if (clazz == null) {
            throw new IllegalArgumentException("not found handler for type: " + type);
        }
        return (AbstractProduct) BeanAware.getBean(clazz);
    }
}
