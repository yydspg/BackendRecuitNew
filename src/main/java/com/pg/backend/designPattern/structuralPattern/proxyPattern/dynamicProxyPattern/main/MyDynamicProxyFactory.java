package com.pg.backend.designPattern.structuralPattern.proxyPattern.dynamicProxyPattern.main;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;

/**
 * JDK 代理基于接口,本实例
 * @author paul 2024/3/6
 */
@Slf4j
public class MyDynamicProxyFactory {
    //考虑到的想法是 ,基于 Enum ,但是考虑到类数量,基于hashMap<K,V> 全类名存储
    private final HashMap<String,Object> proxiedObjs = new HashMap<>();

    public  Object  getProxy( Object  o) {
        return Proxy.newProxyInstance(o.getClass().getClassLoader(), o.getClass().getInterfaces(), (InvocationHandler) o);
    }
    public Object getProxy(String clzName) {
        if (clzName == null || clzName.isEmpty()) {
            log.info("paramsError");
            return null;
        }
        Object o = this.get(clzName);
        return Proxy.newProxyInstance(o.getClass().getClassLoader(), o.getClass().getInterfaces(), (InvocationHandler) o);
    }
    @SneakyThrows
    public synchronized <T extends InvocationHandler>  void  put(T o) {
        proxiedObjs.put(o.getClass().getName(),o);
    }
    private synchronized Object get(String name) {
        return proxiedObjs.get(name);
    }
}
