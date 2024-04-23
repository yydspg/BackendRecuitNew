package com.pg.backend.common.util;

import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author paul 2024/4/23
 */

@Component
public class SpringContextKit implements ApplicationContextAware {
    @Getter
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext appContext) throws BeansException {
        applicationContext = appContext;
    }
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }
    public static <T> Map<String,T> getBeansOfType(Class<T> clazz) {return getApplicationContext().getBeansOfType(clazz);}
}
