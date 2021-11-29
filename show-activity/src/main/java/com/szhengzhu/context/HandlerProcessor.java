package com.szhengzhu.context;

import com.google.common.collect.Maps;
import com.szhengzhu.annotation.ActivityType;
import com.szhengzhu.annotation.PartType;
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
        Map<String, Class> actMap = Maps.newHashMapWithExpectedSize(4);
        Map<String, Class> partMap = Maps.newHashMapWithExpectedSize(2);
        ClassScaner.scan(HANDLER_PACKAGE,ActivityType.class,PartType.class).forEach(clazz -> {
            if (clazz.isAnnotationPresent(ActivityType.class)) {
                // 获取注解中的类型值
                String type = clazz.getAnnotation(ActivityType.class).value();
                // 将注解中的类型值作为key，对应的类作为value，保存在Map中
                actMap.put(type, clazz);
            } else if(clazz.isAnnotationPresent(PartType.class)) {
                // 获取注解中的类型值
                String type = clazz.getAnnotation(PartType.class).value();
                // 将注解中的类型值作为key，对应的类作为value，保存在Map中
                partMap.put(type, clazz);
            }
        });
        // 初始化ProductContext，将其注册到spring容器中
        ActivityContext actContext = new ActivityContext(actMap);
        PartContext partContext = new PartContext(partMap);
        beanFactory.registerSingleton(ActivityContext.class.getName(), actContext);
        beanFactory.registerSingleton(PartContext.class.getName(), partContext);
    }

}
