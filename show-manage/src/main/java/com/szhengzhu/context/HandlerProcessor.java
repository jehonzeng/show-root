package com.szhengzhu.context;

import com.google.common.collect.Maps;
import com.szhengzhu.annotation.OrderType;
import com.szhengzhu.annotation.ProductType;
import com.szhengzhu.util.ClassScaner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Administrator
 */
@Component
@SuppressWarnings("rawtypes")
public class HandlerProcessor implements BeanFactoryPostProcessor {

    private static final String HANDLER_PACKAGE = "com.szhengzhu.handler";
    
    /**
     * 扫描@ProductType,初始化HandlerContext,将其注册到spring容器中
     * 
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Map<String, Class> handlerMap = Maps.newHashMapWithExpectedSize(4);
        Map<String, Class> orderMap = Maps.newHashMapWithExpectedSize(3);
        ClassScaner.scan(HANDLER_PACKAGE, ProductType.class, OrderType.class).forEach(clazz -> {
            if (clazz.isAnnotationPresent(ProductType.class)) {
             // 获取注解中的类型值
                String type = clazz.getAnnotation(ProductType.class).value();
                // 将注解中的类型值作为key，对应的类作为value，保存在Map中
                handlerMap.put(type, clazz);
            } else if (clazz.isAnnotationPresent(OrderType.class)) {
             // 获取注解中的类型值
                String type = clazz.getAnnotation(OrderType.class).value();
                // 将注解中的类型值作为key，对应的类作为value，保存在Map中
                orderMap.put(type, clazz);
            }
        });
        // 初始化ProductContext，将其注册到spring容器中
        ProductContext context = new ProductContext(handlerMap);
        // 初始化ProductContext，将其注册到spring容器中
        OrderContext orderContext = new OrderContext(orderMap);
        
        beanFactory.registerSingleton(ProductContext.class.getName(), context);
        beanFactory.registerSingleton(OrderContext.class.getName(), orderContext);
    }

}
